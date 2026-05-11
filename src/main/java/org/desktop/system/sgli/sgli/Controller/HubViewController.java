package org.desktop.system.sgli.sgli.Controller;

import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.PaymentModel;
import org.desktop.system.sgli.sgli.Utils.AlertAction;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;


import com.itextpdf.text.Document;

public class HubViewController {

    // Contract
    @FXML
    private TextField nameLocadorField;
    @FXML
    private TextField nameLocatarioField;
    @FXML
    private TextField cpfCnpjField;
    @FXML
    private TextField valorAlugField;
    @FXML
    private TextField valorIptuField;
    @FXML
    private TextField valorCondField;
    @FXML
    private DatePicker dateInitPicker;
    @FXML
    private DatePicker dateEndPicker;

    // Payment
    @FXML
    private ComboBox<ContractModel> contractComboBox;
    @FXML
    private DatePicker monthRefPicker;
    @FXML
    private TextField valorBaseField;

    // Table View
    @FXML
    private TableView<ContractModel> contractsTable;
    @FXML
    private TableView<PaymentModel> paymentsTable;


    //List Contract and Payment
    private ObservableList<ContractModel> contractsList = FXCollections.observableArrayList();

    private ObservableList<PaymentModel> paymentsList = FXCollections.observableArrayList();


