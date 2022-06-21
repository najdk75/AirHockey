package bcan.pi4.airhockey;

import bcan.pi4.airhockey.fenetres.*;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private GameController game;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleClose(javafx.scene.input.MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void handlePartie(javafx.scene.input.MouseEvent event){

        GameController game=new GameController();

        Stage window= (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(game.myScene);
        window.show();

    }
    public void fenetre1(javafx.scene.input.MouseEvent event) throws IOException {

        Fen1 fen =new Fen1();

        Stage window= (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(fen.myScnene);
        window.show();

    }
    public void fenetre3(javafx.scene.input.MouseEvent event) throws IOException {

        Fen3 fen =new Fen3();

        Stage window= (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(fen.myScnene);
        window.show();

    }

    public void fenetre4(javafx.scene.input.MouseEvent event) throws IOException {

        Fen4 fen =new Fen4();

        Stage window= (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(fen.myScnene);
        window.show();

    }
    public void fenetre5(javafx.scene.input.MouseEvent event) throws IOException {

        Fen5 fen =new Fen5();

        Stage window= (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(fen.myScnene);
        window.show();

    }

    public void fenetre6(javafx.scene.input.MouseEvent event) throws IOException {

        Fen6 fen =new Fen6();

        Stage window= (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(fen.myScnene);
        window.show();

    }


}