package Gui;

import Logic.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameWindow extends Application {
    private Controller controller;

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

    public void show() {
        launch(GameWindow.class);
    }
}
