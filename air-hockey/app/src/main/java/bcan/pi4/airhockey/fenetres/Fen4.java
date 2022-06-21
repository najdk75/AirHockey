package bcan.pi4.airhockey.fenetres;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Fen4 {
    public Scene myScnene;

    public Fen4() throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("4-rechercheManuelle.fxml"));
        myScnene = new Scene(root);
        myScnene.setRoot(root);


    }
}