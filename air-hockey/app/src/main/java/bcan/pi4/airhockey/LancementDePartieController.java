package bcan.pi4.airhockey;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LancementDePartieController implements Initializable {
    @FXML
    Button StartGameButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleStartGame(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("6-PlateauDeJeu.fxml"));
        Stage window=(Stage)StartGameButton.getScene().getWindow();
        window.setScene(new Scene(root,730,500));

    }
}
