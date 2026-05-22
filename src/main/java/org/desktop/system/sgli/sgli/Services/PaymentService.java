package org.desktop.system.sgli.sgli.Services;

import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.PaymentModel;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentService {

    private PaymentService() {
        throw new UnsupportedOperationException("Classe de serviço não deve ser instanciada");
    }

    public static PaymentModel validateAndCreate(ContractModel selectedContract, LocalDate monthRef, String valorBaseStr) {

        if (selectedContract == null) {
            throw new IllegalArgumentException("Selecione um contrato!");
        }
        if (monthRef == null) {
            throw new IllegalArgumentException("Selecione um Mês de Referência!");
        }
        if (valorBaseStr == null || valorBaseStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha o campo de Valor Base!");
        }


        BigDecimal valorBase;
        try {
            valorBase = new BigDecimal(valorBaseStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor base inválido! Digite um número válido.");
        }

        return new PaymentModel(null, selectedContract, monthRef, valorBase);
    }
}
