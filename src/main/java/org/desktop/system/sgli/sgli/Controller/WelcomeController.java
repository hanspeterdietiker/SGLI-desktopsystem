package org.desktop.system.sgli.sgli.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WelcomeController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onWelcomeButton() {
        welcomeText.setText("Estamos Desenvolvendo!");
    }
}
