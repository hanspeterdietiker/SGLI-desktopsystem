package org.desktop.system.sgli.sgli.Controller.Dialog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.desktop.system.sgli.sgli.Services.LgpdAuditService.AuditReport;

public class LgpdAuditDialog extends Dialog<Void> {

    public LgpdAuditDialog(AuditReport report) {
        setTitle("Relatório LGPD");
        setHeaderText("Resultado da Auditoria de Conformidade LGPD");

        VBox content = new VBox(8);
        content.setPadding(new Insets(12));

        content.getChildren().addAll(
            statusLabel("Total de contratos", String.valueOf(report.totalContracts()), false),
            statusLabel("CPFs inválidos (dígito verificador)", String.valueOf(report.invalidCpfCount()),
                        report.invalidCpfCount() > 0),
            statusLabel("Contratos expirados ainda retidos", String.valueOf(report.expiredContractCount()),
                        report.expiredContractCount() > 0),
            statusLabel("Dias do dado mais antigo retido pós-expiração",
                        String.valueOf(report.oldestExpiredDaysRetained()),
                        report.oldestExpiredDaysRetained() > 1825)
        );

        if (!report.invalidCpfContracts().isEmpty()) {
            TextArea ids = new TextArea(String.join("\n", report.invalidCpfContracts()));
            ids.setEditable(false);
            ids.setPrefRowCount(3);
            content.getChildren().add(new Label("IDs dos contratos com CPF inválido:"));
            content.getChildren().add(ids);
        }

        getDialogPane().setContent(content);
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    }

    private Label statusLabel(String desc, String value, boolean warn) {
        Label l = new Label(desc + ": " + value + (warn ? " ⚠" : " ✓"));
        if (warn) l.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;");
        else l.setStyle("-fx-text-fill: #27ae60;");
        return l;
    }
}
