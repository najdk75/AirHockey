package bcan.pi4.airhockey.fenetres;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Fen2 {
    public Scene myScnene;

    public Fen2() throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("2-choix_map.fxml"));
        myScnene = new Scene(root);
        myScnene.setRoot(root);


    }
}
