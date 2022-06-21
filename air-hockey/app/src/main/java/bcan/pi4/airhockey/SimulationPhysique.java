package bcan.pi4.airhockey;

import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * La classe SimulationPhysique de gérer la physique de ce projet.
 * Elle contiendra des méthodes permettant de vérifier les collisions entre les palets et la balle.
 * Elle s'occupera du lien entre modèle physique et modèle graphique, car appelée par ce dernier.
 * Elle actualisera le modèle physique à chaque frame.
 */

public class SimulationPhysique {
    /**
     * Force de friction permettant au GamePuck de ne pas bouger indéfiniment
     * si aucun choc n'est détecté pour une certaine periode de temps.
     * Il peut être désactivé, car le but du jeu est également de taper dedans pour scorer.
     */
    private static final double friction = 0.999;

    /**
     * Représente le terrain de jeu
     */
    private final GameField gameField;

    /**
     * Représente le disque du jeu
     */
    private final GamePuck gamePuck;

    /**
     * Représente le joueur numéro 1 situé au bas de l'écran
     */
    private final Paddle player_1;

    /**
     * Représente le joueur numéro 2 situé au en haut de l'écran
     */
    private final Paddle player_2;

    /**
     * Représente la liste des joueurs
     */
    private final ArrayList<Paddle> players = new ArrayList<>();

    /**
     * Représente le temps qui s'est écoulé entre deux frames différentes
     */
    private double deltaT;

    public SimulationPhysique(GameField gameField, GamePuck gamePuck, Paddle player_1, Paddle player_2) {
        this.gameField = gameField;
        this.gamePuck = gamePuck;
        this.player_1 = player_1;
        this.player_2 = player_2;
        players.add(player_1);
        players.add(player_2);

    }

    /**
     * Méthode qui va servir à :
     * - vérifier les collisions entre les palets et le disque
     * - vérifier les collisions entre le mur et le disque
     * - actualiser la position des palets et du disque en tenant compte de leur limite de déplacements.
     * - Vérifier si un joueur a marqué un but, si oui repositionner tous les objets physiques.
     *
     * @param deltaT sert à actualiser le modèle physique avec un temps infinitésimal.
     */
    public void run(double deltaT) {
        this.deltaT = deltaT;
        checkIfCollideWithGamePuck();
        checkIfCollideWithWalls();
        updatePaddlesPosition(gameField);
        gamePuck.updatePosition(friction, deltaT);

        if (someoneScored(gameField,player_1,player_2)) resetAll();
        System.out.println("Player1 score = " + player_1.getScore());
        System.out.println("Player2 score = " + player_2.getScore());



    }

    public boolean someoneScored(GameField gameField,Paddle player_1, Paddle player_2){
        return gamePuck.checkIfScored(gameField,player_1,player_2);
    }

    /**
     * Méthode qui va appeler contraindre les déplacements des palets pour les deux joueurs.
     */
    public void updatePaddlesPosition(GameField gameField) {
        for (Paddle p : players) {
            p.updatePlayerPuckPositionAfterCollision(gameField);
        }
    }

    /**
     * Méthode fondamentale qui va gérer les collisions avec le disque.
     * Si un disque rentre à l'intérieur d'un palet entre une frame f et la suivante f+1 : alors on séparera les deux objets
     * avant d'effectuer le calcul de choc.
     */
    public void checkIfCollideWithGamePuck() {
        for (Paddle player : players) {
            if (gamePuck.overlapsWithPlayer(player)) {
                gamePuck.resolveOverlapping(player);

                gamePuck.resolveCollision(player);


            }
        }
    }

    /**
     * Méthode permettant de repositionner le disque potentiellement bloqué dans un mur du à la non rigidité de nos objets.
     * Elle changera sa vitesse par la suite.
     */
    public void checkIfCollideWithWalls() {
        gamePuck.resolveOverlappingWithWall(gameField, deltaT);
        gamePuck.updateVelocityAfterGameFieldCollision(gameField);

    }

    /**
     * on remet les objets physiques à leur position de départ et on met leur vitesse à 0.
     * Méthode permettant de gérer ce qui se passe après un but.
     */
    public void resetAll(){
        this.gamePuck.resetPositionAndVelocity(gameField);
        this.player_1.resetPositionAndVelocity(gameField);
        this.player_2.resetPositionAndVelocity(gameField);
    }

    /** on met la vitesse à 0 pour les deux palets */
    public void resetPaddleVelocities() {
        player_1.resetVelocity();
        player_2.resetVelocity();
    }

    public void getPaddleVelocities(MouseEvent mouseEvent) {

        Vector2D mousePosition = new Vector2D(mouseEvent.getSceneX(), mouseEvent.getSceneY());


        // si la souris est dans la partie du joueur 1 alors on calcule la vitesse du joueur 1.
        if (mouseEvent.getSceneY() > gameField.getHeight().getValue() / 2) {
            Vector2D newVelocity = (mousePosition.sub(player_1.getPosition())).multiplyBy(1 / deltaT);
            player_1.getPosition().is(mousePosition);
            player_1.getVelocity().is(newVelocity);

        } else {
            Vector2D newVelocity = (mousePosition.sub(player_2.getPosition())).multiplyBy(1 / deltaT);
            player_2.getPosition().is(mousePosition);
            player_2.getVelocity().is(newVelocity);
        }

    }

}