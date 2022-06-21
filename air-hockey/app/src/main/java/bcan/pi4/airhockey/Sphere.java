package bcan.pi4.airhockey;



import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * La classe Sphere permettra de représenter les élements du jeu avec leur propriétés
 * physiques comme leur rayon, la position de leur centre, leur vitesse
 * ou encore leur masse.
 * Elle sera la classe mère des classes Paddle et GamePuck qui sont des éléments sphériques
 * du jeu.
 */

public class Sphere {

    /** Représente le rayon de la sphère */
    private final DoubleProperty radius;

    /** Représente la position de la sphère */
    private Vector2D position;

    /** Représente la vitesse de la sphère */
    private Vector2D velocity;

    /** Représente la masse de la sphère */
    private final double mass;

    /** Constructeur de la sphère */
    public Sphere(Vector2D velocity, Vector2D position, double mass, double radius) {
        this.radius = new SimpleDoubleProperty(radius);
        this.velocity = velocity;
        this.position = position;
        this.mass = mass;

    }


    public void resetVelocity() {
        this.velocity.is(new Vector2D());
    }

    /********************************************************************************
		Getters & Setters :
	 ******************************************************************************* */


    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getPosition() {
        return position;
    }

    public double  getMass() {
        return mass;
    }

    public final DoubleProperty getRadius(){
        return radius;
    }



}
