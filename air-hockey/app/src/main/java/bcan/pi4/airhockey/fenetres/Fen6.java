package bcan.pi4.airhockey.fenetres;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Fen6 {

    public Scene myScnene;

    public Fen6() throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Plateau.fxml"));
        myScnene = new Scene(root);
        myScnene.setRoot(root);


    }
}
