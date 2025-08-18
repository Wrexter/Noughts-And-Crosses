package org.example.noughtsandcrosses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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


    private String currentPlayer = "O";
    private Button[] buttons;

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

    }

    @FXML
    private void handleNewGame(ActionEvent event) {
        System.out.println("New game clicked");
        for(Button b: buttons){
            b.setText("");
            b.setDisable(false);
        }
        currentPlayer = "O";
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.out.println("Exit clicked");
        System.exit(0);
    }
}