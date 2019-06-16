package Sprites;

import Logic.Controller;
import Structures.LinkedList;
import Structures.Queue;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import Structures.LinkedList;
import Structures.Queue;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.shape.Line;


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
    private Queue<Airport> internalRoute;
    private LinkedList<Line> linesList;
    private LinkedList<Text> orderList;
    private LinkedList<Text> weightList;


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

        image.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                drawRoute(container, controller);
            if (mouseEvent.getButton() == MouseButton.SECONDARY)
                undraw(container);
        });

        image.setOnMouseEntered(mouseEvent -> image.setEffect(new DropShadow(5, Color.LIGHTGRAY)));
        image.setOnMouseExited(mouseEvent -> image.setEffect(null));
    }

    private void undraw(Pane container) {
        int size = linesList.getSize();
        for (int i=0; i<size; i++){
            Text order = orderList.get(i);
            Text weight = weightList.get(i);
            Line line = linesList.get(i);
            Platform.runLater(()-> container.getChildren().removeAll(line, order, weight));
        }

        Text order = orderList.get(orderList.getSize()-1);
        Platform.runLater(()-> container.getChildren().remove(order));
    }


    private void drawRoute(Pane container, Controller controller){
        int size = internalRoute.getSize();
        for (int i=0; i<size; i++) {
            Airport startAirport = internalRoute.get(i);
            Airport endAirport = internalRoute.get(i + 1);

            Text number = new Text();
            number.setStyle("-fx-font-size: 20; -fx-fill: white;");
            number.setX(startAirport.posX + 5);
            number.setY(startAirport.posY + 5);
            number.setText(Integer.toString(i + 1));
            orderList.add(number);

            if (endAirport != null) {
                double startPosX = startAirport.posX;
                double startPosY = startAirport.posY;
                double endPosX = endAirport.posX;
                double endPosY = endAirport.posY;

                Line line = new Line(startPosX, startPosY, endPosX, endPosY);
                line.setStroke(Color.WHITE);
                line.setStrokeWidth(2);
                linesList.add(line);

                int[] middle = calculateMiddle((int) startPosX, (int) endPosX, (int) startPosY, (int) endPosY);
                String calculateWight = Double.toString(controller.getWeight(startAirport.getId(), endAirport.getId()));
                Text weight = new Text(calculateWight);
                weight.setStyle("-fx-font-size: 20; -fx-fill: white;");
                weight.setX(middle[0]);
                weight.setY(middle[1]+15);
                weightList.add(weight);
            }
        }
        draw(container);
    }

    private int[] calculateMiddle(int x1, int x2, int y1, int y2){
        System.out.println("Calculating middle of edge..");
        int[] point = new int[2];
        int x = ((x1 + x2)/2);
        int y = ((y1 + y2)/2);
        point[0] = x;
        point[1] = y;
        System.out.println("X: " + x + " Y: " + y);
        return point;
    }

    private void draw(Pane container) {
        undraw(container);
        int size = linesList.getSize();
        for (int i=0; i<size; i++){
            Text order = orderList.get(i);
            Text weight = weightList.get(i);
            Line line = linesList.get(i);
            Platform.runLater(()-> container.getChildren().addAll(line, order, weight));
        }

        Text order = orderList.get(orderList.getSize()-1);
        Platform.runLater(()-> container.getChildren().addAll(order));
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
            System.out.println("Peso: " + totalWeight);
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
