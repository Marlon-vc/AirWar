package Gui;

import Logic.Controller;

import Structures.Timer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameWindow extends Application {
    private Controller controller;
    private BorderPane mainLayout;
    private PixelReader pixelReader;
    private String cwd = System.getProperty("user.dir");


    @Override
    public void start(Stage stage) {
        mainLayout = new BorderPane();
        initialize();

        Image imageColor = new Image("file://" + cwd +"/res/images/map1.jpg", 1280, 720, false, false);
        pixelReader = imageColor.getPixelReader();
        Image mapImg = Controller.loadImage("/res/images/map.jpg");
        ImageView mapIV = new ImageView(mapImg);

        mapIV.setFitWidth(1280);
        mapIV.setFitHeight(720);

        //timer

        HBox timerBox = new HBox();
        timerBox.setAlignment(Pos.CENTER);
        timerBox.setSpacing(10);
        timerBox.setPadding(new Insets(10));

        Label timerLabel = new Label();
        System.out.println("Player name " + this.controller.getPlayerName());
        Label playerName = new Label("Jugador: " + this.controller.getPlayerName() + " -  Tiempo restante:");

        Timer timer = new Timer(60*5, timerLabel);
        timer.startTimer();

        timerBox.getChildren().addAll(playerName, timerLabel);

        //colocar estilo
        timerLabel.setId("game-label");
        playerName.setId("game-label");
        timerBox.setId("game-container");

        mainLayout.getChildren().addAll(mapIV);
        mainLayout.setTop(timerBox);

        Scene scene = new Scene(mainLayout, 1280, 720);
        stage.setResizable(false);
        scene.getStylesheets().add(("file:///" + cwd + "/res/Style/style.css").replace(" ", "%20"));
        stage.setScene(scene);
        stage.setTitle("AirWar");
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> controller.stopGame());
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
