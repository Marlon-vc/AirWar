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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameWindow extends Application {
    private Controller controller;
    private BorderPane mainLayout;

    private PixelReader pixelReader;

    @Override
    public void start(Stage stage) {


        mainLayout = new BorderPane();
        initialize();

        Image mapImg = Controller.loadImage("/res/images/map.jpg");
        ImageView mapIV = new ImageView(mapImg);
        mapIV.setFitWidth(1280);
        mapIV.setFitHeight(720);

        Image imageColor = new Image("file://" + System.getProperty("user.dir")+"/res/images/map1.jpg", 1280, 720, false, false);
        pixelReader = imageColor.getPixelReader();


        mapIV.setOnMouseClicked(mouseEvent -> {
            Color color = pixelReader.getColor((int) mouseEvent.getX(), (int) mouseEvent.getY());
            int colorInt = pixelReader.getArgb((int) mouseEvent.getX(), (int) mouseEvent.getY());
            int red = (colorInt >> 16) & 0xff;
            int green = (colorInt >> 8) & 0xff;
            int blue = colorInt & 0xff;

//            System.out.println("Color at click:\nRed: " + red + "\nGreen: " + green + "\n Blue: " + blue);
            System.out.println("Color at click: " + colorInt);
        });

        mainLayout.getChildren().addAll(mapIV);
        Scene scene = new Scene(mainLayout, 1280, 720);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("AirWar");
        stage.show();


    }

    public Pane getMainContainer() {
        return this.mainLayout;
    }

    public PixelReader getPixelReader() {
        return pixelReader;
    }

    private void initialize() {
        this.controller = Controller.getInstance();
        this.controller.setGameWindow(this);

    }

    public static void show() {
        new GameWindow().start(new Stage());
    }
}
