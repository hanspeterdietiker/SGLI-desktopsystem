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

    private final ContractRepository contractRepository;


    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public ContractModel save(
            String nameLocador, String nameLocatario, String cpfLocatario, String cpfLocador,
            String valorAlugStr, String valorIptuStr, String valorCondStr,
            LocalDate dateInit, LocalDate dateEnd, ContractTypeEnum contractType) {

        ContractModel contract = validateContractField(
                nameLocador, nameLocatario, cpfLocatario, cpfLocador,
                valorAlugStr, valorIptuStr, valorCondStr, dateInit, dateEnd, contractType);

        if (contractRepository.existsByNameLocatario(nameLocatario)) {
            throw new IllegalArgumentException(
                    "Já existe um contrato com o Locatario: \"" + nameLocatario.trim() + "\".\n" +
                            "Se necessário, edite ou exclua o contrato existente.");
        }

        if (contractRepository.existsByCpfLocatario(cpfLocatario)) {
            throw new IllegalArgumentException(
                    "Ja existe um contrato com o numero de CPF:\"" + cpfLocatario.trim() + "\".\n" +
                            "Se necessário, edite ou exclua o contrato existente."

            );
        }

        return contractRepository.save(contract);
    }

    public ContractModel update(ContractModel contract) {
        if (contract.getDateInit() == null || contract.getDateEnd() == null) {
            throw new IllegalArgumentException("Selecione as datas de início e fim do contrato!");
        }
        if (isBlank(contract.getNameLocador()) || isBlank(contract.getNameLocatario())) {
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
        return contractRepository.update(contract);
    }


    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public void delete(UUID id) {
        contractRepository.delete(id);
    }

    public List<ContractModel> findAll() {
        return contractRepository.findAll();
    }

    public static ContractTypeEnum resolveContractType(String type) {
        return switch (type) {
            case "FIADOR" -> ContractTypeEnum.FIADOR;
            case "NO_INFORM" -> ContractTypeEnum.NO_INFORM;
            default -> ContractTypeEnum.CAUCAO;
        };
    }


    private static ContractModel validateContractField(
            String nameLocador, String nameLocatario, String cpfLocatario, String cpfLocador,
            String valorAlugStr, String valorIptuStr, String valorCondStr,
            LocalDate dateInit, LocalDate dateEnd, ContractTypeEnum contractType) {

        if (dateInit == null || dateEnd == null) {
            throw new IllegalArgumentException("Selecione as datas de início e fim do contrato!");
        }
        if (nameLocador == null || nameLocador.trim().isEmpty() ||
                nameLocatario == null || nameLocatario.trim().isEmpty()) {
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
            throw new IllegalArgumentException("Valor numérico inválido:\n " +
                    "Digite apenas números válidos para aluguel, IPTU e condomínio.");
        }

        return new ContractModel(null, nameLocador.trim(), nameLocatario.trim(),
                cpfLocatario.trim(), cpfLocador.trim(), valorAlug, valorIptu, valorCond, dateInit, dateEnd, contractType);
    }


}
