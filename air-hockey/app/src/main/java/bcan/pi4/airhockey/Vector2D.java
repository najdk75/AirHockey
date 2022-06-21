package bcan.pi4.airhockey;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * La classe Vector2D permettra de fonder une base géométrique à ce projet
 * Elle contiendra des méthodes permettant de calculer les transformations géométriques nécessaires
 * afin de bien faire fonctionner le jeu.
 * On utilisera des Properties afin de faciliter le lien entre modèle graphique et modèle physique.
 */


public class Vector2D {

    private final DoubleProperty X;
    private final DoubleProperty Y;

    public Vector2D(double X, double Y) {
        this.X = new SimpleDoubleProperty(X);
        this.Y = new SimpleDoubleProperty(Y);
    }

    public Vector2D(){
        this.X = new SimpleDoubleProperty(0);
        this.Y = new SimpleDoubleProperty(0);
    }

    /** Multiplication d'un vecteur par un scalaire. */
    public Vector2D multiplyBy(double k) {

        return new Vector2D(X.getValue() * k,Y.getValue() * k);
    }
    /** Soustraction de deux vecteurs */

    public Vector2D sub(Vector2D v){
        return new Vector2D(X.get() - v.getX().get(), Y.get() - v.getY().get());
    }
    /** Addition de deux vecteurs */
    public Vector2D add(Vector2D v){
        return new Vector2D(X.get() + v.getX().get(), Y.get() + v.getY().get());

    }

    /** Distance entre deux vecteurs élevée au carré pour réduire le coût des performances. */
    public double distanceBetweenSquared(Vector2D v){
        return square(v.getX().getValue()- X.getValue()) + square(v.getY().getValue() - getY().getValue());
    }

    /** Calcul du carré d'un nombre */
    public double square(double a){
        return (a*a);
    }
    /** Longueur d'un vecteur */
    public double length(){
        return Math.sqrt(X.getValue() * X.getValue() + Y.getValue() * Y.getValue());
    }

    /** Normalisation d'un vecteur en le divisant par sa longueur si celle ci n'est pas nulle,
     * nous permettra de déterminer la direction de son vecteur unitaire.
     */
    public Vector2D normalize() {
        if (length() == 0) return null;
        return multiplyBy(1/length());
    }

    /** méthode à utiliser uniquement pour les vecteurs vitesses.
     *
     * @return vrai si la norme du vecteur vitesse est nulle, faux sinon.
     */
    public boolean isZero() {
        return length() == 0;
    }

    /**
     * Sert à copier les attributs du vecteur v avant que celui ci finisse dans le garbage collector.
     * @param v permettra les opérations évoquées ci-dessus.
     */
    public void is(Vector2D v) {
        setX(v.getX().get());
        setY(v.getY().get());
    }


    /********************************************************************************
     Getters & Setters + Méthode ToString :
     ******************************************************************************* */

    public final DoubleProperty getX() {
        return X;
    }

    public final DoubleProperty getY() {
        return Y;
    }

    public final void setX(double X) {
        this.X.set(X);
    }

    public final void setY(double Y) {
        this.Y.set(Y);
    }

    public String toString(){
        return "(" + X.getValue() + "," + getY().getValue() + ")";
    }



}
