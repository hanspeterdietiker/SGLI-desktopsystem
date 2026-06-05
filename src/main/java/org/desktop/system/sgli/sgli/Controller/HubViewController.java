package org.desktop.system.sgli.sgli.Controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import org.desktop.system.sgli.sgli.Controller.Dialog.LgpdAuditDialog;
import org.desktop.system.sgli.sgli.Repository.ContractRepository;
import org.desktop.system.sgli.sgli.Services.ContractService;
import org.desktop.system.sgli.sgli.Services.LgpdAuditService;
import org.desktop.system.sgli.sgli.Services.PaymentService;
import org.desktop.system.sgli.sgli.Services.PdfReportService;
import org.desktop.system.sgli.sgli.Components.ActionTableCell;
import org.desktop.system.sgli.sgli.Controller.Dialog.ContractPutDialog;
import org.desktop.system.sgli.sgli.Controller.Dialog.PaymentPutDialog;
import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.Enum.ContractTypeEnum;
import org.desktop.system.sgli.sgli.Entity.PaymentModel;
import org.desktop.system.sgli.sgli.Utils.AlertAction;
import org.desktop.system.sgli.sgli.Utils.DateFormatterUtils;
import org.desktop.system.sgli.sgli.Utils.DecimalFormatterUtils;
import org.desktop.system.sgli.sgli.Utils.CpfUtils;
import org.desktop.system.sgli.sgli.Utils.FormUtils;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Consumer;


public class HubViewController {

    // Contract
    @FXML
    private TextField nameLocadorField;
    @FXML
    private TextField nameLocatarioField;
    @FXML
    private TextField cpfLocatarioField;
    @FXML
    private TextField cpfLocadorField;
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
    @FXML
    private RadioButton caucaoRadio;
    @FXML
    private RadioButton fiadorRadio;
    @FXML
    private RadioButton semInformRadio;

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


    // Pagination
    @FXML
    private Label pageLabel;
    @FXML
    private Button prevPageButton;
    @FXML
    private Button nextPageButton;

    private int currentPage = 0;

    //  List Contract and Payment
    private final ObservableList<ContractModel> contractsList = FXCollections.observableArrayList();
    private final ObservableList<ContractModel> pagedContractsList = FXCollections.observableArrayList();
    private final ObservableList<PaymentModel> paymentsList = FXCollections.observableArrayList();

    private final ContractService contractService;
    private final PaymentService paymentService;
    private LgpdAuditService lgpdAuditService;

    public HubViewController(ContractService contractService, PaymentService paymentService) {
        this.contractService = contractService;
        this.paymentService = paymentService;
    }


