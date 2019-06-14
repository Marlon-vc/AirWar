package Sprites;

import Structures.Queue;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Airport extends Sprite {

    private int id;
    private double time;
    private Queue<Plane> planeQueue;

    public Airport(Image spriteImage, int id, double posX, double posY) {
        super(spriteImage, posX, posY);
        this.id = id;
        this.planeQueue = new Queue<>();

        Tooltip tooltip = new Tooltip();
        tooltip.setText("Airport " + id + "\nPos X: " + posX + "\nPos Y: " + posY);
        Tooltip.install(image, tooltip);
        image.setOnMouseEntered(mouseEvent -> image.setEffect(new DropShadow(5, Color.LIGHTGRAY)));
        image.setOnMouseExited(mouseEvent -> image.setEffect(null));
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTime() {
        return this.time;
    }

    public boolean isEmpty() {
        return (planeQueue.getSize() == 0);
    }

    public void addPlane(Plane plane) {
        planeQueue.enqueue(plane);
    }

}
