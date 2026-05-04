package org.desktop.system.sgli.sgli.Dto;

import java.util.Date;

public class ContractDto {
    private String nameLocador;
    private String cpfCnpj;
    private Float valueBase;
    private Date dataInit;
    private Date dataEnd;

    public ContractDto(String nameLocador, String cpfCnpj, Float valueBase, Date dataInit, Date dataEnd) {
        this.nameLocador = nameLocador;
        this.cpfCnpj = cpfCnpj;
        this.valueBase = valueBase;
        this.dataInit = dataInit;
        this.dataEnd = dataEnd;
    }

    public ContractDto() {
    }

    public String getNameLocador() {
        return nameLocador;
    }

    public void setNameLocador(String nameLocador) {
        this.nameLocador = nameLocador;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public Float getValueBase() {
        return valueBase;
    }

    public void setValueBase(Float valueBase) {
        this.valueBase = valueBase;
    }

    public Date getDataInit() {
        return dataInit;
    }

    public void setDataInit(Date dataInit) {
        this.dataInit = dataInit;
    }

    public Date getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(Date dataEnd) {
        this.dataEnd = dataEnd;
    }
}

