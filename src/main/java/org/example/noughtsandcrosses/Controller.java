package org.example.noughtsandcrosses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Controller {
    @FXML private Button btn0;
    @FXML private Button btn1;
    @FXML private Button btn2;
    @FXML private Button btn3;
    @FXML private Button btn4;
    @FXML private Button btn5;
    @FXML private Button btn6;
    @FXML private Button btn7;
    @FXML private Button btn8;

    @FXML private RadioMenuItem mnPVP;
    @FXML private RadioMenuItem mnPVB;

    private String currentPlayer = "O";
    private Button[] buttons;
    private String gameMode;

    private final int[][] winPatterns = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };

    public void setGameMode(String mode) {
        this.gameMode = mode;
        if ("PVP".equals(mode)) {
            mnPVP.setSelected(true);
        } else {
            mnPVB.setSelected(true);
        }
    }

    @FXML
    private void initialize(){
        buttons = new Button[]{btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8};
        for(Button b: buttons){
            b.setFocusTraversable(false);
        }
    }

    @FXML
    protected void handleCellClick(ActionEvent event) {
        Button clicked = (Button) event.getSource();

        if (currentPlayer.equals("O")){
            clicked.setText("O");
            currentPlayer = "X";
            if (gameMode.equals("PVB")){
                botMove();
            }
        }
        else{
            clicked.setText("X");
            currentPlayer = "O";
        }
        clicked.setDisable(true);

        if (checkWin()) {
            showAlert('"' + clicked.getText() + '"' + " wins!");
            for (Button b : buttons) {
                b.setDisable(true);
            }
        } else if (isBoardFull()) {
            showAlert("Draw!");
        }

    }

    @FXML
    private void handleNewGame() {
        clearBoard();
    }

    private void clearBoard(){
        for(Button b: buttons){
            b.setText("");
            b.setStyle("");
            b.setDisable(false);
        }
        currentPlayer = "O";
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleAbout() {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("Noughts and crosses\n" +
                "Author: Marcin Domżalski");
        about.setContentText("This is a simple noughts and crosses game with single player and player versus player modes");
        Stage stage = (Stage) about.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("/icons/NoughtsAndCrossesIcon.png"))));
        about.show();
    }

    @FXML
    private void handlePVP() {
        setGameMode("PVP");
        clearBoard();
    }

    @FXML
    private void handlePVB() {
        setGameMode("PVB");
        clearBoard();
    }

    @FXML
    private boolean checkWin() {
        for (int[] pattern : winPatterns) {
            String t1 = buttons[pattern[0]].getText();
            String t2 = buttons[pattern[1]].getText();
            String t3 = buttons[pattern[2]].getText();

            if (!t1.isEmpty() && t1.equals(t2) && t2.equals(t3)) {
                buttons[pattern[0]].setStyle("-fx-text-fill: red;");
                buttons[pattern[1]].setStyle("-fx-text-fill: red;");
                buttons[pattern[2]].setStyle("-fx-text-fill: red;");
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (Button b : buttons) {
            if (b.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText(null);
        alert.setContentText(message + "\nFor new game press F1");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("/icons/NoughtsAndCrossesIcon.png"))));
        alert.showAndWait();
    }

    private void botMove(){
        if(!checkWin()){
            for(int[] pattern: winPatterns){
                int countX = 0;
                int emptyIndex = -1;

                for(int i = 0; i<3; i++){
                    String value = buttons[pattern[i]].getText();
                    if (value.equals("X")) countX++;
                    else if (value.isEmpty()) emptyIndex = pattern[i];
                }

                if (countX == 2 && emptyIndex != -1) {
                    buttons[emptyIndex].setText("X");
                    buttons[emptyIndex].setDisable(true);
                    currentPlayer = "O";
                    return;
                }

            }

            for(int[] pattern: winPatterns){
                int countO = 0;
                int emptyIndex = -1;

                for(int i = 0; i<3; i++){
                    String value = buttons[pattern[i]].getText();
                    if (value.equals("O")) countO++;
                    else if (value.isEmpty()) emptyIndex = pattern[i];
                }

                if (countO == 2 && emptyIndex != -1) {
                    buttons[emptyIndex].setText("X");
                    buttons[emptyIndex].setDisable(true);
                    currentPlayer = "O";
                    return;
                }

            }

            if(btn4.getText().isEmpty()){
                btn4.setText("X");
                btn4.setDisable(true);
                currentPlayer = "O";
                return;
            }

            List<Integer> availableFields = new ArrayList<>();

            for(int idx: new int[]{0,2,6,8}) if(buttons[idx].getText().isEmpty()) availableFields.add(idx);
            for(int idx: new int[]{1,3,5,7}) if(buttons[idx].getText().isEmpty()) availableFields.add(idx);

            if(!availableFields.isEmpty()) {
                int randomIndex = new Random().nextInt(availableFields.size());
                int chosenIndex = availableFields.get(randomIndex);
                buttons[chosenIndex].setText("X");
                buttons[chosenIndex].setDisable(true);
                currentPlayer = "O";
            }
        }
    }
}