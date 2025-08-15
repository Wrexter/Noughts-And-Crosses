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

    @FXML
    protected void handleCellClick(ActionEvent event) {
        //Button clicked = (Button) event.getSource();
        System.out.println("Cell clicked");
        //logika ruchu
    }

    @FXML
    private void handleNewGame(ActionEvent event) {
        System.out.println("New game clicked");
        //reset planszy
    }
}