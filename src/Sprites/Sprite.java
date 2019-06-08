package Sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {

    ImageView image;
    double posX;
    double posY;
    double rotation;

    Sprite() {
        this.image = new ImageView();
        this.posX = 0;
        this.posY = 0;
        this.rotation = 0;
    }

    Sprite(Image image) {
        this.image = new ImageView(image);
        this.posX = 0;
        this.posY = 0;
        this.rotation = 0;
    }

    Sprite(Image image, double posX, double posY) {
        this.image = new ImageView(image);
        this.posX = posX;
        this.posY = posY;
        this.image.setX(this.posX);
        this.image.setY(this.posY);
        this.rotation = 0;
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

    public void rotate(double degrees) {
        this.rotation = degrees;
        this.image.setRotate(this.rotation);
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
        this.image.setX(this.posX);
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
        this.image.setY(this.posY);
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


    /*TODO:
       Para la trayectoria del avión utilizar una función lineal (y=mx+b).
       Con la posición (x, y) del destino y la actual del avión calcular el criterio de la función.
       Luego calcular la distancia en X entre los puntos, y dividirla en partes (unas 10 o más).
       Para cada parte (posición en X) calcular la posición respectiva en Y, utilizando la función lineal.
       Mover el avión repitiendo los pasos, sumando a la posición en X una parte.
       .

      TODO:
       Para el ángulo de rotación del avión calcular el ángulo entre la recta que contiene al avión y al destino,
       con respecto a la horizontal, y girar el avión esa cantidad de grados.
       .
       https://stackoverflow.com/questions/2676719/calculating-the-angle-between-the-line-defined-by-two-points
    * */

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
