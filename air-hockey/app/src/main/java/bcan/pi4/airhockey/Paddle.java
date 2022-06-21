package bcan.pi4.airhockey;

/**
 * La classe Paddle permettra de représenter les palets des joueurs.
 * Elle contiendra les méthodes permettant de limiter leur déplacement à leur partie de terrain respective.
 * Elle héritera de la classe sphère pour faciliter également la transition entre modèle physique et modèle graphique.
 */



public class Paddle extends Sphere  {

    /** L'attribut joueur permettra de savoir de quel joueur il s'agit
     * false = le joueur 2 situé en haut.
     * true = joueur 1 situé en bas.
     */
    private final boolean joueur;


    /** permet de gérer le score du joueur **/
    private int score;

    public Paddle(Vector2D velocity, Vector2D position, double mass, double radius,boolean joueur) {
        super(velocity, position, mass, radius);
        this.joueur = joueur;
    }

    public Paddle(double mass, double radius, boolean joueur) {
        super(new Vector2D(), new Vector2D(), mass, radius);
        this.joueur = joueur;
        this.score = 0;


    }

    /**
     * incrémente le score quand le joueur a marqué un but.
     */
    public void scored(){
        this.score++;
    }


    /**
     * Fonction permettant de limiter les déplacements sur le terrain en fonction de la valeur de joueur.
     * @param gameField sert à nous donner les dimensions du terrain.
     */
    public void updatePlayerPuckPositionAfterCollision(GameField gameField){

        if (joueur) {
            if(collidesWithTopPlayer1(gameField)) getPosition().setY(getPosition().getY().getValue() + (gameField.getHeight().getValue()/2 - (getPosition().getY().getValue() - getRadius().getValue())));
            if(collidesWithBottomPlayer1(gameField)) getPosition().setY(gameField.getHeight().getValue() - getRadius().getValue());
        }
        else {
            if(collidesWithTopPlayer2()) getPosition().setY(getRadius().getValue());

            if(collidesWithBottomPlayer2(gameField)) getPosition().setY(gameField.getHeight().getValue()/2 - getRadius().getValue());
        }

        if(collidesWithRightBorder(gameField)) getPosition().setX(gameField.getWidth().getValue() - getRadius().getValue());
        if(collidesWithLeftBorder()) getPosition().setX(getRadius().getValue());
    }



    /**
     * @param gameField  le terrain de jeu avec ses caractéristiques de hauteur et de largeur
     * @return vrai si le palet du joueur 1 dépasse la hauteur maximale du terrain de jeu, faux sinon.
     */
    private boolean collidesWithBottomPlayer1(GameField gameField) {
        return ((getPosition().getY().getValue() + getRadius().getValue() > gameField.getHeight().getValue()));
    }

    /**
     * @param gameField terrain de jeu avec ses caractéristiques de hauteur et de largeur
     * @return vrai si le palet du joueur 1 dépasse la hauteur minimale de sa partie de terrain qui est la moitié de la hauteur de GameField
     * faux sinon.
     */

    private boolean collidesWithTopPlayer1(GameField gameField){
        return ((getPosition().getY().getValue() - getRadius().getValue() < gameField.getHeight().getValue()/2 ));
    }

    /**
     * @return vrai si le palet du joueur2 dépasse la hauteur minimale du terrain de jeu qui est 0
     * faux sinon.
     */
    private boolean collidesWithTopPlayer2(){
        return (getPosition().getY().getValue() - getRadius().getValue() < 0);
    }

    /**
     * @param gameField terrain de jeu avec ses caractéristiques de hauteur et de largeur
     * @return vrai si le palet du joueur2 dépasse la hauteur maximale de sa partie de terrain qui est la moitié de la hauteur de GameField
     * faux sinon.
     */
    private boolean collidesWithBottomPlayer2(GameField gameField){
        return (getPosition().getY().getValue() + getRadius().getValue() > gameField.getHeight().getValue() /2 );
    }

    /**
     *
     * @param gameField  sert ici  à évaluer la largeur de la fenêtre
     * @return vrai si le palet d'un des joueurs dépasse la largeur du terrain de jeu
     * faux sinon.
     */
    private boolean collidesWithRightBorder(GameField gameField) {
        return ((getPosition().getX().getValue() + getRadius().getValue() > gameField.getWidth().getValue()));
    }

    /**
     *
     * @return vrai si le palet dépasse la largeur minimale du terrain vers la gauche.
     * faux sinon.
     */
    private boolean collidesWithLeftBorder(){
        return ((getPosition().getX().getValue() - getRadius().getValue() < 0));
    }
    /**
     *
     * @param gameField permet ici de nous donner les positions de départ des joueurs par rapport aux dimensions
     *                  du terrain de jeu.
     *      Le joueur 1 sera placé en bas et au milieu de l'écran.
     *      Le joueur 2 sera placé en haut et au milieu de l'écran.
     *
     * On veillera à reinitialiser toutes les vitesses si elle ne le sont pas déjà.
     */
    public void resetPositionAndVelocity(GameField gameField){

        if (joueur){
            this.getPosition().is(new Vector2D(gameField.getWidth().get()/2,gameField.getHeight().get() - getRadius().get()));
        } else
        {
            this.getPosition().is(new Vector2D(gameField.getWidth().get()/2,getRadius().get()));
        }
        if (this.getVelocity().isZero()){
            resetVelocity();

        }
    }

    /********************************************************************************
                            Getters :
     ******************************************************************************* */

    public boolean isJoueur() {
        return joueur;
    }

    public int getScore() {
        return score;
    }

}

