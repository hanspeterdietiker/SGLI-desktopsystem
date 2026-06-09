package org.desktop.system.sgli.sgli.Services;

import org.desktop.system.sgli.sgli.Dto.ContractDto;
import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.Enum.ContractTypeEnum;
import org.desktop.system.sgli.sgli.Repository.ContractRepository;
import org.desktop.system.sgli.sgli.Utils.CpfUtils;
import org.desktop.system.sgli.sgli.Utils.LgpdAuditLoggerUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ContractService {

    public record ContractPage(List<ContractModel> contracts, int totalPages, int currentPage) {
    }

    private static final int PAGE_SIZE = 6;

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public ContractModel save(ContractDto contractDto) {

        ContractModel contract = new ContractModel();
        contract.setNameLocador(contractDto.nameLocador());
        contract.setNameLocatario(contractDto.nameLocatario());
        contract.setCpfLocatario(contractDto.cpfLocatario());
        contract.setCpfLocador(contractDto.cpfLocador());
        contract.setDateInit(contractDto.dateInit());
        contract.setDateEnd(contractDto.dateEnd());
        contract.setContractType(contractDto.contractType());

        validateForCreate(contract, contractDto.valorAlugStr(), contractDto.valorIptuStr(), contractDto.valorCondStr());

        if (contractRepository.existsByNameLocatario(contract.getNameLocatario())) {
            throw new IllegalArgumentException(
                    "Já existe um contrato para este locatário.\n" +
                            "Se necessário, edite ou exclua o contrato existente.");
        }

        if (contractRepository.existsByCpfLocatario(contract.getCpfLocatario())) {
            throw new IllegalArgumentException(
                    "Já existe um contrato com este CPF de locatário.\n" +
                            "Se necessário, edite ou exclua o contrato existente.");
        }

        ContractModel saved = contractRepository.save(contract);
        LgpdAuditLoggerUtils.logCreate("Contract", CpfUtils.mask(saved.getCpfLocatario()));
        return saved;
    }

    public ContractModel update(UUID id, ContractDto contractDto) {
        ContractModel contract = new ContractModel();
        contract.setId(id);
        contract.setNameLocador(contractDto.nameLocador());
        contract.setNameLocatario(contractDto.nameLocatario());
        contract.setCpfLocatario(contractDto.cpfLocatario());
        contract.setCpfLocador(contractDto.cpfLocador());
        contract.setDateInit(contractDto.dateInit());
        contract.setDateEnd(contractDto.dateEnd());
        contract.setContractType(contractDto.contractType());

        validateForCreate(contract, contractDto.valorAlugStr(), contractDto.valorIptuStr(), contractDto.valorCondStr());

        if (contractRepository.existsByNameLocatarioAndId(contract.getNameLocatario(), contract.getId())) {
            throw new IllegalArgumentException(
                    "Já existe um contrato para este locatário.\n" +
                            "Se necessário, edite ou exclua o contrato existente.");
        }

        if (contractRepository.existsByCpfLocatarioAndId(contract.getCpfLocatario(), contract.getId())) {
            throw new IllegalArgumentException(
                    "Já existe um contrato com este CPF de locatário.\n" +
                            "Se necessário, edite ou exclua o contrato existente.");
        }

        ContractModel updated = contractRepository.update(contract);
        LgpdAuditLoggerUtils.logUpdate("Contract", CpfUtils.mask(updated.getCpfLocatario()));
        return  updated;
    }

    public void delete(UUID id) {
        contractRepository.delete(id);
        LgpdAuditLoggerUtils.logDelete("Contract", id.toString());
    }

    public List<ContractModel> findAll() {
        return contractRepository.findAll();
    }

    public ContractPage findPage(int pageNumber) {
        long total = contractRepository.countAll();
        int totalPages = totalPages(total);
        int safePage = Math.min(pageNumber, totalPages - 1);
        List<ContractModel> contracts = contractRepository.findByPag(PAGE_SIZE, safePage * PAGE_SIZE);
        return new ContractPage(contracts, totalPages, safePage);
    }


    public static ContractTypeEnum resolveContractType(String type) {
        return switch (type) {
            case "FIADOR" -> ContractTypeEnum.FIADOR;
            case "CAUCAO" -> ContractTypeEnum.CAUCAO;
            default -> ContractTypeEnum.NO_INFORM;
        };
    }



    private static int totalPages(long total) {
        return (int) Math.max(1, (total + PAGE_SIZE - 1) / PAGE_SIZE);
    }

    private static void validateForCreate(ContractModel contract,
                                          String valorAlugStr, String valorIptuStr, String valorCondStr) {

        if (contract.getDateInit() == null || contract.getDateEnd() == null) {
            throw new IllegalArgumentException("Selecione as datas de início e fim do contrato!");
        }
        if (contract.getNameLocador() == null || contract.getNameLocador().isBlank()
                || contract.getNameLocatario() == null || contract.getNameLocatario().isBlank()) {
            throw new IllegalArgumentException("Preencha os campos de nome do locador e locatário!");
        }
        if (!CpfUtils.isFormatValid(contract.getCpfLocatario())) {
            throw new IllegalArgumentException("CPF do Locatário inválido! Use o formato 000.000.000-00.");
        }
        if (!CpfUtils.isFormatValid(contract.getCpfLocador())) {
            throw new IllegalArgumentException("CPF do Locador inválido! Use o formato 000.000.000-00.");
        }
        if (contract.getContractType() == null) {
            throw new IllegalArgumentException("Selecione um tipo de contrato!");
        }

        try {
            contract.setValorAlug(new BigDecimal(valorAlugStr.trim()));
            contract.setValorIptu(new BigDecimal(valorIptuStr.trim()));
            contract.setValorCond(new BigDecimal(valorCondStr.trim()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Valor numérico inválido:\n Digite apenas números válidos para aluguel, IPTU e condomínio.");
        }
    }
}
