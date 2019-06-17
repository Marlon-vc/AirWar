package Sprites;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Missile extends Sprite {
    private int top;
    private double currentY;
    private double moveStep;
    private boolean stop;


    public Missile() {
        super();
        init();
    }

    public Missile(Image missileImage, double posX, double posY) {
        super(missileImage, posX, posY);
        init();
    }

    private void init() {
        top = -20;
        currentY = posY;
        moveStep = 1;
        this.stop = false;
    }

    public void moveY() {
        if (!stop) {
            currentY -= moveStep;
            setPosY(currentY);
        }
    }

    public boolean check() {
        if (currentY < top) {
            this.stop = true;
            return true;
        }

        return false;

    }

    public void setMoveStep(double moveStep) {
        this.moveStep = moveStep;
    }
}
