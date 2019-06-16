package Gui;

import Logic.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameWindow extends Application {
    private Controller controller;
    private BorderPane mainLayout;

    @Override
    public void start(Stage stage) {

        mainLayout = new BorderPane();
        initialize();

        Image mapImg = Controller.loadImage("/res/images/map.jpg");
        ImageView mapIV = new ImageView(mapImg);

        mainLayout.getChildren().addAll(mapIV);
        Scene scene = new Scene(mainLayout, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("AirWar");
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> controller.stopGame());
        stage.show();
    }

    public Pane getMainContainer() {
        return this.mainLayout;
    }

    private void initialize() {
        this.controller = Controller.getInstance();
        this.controller.setGameWindow(this);

    }

    public static void show() {
        new GameWindow().start(new Stage());
    }
}
