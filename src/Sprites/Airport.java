package Sprites;

import Structures.LinkedList;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Airport extends Sprite {

    private LinkedList<Plane> planes;

    public Airport(Image spriteImage, double posX, double posY) {
        super(spriteImage, posX, posY);
        this.planes = new LinkedList<>();
        init();
    }

    private void init() {
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Airport " + hashCode() + "\nPos X: " + posX + "\nPos Y: " + posY);
        Tooltip.install(image, tooltip);

        image.setOnMouseEntered(mouseEvent -> image.setEffect(new DropShadow(5, Color.LIGHTGRAY)));
        image.setOnMouseExited(mouseEvent -> image.setEffect(null));
    }

}
