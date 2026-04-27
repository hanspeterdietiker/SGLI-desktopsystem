package org.desktop.system.sgli.sgli.Entity;

import jakarta.persistence.*;

import java.util.Date;
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

    private Date monthRef;
    private Date datePayment;
    private Float valorAlug;
    private Float valorIptu;
    private Float valorCond;

    public PaymentModel(UUID id, Float valorCond, Float valorIptu,
                        Float valorAlug, Date datePayment, Date monthRef, ContractModel contract) {
        this.id = id;
        this.valorCond = valorCond;
        this.valorIptu = valorIptu;
        this.valorAlug = valorAlug;
        this.datePayment = datePayment;
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

    public Float getValorCond() {
        return valorCond;
    }

    public void setValorCond(Float valorCond) {
        this.valorCond = valorCond;
    }

    public Float getValorIptu() {
        return valorIptu;
    }

    public void setValorIptu(Float valorIptu) {
        this.valorIptu = valorIptu;
    }

    public Float getValorAlug() {
        return valorAlug;
    }

    public void setValorAlug(Float valorAlug) {
        this.valorAlug = valorAlug;
    }

    public Date getMonthRef() {
        return monthRef;
    }

    public void setMonthRef(Date monthRef) {
        this.monthRef = monthRef;
    }

    public ContractModel getContract() {
        return contract;
    }

    public void setContract(ContractModel contract) {
        this.contract = contract;
    }

    public Date getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(Date datePayment) {
        this.datePayment = datePayment;
    }
}
