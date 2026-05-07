package org.desktop.system.sgli.sgli.Controller;

import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.desktop.system.sgli.sgli.Dto.ContractDto;
import org.desktop.system.sgli.sgli.Dto.PaymentDto;
import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.PaymentModel;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
    private DatePicker dataInitPicker;
    @FXML
    private DatePicker dataEndPicker;

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

    // Tabs de Listagem <Tab> - usando DTOs
    @FXML
    private TableView<ContractDto> contractsTable;
    @FXML
    private TableView<PaymentDto> paymentsTable;


    private ObservableList<ContractModel> contractsList = FXCollections.observableArrayList();
    private ObservableList<PaymentModel> paymentsList = FXCollections.observableArrayList();


    private ObservableList<ContractDto> contractsDTOList = FXCollections.observableArrayList();
    private ObservableList<PaymentDto> paymentsDTOList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        contractsTable.setItems(contractsDTOList);
        paymentsTable.setItems(paymentsDTOList);


        contractComboBox.setItems(contractsList);
        contractComboBox.setConverter(new javafx.util.StringConverter<ContractModel>() {
            @Override
            public String toString(ContractModel contract) {
                return contract != null ? contract.getNameLocador() : "";
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
            Float valueBase = Float.parseFloat(valueBaseField.getText());
            LocalDate dataInitLocal = dataInitPicker.getValue();
            LocalDate dataEndLocal = dataEndPicker.getValue();

            if (dataInitLocal == null || dataEndLocal == null) {
                AlertException.showAlert("Erro", "Selecione as datas de início e fim do contrato!");

            }

            Date dataInit = Date.from(dataInitLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date dataEnd = Date.from(dataEndLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());


            ContractModel contract = new ContractModel(null, nameLocador, nameLocatario, cpfCnpj, dataInit, valueBase, dataEnd);
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

            Date monthRef = Date.from(monthRefLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());



            PaymentModel payment = new PaymentModel(null, valorCond, valorIptu, valorAlug,  monthRef, selectedContract);
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
                    doc.add(new com.itextpdf.text.Paragraph("Data Início: " + contract.getDataInit()));
                    doc.add(new com.itextpdf.text.Paragraph("Data Fim: " + contract.getDataEnd()));
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
        dataInitPicker.setValue(null);
        dataEndPicker.setValue(null);
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
                    doc.add(new Paragraph("Contrato" + payment.getContract()));
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