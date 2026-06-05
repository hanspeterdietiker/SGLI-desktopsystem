package org.desktop.system.sgli.sgli.Services;

import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.PaymentModel;
import org.desktop.system.sgli.sgli.Repository.PaymentRepository;
import org.desktop.system.sgli.sgli.Utils.CpfUtils;
import org.desktop.system.sgli.sgli.Utils.LgpdAuditLogger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentModel save(ContractModel selectedContract, LocalDate monthRef, String valorBaseStr) {
        PaymentModel payment = validateAndCreate(selectedContract, monthRef, valorBaseStr);
        PaymentModel saved = paymentRepository.save(payment);
        LgpdAuditLogger.logCreate("Payment", CpfUtils.mask(saved.getContract().getCpfLocatario()));
        return saved;
    }

    public PaymentModel update(PaymentModel payment) {
        if (payment.getContract() == null) {
            throw new IllegalArgumentException("Selecione um contrato!");
        }
        if (payment.getMonthRef() == null) {
            throw new IllegalArgumentException("Selecione um Mês de Referência!");
        }
        if (payment.getValorBase() == null) {
            throw new IllegalArgumentException("Preencha o campo de Valor Base!");
        }
        return paymentRepository.update(payment);
    }

    public void delete(UUID id) {
        paymentRepository.delete(id);
        LgpdAuditLogger.logDelete("Payment", id.toString());
    }

    public void deleteByContractId(UUID contractId) {
        paymentRepository.deleteByContractId(contractId);
        LgpdAuditLogger.logDelete("Payment[byContract]", contractId.toString());
    }

    public List<PaymentModel> findAll() {
        return paymentRepository.findAll();
    }

    private static PaymentModel validateAndCreate(ContractModel selectedContract, LocalDate monthRef, String valorBaseStr) {
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
