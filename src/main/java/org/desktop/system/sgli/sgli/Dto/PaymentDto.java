package org.desktop.system.sgli.sgli.Dto;

import java.util.Date;

public class PaymentDto {
    private String contractName;
    private Date monthRef;
    private Date datePayment;
    private Float valorAlug;
    private Float valorIptu;
    private Float valorCond;

    public PaymentDto(String contractName, Date monthRef, Date datePayment, Float valorAlug, Float valorIptu, Float valorCond) {
        this.contractName = contractName;
        this.monthRef = monthRef;
        this.datePayment = datePayment;
        this.valorAlug = valorAlug;
        this.valorIptu = valorIptu;
        this.valorCond = valorCond;
    }

    public PaymentDto() {
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Date getMonthRef() {
        return monthRef;
    }

    public void setMonthRef(Date monthRef) {
        this.monthRef = monthRef;
    }

    public Date getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(Date datePayment) {
        this.datePayment = datePayment;
    }

    public Float getValorAlug() {
        return valorAlug;
    }

    public void setValorAlug(Float valorAlug) {
        this.valorAlug = valorAlug;
    }

    public Float getValorIptu() {
        return valorIptu;
    }

    public void setValorIptu(Float valorIptu) {
        this.valorIptu = valorIptu;
    }

    public Float getValorCond() {
        return valorCond;
    }

    public void setValorCond(Float valorCond) {
        this.valorCond = valorCond;
    }
}

