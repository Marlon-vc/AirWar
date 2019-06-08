package Gui;

import Logic.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameWindow extends Application {
    private Controller controller;
    private BorderPane mainLayout;

    @Override
    public void start(Stage stage) {
        initialize();

        mainLayout = new BorderPane();

        ImageView mapImageView = new ImageView(Controller.loadImage("/res/images/map.jpg"));
        mapImageView.setFitHeight(720);
        mapImageView.setFitWidth(1280);

        mainLayout.getChildren().addAll(mapImageView);

        Scene scene = new Scene(mainLayout, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("AirWar");
        stage.setResizable(false);
        stage.show();

        controller.load(25);
    }

    private void initialize() {
        this.controller = Controller.getInstance();
        this.controller.setGameWindow(this);
    }

    public void show() {
        launch(GameWindow.class);
    }
}
