package Sprites;

import Logic.Ruta;
import Structures.LinkedList;
import javafx.scene.image.Image;

public class Plane extends Sprite {

    private LinkedList<Airport> route;
    private double speed;

    public Plane(Image avion, double posX, double posY, LinkedList<Ruta> ruta, int speed) {
        super();
        this.speed = 1;
    }

    public Plane(Image planeImage) {
        super(planeImage);
        this.speed = 1;
        //TODO seleccionar aleatoriamente un aeropuerto para navegar y calcular la ruta más corta
    }

    public Plane(Image image, double posX, double posY, LinkedList<Airport> route, double speed) {
        super(image, posX, posY);
        this.route = route;
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
