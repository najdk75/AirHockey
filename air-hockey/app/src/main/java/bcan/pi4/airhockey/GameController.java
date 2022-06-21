package bcan.pi4.airhockey;

import javafx.animation.AnimationTimer;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    Scene myScene;
    SimulationPhysique simulation;
    GamePuck gamePuck;
    Paddle player_1, player_2;
    GameField gameField;
    StackPane gamePane;
    Circle gui_Game_Puck, gui_1_Puck, gui_2_Puck;
    Converter converter = new Converter();
    GameLoop gameLoop;


    //ouvrir la fenetre du jeu

    public GameController() {
        gameField = new GameField();

        initializePucksAndCircle();
        bindProperties();
        initializeGamePane();
        setGamePaneListeners();


        myScene = new Scene(gamePane, gameField.getWidth().getValue(), gameField.getHeight().getValue());
        myScene.setRoot(gamePane);
        gamePane.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY, Insets.EMPTY)));



        simulation = new SimulationPhysique(gameField, gamePuck, player_1, player_2);
        gameLoop = new GameLoop();
        gameLoop.start();


    }


    /**
     * Méthode servant à lier les propriétés des objets physiques aux objets graphiques afin de constamment actualiser leur position
     */
    private void bindProperties() {
        converter.bindProperties(gamePuck, gui_Game_Puck);
        converter.bindProperties(player_1, gui_1_Puck);
        converter.bindProperties(player_2, gui_2_Puck);

    }

    private void initializeGamePane() {
        gamePane = new StackPane();
        gamePane.setPrefSize(gameField.getWidth().getValue(), gameField.getHeight().getValue());
        gamePane.getChildren().addAll(gui_Game_Puck, gui_1_Puck, gui_2_Puck);


    }

    /**
     * Initialisation des palets et disque graphiques à leur bonne position.
     * Les caractéristiques sphériques du modèle physique sont automatiquement traduit dans le modèle graphique.
     */
    private void initializePucksAndCircle() {

        player_1 = new Paddle(1.5,40,true);
        gui_1_Puck = new Circle(player_1.getRadius().getValue());
        gui_1_Puck.setFill(new ImagePattern(new Image("maquette/images/poussoir_rouge.png")));;


        player_2 = new Paddle(1.5,40,false);
        gui_2_Puck = new Circle(player_2.getRadius().getValue());
        gui_2_Puck.setFill(new ImagePattern(new Image("maquette/images/poussoir_bleu.png")));

        gamePuck = new GamePuck(1,25);
        gui_Game_Puck = new Circle(gamePuck.getRadius().getValue());
        gui_Game_Puck.setFill(new ImagePattern(new Image("maquette/images/pallet.png")));
        positionObjectsOnPane(gameField);
    }

    private void positionObjectsOnPane(GameField gameField) {
        player_1.resetPositionAndVelocity(gameField);
        player_2.resetPositionAndVelocity(gameField);
        gamePuck.resetPositionAndVelocity(gameField);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    /**
     * Méthode permettant d'appliquer les mouvements des Paddles
     * Si la souris se trouve vers le haut alors c'est le joueur 2 qui sera contrôlé
     * Si elle se trouve vers le bas alors c'est le joueur 1 qui le sera.
     * On calculera la vitesse des palets en fonction du deltaT calculé dans la simulation.
     * Ainsi la vitesse d'un palet sera :
     *                      p(t + dT) - p(t)
     *                  v = ---------------
     *                          dt
     * où p(t) est la position du palet au temps t.
     * <p>
     * On fera aussi en sorte que la vitesse du palet soit mise à 0 lorsque la souris sort du terrain de jeu. (à voir si utile en faisant des tests)
     */
    public void setGamePaneListeners() {

        gamePane.setOnMouseMoved(mouseEvent -> simulation.getPaddleVelocities(mouseEvent));

        gamePane.setOnMouseExited(mouseEvent -> simulation.resetPaddleVelocities());
    }

    private class GameLoop extends AnimationTimer {

        private long previousNanoTime = System.nanoTime(); // temps actuel en nanosecondes

        public void handle(long currentNanoTime) {

            double deltaTime = (currentNanoTime - previousNanoTime) / 1_000_000_000f; // Le deltaT sera le temps en secondes, écoulé entre deux frames.
            simulation.run(deltaTime);
            previousNanoTime = currentNanoTime;

        }
    }

}



