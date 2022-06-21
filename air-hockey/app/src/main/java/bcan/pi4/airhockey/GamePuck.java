package bcan.pi4.airhockey;

public class GamePuck extends Sphere {
    /**
     * La classe GamePuck permettra de représenter le disque du jeu.
     *
     * Elle contiendra des  méthodes permettant :
     *  - Actualiser la position du disque avec un dt donné.
     *  - Vérifier si le disque entre en collision avec un mur.
     *  - Vérifier si le disque n'est pas bloqué dans un mur, si oui, le repositionner puis changer sa vitesse
     *  - Vérifier si le disque n'est pas coincé dans un autre disque, si oui le repositionner
     *  - Vérifier si le disque entre en collision avec un palet.
     *  - Calculer sa nouvelle vitesse grâce aux lois des chocs élastiques
     *  - D'appliquer la friction donné par le modèle physique
     */

    /**
     * Constante permettant de limiter la vitesse du disque
     * car celle-ci, si trop élevée, rendrait notre jeu sans intérêt.
     */
    public final double MAX_VELOCITY = 1200;


    public GamePuck(Vector2D velocity, Vector2D position, double mass, double radius) {
        super(velocity, position, mass, radius);
    }

    public GamePuck(double mass, double radius) {
        super(new Vector2D(), new Vector2D(), mass, radius);
    }

    /**
     * Fonction permettant d'actualiser la position du disque selon un dt et d'appliquer la friction par la suite.
     *
     * @param friction  donné par le modèle physique
     * @param deltaTime dt permettant d'actualiser la position du disque
     */
    public void updatePosition(double friction, double deltaTime) {

        getPosition().is(getPosition().add(getVelocity().multiplyBy(deltaTime)));
        applyFrictionOnPuck(friction);
    }

    /**
     * Applique la friction sur la vitesse du disque
     *
     * @param friction
     */
    public void applyFrictionOnPuck(double friction) {
        getVelocity().multiplyBy(friction);
    }

    /**
     * Change la vitesse du disque selon le type de collision.
     * La vitesse du disque sera inversée selon le mur qu'il aura percuté.
     *
     * @param gameField
     */
    public void updateVelocityAfterGameFieldCollision(GameField gameField) {
        if (collidesWithLeftOrRightWall(gameField)) {
            getVelocity().setX(-getVelocity().getX().getValue());
        }
        if (collidesWithTopOrBottomWall(gameField)) {
            getVelocity().setY(-getVelocity().getY().getValue());
        }

    }

    /**
     * Méthode servant à vérifier si le disque a percuté le mur d'en bas ou d'en haut.
     *
     * @param gameField
     * @return vrai si elle a percuté, faux sinon.
     */
    private boolean collidesWithTopOrBottomWall(GameField gameField) {

        return collidesWithBottomWall(gameField) || collidesWithTopWall(gameField);
    }
    
    private boolean collidesWithTopWall(GameField gameField){
       return (getPosition().getY().getValue() - getRadius().getValue() <= 0 && !player1Scored(gameField));
    }
    private boolean collidesWithBottomWall(GameField gameField){
        return (getPosition().getY().getValue() + getRadius().getValue() >= gameField.getHeight().getValue() && !player2Scored(gameField));
    }

    /**
     * Méthode servant à vérifier si le disque a percuté le mur de droite ou de gauche
     *
     * @param gameField
     * @return vrai si elle a percuté, faux sinon.
     */
    private boolean collidesWithLeftOrRightWall(GameField gameField) {
        return ((getPosition().getX().getValue() + getRadius().getValue() >= gameField.getWidth().getValue()) || (getPosition().getX().getValue() - getRadius().getValue() <= 0));
    }


    /**
     * Méthode servant à la résolution des chocs élastiques
     * Évidemment dans notre cas, seul la vitesse du disque changera.
     * On s'inspirera en grande partie de :
     * https://fr.wikipedia.org/wiki/Choc_élastique
     *
     * @param player represente le palet du joueur avec lequel le disque a eu un choc.
     *               Cependant quand le joueur bouge trop vite la souris, celle ci traversera le palet
     *               et effectuera un calcul de collision probablement inexact.
     *               à essayer d'améliorer.
     */

