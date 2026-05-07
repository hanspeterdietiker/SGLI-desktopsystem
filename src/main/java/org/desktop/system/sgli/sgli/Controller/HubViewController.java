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

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;


import com.itextpdf.text.Document;
import org.desktop.system.sgli.sgli.Exceptions.AlertException;

public class HubViewController {

    // Contract
    @FXML
    private TextField nameLocadorField;
    @FXML
    private TextField nameLocatarioField;
    @FXML
    private TextField cpfCnpjField;
    @FXML
    private TextField valueBaseField;
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
    private TextField valorAlugField;
    @FXML
    private TextField valorIptuField;
    @FXML
    private TextField valorCondField;

    @FXML
    private TableView<ContractModel> contractsTable;
    @FXML
    private TableView<PaymentModel> paymentsTable;

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
            BigDecimal valueBase = new BigDecimal(valueBaseField.getText());
            LocalDate dateInitLocal = dateInitPicker.getValue();
            LocalDate dateEndLocal = dateEndPicker.getValue();

            if (dateInitLocal == null || dateEndLocal == null) {
                AlertException.showAlert("Erro", "Selecione as datas de início e fim do contrato!");
                return;
            }


            ContractModel contract = new ContractModel(null, nameLocador, nameLocatario, cpfCnpj, dateInitLocal, valueBase, dateEndLocal);
            contractsList.add(contract);
            contractComboBox.setItems(contractsList);


            AlertException.showAlert("Sucesso", "Contrato salvo com sucesso!");
            clearFieldContract();
        } catch (Exception e) {
            AlertException.showAlert("Erro", "Erro ao salvar contrato: " + e.getMessage());
        }

    }

    @FXML
    private void savePayment() {
        try {
            ContractModel selectedContract = contractComboBox.getValue();
            if (selectedContract == null) {
                AlertException.showAlert("Erro", "Selecione um contrato!");
                return;
            }

            LocalDate monthRefLocal = monthRefPicker.getValue();
            Float valorAlug = Float.parseFloat(valorAlugField.getText());
            Float valorIptu = Float.parseFloat(valorIptuField.getText());
            Float valorCond = Float.parseFloat(valorCondField.getText());


            PaymentModel payment = new PaymentModel(null, valorCond, valorIptu, valorAlug, monthRefLocal, selectedContract);
            paymentsList.add(payment);


            AlertException.showAlert("Sucesso", "Pagamento salvo com sucesso!");
            clearFieldPayment();
        } catch (Exception e) {
            AlertException.showAlert("Erro", "Erro ao salvar pagamento: " + e.getMessage());
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
                    doc.add(new com.itextpdf.text.Paragraph("Valor Base: R$ " + contract.getValueBase()));
                    doc.add(new com.itextpdf.text.Paragraph("Data Início: " + contract.getDateInit()));
                    doc.add(new com.itextpdf.text.Paragraph("Data Fim: " + contract.getDateEnd()));
                    doc.add(new com.itextpdf.text.Paragraph("\n"));
                }
            }

            doc.close();
            AlertException.showAlert("Sucesso", "PDF salvo em: " + downloadsPath);
        } catch (Exception e) {
            AlertException.showAlert("Erro", "Erro ao exportar relatório de Contratos: " + e.getMessage());
            if (doc.isOpen()) {
                doc.close();
            }
        }
    }

    @FXML
    private void refreshListContract() {
        try {

            contractsTable.refresh();
            AlertException.showAlert("Info", "Lista de contratos atualizada!");
        } catch (Exception e) {
            AlertException.showAlert("Erro", "Erro ao atualizar contrato: " + e.getMessage());
        }
    }

    private void clearFieldContract() {
        nameLocadorField.clear();
        nameLocatarioField.clear();
        cpfCnpjField.clear();
        valueBaseField.clear();
        dateInitPicker.setValue(null);
        dateEndPicker.setValue(null);
    }

    @FXML
    private void refreshListPayment() {
        try {

            paymentsTable.refresh();
            AlertException.showAlert("Info", "Lista de pagamentos atualizada!");
        } catch (Exception e) {
            AlertException.showAlert("Erro", "Erro ao atualizar pagamento: " + e.getMessage());
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
                    doc.add(new Paragraph("Valor Aluguel R$ " + payment.getValorAlug()));
                    doc.add(new Paragraph("Valor Iptu R$ " + payment.getValorIptu()));
                    doc.add(new Paragraph("Valor Condominio R$ " + payment.getValorCond()));
                    doc.add(new Paragraph("\n"));
                }
            }

            doc.close();
            AlertException.showAlert("Sucesso", "PDF salvo em: " + downloadsPath);
        } catch (Exception e) {
            AlertException.showAlert("Erro", "Erro ao exportar relatório de Pagamentos: " + e.getMessage());
            if (doc.isOpen()) {
                doc.close();
            }
        }
    }


    private void clearFieldPayment() {
        contractComboBox.setValue(null);
        monthRefPicker.setValue(null);
        valorAlugField.clear();
        valorIptuField.clear();
        valorCondField.clear();
    }


}