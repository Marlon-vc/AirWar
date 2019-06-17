package Logic;

import Sprites.Plane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Collision {

    private Plane plane;
    private ImageView explosion;
    private boolean hit;

    public Collision(Image collisionImage) {
        this.explosion = new ImageView(collisionImage);
        this.explosion.setPreserveRatio(true);
        this.explosion.setFitWidth(25);
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public ImageView getExplosion() {
        return explosion;
    }

    public void setExplosion(ImageView explosion) {
        this.explosion = explosion;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setPos(double x, double y){
        this.explosion.setX(x);
        this.explosion.setY(y);
    }
}