    public void resolveCollision(Paddle player) {

        // On calcule l'angle de collision entre les deux spheres grâce au théorème de pythagore
        double theta = Math.atan2(player.getPosition().getY().get() - this.getPosition().getY().get(), player.getPosition().getX().get() - this.getPosition().getX().get());

        // On calcule la norme des vitesses pour faciliter le calcul.
        double v1 = cos(theta) * this.getVelocity().getX().get() + sin(theta) * this.getVelocity().getY().get();
        double v2 = cos(theta) * player.getVelocity().getX().get() + sin(theta) * player.getVelocity().getY().get();

        double m1 = getMass();
        double m2 = player.getMass();

        // Calcul de la vitesse après le choc élastique.
        double v1_after = ((m1 - m2) * v1 + 2 * m2 * v2) / (m1 + m2);

        // on recalcule la vitesse de la balle avec un certain angle qu'elle devra suivre.
        double v1_Final = v1_after - v1;
        getVelocity().is(new Vector2D(cos(theta) * v1_Final, sin(theta) * v1_Final));


        // on limite la vitesse si celle-ci est trop grande après le calcul du choc.
        boundVelocity();

    }

    /**
     * @param player
     * @return la distance au carré entre un palet et le disque
     */
    private double distanceSquared(Paddle player) {
        return player.getPosition().distanceBetweenSquared(getPosition());
    }

    /**
     * @param gameField
     * @param deltaT    nous donnera une approximation pour la frame suivante, peut être inexacte vu que le deltaT
     *                  est différent à chaque frame.
     *                  <p>
     *                  Méthode permettant au disque de ne pas rester bloqué dans un mur du à la non continuité du modèle physique
     *                  en effet, celui ci s'actualisant par frame et non par temps continu comme dans la vraie vie,
     *                  il se peut qu'il puisse rester bloqué dans un mur et adopte des comportements étranges.
     *                  On vérifiera donc si la position du disque au temps t + dt est dans le mur, si c'est le cas on repositionnera
     *                  le disque proprement.
     */

    public void resolveOverlappingWithWall(GameField gameField, double deltaT) {

        Vector2D positionAfterUpdate = this.getPosition().add(this.getVelocity().multiplyBy(deltaT));



        if (overlapsAtRightWall(gameField, positionAfterUpdate)) resolveOverlappingRight(gameField);
        if (overlapsAtLeftWall(positionAfterUpdate)) resolveOverlappingLeft();
        if (overlapsAtTopWall(gameField, positionAfterUpdate)) resolveOverlappingTop();
        if (overlapsAtBottomWall(gameField, positionAfterUpdate)) resolveOverlappingBottom(gameField);
    }


    /********************************************************************************
     *                    Fonctions permettant de repositionner le disque proprement
     *                         suite à un overlapping.
     ******************************************************************************* */
    private void resolveOverlappingBottom(GameField gameField) {
        this.getPosition().setY(gameField.getHeight().getValue() - getRadius().getValue());
    }

    private void resolveOverlappingTop() {
        this.getPosition().setY(getRadius().getValue());
    }

    private void resolveOverlappingLeft() {
        this.getPosition().setX(getRadius().getValue());
    }

    private void resolveOverlappingRight(GameField gameField) {
        this.getPosition().setX(gameField.getWidth().getValue() - getRadius().getValue());
    }


    /********************************************************************************
     *                    Fonctions permettant de vérifier si le disque est coincé dans
     *                    le mur le temps t + dt.
     ******************************************************************************* */
    private boolean overlapsAtRightWall(GameField gameField, Vector2D positionAfterUpdate) {
        return (positionAfterUpdate.getX().getValue() + getRadius().get() > gameField.getWidth().getValue());
    }

    private boolean overlapsAtLeftWall(Vector2D positionAfterUpdate) {
        return positionAfterUpdate.getX().getValue() - getRadius().getValue() < 0;
    }

    private boolean overlapsAtTopWall(GameField gameField, Vector2D positionAfterUpdate) {
        return (positionAfterUpdate.getY().getValue() - getRadius().getValue() < 0 && !player1Scored(gameField));
    }

    private boolean overlapsAtBottomWall(GameField gameField, Vector2D positionAfterUpdate) {
        return (positionAfterUpdate.getY().getValue() + getRadius().getValue() > gameField.getHeight().getValue() && !player2Scored(gameField) );
    }