    @FXML
    public void initialize() {
        FormUtils.applyCpfMask(cpfLocatarioField, cpfLocadorField);

        for (TableColumn<ContractModel, ?> column : contractsTable.getColumns()) {
            if ("Data Início".equals(column.getText())) {
                @SuppressWarnings("unchecked") TableColumn<ContractModel, LocalDate> dateInitColumn = (TableColumn<ContractModel, LocalDate>) column;
                dateInitColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : DateFormatterUtils.dateFormatter.format(item));
                    }
                });
            } else if ("Data Fim".equals(column.getText())) {
                @SuppressWarnings("unchecked") TableColumn<ContractModel, LocalDate> dateEndColumn = (TableColumn<ContractModel, LocalDate>) column;
                dateEndColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : DateFormatterUtils.dateFormatter.format(item));
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
                        setText(empty || item == null ? null : "R$ " + DecimalFormatterUtils.decimalFormat.format(item));
                    }
                });
            } else if ("IPTU".equals(column.getText())) {
                @SuppressWarnings("unchecked") TableColumn<ContractModel, BigDecimal> valorIptuColumn = (TableColumn<ContractModel, BigDecimal>) column;
                valorIptuColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(BigDecimal item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : "R$ " + DecimalFormatterUtils.decimalFormat.format(item));
                    }
                });
            } else if ("Condomínio".equals(column.getText())) {
                @SuppressWarnings("unchecked") TableColumn<ContractModel, BigDecimal> valorCondColumn = (TableColumn<ContractModel, BigDecimal>) column;
                valorCondColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(BigDecimal item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : "R$ " + DecimalFormatterUtils.decimalFormat.format(item));
                    }
                });
            }
        }


        for (TableColumn<ContractModel, ?> column : contractsTable.getColumns()) {
            if ("CPF Locatário".equals(column.getText()) || "CPF Locador".equals(column.getText())) {
                @SuppressWarnings("unchecked")
                TableColumn<ContractModel, String> cpfColumn = (TableColumn<ContractModel, String>) column;
                cpfColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(String cpf, boolean empty) {
                        super.updateItem(cpf, empty);
                        setText(empty || cpf == null ? null : CpfUtils.mask(cpf));
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
                        setText(empty || item == null ? null : "R$ " + DecimalFormatterUtils.decimalFormat.format(item));
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
                        setText(empty || item == null ? null : DateFormatterUtils.dateFormatter.format(item));
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

        contractsTable.setItems(pagedContractsList);
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

        loadDataFromDatabase();
        lgpdAuditService = new LgpdAuditService(new ContractRepository());
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

            String selectedType = fiadorRadio.isSelected() ? "FIADOR" : semInformRadio.isSelected() ? "NO_INFORM" : "CAUCAO";
            ContractTypeEnum contractType = ContractService.resolveContractType(selectedType);

            contractService.save(
                    nameLocadorField.getText(), nameLocatarioField.getText(),
                    cpfLocatarioField.getText(), cpfLocadorField.getText(),
                    valorAlugField.getText(), valorIptuField.getText(), valorCondField.getText(),
                    dateInitPicker.getValue(), dateEndPicker.getValue(), contractType);
            loadDataFromDatabase();
            AlertAction.showAlert("Sucesso", "Contrato salvo com sucesso!");

            clearFieldContract();

        } catch (IllegalArgumentException e) {

            AlertAction.showAlert("Erro de Validação", e.getMessage());

        } catch (Exception e) {

            AlertAction.showAlert("Erro Inesperado", "Erro ao salvar contrato: " + e.getMessage());
        }
    }

    @FXML
    private void savePayment() {
        try {

            PaymentModel savedPayment = paymentService.save(
                    contractComboBox.getValue(), monthRefPicker.getValue(), valorBaseField.getText());
            paymentsList.add(savedPayment);
            loadDataFromDatabase();
            AlertAction.showAlert("Sucesso", "Pagamento salvo com sucesso!");

            clearFieldPayment();

        } catch (IllegalArgumentException e) {

            AlertAction.showAlert("Erro de Validação", e.getMessage());

        } catch (Exception e) {

            AlertAction.showAlert("Erro Inesperado", "Erro ao salvar pagamento: " + e.getMessage());
        }
    }

    private void editContract(ContractModel contract) {
        ContractPutDialog dialog = new ContractPutDialog(contract);
        var result = dialog.showAndWait();

        if (result.isPresent() && result.get() != null) {
            try {
                contractService.update(result.get());
                loadDataFromDatabase();
                AlertAction.showAlert("Sucesso", "Contrato atualizado com sucesso!");
            } catch (IllegalArgumentException e) {
                AlertAction.showAlert("Erro de Validação", e.getMessage());
            } catch (Exception e) {
                AlertAction.showAlert("Erro Inesperado", "Erro ao atualizar contrato: " + e.getMessage());
            }
        }
    }

    private void deleteContract(ContractModel contract) {
        boolean confirmed = AlertAction.showConfirmation("Confirmar Exclusão", "Tem certeza que deseja deletar o contrato de " + contract.getNameLocatario() + "?");

        if (confirmed) {
            paymentService.deleteByContractId(contract.getId());
            contractService.delete(contract.getId());
            loadDataFromDatabase();
            AlertAction.showAlert("Sucesso", "Contrato deletado com sucesso!");
        }
    }

    private void editPayment(PaymentModel payment) {
        PaymentPutDialog dialog = new PaymentPutDialog(payment, contractsList);
        var result = dialog.showAndWait();

        if (result.isPresent() && result.get() != null) {
            try {
                PaymentModel updatedPayment = paymentService.update(result.get());
                int paymentIndex = paymentsList.indexOf(payment);
                if (paymentIndex >= 0) {
                    paymentsList.set(paymentIndex, updatedPayment);
                }
                AlertAction.showAlert("Sucesso", "Pagamento atualizado com sucesso!");
            } catch (IllegalArgumentException e) {
                AlertAction.showAlert("Erro de Validação", e.getMessage());
            } catch (Exception e) {
                AlertAction.showAlert("Erro Inesperado", "Erro ao atualizar pagamento: " + e.getMessage());
            }
        }
    }

    private void deletePayment(PaymentModel payment) {
        boolean confirmed = AlertAction.showConfirmation("Confirmar Exclusão", "Tem certeza que deseja deletar este pagamento?");

        if (confirmed) {
            paymentService.delete(payment.getId());
            paymentsList.remove(payment);
            paymentsTable.refresh();
            AlertAction.showAlert("Sucesso", "Pagamento deletado com sucesso!");
        }
    }

    @FXML
    private void exportReportContract() {
        PdfReportService.exportContractsReport(contractsList);
    }

    @FXML
    private void refreshListContract() {
        try {
            loadDataFromDatabase();
            AlertAction.showAlert("Info", "Lista de contratos atualizada!");
        } catch (Exception e) {
            AlertAction.showAlert("Erro", "Erro ao atualizar contrato: " + e.getMessage());
        }
    }

    private void clearFieldContract() {
        FormUtils.clearFields(nameLocadorField, nameLocatarioField, cpfLocatarioField, cpfLocadorField, valorAlugField, valorIptuField, valorCondField, dateInitPicker, dateEndPicker);
        caucaoRadio.setSelected(true);

    }

    @FXML
    private void refreshListPayment() {
        try {
            loadDataFromDatabase();
            AlertAction.showAlert("Info", "Lista de pagamentos atualizada!");
        } catch (Exception e) {
            AlertAction.showAlert("Erro", "Erro ao atualizar pagamento: " + e.getMessage());
        }
    }

    @FXML
    private void exportReportPayment() {
        PdfReportService.exportPaymentsReport(paymentsList);
    }

    @FXML
    private void openLgpdAudit() {
        var report = lgpdAuditService.runAudit();
        new LgpdAuditDialog(report).showAndWait();
    }

    private void clearFieldPayment() {
        FormUtils.clearFields(contractComboBox, monthRefPicker, valorBaseField);
    }

    @FXML
    private void goToPrevPage() {
        if (currentPage > 0) {
            currentPage--;
            loadContractsPage();
        }
    }

    @FXML
    private void goToNextPage() {
        currentPage++;
        loadContractsPage();
    }

    private void loadContractsPage() {
        var page = contractService.findPage(currentPage);
        currentPage = page.currentPage();
        pagedContractsList.setAll(page.contracts());
        contractsTable.refresh();
        pageLabel.setText("Página " + (currentPage + 1) + " de " + page.totalPages());
        prevPageButton.setDisable(currentPage == 0);
        nextPageButton.setDisable(currentPage + 1 >= page.totalPages());
    }

    private void loadDataFromDatabase() {
        contractsList.setAll(contractService.findAll());
        paymentsList.setAll(paymentService.findAll());
        loadContractsPage();

    }

}
