package org.desktop.system.sgli.sgli.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "tb_contracts")
public class ContractModel {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;


    private String nameLocador;
    private String nameLocatario;
    private String cpfCnpj;
    private BigDecimal valueBase;
    private LocalDate dateInit;
    private LocalDate dateEnd;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentModel> payments;

    public ContractModel(UUID id, String nameLocador, String nameLocatario, String cpfCnpj,
                         LocalDate dataInit, BigDecimal valueBase, LocalDate dataEnd) {
        this.id = id;
        this.nameLocador = nameLocador;
        this.nameLocatario = nameLocatario;
        this.cpfCnpj = cpfCnpj;
        this.dateInit = dataInit;
        this.valueBase = valueBase;
        this.dateEnd = dataEnd;
    }

    public ContractModel() {
    }

    public String getNameLocatario() {
        return nameLocatario;
    }

    public void setNameLocatario(String nameLocatario) {
        this.nameLocatario = nameLocatario;
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

    public LocalDate getDateInit() {
        return dateInit;
    }

    public void setDateInit(LocalDate dataInit) {
        this.dateInit = dataInit;
    }

    public BigDecimal getValueBase() {
        return valueBase;
    }

    public void setValueBase(BigDecimal valueBase) {
        this.valueBase = valueBase;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dataEnd) {
        this.dateEnd = dataEnd;
    }

    public List<PaymentModel> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentModel> payments) {
        this.payments = payments;
    }
}