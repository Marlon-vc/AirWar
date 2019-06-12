package Sprites;

import Structures.LinkedList;
import javafx.scene.image.Image;

public class Plane extends Sprite {

    private LinkedList<Airport> route;
    private double speed;

    public Plane() {
        super();
        this.speed = 1;
    }

    public Plane(Image planeImage) {
        super(planeImage);
        this.speed = 1;
        //TODO seleccionar aleatoriamente un aeropuerto para navegar y calcular la ruta más corta
    }
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
