package org.desktop.system.sgli.sgli.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_payments")
public class PaymentModel {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false)
    private ContractModel contract;


    private LocalDate monthRef;

    private BigDecimal valorBase;


    public PaymentModel(UUID id, ContractModel contract, LocalDate monthRef, BigDecimal valorBase) {
        this.id = id;
        this.contract = contract;
        this.monthRef = monthRef;
        this.valorBase = valorBase;
    }

    public PaymentModel() {
    }

    public LocalDate getMonthRef() {
        return monthRef;
    }

    public void setMonthRef(LocalDate monthRef) {
        this.monthRef = monthRef;
    }

    public BigDecimal getValorBase() {
        return valorBase;
    }

    public void setValorBase(BigDecimal valorBase) {
        this.valorBase = valorBase;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ContractModel getContract() {
        return contract;
    }

    public void setContract(ContractModel contract) {
        this.contract = contract;
    }

}