    @FXML
    public void initialize() {

        contractsTable.setItems(contractsList);
        paymentsTable.setItems(paymentsList);
        ;

        contractComboBox.setItems(contractsList);
        contractComboBox.setConverter(new javafx.util.StringConverter<ContractModel>() {
            @Override
            public String toString(ContractModel contract) {
                return contract != null ? contract.getNameLocatario() : "";
            }

            @Override
            public ContractModel fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    private void saveContract() {
        try {
            String nameLocador = nameLocadorField.getText();
            String nameLocatario = nameLocatarioField.getText();
            String cpfCnpj = cpfCnpjField.getText();
            BigDecimal valorAlug = new BigDecimal(valorAlugField.getText());
            BigDecimal valorIptu = new BigDecimal(valorIptuField.getText());
            BigDecimal valorCond = new BigDecimal(valorCondField.getText());
            LocalDate dateInitLocal = dateInitPicker.getValue();
            LocalDate dateEndLocal = dateEndPicker.getValue();

            if (dateInitLocal == null || dateEndLocal == null) {
                AlertAction.showAlert("Erro", "Selecione as datas de início e fim do contrato!");
                return;
            }

            ContractModel contract = new ContractModel(null, nameLocador, nameLocatario,
                    cpfCnpj, valorAlug, valorIptu, valorCond, dateInitLocal, dateEndLocal);
            contractsList.add(contract);
            contractComboBox.setItems(contractsList);


            AlertAction.showAlert("Sucesso", "Contrato salvo com sucesso!");
            clearFieldContract();
        } catch (Exception e) {
            AlertAction.showAlert("Erro", "Erro ao salvar contrato: " + e.getMessage());
        }

    }

    @FXML
    private void savePayment() {
        try {
            ContractModel selectedContract = contractComboBox.getValue();
            if (selectedContract == null) {
                AlertAction.showAlert("Erro", "Selecione um contrato!");
                return;
            }

            LocalDate monthRefLocal = monthRefPicker.getValue();
            BigDecimal valorBase = new BigDecimal(valorBaseField.getText());


            PaymentModel payment = new PaymentModel(null, selectedContract, monthRefLocal, valorBase);
            paymentsList.add(payment);


            AlertAction.showAlert("Sucesso", "Pagamento salvo com sucesso!");
            clearFieldPayment();
        } catch (Exception e) {
            AlertAction.showAlert("Erro", "Erro ao salvar pagamento: " + e.getMessage());
        }
    }

    @FXML
    private void exportReportContract() {
        Document doc = new Document();
        try {

            String downloadsPath = System.getProperty("user.home") + "\\Downloads\\Relatorio_Contratos.pdf";

            PdfWriter.getInstance(doc, new java.io.FileOutputStream(downloadsPath));
            doc.open();


            com.itextpdf.text.Paragraph title = new com.itextpdf.text.Paragraph("Relatório de Contratos");
            title.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(new com.itextpdf.text.Paragraph("\n"));


            if (contractsList.isEmpty()) {
                doc.add(new com.itextpdf.text.Paragraph("Nenhum contrato registrado."));
            } else {
                for (ContractModel contract : contractsList) {
                    doc.add(new com.itextpdf.text.Paragraph("Nome Locador: " + contract.getNameLocador()));
                    doc.add(new com.itextpdf.text.Paragraph("Nome Locatario: " + contract.getNameLocatario()));
                    doc.add(new com.itextpdf.text.Paragraph("CPF/CNPJ: " + contract.getCpfCnpj()));
                    doc.add(new com.itextpdf.text.Paragraph("Valor Condominio R$ " + contract.getValorCond()));
                    doc.add(new com.itextpdf.text.Paragraph("Valor Aluguel R$ " + contract.getValorAlug()));
                    doc.add(new com.itextpdf.text.Paragraph("Valor Iptu R$ " + contract.getValorIptu()));
                    doc.add(new com.itextpdf.text.Paragraph("Data Início: " + contract.getDateInit()));
                    doc.add(new com.itextpdf.text.Paragraph("Data Fim: " + contract.getDateEnd()));
                    doc.add(new com.itextpdf.text.Paragraph("\n"));
                }
            }

            doc.close();
            AlertAction.showAlert("Sucesso", "PDF salvo em: " + downloadsPath);
        } catch (Exception e) {
            AlertAction.showAlert("Erro", "Erro ao exportar relatório de Contratos: " + e.getMessage());
            if (doc.isOpen()) {
                doc.close();
            }
        }
    }

    @FXML
    private void refreshListContract() {
        try {

            contractsTable.refresh();
            AlertAction.showAlert("Info", "Lista de contratos atualizada!");
        } catch (Exception e) {
            AlertAction.showAlert("Erro", "Erro ao atualizar contrato: " + e.getMessage());
        }
    }

    private void clearFieldContract() {
        nameLocadorField.clear();
        nameLocatarioField.clear();
        cpfCnpjField.clear();
        valorAlugField.clear();
        valorIptuField.clear();
        valorCondField.clear();
        dateInitPicker.setValue(null);
        dateEndPicker.setValue(null);
    }

    @FXML
    private void refreshListPayment() {
        try {

            paymentsTable.refresh();
            AlertAction.showAlert("Info", "Lista de pagamentos atualizada!");
        } catch (Exception e) {
            AlertAction.showAlert("Erro", "Erro ao atualizar pagamento: " + e.getMessage());
        }
    }

    @FXML
    private void exportReportPayment() {
        Document doc = new Document();
        try {

            String downloadsPath = System.getProperty("user.home") + "\\Downloads\\Relatorio_Pagamentos.pdf";

            PdfWriter.getInstance(doc, new FileOutputStream(downloadsPath));
            doc.open();


            Paragraph title = new Paragraph("Relatório de Pagamentos");
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(new Paragraph("\n"));


            if (paymentsList.isEmpty()) {
                doc.add(new Paragraph("Nenhum Pagamento registrado."));
            } else {
                for (PaymentModel payment : paymentsList) {
                    doc.add(new Paragraph("Locatario" + payment.getContract().getNameLocatario())); // Fixing Object to String
                    doc.add(new Paragraph("Mes de Referencia " + payment.getMonthRef()));
                    doc.add(new Paragraph("Valor Base R$ " + payment.getValorBase()));
                    doc.add(new Paragraph("\n"));
                }
            }

            doc.close();
            AlertAction.showAlert("Sucesso", "PDF salvo em: " + downloadsPath);
        } catch (Exception e) {
            AlertAction.showAlert("Erro", "Erro ao exportar relatório de Pagamentos: " + e.getMessage());
            if (doc.isOpen()) {
                doc.close();
            }
        }
    }


    private void clearFieldPayment() {
        contractComboBox.setValue(null);
        monthRefPicker.setValue(null);
        valorBaseField.clear();
    }


}