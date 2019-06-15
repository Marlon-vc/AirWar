package Sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Battery extends Sprite {

    double speed = 1;
    int Xmin = 10;
    boolean direction;
    double screenSize;


    public Battery() {
    }

    public Battery(Image image, double screenSize) {
        //La posicion en y no deberia cambiar por lo tanto se mantiene fijo
        //La posicion en x se inicializa en 10 de modo que el objeto inicia en esta posicion de la pantalla
        super(image, 10, 100);
        this.screenSize = screenSize;
    }
    public void variateSpeed(){
        Random random = new Random();
        int factor = random.nextInt(1);
        speed = speed*factor;
    }
    public void changePosition(){
        direction = true;
        variateSpeed();

        if (direction) {
            if (this.posX + speed > this.screenSize) {
                direction = false;
                this.posX = this.posX - speed;
            }else{
                this.posX = this.posX + speed;
            }
        }else{
            if (this.posX + speed < 10) {
                direction = true;
                this.posX = this.posX + speed;
            }else{
                this.posX = this.posX - speed;
            }
        }
    }
}
