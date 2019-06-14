package Sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Battery extends Sprite {

    int Xmin;
    int Xmax;
    double speed;



    public Battery(Image image, double posX, double posY) {
        super(image, posX, posY);
    }
    public void variateSpeed(){
        Random random = new Random();
        int factor = random.nextInt(1);
        speed = speed*factor;
    }


}
