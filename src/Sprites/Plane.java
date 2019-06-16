package Sprites;

import Structures.LinkedList;
import Structures.Queue;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
public class Plane extends Sprite {

    private double speed;
    private Airport currentDestination;
    private Queue<Airport> route;

    private double m;
    private double b;
    private Tooltip tooltip;


    public Plane() {
        super();
        this.speed = 1;
    }

    public Plane(Image planeImage, double posX, double posY) {
        super(planeImage, posX, posY);
        this.speed = 1;
    }


    private void init() {
        int currentIdAirport = currentDestination.getId();
        tooltip = new Tooltip();
        tooltip.setText("Next destination: Airport " + currentIdAirport + "\n" +
                "Destinations to travel: " + "\n" +
                "Speed of airplane: " + this.speed);

        //TODO colocar destinos por recorrer

        tooltip.setShowDelay(Duration.ZERO);

        Tooltip.install(image, tooltip);

        image.setOnMouseEntered(mouseEvent -> image.setEffect(new DropShadow(5, Color.LIGHTGRAY)));
        image.setOnMouseExited(mouseEvent -> image.setEffect(null));
    }


    public void setTextToTooltip(String text){
        tooltip.setText(text);
    }


    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isEnd() {
        return (route.getSize() == 0);
    }

    public Queue<Airport> getRoute() {
        return route;
    }
}
