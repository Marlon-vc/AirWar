package Gui;

import Logic.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameWindow extends Application {
    private Controller controller;
    private String cws = System.getProperty("user.dir");


    @Override
    public void start(Stage stage) {
        initialize();

        BorderPane mainLayout = new BorderPane();

        Scene scene = new Scene(mainLayout, 600, 400);
        stage.setScene(scene);
        stage.setTitle("AirWar");
        stage.show();

    }

    private void initialize() {
        this.controller = new Controller();
        this.controller.setGameWindow(this);
    }

    private void mapBackground() {
        /**
         * metodo para cargar la imagen mapa de fondo
         */
        BorderPane mapContainer = new BorderPane();
        Image mapImage = imageLoader(cws + "/res/images/map.jpg");
    }

    private javafx.scene.image.Image imageLoader(String path){
//    Image imageLoader(String path) {
        /**
         * metodo para obtener la direccion de la imagen
         */
        try {
            FileInputStream i = new FileInputStream(path);
            return new javafx.scene.image.Image(i);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't load images!");
        }
        System.out.println("Could not find " + path);
        return null;
    }

     public void show(){
            launch(GameWindow.class);
        }
}
