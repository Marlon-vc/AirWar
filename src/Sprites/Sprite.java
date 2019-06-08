package Sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {

    ImageView spriteImage;
    double posX;
    double posY;
    double rotation;

    Sprite() {
        this.spriteImage = new ImageView();
        this.posX = 0;
        this.posY = 0;
        this.rotation = 0;
    }

    Sprite(Image spriteImage) {
        this.spriteImage = new ImageView(spriteImage);
        this.posX = 0;
        this.posY = 0;
        this.rotation = 0;
    }

    Sprite(Image spriteImage, double posX, double posY) {
        this.spriteImage = new ImageView(spriteImage);
        this.posX = posX;
        this.posY = posY;
        this.spriteImage.setX(this.posX);
        this.spriteImage.setY(this.posY);
        this.rotation = 0;
    }

    public ImageView getImage() {
        return spriteImage;
    }

    public void setImage(Image image) {
        this.spriteImage.setImage(image);
    }

    public void setSize(double size) {
        this.spriteImage.setPreserveRatio(true);
        this.spriteImage.setFitWidth(size);
//        this.spriteImage.setFitHeight(size);
    }

    public void rotate(double degrees) {
        this.rotation = degrees;
        this.spriteImage.setRotate(this.rotation);
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
        this.spriteImage.setX(this.posX);
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
        this.spriteImage.setY(this.posY);
    }

    public void moveX(double xMovement) {
        this.posX += xMovement;
    }

    public void moveY(double yMovement) {
        this.posY += yMovement;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public static void main(String[] args) {
        Sprite sprite = new Sprite();
        sprite.setPosX(100);
        System.out.println(sprite.getPosX());
        sprite.moveX(-50);
        System.out.println(sprite.getPosX());
    }

    @Override
    public String toString() {
        return "Sprite@" + hashCode() + "(" + posX + ", " + posY + ")";
    }
}
