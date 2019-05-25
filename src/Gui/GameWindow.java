package Gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameWindow extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane mainLayout = new BorderPane();

        Scene scene = new Scene(mainLayout, 600, 400);
        stage.setScene(scene);
        stage.setTitle("AirWar");
        stage.show();
    }

    public void show() {
        launch(GameWindow.class);
    }
}
