package org.desktop.system.sgli.sgli.Utils;

import javafx.scene.control.Alert;

public class AlertAction extends RuntimeException {
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}