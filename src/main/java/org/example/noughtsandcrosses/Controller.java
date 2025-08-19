package org.example.noughtsandcrosses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;

public class Controller {
    @FXML private Button btn1;
    @FXML private Button btn2;
    @FXML private Button btn3;
    @FXML private Button btn4;
    @FXML private Button btn5;
    @FXML private Button btn6;
    @FXML private Button btn7;
    @FXML private Button btn8;
    @FXML private Button btn9;

    @FXML private RadioMenuItem mnPVP;
    @FXML private RadioMenuItem mnPVB;

    private String currentPlayer = "O";
    private Button[] buttons;
    private String gameMode;

    public void setGameMode(String mode) {
        this.gameMode = mode;
        if ("PVP".equals(mode)) {
            mnPVP.setSelected(true);
        } else {
            mnPVB.setSelected(true);
        }
        System.out.println("Game mode set to " + this.gameMode);
    }

    @FXML
    private void initialize(){
        buttons = new Button[]{btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
        for(Button b: buttons){
            b.setFocusTraversable(false);
        }
    }

    @FXML
    protected void handleCellClick(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        System.out.println(clicked.getId() + " Cell clicked");

        if (currentPlayer.equals("O")){
            clicked.setText("O");
            currentPlayer = "X";
        }
        else{
            clicked.setText("X");
            currentPlayer = "O";
        }
        clicked.setDisable(true);

        if (checkWin()) {
            System.out.println("Wygrywa: " + clicked.getText());
            showAlert("Wygrywa: " + clicked.getText());
            for (Button b : buttons) {
                b.setDisable(true);
            }
        } else if (isBoardFull()) {
            System.out.println("Remis!");
            showAlert("Remis");
        }

    }

    @FXML
    private void handleNewGame(ActionEvent event) {
        System.out.println("New game clicked");
        clearBoard();
    }

    private void clearBoard(){
        for(Button b: buttons){
            b.setText("");
            b.setDisable(false);
        }
        currentPlayer = "O";
        System.out.println("Board cleared");
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.out.println("Exit clicked");
        System.exit(0);
    }

    @FXML
    private void handlePVP(ActionEvent event) {
        RadioMenuItem clicked = (RadioMenuItem) event.getSource();
        System.out.println("Checked " + clicked.getText());
        setGameMode("PVP");
        clearBoard();
    }

    @FXML
    private void handlePVB(ActionEvent event) {
        RadioMenuItem clicked = (RadioMenuItem) event.getSource();
        System.out.println("Checked " + clicked.getText());
        setGameMode("PVB");
        clearBoard();
    }

    @FXML
    private boolean checkWin() {
        int[][] winPatterns = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8},
                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},
                {0, 4, 8},
                {2, 4, 6}
        };
        for (int[] pattern : winPatterns) {
            String t1 = buttons[pattern[0]].getText();
            String t2 = buttons[pattern[1]].getText();
            String t3 = buttons[pattern[2]].getText();

            if (!t1.isEmpty() && t1.equals(t2) && t2.equals(t3)) {
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
        alert.setTitle("Koniec gry");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}