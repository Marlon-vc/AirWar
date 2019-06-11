package Sprites;

import javafx.scene.image.Image;

public class Plane extends Sprite {

    private double speed;

    public Plane() {
        super();
        this.speed = 1;
    }

    public Plane(Image planeImage) {
        super(planeImage);
        this.speed = 1;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
