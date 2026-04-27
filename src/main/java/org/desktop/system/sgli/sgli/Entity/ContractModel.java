package org.desktop.system.sgli.sgli.Entity;

import java.util.UUID;
import java.util.Date;

public class ContractModel {
    private UUID id;
    private String nameLocador;
    private String CPF_CNPJ;
    private Float valueBase;
    private Date dataInit;
    private Date dataEnd;

    public ContractModel(UUID id, String nameLocador, String CPF_CNPJ,
                         Date dataInit, Float valueBase, Date dataEnd) {
        this.id = id;
        this.nameLocador = nameLocador;
        this.CPF_CNPJ = CPF_CNPJ;
        this.dataInit = dataInit;
        this.valueBase = valueBase;
        this.dataEnd = dataEnd;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNameLocador() {
        return nameLocador;
    }

    public void setNameLocador(String nameLocador) {
        this.nameLocador = nameLocador;
    }

    public String getCPF_CNPJ() {
        return CPF_CNPJ;
    }

    public void setCPF_CNPJ(String CPF_CNPJ) {
        this.CPF_CNPJ = CPF_CNPJ;
    }

    public Date getDataInit() {
        return dataInit;
    }

    public void setDataInit(Date dataInit) {
        this.dataInit = dataInit;
    }

    public Float getValueBase() {
        return valueBase;
    }

    public void setValueBase(Float valueBase) {
        this.valueBase = valueBase;
    }

    public Date getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(Date dataEnd) {
        this.dataEnd = dataEnd;
    }
}