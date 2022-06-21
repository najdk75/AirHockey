package bcan.pi4.airhockey.fenetres;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Fen5 {
    public Scene myScnene;

    public Fen5() throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("5-lancementDePartie.fxml"));
        myScnene = new Scene(root);
        myScnene.setRoot(root);


    }
}
