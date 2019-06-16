package Sprites;

import javafx.scene.image.Image;

public class Missile extends Sprite {
    private boolean inMovement;

    public Missile(){
        super();
    }

    public Missile(Image missileImage, double posX, double posY){
        super(missileImage, posX, posY);
        inMovement = true;
    }

    public void reduceY(){
        if (this.posY-5 < 0)
            this.posY = 0;
        else
            this.posY -= 5;
    }


}
