package bcan.pi4.airhockey;

import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
// classe qui sert à relier les propriétés des objets.
public class Converter {

    public void bindProperties(Sphere sphere, Circle circle){
        bindPosition(sphere, circle);
    }


    private void bindPosition(Sphere sphere, Circle circle){
        circle.layoutXProperty().bind(sphere.getPosition().getX());
        circle.layoutYProperty().bind(sphere.getPosition().getY());
    }


}
