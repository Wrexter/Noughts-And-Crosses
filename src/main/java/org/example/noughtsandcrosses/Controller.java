package org.example.noughtsandcrosses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private void botMove(){
        if(!checkWin()){
            for(int[] pattern: winPatterns){
                int countX = 0;
                int emptyIndex = -1;
                int countO = 0;

                for(int i = 0; i<3; i++){
                    String value = buttons[pattern[i]].getText();
                    if (value.equals("X")) countX++;
                    else if (value.equals("O")) countO++;
                    else if (value.isEmpty()) emptyIndex = pattern[i];
                }

                if (countX == 2 && emptyIndex != -1) {
                    buttons[emptyIndex].setText("X");
                    buttons[emptyIndex].setDisable(true);
                    currentPlayer = "O";
                    return;
                }

                else if (countO == 2 && emptyIndex != -1) {
                    buttons[emptyIndex].setText("X");
                    buttons[emptyIndex].setDisable(true);
                    currentPlayer = "O";
                    return;
                }
            }


            if(btn5.getText().isEmpty()){
                btn5.setText("X");
                btn5.setDisable(true);
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

            /*
            if(btn5.getText().isEmpty()){
                btn5.setText("X");
                btn5.setDisable(true);
                currentPlayer = "O";
                return;
            }

            int[] corners = {0, 2, 6, 8};
            for (int idx : corners) {
                if (buttons[idx].getText().isEmpty()) {
                    buttons[idx].setText("X");
                    buttons[idx].setDisable(true);
                    currentPlayer = "O";
                    return;
                }
            }

            int[] sides = {1, 3, 5, 7};
            for (int idx : sides) {
                if (buttons[idx].getText().isEmpty()) {
                    buttons[idx].setText("X");
                    buttons[idx].setDisable(true);
                    currentPlayer = "O";
                    return;
                }
            }
             */



        }

    }
}