package bcan.pi4.airhockey;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class PlateauDeJeuController implements Initializable {
    @FXML
    Pane game;

    GameController gameController=new GameController();
    public void handlePause(MouseEvent mouseEvent) {
    }

    public void handleQuit(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game.getChildren().add(gameController.gamePane);
    }
    public void handleClose(javafx.scene.input.MouseEvent mouseEvent) {
        System.exit(0);
    }

}
