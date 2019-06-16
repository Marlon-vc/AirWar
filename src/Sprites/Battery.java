package Sprites;

import javafx.scene.image.Image;

import java.util.concurrent.ThreadLocalRandom;

public class Battery extends Sprite {

    private int leftX;
    private int rightX;
    private int currentX;
    private double moveStep;
    private boolean right;


    public Battery() {
        init();
    }

    public Battery(Image image, double posX, double posY) {
        super(image, posX, posY);
        init();
    }

    public Battery(Image image) {
        //La posicion en y no deberia cambiar por lo tanto se mantiene fijo
        //La posicion en x se inicializa en 10 de modo que el objeto inicia en esta posicion de la pantalla
        super(image, 10, 100);
        init();
    }

    public Battery(double posX, double posY) {
        super(posX, posY);
        init();
    }

    private void init() {
        this.leftX = 10;
        this.rightX = 1230;
        this.currentX = 640;
        this.right = true;
        this.moveStep = 5;
    }

    public void checkPosition() {
        if (currentX > rightX) {
            right = false;
            moveStep = ThreadLocalRandom.current().nextDouble(0.1, 10);
//            System.out.println("move step "+moveStep);
        } else if (currentX < leftX) {
            right = true;
            moveStep = ThreadLocalRandom.current().nextDouble(0.1, 10);
//            System.out.println("move step "+moveStep);
        }
    }

    public void moveX() {
        if (right) {
            currentX += moveStep;
            setPosX(currentX);
        } else {
            currentX -= moveStep;
            setPosX(currentX);
        }
    }


}

