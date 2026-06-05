package org.desktop.system.sgli.sgli.Services;

import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.Enum.ContractTypeEnum;
import org.desktop.system.sgli.sgli.Repository.ContractRepository;
import org.desktop.system.sgli.sgli.Utils.CpfUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ContractService {

    public record ContractPage(List<ContractModel> contracts, int totalPages, int currentPage) {
    }

    private static final int PAGE_SIZE = 5;

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public ContractModel save(
            String nameLocador, String nameLocatario, String cpfLocatario, String cpfLocador,
            String valorAlugStr, String valorIptuStr, String valorCondStr,
            LocalDate dateInit, LocalDate dateEnd, ContractTypeEnum contractType) {

        ContractModel contract = buildContract(
                nameLocador, nameLocatario, cpfLocatario, cpfLocador,
                valorAlugStr, valorIptuStr, valorCondStr, dateInit, dateEnd, contractType);

        if (contractRepository.existsByNameLocatario(nameLocatario)) {
            throw new IllegalArgumentException(
                    "Já existe um contrato para este locatário.\n" +
                            "Se necessário, edite ou exclua o contrato existente.");
        }

        if (contractRepository.existsByCpfLocatario(cpfLocatario)) {
            throw new IllegalArgumentException(
                    "Já existe um contrato com este CPF de locatário.\n" +
                            "Se necessário, edite ou exclua o contrato existente.");
        }

        return contractRepository.save(contract);
    }

    public ContractModel update(ContractModel contract) {
        validateForUpdate(contract);
        return contractRepository.update(contract);
    }

    public void delete(UUID id) {
        contractRepository.delete(id);
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

    private static void validateForUpdate(ContractModel contract) {
        if (contract.getDateInit() == null || contract.getDateEnd() == null) {
            throw new IllegalArgumentException("Selecione as datas de início e fim do contrato!");
        }
        if (contract.getNameLocador().isBlank() || contract.getNameLocatario().isBlank()) {
            throw new IllegalArgumentException("Preencha os campos de nome do locador e locatário!");
        }
        if (!CpfUtils.isFormatValid(contract.getCpfLocatario())) {
            throw new IllegalArgumentException("CPF do Locatário inválido! Use o formato 000.000.000-00.");
        }
        if (!CpfUtils.isFormatValid(contract.getCpfLocador())) {
            throw new IllegalArgumentException("CPF do Locador inválido! Use o formato 000.000.000-00.");
        }
        if (contract.getValorAlug() == null || contract.getValorIptu() == null || contract.getValorCond() == null) {
            throw new IllegalArgumentException("Valor numérico inválido!");
        }
        if (contract.getContractType() == null) {
            throw new IllegalArgumentException("Selecione um tipo de contrato!");
        }
    }

    private static int totalPages(long total) {
        return (int) Math.max(1, (total + PAGE_SIZE - 1) / PAGE_SIZE);
    }

    private static ContractModel buildContract(
            String nameLocador, String nameLocatario, String cpfLocatario, String cpfLocador,
            String valorAlugStr, String valorIptuStr, String valorCondStr,
            LocalDate dateInit, LocalDate dateEnd, ContractTypeEnum contractType) {

        if (dateInit == null || dateEnd == null) {
            throw new IllegalArgumentException("Selecione as datas de início e fim do contrato!");
        }
        if (nameLocador == null || nameLocador.isBlank() || nameLocatario == null || nameLocatario.isBlank()) {
            throw new IllegalArgumentException("Preencha os campos de nome do locador e locatário!");
        }
        if (!CpfUtils.isFormatValid(cpfLocatario)) {
            throw new IllegalArgumentException("CPF do Locatário inválido! Use o formato 000.000.000-00.");
        }
        if (!CpfUtils.isFormatValid(cpfLocador)) {
            throw new IllegalArgumentException("CPF do Locador inválido! Use o formato 000.000.000-00.");
        }
        if (contractType == null) {
            throw new IllegalArgumentException("Selecione um tipo de contrato!");
        }

        BigDecimal valorAlug;
        BigDecimal valorIptu;
        BigDecimal valorCond;

        try {
            valorAlug = new BigDecimal(valorAlugStr.trim());
            valorIptu = new BigDecimal(valorIptuStr.trim());
            valorCond = new BigDecimal(valorCondStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Valor numérico inválido:\n Digite apenas números válidos para aluguel, IPTU e condomínio.");
        }

        return new ContractModel(null, nameLocador.trim(), nameLocatario.trim(),
                cpfLocatario.trim(), cpfLocador.trim(), valorAlug, valorIptu, valorCond,
                dateInit, dateEnd, contractType);
    }
}
