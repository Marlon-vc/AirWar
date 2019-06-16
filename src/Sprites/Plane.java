package Sprites;

import Logic.Controller;
import Structures.LinkedList;
import Structures.Queue;
import javafx.application.Platform;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;


/*TODO:
       Para la trayectoria del avión utilizar una función lineal (y=mx+b).
       Con la posición (x, y) del destino y la actual del avión calcular el criterio de la función.
       Luego calcular la distancia en X entre los puntos, y dividirla en partes (unas 10 o más).
       Para cada parte (posición en X) calcular la posición respectiva en Y, utilizando la función lineal.
       Mover el avión repitiendo los pasos, sumando a la posición en X una parte.

      TODO:
       Para el ángulo de rotación del avión calcular el ángulo entre la recta que contiene al avión y al destino,
       con respecto a la horizontal, y girar el avión esa cantidad de grados.
       .
       https://stackoverflow.com/questions/2676719/calculating-the-angle-between-the-line-defined-by-two-points
    * */

public class Plane extends Sprite {

    private boolean isVisible;
    private boolean isOnAir;

    private double rotation;
    private Airport routeTarget;
    private Airport routeOrigin;
    private Queue<Airport> route;

    private double m;
    private double b;

    private Tooltip tooltip;


    private double moveStep;
    private double totalWeight;
    private double currentDistance;
    private Controller controller;

    public Plane() {
        super();
    }

    public Plane(Image planeImage, double posX, double posY) {
        super(planeImage, posX, posY);

        this.controller = Controller.getInstance();
        this.isVisible = false;
        this.rotation = 0;
        init();
    }


    private void init() {
//        int currentIdAirport = currentDestination.getId();
        tooltip = new Tooltip();

        tooltip.setText("Next destination: Airport "  + "\n" +
                "Destinations to travel: "); //TODO destinations to travel
        //TODO colocar en tooltip el siguiente aeropuerto a visitar y los destinos que le faltan por recorrer
        tooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(image, tooltip);

        Pane container = controller.getGameWindow();
        linesList = new LinkedList<>();
        orderList = new LinkedList<>();
        weightList = new LinkedList<>();

        tooltip.setText("Next destination: Airport "  + "\n" +
                "Destinations to travel: ");

        //TODO colocar destinos por recorrer

        tooltip.setShowDelay(Duration.ZERO);

        Tooltip.install(image, tooltip);

        image.setOnMouseEntered(mouseEvent -> image.setEffect(new DropShadow(5, Color.LIGHTGRAY)));
        image.setOnMouseExited(mouseEvent -> image.setEffect(null));
    }


    public void setTextToTooltip(String text){
        tooltip.setText(text);
    }



    public void setRouteOrigin(Airport origin) {
        this.routeTarget = origin;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisibility(boolean visible) {
        this.isVisible = visible;
    }

    public boolean isOnAir() {
        return this.isOnAir;
    }

    public void setOnAir(boolean onAir) {
        this.isOnAir = onAir;
    }

    @Override
    public void updatePos() {
        super.updatePos();
        image.setRotate(this.rotation);
    }

    public Queue<Airport> getRoute() {
        return route;
    }

    public void setRoute(Queue<Airport> route) {
        this.route = route;
        this.internalRoute = this.route;
        nextNode();
    }

    public void nextNode() {
        if (route.getSize() > 0) {
            //Aeropuerto origen
            routeOrigin = routeTarget;
            //Aeropuerto destino
            routeTarget = route.dequeue();

            //Distancia entre los puntos
            totalWeight = controller.getGraph().getRouteWeight(routeOrigin.getId(), routeOrigin.getId());
//            System.out.println("Peso: " + totalWeight);
            //Fracción del movimiento
            moveStep = totalWeight * 0.05;
            //Cálculo de la función del movimiento
            calculateMovement();
            //Cálculo del ángulo del avión
            calculateAngle();
            //Distancia actual en X
            currentDistance = posX;
        } else {
            System.out.println("The plane has no more routes!!");
        }
    }

    private void setLine() {
        Line line = new Line();
        line.setStyle("-fx-background-color: white; -fx-color-fill: white;");
        line.setStartX(routeOrigin.getPosX());
        line.setStartY(routeOrigin.getPosY());
        line.setEndX(routeTarget.getPosX());
        line.setEndY(routeTarget.getPosY());
        Platform.runLater(()->controller.getMainPane().getChildren().add(line));
    }

    private void calculateMovement() {
        this.m = (routeTarget.getPosY() - this.posY) / (routeTarget.getPosX() - this.posX);
        this.b = routeTarget.getPosY() - (this.m * routeTarget.getPosX());
    }

    private void calculateAngle() {

        double deltaY = (routeOrigin.getPosY() - routeTarget.getPosY());
        double deltaX = (routeTarget.getPosX() - routeOrigin.getPosX());

        double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
        this.rotation = (result < 0) ? (360d + result) : result;
//        System.out.println("Rotation: " + rotation);
    }

    public void nextX() {
        this.currentDistance += moveStep;
        setPosX(this.currentDistance);
    }

    public void nextY() {
        setPosY((this.m * this.currentDistance) + this.b);
    }

    public static void main(String[] args) {
    }
}
