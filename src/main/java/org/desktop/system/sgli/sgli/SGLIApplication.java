package org.desktop.system.sgli.sgli;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SGLIApplication extends Application {
    Image icone = new Image(Objects.requireNonNull(SGLIApplication.class.getResourceAsStream("/org/desktop/system/sgli/sgli/assets/icon.png")));
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SGLIApplication.class.getResource("view/welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 720);
        stage.setTitle("SGLI System - Application");
        stage.getIcons().add(icone);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}