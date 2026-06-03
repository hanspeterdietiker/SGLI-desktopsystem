package org.desktop.system.sgli.sgli.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.desktop.system.sgli.sgli.Config.InjectionRoot;
import java.io.IOException;

public class WelcomeController {


    @FXML
    protected void onClickToHub(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/desktop/system/sgli/sgli/view/hub-view.fxml"));
        loader.setControllerFactory(c -> InjectionRoot.hubViewController());
        Parent root = loader.load();

        Scene hubScene = new Scene(root, 1368, 720);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(hubScene);
        stage.show();
    }
}





