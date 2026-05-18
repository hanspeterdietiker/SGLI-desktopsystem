package org.desktop.system.sgli.sgli.Controller;

import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import org.desktop.system.sgli.sgli.Controller.Dialog.ContractPutDialog;
import org.desktop.system.sgli.sgli.Controller.Dialog.PaymentPutDialog;
import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.PaymentModel;
import org.desktop.system.sgli.sgli.Utils.AlertAction;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


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
                @SuppressWarnings("unchecked")
                TableColumn<ContractModel, LocalDate> dateInitColumn = (TableColumn<ContractModel, LocalDate>) column;
                dateInitColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : dateFormatter.format(item));
                    }
                });
            } else if ("Data Fim".equals(column.getText())) {
                @SuppressWarnings("unchecked")
                TableColumn<ContractModel, LocalDate> dateEndColumn = (TableColumn<ContractModel, LocalDate>) column;
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
                @SuppressWarnings("unchecked")
                TableColumn<ContractModel, BigDecimal> valorAlugColumn = (TableColumn<ContractModel, BigDecimal>) column;
                valorAlugColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(BigDecimal item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : "R$ " + decimalFormat.format(item));
                    }
                });
            } else if ("IPTU".equals(column.getText())) {
                @SuppressWarnings("unchecked")
                TableColumn<ContractModel, BigDecimal> valorIptuColumn = (TableColumn<ContractModel, BigDecimal>) column;
                valorIptuColumn.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(BigDecimal item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : "R$ " + decimalFormat.format(item));
                    }
                });
            } else if ("Condomínio".equals(column.getText())) {
                @SuppressWarnings("unchecked")
                TableColumn<ContractModel, BigDecimal> valorCondColumn = (TableColumn<ContractModel, BigDecimal>) column;
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
                @SuppressWarnings("unchecked")
                TableColumn<PaymentModel, BigDecimal> valorBaseColumn = (TableColumn<PaymentModel, BigDecimal>) column;
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
                @SuppressWarnings("unchecked")
                TableColumn<PaymentModel, LocalDate> monthRefColumn = (TableColumn<PaymentModel, LocalDate>) column;
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
                @SuppressWarnings("unchecked")
                TableColumn<PaymentModel, String> contractNameColumn = (TableColumn<PaymentModel, String>) column;
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

    private void configureContractActionsColumn() {
        for (TableColumn<ContractModel, ?> column : contractsTable.getColumns()) {
            if ("Ações".equals(column.getText())) {
                @SuppressWarnings("unchecked")
                TableColumn<ContractModel, Void> actionsColumn = (TableColumn<ContractModel, Void>) column;
                actionsColumn.setCellFactory(col -> new TableCell<>() {
                    private final Button editBtn = new Button("Editar");
                    private final Button deleteBtn = new Button("Deletar");
                    private final HBox container = new HBox();

                    {
                        editBtn.getStyleClass().add("edit-button");
                        deleteBtn.getStyleClass().add("delete-button");

                        editBtn.setOnAction(event -> {
                            ContractModel contract = getTableView().getItems().get(getIndex());
                            editContract(contract);
                        });

                        deleteBtn.setOnAction(event -> {
                            ContractModel contract = getTableView().getItems().get(getIndex());
                            deleteContract(contract);
                        });

                        container.setSpacing(10);
                        container.setStyle("-fx-alignment: CENTER;");
                        container.getChildren().addAll(editBtn, deleteBtn);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : container);
                    }
                });
            }
        }
    }

    private void configurePaymentActionsColumn() {
        for (TableColumn<PaymentModel, ?> column : paymentsTable.getColumns()) {
            if ("Ações".equals(column.getText())) {
                @SuppressWarnings("unchecked")
                TableColumn<PaymentModel, Void> actionsColumn = (TableColumn<PaymentModel, Void>) column;
                actionsColumn.setCellFactory(col -> new TableCell<>() {
                    private final Button editBtn = new Button("Editar");
                    private final Button deleteBtn = new Button("Deletar");
                    private final HBox container = new HBox();

                    {
                        editBtn.getStyleClass().add("edit-button");
                        deleteBtn.getStyleClass().add("delete-button");

                        editBtn.setOnAction(event -> {
                            PaymentModel payment = getTableView().getItems().get(getIndex());
                            editPayment(payment);
                        });

                        deleteBtn.setOnAction(event -> {
                            PaymentModel payment = getTableView().getItems().get(getIndex());
                            deletePayment(payment);
                        });

                        container.setSpacing(10);
                        container.setStyle("-fx-alignment: CENTER;");
                        container.getChildren().addAll(editBtn, deleteBtn);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : container);
                    }
                });
            }
        }
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

            ContractModel contract = new ContractModel(null, nameLocador, nameLocatario,
                    cpfCnpj, valorAlug, valorIptu, valorCond, dateInitLocal, dateEndLocal);

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
        boolean confirmed = AlertAction.showConfirmation("Confirmar Exclusão", 
            "Tem certeza que deseja deletar o contrato de " + contract.getNameLocatario() + "?");
        
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
        boolean confirmed = AlertAction.showConfirmation("Confirmar Exclusão", 
            "Tem certeza que deseja deletar este pagamento?");
        
        if (confirmed) {
            paymentsList.remove(payment);
            paymentsTable.refresh();
            AlertAction.showAlert("Sucesso", "Pagamento deletado com sucesso!");
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
            com.itextpdf.text.Paragraph dataCreated = new com.itextpdf.text.Paragraph("Data de Criação: " + LocalDate.now().format(dateFormatter));
            dataCreated.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            doc.add(dataCreated);


            if (contractsList.isEmpty()) {
                doc.add(new com.itextpdf.text.Paragraph("Nenhum contrato registrado."));
            } else {

                for (ContractModel contract : contractsList) {
                    doc.add(new com.itextpdf.text.Paragraph("Nome Locador: " + contract.getNameLocador()));
                    doc.add(new com.itextpdf.text.Paragraph("Nome Locatario: " + contract.getNameLocatario()));
                    doc.add(new com.itextpdf.text.Paragraph("CPF/CNPJ: " + contract.getCpfCnpj()));
                    doc.add(new com.itextpdf.text.Paragraph("Valor Condominio R$ " + decimalFormat.format(contract.getValorCond())));
                    doc.add(new com.itextpdf.text.Paragraph("Valor Aluguel R$ " + decimalFormat.format(contract.getValorAlug())));
                    doc.add(new com.itextpdf.text.Paragraph(String.format("Valor Iptu R$ " + decimalFormat.format(contract.getValorIptu()))));
                    doc.add(new com.itextpdf.text.Paragraph("Data Início: " + contract.getDateInit().format(dateFormatter)));
                    doc.add(new com.itextpdf.text.Paragraph("Data Fim: " + contract.getDateEnd().format(dateFormatter)));
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
            com.itextpdf.text.Paragraph dataCreated = new com.itextpdf.text.Paragraph("Data de Criação: " + LocalDate.now().format(dateFormatter));
            dataCreated.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
            doc.add(dataCreated);


            if (paymentsList.isEmpty()) {
                doc.add(new Paragraph("Nenhum Pagamento registrado."));
            } else {

                for (PaymentModel payment : paymentsList) {
                    doc.add(new Paragraph("Locatario: " + payment.getContract().getNameLocatario()));
                    doc.add(new Paragraph("Mes de Referencia: " + payment.getMonthRef().format(dateFormatter)));
                    doc.add(new Paragraph("Valor Base R$ " + decimalFormat.format(payment.getValorBase())));
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
