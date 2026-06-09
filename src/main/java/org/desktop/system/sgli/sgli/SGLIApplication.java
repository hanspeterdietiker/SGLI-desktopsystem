package org.desktop.system.sgli.sgli;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.desktop.system.sgli.sgli.Repository.JpaUtil;

import java.util.Objects;

    /*
     * @author Hanspeter Dietiker, disponivel em : https://github.com/hanspeterdietiker
     * Java 21, JavaFx 17,
     * SGLI - System
     */

public class SGLIApplication extends Application {

    private final Image icone = new Image(Objects.requireNonNull(SGLIApplication.class.getResourceAsStream("/org/desktop/system/sgli/sgli/assets/icon.png")));
    private RotateTransition rotateTransition;

    private Scene createPreloaderScene() {
        ImageView iconView = new ImageView(icone);
        iconView.setFitWidth(300);
        iconView.setFitHeight(300);
        iconView.setPreserveRatio(true);

        rotateTransition = new RotateTransition(Duration.seconds(30), iconView);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.play();

        StackPane root = new StackPane(iconView);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0F4C75 0%, #1B5E8C 50%, #2E8BC0 100%)");

        return new Scene(root, 1368, 768, Color.web("#4A90E2"));
    }

    @Override
    public void start(Stage stage) {
        Scene preloaderScene = createPreloaderScene();
        stage.setScene(preloaderScene);
        stage.setTitle("SGLI System");
        stage.getIcons().add(icone);
        stage.show();

        Thread initThread = new Thread(() -> {
            JpaUtil.initialize();
            Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(SGLIApplication.class.getResource("view/welcome-view.fxml"));
                    Scene welcomeScene = new Scene(fxmlLoader.load(), 1368, 768);

                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), preloaderScene.getRoot());
                    fadeOut.setFromValue(1.0);
                    fadeOut.setToValue(0.0);
                    fadeOut.setOnFinished(e -> {
                        rotateTransition.stop();
                        stage.setScene(welcomeScene);
                    });
                    fadeOut.play();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
        initThread.setDaemon(true);
        initThread.start();
    }

    @Override
    public void stop() {
        JpaUtil.close();
    }

    public static void main(String[] args) {
        launch();
    }
}

