package Sprites;

import Structures.Queue;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Airport extends Sprite {

    private int id;
    private double time;
    private Queue<Plane> planeQueue;
    private Tooltip tooltip;
    private boolean isCarrier;


    public Airport(int id, double posX, double posY) {
        super(posX, posY);
        this.id = id;
        this.planeQueue = new Queue<>();

        tooltip = new Tooltip();
        tooltip.setText("Airport " + id + "\nPos X: " + posX + "\nPos Y: " + posY);
        tooltip.setShowDelay(Duration.ZERO);

        Tooltip.install(image, tooltip);
        image.setOnMouseEntered(mouseEvent -> image.setEffect(new DropShadow(5, Color.LIGHTGRAY)));
        image.setOnMouseExited(mouseEvent -> image.setEffect(null));
    }

    public int getId() {
        return id;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTimeLeft() {
        return this.time;
    }

    public boolean hasTimeLeft() {
        return (time > 0);
    }

    public void decreaseTime(double time) {
        this.time -= time;
    }

    public boolean isEmpty() {
        return (planeQueue.getSize() == 0);
    }

    public void addPlane(Plane plane) {
        planeQueue.enqueue(plane);
    }

    public void setTextToTooltip(String text){
        tooltip.setText(text);
    }

    public boolean isCarrier() {
        return isCarrier;
    }

    public void setCarrier(boolean carrier) {
        isCarrier = carrier;
    }

    public Plane getNextPlane() {
        return planeQueue.dequeue();
    }
}
