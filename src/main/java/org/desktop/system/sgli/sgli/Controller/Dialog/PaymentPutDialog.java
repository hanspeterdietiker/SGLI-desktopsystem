package org.desktop.system.sgli.sgli.Controller.Dialog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.PaymentModel;
import org.desktop.system.sgli.sgli.Utils.AlertAction;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentPutDialog extends Dialog<PaymentModel> {
    private final ComboBox<ContractModel> contractComboBox = new ComboBox<>();
    private final DatePicker monthRefPicker = new DatePicker();
    private final TextField valorBaseField = new TextField();
    private PaymentModel payment;

    public PaymentPutDialog(PaymentModel payment, ObservableList<ContractModel> contractsList) {
        this.payment = payment;
        setTitle("Editar Pagamento");
        getDialogPane().setPrefWidth(500);
        getDialogPane().setPrefHeight(300);


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


        contractComboBox.setValue(payment.getContract());
        monthRefPicker.setValue(payment.getMonthRef());
        valorBaseField.setText(payment.getValorBase().toString());


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(15);
        gridPane.setVgap(12);

        gridPane.add(new Label("Contrato:"), 0, 0);
        gridPane.add(contractComboBox, 1, 0);

        gridPane.add(new Label("Mês Referência:"), 0, 1);
        gridPane.add(monthRefPicker, 1, 1);

        gridPane.add(new Label("Valor Base:"), 0, 2);
        gridPane.add(valorBaseField, 1, 2);


        contractComboBox.setPrefWidth(250);
        monthRefPicker.setPrefWidth(250);
        valorBaseField.setPrefWidth(250);

        getDialogPane().setContent(gridPane);


        ButtonType saveButton = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().addAll(saveButton, cancelButton);

        setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                return validateAndSave();
            }
            return null;
        });
    }

    private PaymentModel validateAndSave() {
        try {
            ContractModel selectedContract = contractComboBox.getValue();
            if (selectedContract == null) {
                AlertAction.showAlert("Erro", "Selecione um contrato!");
                return null;
            }

            LocalDate monthRef = monthRefPicker.getValue();
            if (monthRef == null) {
                AlertAction.showAlert("Erro", "Selecione um mês de referência!");
                return null;
            }

            String valorBaseStr = valorBaseField.getText().trim();
            if (valorBaseStr.isEmpty()) {
                AlertAction.showAlert("Erro", "Preencha o campo de Valor Base!");
                return null;
            }

            BigDecimal valorBase = new BigDecimal(valorBaseStr);

            payment.setContract(selectedContract);
            payment.setMonthRef(monthRef);
            payment.setValorBase(valorBase);

            return payment;
        } catch (NumberFormatException e) {
            AlertAction.showAlert("Erro", "Valor base inválido! Digite um número válido.");
            return null;
        } catch (Exception e) {
            AlertAction.showAlert("Erro", "Erro ao salvar: " + e.getMessage());
            return null;
        }
    }
}

