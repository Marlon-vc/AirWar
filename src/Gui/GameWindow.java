package Gui;

import Logic.Controller;
import Logic.Ruta;
import Sprites.Airport;
import Structures.LinkedList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameWindow extends Application {
    private Controller controller;
    private BorderPane mainLayout;

    @Override
    public void start(Stage stage) {

        mainLayout = new BorderPane();
        initialize();

        Image mapImg = Controller.loadImage("/res/images/map.jpg");

        PixelReader pixelReader = mapImg.getPixelReader();

        ImageView mapIV = new ImageView(mapImg);

        mapIV.setOnMouseClicked(mouseEvent -> {
            Color color = pixelReader.getColor((int) mouseEvent.getX(), (int) mouseEvent.getY());
            int colorInt = pixelReader.getArgb((int) mouseEvent.getX(), (int) mouseEvent.getY());
            int red = (colorInt >> 16) & 0xff;
            int green = (colorInt >> 8) & 0xff;
            int blue = colorInt & 0xff;

            System.out.println("Color at click:\nRed: " + red + "\nGreen: " + green + "\n Blue: " + blue);

//            System.out.println("Color at click is " + color.toString());
        });

//        //////////////////////////////////////////////////////
//
//        ImageView airportImg = new ImageView(Controller.loadImage("/res/images/airport2.png"));
//        airportImg.setFitWidth(25);
//        airportImg.setPreserveRatio(true);
//        airportImg.setX(50);
//        airportImg.setY(50);
//
//////////////////////////////////////////////////////////////
//        Airport airport = new Airport(Controller.loadImage("/res/images/airport2.png"), 200,200);
//        Airport airport1 = new Airport(Controller.loadImage("/res/images/airport2.png"), 300,300);
//        Airport airport2 = new Airport(Controller.loadImage("/res/images/airport2.png"), 400,400);
//        LinkedList<Airport> listaAirport = new LinkedList<>();
//        listaAirport.add(airport);
//        listaAirport.add(airport1);
//        listaAirport.add(airport2);
//        LinkedList<Ruta> listaRuta = Controller.generateRuta(listaAirport, 2);
//        for(int j = 0; j < listaRuta.getSize(); j++){
//            System.out.println("va desde: "+ listaRuta.get(j).getX1()+","+listaRuta.get(j).getY1());
//            System.out.println("hasta: "+ listaRuta.get(j).getX2()+","+listaRuta.get(j).getY2());
//            System.out.println("*********************************************");
//        }
//////////////////////////////////////////////////////////////



        mainLayout.getChildren().addAll(mapIV);
        Scene scene = new Scene(mainLayout, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("AirWar");
        stage.show();

        controller.load(3, mainLayout);
    }

    private void initGameWindow(){

    }

    private void initialize() {
        this.controller = Controller.getInstance();
        this.controller.setGameWindow(this);
    }

    public void show() {
        launch(GameWindow.class);
    }
}
