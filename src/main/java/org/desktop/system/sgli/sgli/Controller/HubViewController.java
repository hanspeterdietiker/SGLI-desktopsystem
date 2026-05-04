package org.desktop.system.sgli.sgli.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.desktop.system.sgli.sgli.Dto.ContractDto;
import org.desktop.system.sgli.sgli.Dto.PaymentDto;
import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.PaymentModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class HubViewController {

    // Contract
    @FXML
    private TextField nameLocadorField;
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
    private DatePicker datePaymentPicker;
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
            String cpfCnpj = cpfCnpjField.getText();
            Float valueBase = Float.parseFloat(valueBaseField.getText());
            LocalDate dataInitLocal = dataInitPicker.getValue();
            LocalDate dataEndLocal = dataEndPicker.getValue();

            Date dataInit = Date.from(dataInitLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date dataEnd = Date.from(dataEndLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());


            ContractModel contract = new ContractModel(null, nameLocador, cpfCnpj, dataInit, valueBase, dataEnd);
            contractsList.add(contract);
            contractComboBox.setItems(contractsList);



            showAlert("Sucesso", "Contrato salvo com sucesso!");
            clearFieldContract();
        } catch (Exception e) {
            showAlert("Erro", "Erro ao salvar contrato: " + e.getMessage());
        }
    }

    @FXML
    private void savePayment() {
        try {
            ContractModel selectedContract = contractComboBox.getValue();
            if (selectedContract == null) {
                showAlert("Erro", "Selecione um contrato!");
                return;
            }

            LocalDate monthRefLocal = monthRefPicker.getValue();
            LocalDate datePaymentLocal = datePaymentPicker.getValue();
            Float valorAlug = Float.parseFloat(valorAlugField.getText());
            Float valorIptu = Float.parseFloat(valorIptuField.getText());
            Float valorCond = Float.parseFloat(valorCondField.getText());

            Date monthRef = Date.from(monthRefLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date datePayment = Date.from(datePaymentLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());


            PaymentModel payment = new PaymentModel(null, valorCond, valorIptu, valorAlug, datePayment, monthRef, selectedContract);
            paymentsList.add(payment);


            showAlert("Sucesso", "Pagamento salvo com sucesso!");
            clearFieldPayment();
        } catch (Exception e) {
            showAlert("Erro", "Erro ao salvar pagamento: " + e.getMessage());
        }
    }

    @FXML
    private void refreshListContract() {
        try {

            contractsTable.refresh();
            showAlert("Info", "Lista de contratos atualizada!");
        } catch (Exception e) {
            showAlert("Erro", "Erro ao atualizar contrato: " + e.getMessage());
        }
    }

    private void clearFieldContract() {
        nameLocadorField.clear();
        cpfCnpjField.clear();
        valueBaseField.clear();
        dataInitPicker.setValue(null);
        dataEndPicker.setValue(null);
    }

    @FXML
    private void refreshListPayment() {
        try {

            paymentsTable.refresh();
            showAlert("Info", "Lista de pagamentos atualizada!");
        } catch (Exception e) {
            showAlert("Erro", "Erro ao atualizar pagamento: " + e.getMessage());
        }
    }


    private void clearFieldPayment() {
        contractComboBox.setValue(null);
        monthRefPicker.setValue(null);
        datePaymentPicker.setValue(null);
        valorAlugField.clear();
        valorIptuField.clear();
        valorCondField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}