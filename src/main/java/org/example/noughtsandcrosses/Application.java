package org.example.noughtsandcrosses;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("Noughts and crosses");
        stage.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("/icons/NoughtsAndCrossesIcon.png"))));
        stage.setResizable(false);
        stage.setScene(scene);

        Controller controller = fxmlLoader.getController();

        Alert setGameMode = new Alert(Alert.AlertType.CONFIRMATION);
        setGameMode.setTitle("Noughts and crosses");
        setGameMode.setHeaderText("Pick a game mode");
        setGameMode.setContentText("Would you like to play against player or bot?");

        ButtonType pvp = new ButtonType("Player vs Player");
        ButtonType pvb = new ButtonType("Player vs Bot");
        setGameMode.getButtonTypes().setAll(pvp, pvb);

        Stage alertStage = (Stage) setGameMode.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("/icons/NoughtsAndCrossesIcon.png"))));

        Optional<ButtonType> result = setGameMode.showAndWait();
        if (result.isPresent() && result.get() == pvp) {
            controller.setGameMode("PVP");
        } else {
            controller.setGameMode("PVB");
        }

        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}