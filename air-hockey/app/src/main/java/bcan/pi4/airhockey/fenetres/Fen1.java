package bcan.pi4.airhockey.fenetres;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Fen1 {

        public Scene myScnene;

        public Fen1() throws IOException {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("1-Accueil.fxml"));
            myScnene = new Scene(root);
            myScnene.setRoot(root);


        }
    }
