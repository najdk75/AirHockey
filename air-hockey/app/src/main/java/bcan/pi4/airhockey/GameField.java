package bcan.pi4.airhockey;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * La classe GameField représente le terrain de jeu
 * Elle permettra de faire le lien entre le modèle graphique et le modèle physique.
 * En effet les attributs de cette classe serviront à l'établissement des dimensions de sa fenêtre associée.
 * D'où l'utilisation des Properties.
 */

public class GameField {

    /** Représente la largeur du terrain de jeu qui sera de 500px **/
    private final DoubleProperty width;

    /** Représente la largeur du terrain de jeu qui sera de 800px */
    private final DoubleProperty height;

    /**
     * goal_1 représentera les buts du haut.
     * goal_2 représentera les buts du bas.
     * Chacun est un tableau de vecteur de deux coordonnées:
     * en effet goal_i[0] representera les coordonnées du début du goal et goal_i[1]
     * les coordonnées de la fin du goal. pour i = 1,2.
     *
     * on aura la configuration suivante :
     *
     *                         (100,0)      (400,0)
     *              100px                           100px
                  |--------|                    |---------|
                  |                                       |
                  |                                       |
                  |                                       | height = 800px                                      |
                  |                                       |
                  |                                       |
                  |                                       |
                  |--------|                    |---------|

                        (100,height)        (400,height)
     */

    private final int xStartGoal = 100;
    private final int xEndGoal = 400;



    public GameField(){
        this.width = new SimpleDoubleProperty(500);
        this.height = new SimpleDoubleProperty(800);

    }



    /********************************************************************************
     *                          Getters
     ******************************************************************************* */


    public final DoubleProperty getWidth() {
        return width;
    }

    public final DoubleProperty getHeight() {
        return height;
    }

    public int getxStartGoal() {
        return xStartGoal;
    }

    public int getxEndGoal() {
        return xEndGoal;
    }
}
