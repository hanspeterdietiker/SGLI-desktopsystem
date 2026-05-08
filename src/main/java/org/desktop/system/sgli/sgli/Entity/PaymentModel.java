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

    private BigDecimal valorAlug;
    private BigDecimal valorIptu;
    private BigDecimal valorCond;

    public PaymentModel(UUID id, BigDecimal valorCond, BigDecimal valorIptu,
                        BigDecimal valorAlug,  LocalDate monthRef, ContractModel contract) {
        this.id = id;
        this.valorCond = valorCond;
        this.valorIptu = valorIptu;
        this.valorAlug = valorAlug;
        this.monthRef = monthRef;
        this.contract = contract;
    }
    public PaymentModel() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getValorCond() {
        return valorCond;
    }

    public void setValorCond(BigDecimal valorCond) {
        this.valorCond = valorCond;
    }

    public BigDecimal getValorIptu() {
        return valorIptu;
    }

    public void setValorIptu(BigDecimal valorIptu) {
        this.valorIptu = valorIptu;
    }

    public BigDecimal getValorAlug() {
        return valorAlug;
    }

    public void setValorAlug(BigDecimal valorAlug) {
        this.valorAlug = valorAlug;
    }

    public LocalDate getMonthRef() {
        return monthRef;
    }

    public void setMonthRef(LocalDate monthRef) {
        this.monthRef = monthRef;
    }

    public ContractModel getContract() {
        return contract;
    }

    public void setContract(ContractModel contract) {
        this.contract = contract;
    }

}
