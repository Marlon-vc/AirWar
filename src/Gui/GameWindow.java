package Gui;

import Logic.Controller;
import Logic.Ruta;
import Sprites.Airport;
import Structures.LinkedList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameWindow extends Application {
    private Controller controller;
    private BorderPane mainLayout;

@Override
    public void start(Stage stage) throws FileNotFoundException {
        initialize();
        String root=System.getProperty("user.dir");
        String rutaRlativa="/AirWar/res/images/map.jpg";
        String url=root+rutaRlativa;
        FileInputStream i = new FileInputStream(url);
        ImageView addTokenImage = new ImageView(new Image(i));
        //////////////////////////////////////////////////////

        i = new FileInputStream("AirWar/res/images/airport21.png");
        ImageView addTokenImage1 = new ImageView(new Image(i));
        addTokenImage1.setX(50);
        addTokenImage1.setY(50);
////////////////////////////////////////////////////////////
        Airport airport = new Airport(new Image(i), 200,200);
        Airport airport1 = new Airport(new Image(i), 300,300);
        Airport airport2 = new Airport(new Image(i), 400,400);
        LinkedList<Airport> listaAirport = new LinkedList<>();
        listaAirport.add(airport);
        listaAirport.add(airport1);
        listaAirport.add(airport2);
        LinkedList<Ruta> listaRuta = Controller.generateRuta(listaAirport, 2);
        for(int j = 0; j < listaRuta.getSize(); j++){
            System.out.println("va desde: "+ listaRuta.get(j).getX1()+","+listaRuta.get(j).getY1());
            System.out.println("hasta: "+ listaRuta.get(j).getX2()+","+listaRuta.get(j).getY2());
            System.out.println("*********************************************");
        }
////////////////////////////////////////////////////////////


        BorderPane mainLayout = new BorderPane();
        mainLayout.getChildren().addAll(addTokenImage,addTokenImage1);
        Scene scene = new Scene(mainLayout, 1920, 1024);
        stage.setScene(scene);
        stage.setTitle("AirWar");
        stage.show();
}

    private void initialize() {
        this.controller = Controller.getInstance();
        this.controller.setGameWindow(this);
    }

    public void show() {
        launch(GameWindow.class);
    }
}
