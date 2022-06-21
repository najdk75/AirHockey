/*package bcan.pi4.airhockey;

import java.util.concurrent.atomic.AtomicReference;

// prototype simple du jeu à faire impérativement.

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.concurrent.atomic.AtomicReference;

// prototype simple du jeu à faire impérativement.

public class AirHockey extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var gamePane = new GameField();
        var myScene = new Scene(gamePane, 500, 800);

        Parent root = FXMLLoader.load(getClass().getResource("menuPage.fxml"));

        var menuScene =new Scene(root,730,500);

        myScene.setRoot(gamePane);


        //gamePane.setOnMouseMoved(this::move);

        //gamePane.getChildren().addAll(c,c1);
        //gamePane.setOnMouseClicked(this::showCoordonnee);




        //gamePane.getChildren().addAll(gameField, gameField2);
        primaryStage.setTitle("AIR HOCKEY GAME");
        primaryStage.setScene(myScene);
        primaryStage.centerOnScreen();
        primaryStage.show();

    }

    private void showCoordonnee(MouseEvent event) {
        //System.out.println("EventX = " + event.getX() + "\nEventY = " + event.getY() );

    }


    public class GameField extends StackPane {
        public Puck player1, player2, gamePuck;

        public GameField() {
            initialisationPuck();
            setPrefSize(500, 800);
            this.getChildren().addAll(player1, player2, gamePuck);


            setAlignment(player1, Pos.BOTTOM_CENTER);
            setAlignment(player2, Pos.TOP_CENTER);
            setAlignment(gamePuck, Pos.CENTER);

            player1.setOnMouseDragged(this::move);
            player2.setOnMouseDragged(this::move);

        }

        public void move(MouseEvent event) {
            var puckPlayer = (Puck) event.getSource();
            if (puckPlayer.equals(player1)) {
                puckPlayer.setTranslateX(puckPlayer.getTranslateX() + event.getX());
                puckPlayer.setTranslateY(puckPlayer.getTranslateY() + event.getY());

                resetBoundsPlayer1();
            } else {
                puckPlayer.setTranslateX(puckPlayer.getTranslateX() + event.getX());
                puckPlayer.setTranslateY(puckPlayer.getTranslateY() + event.getY());

                resetBoundsPlayer2();

            }

        }

        public void run(){
            System.out.println("Game Puck Radius = " + gamePuck.getRadius());

            if (gamePuck.collide(player1)){
                System.out.println("Game Puck Radius = " + gamePuck.getRadius());
                System.out.println("Collision!!");
                gamePuck.collision(player1);
            };
            if (gamePuck.collide(player2)){
                System.out.println("Collision!!");
                gamePuck.collision(player2);
            }
        }
        private void resetBoundsPlayer1() {
            if (player1.getBoundsInParent().getMinX() < 0) {
                player1.setTranslateX(player1.getTranslateX() - player1.getBoundsInParent().getMinX());
            }
            if (player1.getBoundsInParent().getMaxX() > this.getWidth()) {
                player1.setTranslateX(player1.getTranslateX() - (player1.getBoundsInParent().getMaxX() - this.getWidth()));
            }

            if (player1.getBoundsInParent().getMinY() < this.getHeight() / 2) {
                player1.setTranslateY(player1.getTranslateY() + (this.getHeight() / 2) - player1.getBoundsInParent().getMinY() );
            }

            if (player1.getBoundsInParent().getMaxY() > this.getHeight()) {
                player1.setTranslateY(player1.getTranslateY() - (player1.getBoundsInParent().getMaxY() - this.getHeight()));
            }
        }

        private void resetBoundsPlayer2() {
            if (player2.getBoundsInParent().getMinX() < 0) {
                player2.setTranslateX(player2.getTranslateX() - player2.getBoundsInParent().getMinX());
            }
            if (player2.getBoundsInParent().getMaxX() > this.getWidth()) {
                player2.setTranslateX(player2.getTranslateX() - (player2.getBoundsInParent().getMaxX() - this.getWidth()));
            }

            if (player2.getBoundsInParent().getMinY() < 0) {
                player2.setTranslateY(player2.getTranslateY() - player2.getBoundsInParent().getMinY());
            }

            if (player2.getBoundsInParent().getMaxY() > this.getHeight() / 2) {
                player2.setTranslateY(player2.getTranslateY() - (player2.getBoundsInParent().getMaxY() - this.getHeight() / 2));
            }
        }

        public void initialisationPuck() {
            this.player1 = new Puck(new Vector2D(0, 0), new Vector2D(0, 0), 1, 30);
            player1.setFill(Color.GREEN);
            this.player2 = new Puck(new Vector2D(0, 0), new Vector2D(0, 0), 1, 30);
            player2.setFill(Color.BLUE);
            this.gamePuck = new Puck(new Vector2D(0, 0), new Vector2D(0, 0), 1, 25);

        }


        private void resetBounds(Puck c) {
            if (c.getBoundsInParent().getMinX() < 0) {
                c.setTranslateX(c.getTranslateX() - c.getBoundsInParent().getMinX());
            }

            if (c.getBoundsInParent().getMaxX() > this.getWidth()) {
                c.setTranslateX(c.getTranslateX() - (c.getBoundsInParent().getMaxX() - this.getWidth()));
            }

            if (c.getBoundsInParent().getMinY() < 0) {
                c.setTranslateY(c.getTranslateY() - c.getBoundsInParent().getMinY());
            }

            if (c.getBoundsInParent().getMaxY() > this.getHeight()) {
                c.setTranslateY(c.getTranslateY() - (c.getBoundsInParent().getMaxY() - this.getHeight()));
            }
        }

    }


}

 */
package bcan.pi4.airhockey;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


// prototype simple du jeu à faire impérativement.

public class AirHockey extends Application {

    public String getGreeting() {
        return "Bienvenue et bonne chance!";
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root =FXMLLoader.load(getClass().getClassLoader().getResource("1-Accueil.fxml"));
        var menuScene =new Scene(root,1060,853);

        primaryStage.setTitle("AIR HOCKEY GAME");
        primaryStage.setScene(menuScene);

        primaryStage.centerOnScreen();
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }


}
