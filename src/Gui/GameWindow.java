package Gui;

import Logic.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameWindow extends Application {
    private Controller controller;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        initialize();
        String root=System.getProperty("user.dir");
        String rutaRlativa="/AirWar/res/images/map.jpg";
        String url=root+rutaRlativa;
        FileInputStream i = new FileInputStream(url);
        ImageView addTokenImage = new ImageView(new Image(i));
        //////////////////////////////////////////////////////

        i = new FileInputStream("/home/hazel/Escritorio/airWar/AirWar/res/images/airport.png");
        ImageView addTokenImage1 = new ImageView(new Image(i));
        addTokenImage1.setX(50);
        addTokenImage1.setY(50);

        BorderPane mainLayout = new BorderPane();
        mainLayout.getChildren().addAll(addTokenImage,addTokenImage1);
        Scene scene = new Scene(mainLayout, 1920, 1024);
        stage.setScene(scene);
        stage.setTitle("AirWar");
        stage.show();

    }

    private void initialize() {
        this.controller = new Controller();
        this.controller.setGameWindow(this);
    }

    public void show() {
        launch(GameWindow.class);
    }
}
