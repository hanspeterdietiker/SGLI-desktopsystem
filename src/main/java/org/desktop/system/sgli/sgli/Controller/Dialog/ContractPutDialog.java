package org.desktop.system.sgli.sgli.Controller.Dialog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Utils.AlertAction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractPutDialog extends Dialog<ContractModel> {
    private final TextField nameLocadorField = new TextField();
    private final TextField nameLocatarioField = new TextField();
    private final TextField cpfLocatarioField = new TextField();
    private final TextField cpfLocadorField = new TextField();
    private final TextField valorAlugField = new TextField();
    private final TextField valorIptuField = new TextField();
    private final TextField valorCondField = new TextField();
    private final DatePicker dateInitPicker = new DatePicker();
    private final DatePicker dateEndPicker = new DatePicker();
    private final ContractModel contract;

    public ContractPutDialog(ContractModel contract) {
        this.contract = contract;
        setTitle("Editar Contrato");
        getDialogPane().setPrefWidth(600);
        getDialogPane().setPrefHeight(560);


        nameLocadorField.setText(contract.getNameLocador());
        nameLocatarioField.setText(contract.getNameLocatario());
        cpfLocatarioField.setText(contract.getCpfLocatario());
        cpfLocadorField.setText(contract.getCpfLocador());
        valorAlugField.setText(contract.getValorAlug().toString());
        valorIptuField.setText(contract.getValorIptu().toString());
        valorCondField.setText(contract.getValorCond().toString());
        dateInitPicker.setValue(contract.getDateInit());
        dateEndPicker.setValue(contract.getDateEnd());


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(15);
        gridPane.setVgap(12);

        gridPane.add(new Label("Nome Locador:"), 0, 0);
        gridPane.add(nameLocadorField, 1, 0);

        gridPane.add(new Label("Nome Locatário:"), 0, 1);
        gridPane.add(nameLocatarioField, 1, 1);

        gridPane.add(new Label("CPF Locatário:"), 0, 2);
        gridPane.add(cpfLocatarioField, 1, 2);

        gridPane.add(new Label("CPF Locador:"), 0, 3);
        gridPane.add(cpfLocadorField, 1, 3);

        gridPane.add(new Label("Valor Aluguel:"), 0, 4);
        gridPane.add(valorAlugField, 1, 4);

        gridPane.add(new Label("Valor IPTU:"), 0, 5);
        gridPane.add(valorIptuField, 1, 5);

        gridPane.add(new Label("Valor Condomínio:"), 0, 6);
        gridPane.add(valorCondField, 1, 6);

        gridPane.add(new Label("Data Início:"), 0, 7);
        gridPane.add(dateInitPicker, 1, 7);

        gridPane.add(new Label("Data Fim:"), 0, 8);
        gridPane.add(dateEndPicker, 1, 8);


        nameLocadorField.setPrefWidth(250);
        nameLocatarioField.setPrefWidth(250);
        cpfLocatarioField.setPrefWidth(250);
        cpfLocadorField.setPrefWidth(250);
        valorAlugField.setPrefWidth(250);
        valorIptuField.setPrefWidth(250);
        valorCondField.setPrefWidth(250);
        dateInitPicker.setPrefWidth(250);
        dateEndPicker.setPrefWidth(250);

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

    private ContractModel validateAndSave() {
        try {
            String nameLocador = nameLocadorField.getText().trim();
            String nameLocatario = nameLocatarioField.getText().trim();
            String cpfLocatario = cpfLocatarioField.getText().trim();
            String cpfLocador = cpfLocadorField.getText().trim();
            String valorAlugStr = valorAlugField.getText().trim();
            String valorIptuStr = valorIptuField.getText().trim();
            String valorCondStr = valorCondField.getText().trim();

            if (nameLocador.isEmpty() || nameLocatario.isEmpty()) {
                AlertAction.showAlert("Erro", "Preencha os campos de nome do locador e locatário!");
                return null;
            }
            if (cpfLocatario.isEmpty()) {
                AlertAction.showAlert("Erro", "Preencha o CPF do Locatário!");
                return null;
            }
            if (cpfLocador.isEmpty()) {
                AlertAction.showAlert("Erro", "Preencha o CPF do Locador!");
                return null;
            }
            if (valorAlugStr.isEmpty() || valorIptuStr.isEmpty() || valorCondStr.isEmpty()) {
                AlertAction.showAlert("Erro", "Preencha todos os campos de valores!");
                return null;
            }

            LocalDate dateInit = dateInitPicker.getValue();
            LocalDate dateEnd = dateEndPicker.getValue();

            if (dateInit == null || dateEnd == null) {
                AlertAction.showAlert("Erro", "Selecione as datas de início e fim!");
                return null;
            }

            BigDecimal valorAlug = new BigDecimal(valorAlugStr);
            BigDecimal valorIptu = new BigDecimal(valorIptuStr);
            BigDecimal valorCond = new BigDecimal(valorCondStr);

            contract.setNameLocador(nameLocador);
            contract.setNameLocatario(nameLocatario);
            contract.setCpfLocatario(cpfLocatario);
            contract.setCpfLocador(cpfLocador);
            contract.setValorAlug(valorAlug);
            contract.setValorIptu(valorIptu);
            contract.setValorCond(valorCond);
            contract.setDateInit(dateInit);
            contract.setDateEnd(dateEnd);

            return contract;
        } catch (NumberFormatException e) {
            AlertAction.showAlert("Erro", "Valor inválido! Digite números válidos para os valores monetários.");
            return null;
        } catch (Exception e) {
            AlertAction.showAlert("Erro", "Erro ao salvar: " + e.getMessage());
            return null;
        }
    }
}

