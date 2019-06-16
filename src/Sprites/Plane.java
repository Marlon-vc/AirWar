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
    private Queue<Airport> internalRoute;
    private LinkedList<Line> linesList;
    private LinkedList<Text> orderList;
    private LinkedList<Text> weightList;

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
        route = new Queue<>();
        internalRoute = new Queue<>();
        init();
    }


    private void init() {
//        int currentIdAirport = currentDestination.getId();
        tooltip = new Tooltip();
        tooltip.setText("Next destination: Airport "  + "\n" +
                "Destinations to travel: " + "\n" +
                "Speed of airplane: " + this.speed);
        //TODO colocar en tooltip el siguiente aeropuerto a visitar y los destinos que le faltan por recorrer
        tooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(image, tooltip);

        Controller controller = Controller.getInstance();
        Pane container = controller.getGameWindow();
        linesList = new LinkedList<>();
        orderList = new LinkedList<>();
        weightList = new LinkedList<>();

        image.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                drawRoute(container);
            if (mouseEvent.getButton() == MouseButton.SECONDARY)
                undraw(container);
        });

        image.setOnMouseEntered(mouseEvent -> image.setEffect(new DropShadow(5, Color.LIGHTGRAY)));
        image.setOnMouseExited(mouseEvent -> image.setEffect(null));
    }

    private void undraw(Pane container) {
        for (int i=0; i<linesList.getSize(); i++){
            Text order = orderList.get(i);
            Text weight = weightList.get(i);
            Line line = linesList.get(i);
            Platform.runLater(()-> container.getChildren().removeAll(line, order, weight));
        }

        Text order = orderList.get(orderList.getSize()-1);
        Platform.runLater(()-> container.getChildren().remove(order));
    }


    private void drawRoute(Pane container){
        for (int i=0; i<internalRoute.getSize(); i++) {
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
                Text weight = new Text("Peso ");
                weight.setStyle("-fx-font-size: 20; -fx-fill: white;");
                weight.setX(middle[0]);
                weight.setY(middle[1]);
                weightList.add(weight);
            }
        }

        draw(container);
    }

    public int[] calculateMiddle(int x1, int x2, int y1, int y2){
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
        for (int i=0; i<linesList.getSize(); i++){
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

    public void setRoute(Queue<Airport> route) {
        this.route = route;
        this.internalRoute = this.route;
    }
}
