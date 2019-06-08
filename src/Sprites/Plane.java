package Sprites;

import javafx.scene.image.Image;

public class Plane extends Sprite {

    private double speed;
    private int life;

    public Plane() {
        super();
        this.speed = 1;
        this.life = 100;
    }

    public Plane(Image planeImage) {
        super(planeImage);
        this.speed = 1;
        this.life = 100;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public static void main(String[] args) {
        Plane plane = new Plane();
    }

}
