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
import javafx.scene.layout.Pane;
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

        mainLayout.getChildren().addAll(mapIV);
        Scene scene = new Scene(mainLayout, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("AirWar");
        stage.show();
    }

    public Pane getMainContainer() {
        return this.mainLayout;
    }

    private void initGameWindow(){

    }

    private void initialize() {
        this.controller = Controller.getInstance();
        this.controller.setGameWindow(this);

    }

    public static void show() {
        new GameWindow().start(new Stage());
    }
}
