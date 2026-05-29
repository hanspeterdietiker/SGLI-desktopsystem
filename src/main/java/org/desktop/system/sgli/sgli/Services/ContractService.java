package org.desktop.system.sgli.sgli.Services;

import org.desktop.system.sgli.sgli.Entity.ContractModel;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractService {

    private ContractService() {
        throw new UnsupportedOperationException("Classe de serviço não deve ser instanciada");
    }


    public static ContractModel validateAndCreate(
            String nameLocador, String nameLocatario, String cpfLocatario, String cpfLocador,
            String valorAlugStr, String valorIptuStr, String valorCondStr,
            LocalDate dateInit, LocalDate dateEnd) {

        if (dateInit == null || dateEnd == null) {
            throw new IllegalArgumentException("Selecione as datas de início e fim do contrato!");
        }
        if (nameLocador == null || nameLocador.trim().isEmpty() ||
                nameLocatario == null || nameLocatario.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha os campos de nome do locador e locatário!");
        }
        if (cpfLocatario == null || cpfLocatario.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha o CPF do Locatário!");
        }
        if (cpfLocador == null || cpfLocador.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha o CPF do Locador!");
        }

        BigDecimal valorAlug;
        BigDecimal valorIptu;
        BigDecimal valorCond;

        try {
            valorAlug = new BigDecimal(valorAlugStr.trim());
            valorIptu = new BigDecimal(valorIptuStr.trim());
            valorCond = new BigDecimal(valorCondStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor numérico inválido! Digite apenas números válidos para aluguel, IPTU e condomínio.");
        }
        return new ContractModel(null, nameLocador.trim(), nameLocatario.trim(), cpfLocatario.trim(), cpfLocador.trim(), valorAlug, valorIptu, valorCond, dateInit, dateEnd);
    }
}