    /**
     * @param player represente le palet d'un joueur
     * @return vrai si le palet et le disque sont en interpenetration, faux sinon.
     */

    public boolean overlapsWithPlayer(Paddle player) {
        return (this.distanceSquared(player) < square(getRadius().getValue() + player.getRadius().getValue()));

    }

    /**
     * @param player représente le palet d'un joueur
     *               Méthode permettant de séparer les disques qui sont en interpenetration en prenant en compte la bonne direction à choisir.
     */
    public void resolveOverlapping(Paddle player) {

        double sumOfRadii = player.getRadius().getValue() + this.getRadius().getValue(); // Rayon du disque + rayon du palet du joueur
        Vector2D normalVector = player.getPosition().sub(this.getPosition()); // Vecteur normal reliant les centres respectifs des deux sphères
        double distance = normalVector.length(); // Distance entre les deux centres.
        Vector2D unitNormalVector = normalVector.normalize(); // vecteur normal unitaire
        double depth = Math.abs(distance - sumOfRadii) * 0.5; // la profondeur de l'overlap divisé par deux.

        /** séparation des deux disques en prenant en compte la direction du vecteur normal unitaire puis de la longueur de
         la profondeur    */

        player.getPosition().is(player.getPosition().add(unitNormalVector.multiplyBy(depth)));
        this.getPosition().is(getPosition().sub(unitNormalVector.multiplyBy(depth)));
    }

    /**
     * Ici on limitera la vitesse du disque afin d'éviter les comportements étranges.
     * Bien sûr on vérifiera si la valeur absolue de la vitesse du disque est supérieur à la valeur absolue
     * de la vitesse maximale définie préalablement.
     */
    public void boundVelocity() {
        if (getVelocity().getX().get() > MAX_VELOCITY) {
            getVelocity().setX(MAX_VELOCITY);
        }
        if (getVelocity().getY().get() > MAX_VELOCITY) {
            getVelocity().setY(MAX_VELOCITY);
        }
        if (getVelocity().getX().get() < -MAX_VELOCITY) {
            getVelocity().setX(-MAX_VELOCITY);
        }
        if (getVelocity().getY().get() < -MAX_VELOCITY) {
            getVelocity().setY(-MAX_VELOCITY);
        }

    }

    /**
     *
     * @param gameField terrain de jeu qui nous donnera les dimensions
     * @return si un des deux joueurs a poussé le disque entre les buts adverses.
     *
     */
    public boolean checkIfScored(GameField gameField,Paddle player_1, Paddle player_2){
        if (player1Scored(gameField)){
            player_1.scored();
            return true;
        } else if (player2Scored(gameField)){
            player_2.scored();
            return true;
        } else return false;

    }

    private boolean player2Scored(GameField gameField){
        return  xPuckBetweenGoals(gameField) && (getPosition().getY().get() + getRadius().get() >= gameField.getHeight().get());
    }
    private boolean player1Scored(GameField gameField){

        return (xPuckBetweenGoals(gameField) && (getPosition().getY().get() - getRadius().get() <= 0));
    }
    /** permet de vérifier si l'abscisse du Puck est bien entre le début et la fin du goal **/

    private boolean xPuckBetweenGoals(GameField gameField){

        return (this.getPosition().getX().get() - getRadius().get() >= gameField.getxStartGoal()
                && this.getPosition().getX().get() + getRadius().get() <= gameField.getxEndGoal());
    }

    /**
     *
     * @param gameField permet ici de nous donner les positions de départ du gamePuck par rapport aux dimensions
     *                  du terrain de jeu.
     *      Il sera placé au milieu de celui-ci.
     *      On veillera également à reinitialiser sa vitesse.
     */
    public void resetPositionAndVelocity(GameField gameField) {
        this.getPosition().is(new Vector2D(gameField.getWidth().get()/2,gameField.getHeight().get()/2));
        this.resetVelocity();
    }



    /********************************************************************************
     *                    Fonctions servant à faciliter le calcul
     ******************************************************************************* */
    private double cos(double theta) {
        return Math.cos(theta);
    }

    private double sin(double theta) {
        return Math.sin(theta);
    }

    private double square(double n) {
        return n * n;
    }




}
