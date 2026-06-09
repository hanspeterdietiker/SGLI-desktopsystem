package org.desktop.system.sgli.sgli.Controller.Dialog;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.Enum.ContractTypeEnum;
import org.desktop.system.sgli.sgli.Utils.AlertAction;
import org.desktop.system.sgli.sgli.Utils.CpfUtils;
import org.desktop.system.sgli.sgli.Utils.FormUtils;

import java.time.LocalDate;
import org.desktop.system.sgli.sgli.Dto.ContractDto;

public class ContractPutDialog extends Dialog<ContractDto> {
    private final TextField nameLocadorField = new TextField();
    private final TextField nameLocatarioField = new TextField();
    private final TextField cpfLocatarioField = new TextField();
    private final TextField cpfLocadorField = new TextField();
    private final TextField valorAlugField = new TextField();
    private final TextField valorIptuField = new TextField();
    private final TextField valorCondField = new TextField();
    private final DatePicker dateInitPicker = new DatePicker();
    private final DatePicker dateEndPicker = new DatePicker();
    private final RadioButton caucaoRadio = new RadioButton(ContractTypeEnum.CAUCAO.getLabel());
    private final RadioButton fiadorRadio = new RadioButton(ContractTypeEnum.FIADOR.getLabel());
    private final RadioButton semInformRadio = new RadioButton(ContractTypeEnum.NO_INFORM.getLabel());
    private final ToggleGroup contractTypeGroup = new ToggleGroup();
    private final ContractModel contract;

    public ContractPutDialog(ContractModel contract) {
        this.contract = contract;
        setTitle("Editar Contrato");
        getDialogPane().setPrefWidth(600);
        getDialogPane().setPrefHeight(600);

        caucaoRadio.setToggleGroup(contractTypeGroup);
        fiadorRadio.setToggleGroup(contractTypeGroup);
        semInformRadio.setToggleGroup(contractTypeGroup);

        nameLocadorField.setText(contract.getNameLocador());
        nameLocatarioField.setText(contract.getNameLocatario());
        cpfLocatarioField.setPromptText(CpfUtils.mask(contract.getCpfLocatario()));
        cpfLocadorField.setPromptText(CpfUtils.mask(contract.getCpfLocador()));

        FormUtils.applyCpfMask(cpfLocatarioField, cpfLocadorField);
        valorAlugField.setText(contract.getValorAlug().toString());
        valorIptuField.setText(contract.getValorIptu().toString());
        valorCondField.setText(contract.getValorCond().toString());
        dateInitPicker.setValue(contract.getDateInit());
        dateEndPicker.setValue(contract.getDateEnd());

        ContractTypeEnum currentType = contract.getContractType();
        if (currentType == ContractTypeEnum.FIADOR) {
            fiadorRadio.setSelected(true);
        } else if (currentType == ContractTypeEnum.NO_INFORM) {
            semInformRadio.setSelected(true);
        } else {
            caucaoRadio.setSelected(true);
        }

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

        HBox contractTypeBox = new HBox(15, caucaoRadio, fiadorRadio, semInformRadio);
        gridPane.add(new Label("Tipo de Contrato:"), 0, 9);
        gridPane.add(contractTypeBox, 1, 9);

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

        Button saveButtonNode = (Button) getDialogPane().lookupButton(saveButton);
        saveButtonNode.addEventFilter(ActionEvent.ACTION, event -> {
            if (dateInitPicker.getValue() == null || dateEndPicker.getValue() == null) {
                AlertAction.showAlert("Erro de Validação", "Selecione as datas de início e fim do contrato!");
                event.consume();
            }
        });

        setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                return validateAndUpdate();
            }
            return null;
        });
    }

    private ContractDto validateAndUpdate() {
        LocalDate dateInit = dateInitPicker.getValue();
        LocalDate dateEnd = dateEndPicker.getValue();
        if (dateInit == null || dateEnd == null) {
            AlertAction.showAlert("Erro de Validação", "Selecione as datas de início e fim do contrato!");
            return null;
        }

        String cpfLocatario = cpfLocatarioField.getText().isBlank()
                ? contract.getCpfLocatario()
                : cpfLocatarioField.getText().trim();
        String cpfLocador = cpfLocadorField.getText().isBlank()
                ? contract.getCpfLocador()
                : cpfLocadorField.getText().trim();

        return new ContractDto(
                nameLocadorField.getText().trim(),
                nameLocatarioField.getText().trim(),
                cpfLocatario,
                cpfLocador,
                valorAlugField.getText().trim(),
                valorIptuField.getText().trim(),
                valorCondField.getText().trim(),
                dateInit,
                dateEnd,
                resolveContractType()
        );
    }

    private ContractTypeEnum resolveContractType() {
        if (fiadorRadio.isSelected()) return ContractTypeEnum.FIADOR;
        if (semInformRadio.isSelected()) return ContractTypeEnum.NO_INFORM;
        return ContractTypeEnum.CAUCAO;
    }
}

