package org.desktop.system.sgli.sgli.Controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import org.desktop.system.sgli.sgli.Services.PdfReportService;
import org.desktop.system.sgli.sgli.Components.ActionTableCell;
import org.desktop.system.sgli.sgli.Controller.Dialog.ContractPutDialog;
import org.desktop.system.sgli.sgli.Controller.Dialog.PaymentPutDialog;
import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.PaymentModel;
import org.desktop.system.sgli.sgli.Utils.AlertAction;
import org.desktop.system.sgli.sgli.Utils.FormUtils;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Consumer;


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


    //  List Contract and Payment
    private final ObservableList<ContractModel> contractsList = FXCollections.observableArrayList();

    private final ObservableList<PaymentModel> paymentsList = FXCollections.observableArrayList();

    // Formatadores
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));


    @FXML
    public void initialize() {

        for (TableColumn<ContractModel, ?> column : contractsTable.getColumns()) {
            if ("Data Início".equals(column.getText())) {
                @SuppressWarnings("unchecked") TableColumn<ContractModel, LocalDate> dateInitColumn = (TableColumn<ContractModel, LocalDate>) column;
                dateInitColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : dateFormatter.format(item));
                    }
                });
            } else if ("Data Fim".equals(column.getText())) {
                @SuppressWarnings("unchecked") TableColumn<ContractModel, LocalDate> dateEndColumn = (TableColumn<ContractModel, LocalDate>) column;
                dateEndColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : dateFormatter.format(item));
                    }
                });
            }
        }


        for (TableColumn<ContractModel, ?> column : contractsTable.getColumns()) {
            if ("Aluguel".equals(column.getText())) {
                @SuppressWarnings("unchecked") TableColumn<ContractModel, BigDecimal> valorAlugColumn = (TableColumn<ContractModel, BigDecimal>) column;
                valorAlugColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(BigDecimal item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : "R$ " + decimalFormat.format(item));
                    }
                });
            } else if ("IPTU".equals(column.getText())) {
                @SuppressWarnings("unchecked") TableColumn<ContractModel, BigDecimal> valorIptuColumn = (TableColumn<ContractModel, BigDecimal>) column;
                valorIptuColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(BigDecimal item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : "R$ " + decimalFormat.format(item));
                    }
                });
            } else if ("Condomínio".equals(column.getText())) {
                @SuppressWarnings("unchecked") TableColumn<ContractModel, BigDecimal> valorCondColumn = (TableColumn<ContractModel, BigDecimal>) column;
                valorCondColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(BigDecimal item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : "R$ " + decimalFormat.format(item));
                    }
                });
            }
        }


        for (TableColumn<PaymentModel, ?> column : paymentsTable.getColumns()) {
            if ("Valor Base".equals(column.getText())) {
                @SuppressWarnings("unchecked") TableColumn<PaymentModel, BigDecimal> valorBaseColumn = (TableColumn<PaymentModel, BigDecimal>) column;
                valorBaseColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(BigDecimal item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : "R$ " + decimalFormat.format(item));
                    }
                });
            }
        }
        for (TableColumn<PaymentModel, ?> column : paymentsTable.getColumns()) {
            if ("Mês Ref.".equals(column.getText())) {
                @SuppressWarnings("unchecked") TableColumn<PaymentModel, LocalDate> monthRefColumn = (TableColumn<PaymentModel, LocalDate>) column;
                monthRefColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : dateFormatter.format(item));
                    }
                });
            }
        }
        for (TableColumn<PaymentModel, ?> column : paymentsTable.getColumns()) {
            if ("Contrato".equals(column.getText())) {
                @SuppressWarnings("unchecked") TableColumn<PaymentModel, String> contractNameColumn = (TableColumn<PaymentModel, String>) column;
                contractNameColumn.setCellValueFactory(cellData -> {
                    PaymentModel pagamento = cellData.getValue();
                    if (pagamento != null && pagamento.getContract() != null) {
                        String nomeDoLocatario = pagamento.getContract().getNameLocatario();
                        return new SimpleStringProperty(nomeDoLocatario);
                    } else {
                        return new SimpleStringProperty("Sem contrato");
                    }
                });

            }
        }

        contractsTable.setItems(contractsList);
        paymentsTable.setItems(paymentsList);


        configureContractActionsColumn();

        configurePaymentActionsColumn();

        contractComboBox.setItems(contractsList);
        contractComboBox.setConverter(new javafx.util.StringConverter<>() {
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


    private <T> void configureActionsColumn(TableView<T> table, Consumer<T> onEdit, Consumer<T> onDelete) {
        table.getColumns().stream().filter(col -> "Ações".equals(col.getText())).findFirst().ifPresent(col -> {
            @SuppressWarnings("unchecked") TableColumn<T, Void> actionsColumn = (TableColumn<T, Void>) col;
            actionsColumn.setCellFactory(c -> new ActionTableCell<>(onEdit, onDelete));
        });
    }


    private void configureContractActionsColumn() {
        configureActionsColumn(contractsTable, this::editContract, this::deleteContract);
    }

    private void configurePaymentActionsColumn() {
        configureActionsColumn(paymentsTable, this::editPayment, this::deletePayment);
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

            if (nameLocador.isEmpty() || nameLocatario.isEmpty()) {
                AlertAction.showAlert("Erro", "Preencha os campos de nome do locador e locatário!");
                return;
            }
            if (cpfCnpj.isEmpty()) {
                AlertAction.showAlert("Erro", "Preencha o campo de CPF/CNPJ!");
                return;
            }

            ContractModel contract = new ContractModel(null, nameLocador, nameLocatario, cpfCnpj, valorAlug, valorIptu, valorCond, dateInitLocal, dateEndLocal);

            contractsList.add(contract);
            contractComboBox.setItems(contractsList);
            AlertAction.showAlert("Sucesso", "Contrato salvo com sucesso!");

            clearFieldContract();
        } catch (NumberFormatException e) {
            AlertAction.showAlert("Erro", "Valor inválido! Digite um número válido para aluguel, IPTU e condomínio.");
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
            String valorBaseStr = valorBaseField.getText().trim();

            if (monthRefLocal == null) {
                AlertAction.showAlert("Erro", "Selecione um Mes de Referencia");
                return;
            }

            if (valorBaseStr.isEmpty()) {
                AlertAction.showAlert("Erro", "Preencha o campo de Valor Base!");
                return;
            }

            BigDecimal valorBase = new BigDecimal(valorBaseStr);

            PaymentModel payment = new PaymentModel(null, selectedContract, monthRefLocal, valorBase);
            paymentsList.add(payment);

            AlertAction.showAlert("Sucesso", "Pagamento salvo com sucesso!");
            clearFieldPayment();
        } catch (NumberFormatException e) {
            AlertAction.showAlert("Erro", "Valor base inválido! Digite um número válido.");
        } catch (Exception e) {
            AlertAction.showAlert("Erro", "Erro ao salvar pagamento: " + e.getMessage());
        }
    }

    private void editContract(ContractModel contract) {
        ContractPutDialog dialog = new ContractPutDialog(contract);
        var result = dialog.showAndWait();

        if (result.isPresent() && result.get() != null) {
            contractsTable.refresh();
            contractComboBox.setItems(contractsList);
            AlertAction.showAlert("Sucesso", "Contrato atualizado com sucesso!");
        }
    }

    private void deleteContract(ContractModel contract) {
        boolean confirmed = AlertAction.showConfirmation("Confirmar Exclusão", "Tem certeza que deseja deletar o contrato de " + contract.getNameLocatario() + "?");

        if (confirmed) {
            contractsList.remove(contract);
            contractComboBox.setItems(contractsList);
            contractsTable.refresh();
            AlertAction.showAlert("Sucesso", "Contrato deletado com sucesso!");
        }
    }

    private void editPayment(PaymentModel payment) {
        PaymentPutDialog dialog = new PaymentPutDialog(payment, contractsList);
        var result = dialog.showAndWait();

        if (result.isPresent() && result.get() != null) {
            paymentsTable.refresh();
            AlertAction.showAlert("Sucesso", "Pagamento atualizado com sucesso!");
        }
    }

    private void deletePayment(PaymentModel payment) {
        boolean confirmed = AlertAction.showConfirmation("Confirmar Exclusão", "Tem certeza que deseja deletar este pagamento?");

        if (confirmed) {
            paymentsList.remove(payment);
            paymentsTable.refresh();
            AlertAction.showAlert("Sucesso", "Pagamento deletado com sucesso!");
        }
    }

    @FXML
    private void exportReportContract() {
        PdfReportService.exportContractsReport(contractsList, dateFormatter, decimalFormat);
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
        FormUtils.clearFields(nameLocadorField, nameLocatarioField, cpfCnpjField, valorAlugField, valorIptuField, valorCondField, dateInitPicker, dateEndPicker);

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
        PdfReportService.exportPaymentsReport(paymentsList, dateFormatter, decimalFormat);
    }


    private void clearFieldPayment() {
        FormUtils.clearFields(contractComboBox, monthRefPicker, valorBaseField);
    }


}
