package Sprites;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Random;

public class Battery extends Sprite {

    double speed = 1;
    int Xmin;
    boolean direction = true;
    double Xmax;


    public Battery() {
    }

    public Battery(double posX, double posY) {
        super(posX, posY);
        this.Xmin = 10;
        this.Xmax = 700;
    }



    /**
     * El metodo crea un valor random entre 0 y 1 de tal modo que la velocidad es igual a su multiplicacion por ese valor random
     */
    private void variateSpeed(){
        Random random = new Random();
        double factor = random.nextInt(5);
        speed = speed * factor;
    }

    /**
     * Este metodo se encarga de variar la posicion del objeto segun el valor de velocidad
     */
    public void changePosition(){
        direction = true;
        variateSpeed();
        if (direction) {
            if (this.posX + speed >= this.Xmax) {
                direction = false;
                changePosition();
            }else{
                this.posX = this.posX + speed;
            }
        }else{
            if (this.posX - speed < this.Xmin) {
                direction = true;
                changePosition();
            }else{
                this.posX = this.posX - speed;
            }
        }
        this.updatePos();



    }
    public void move(){
        this.setPosY(this.getPosY()+ 10);
    }

    /**
     * Este metodo sera el encargado de realizar el disparo
     * Se guarda la posicion x,y en el momento en el que se llama el metodo
     * ********************Falta agregar funcionalidad*******************************
     */
    public void shoot(){
        double x = this.getPosX();
        double y = this.getPosY();

    }

    @Override
    public void updatePos() {
        this.getImage().setX(this.getPosX());
        this.getImage().setY(this.getPosY());
    }

    public static Image loadImage(String relativePath) {
        try {
            return new Image("file://" +
                    (System.getProperty("user.dir") + relativePath).replaceAll(" ", "%20"));
        } catch (IllegalArgumentException e) {
            System.out.println("Couln't load " + relativePath);
        }
        return null;
    }
    public static void main(String[] args) {

        Battery b = new Battery(100,100);
        System.out.println(b.getPosX());
        b.variateSpeed();
        b.changePosition();
        System.out.println(b.getPosX());
        System.out.println(b.getPosY());
        b.updatePos();
        System.out.println(b.getImage().getX());
    }

    public double getSpeed() {
        return speed;
    }
}

