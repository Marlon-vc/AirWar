package Sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {

    ImageView image;
    double posX;
    double posY;

    Sprite(double posX, double posY) {
        this.image = new ImageView();
        this.posX = posX;
        this.posY = posY;
        updatePos();
    }

    Sprite() {
        this.image = new ImageView();
        this.posX = 0;
        this.posY = 0;
        updatePos();
    }

    Sprite(Image image, double posX, double posY) {
        this.image = new ImageView(image);
        this.posX = posX;
        this.posY = posY;
        updatePos();
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image.setImage(image);
    }

    public void setSize(double size) {
        this.image.setPreserveRatio(true);
        this.image.setFitWidth(size);
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void updatePos() {
        this.image.setX(this.posX);
        this.image.setY(this.posY);
    }

}
