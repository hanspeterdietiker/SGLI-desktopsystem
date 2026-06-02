package org.desktop.system.sgli.sgli.Entity;

import jakarta.persistence.*;
import org.desktop.system.sgli.sgli.Entity.Enum.ContractTypeEnum;

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
    private String cpfLocatario;
    private String cpfLocador;
    private BigDecimal valorAlug;
    private BigDecimal valorIptu;
    private BigDecimal valorCond;
    private LocalDate dateInit;
    private LocalDate dateEnd;


    @Enumerated(value = EnumType.STRING)
    private ContractTypeEnum contractType;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentModel> payments;

    public ContractModel(UUID id, String nameLocador, String nameLocatario, String cpfLocatario, String cpfLocador, BigDecimal valorAlug,
                         BigDecimal valorIptu, BigDecimal valorCond,
                         LocalDate dateInit, LocalDate dateEnd, ContractTypeEnum contractType) {
        this.id = id;
        this.nameLocador = nameLocador;
        this.nameLocatario = nameLocatario;
        this.cpfLocatario = cpfLocatario;
        this.cpfLocador = cpfLocador;
        this.valorAlug = valorAlug;
        this.valorIptu = valorIptu;
        this.valorCond = valorCond;
        this.dateInit = dateInit;
        this.dateEnd = dateEnd;
        this.contractType = contractType;
    }
    public ContractModel() {}

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

    public String getNameLocatario() {
        return nameLocatario;
    }

    public void setNameLocatario(String nameLocatario) {
        this.nameLocatario = nameLocatario;
    }

    public String getCpfLocatario() {
        return cpfLocatario;
    }

    public void setCpfLocatario(String cpfLocatario) {
        this.cpfLocatario = cpfLocatario;
    }

    public String getCpfLocador() {
        return cpfLocador;
    }

    public void setCpfLocador(String cpfLocador) {
        this.cpfLocador = cpfLocador;
    }

    public BigDecimal getValorAlug() {
        return valorAlug;
    }

    public void setValorAlug(BigDecimal valorAlug) {
        this.valorAlug = valorAlug;
    }

    public BigDecimal getValorIptu() {
        return valorIptu;
    }

    public void setValorIptu(BigDecimal valorIptu) {
        this.valorIptu = valorIptu;
    }

    public BigDecimal getValorCond() {
        return valorCond;
    }

    public void setValorCond(BigDecimal valorCond) {
        this.valorCond = valorCond;
    }

    public LocalDate getDateInit() {
        return dateInit;
    }

    public void setDateInit(LocalDate dateInit) {
        this.dateInit = dateInit;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }
    public ContractTypeEnum getContractType() {
        return contractType;
    }

    public void setContractType(ContractTypeEnum contractType) {
        this.contractType = contractType;
    }

    public Enum getTypeContract() {
        return contractType;
    }
}
