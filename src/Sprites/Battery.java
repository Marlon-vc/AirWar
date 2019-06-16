package Sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Battery extends Sprite {

    double speed = 1;
    int Xmin;
    boolean direction;
    double Xmax;


    public Battery() {
    }

    public Battery(Image image, double posX, double posY) {
        super(image, posX, posY);
    }

    public Battery(Image image) {
        //La posicion en y no deberia cambiar por lo tanto se mantiene fijo
        //La posicion en x se inicializa en 10 de modo que el objeto inicia en esta posicion de la pantalla
        super(image, 10, 100);
        this.Xmin = 10;
        this.Xmax = 1270;
    }

    /**
     * El metodo crea un valor random entre 0 y 1 de tal modo que la velocidad es igual a su multiplicacion por ese valor random
     */
    public void variateSpeed(){
        Random random = new Random();
        int factor = random.nextInt(1);
        speed = speed*factor;
    }

    /**
     * Este metodo se encarga de variar la posicion del objeto segun el valor de velocidad
     */
    public void changePosition(){
        direction = true;
        variateSpeed();
        if (direction) {
            if (this.posX + speed > this.Xmax) {
                direction = false;
                this.posX = this.posX - speed;
            }else{
                this.posX = this.posX + speed;
            }
        }else{
            if (this.posX + speed < this.Xmin) {
                direction = true;
                this.posX = this.posX + speed;
            }else{
                this.posX = this.posX - speed;
            }
        }
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
}
