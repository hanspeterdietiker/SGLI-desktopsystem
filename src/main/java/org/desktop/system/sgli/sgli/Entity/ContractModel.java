package org.desktop.system.sgli.sgli.Entity;

import jakarta.persistence.*;

import java.util.UUID;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "tb_contracts")
public class ContractModel {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    private String nameLocador;
    private String cpfCnpj;
    private Float valueBase;
    private Date dataInit;
    private Date dataEnd;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentModel> payments;

    public ContractModel(UUID id, String nameLocador, String cpfCnpj,
                         Date dataInit, Float valueBase, Date dataEnd) {
        this.id = id;
        this.nameLocador = nameLocador;
        this.cpfCnpj = cpfCnpj;
        this.dataInit = dataInit;
        this.valueBase = valueBase;
        this.dataEnd = dataEnd;
    }

    public ContractModel() {
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

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
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

    public List<PaymentModel> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentModel> payments) {
        this.payments = payments;
    }
}