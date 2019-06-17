package Gui;

import Logic.Controller;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InputConfigurator extends Application{


    private String cwd = System.getProperty("user.dir");
    private VBox mainLayout;
    private Controller controller;
    private String newKey;

    @Override
    public void start(Stage stage) {
        this.controller = Controller.getInstance();
        newKey = "";

        mainLayout = new VBox();
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(10);
        mainLayout.setId("container-background-input");


        VBox requiredInfoContainer = new VBox();
        requiredInfoContainer.setAlignment(Pos.TOP_CENTER);
        requiredInfoContainer.setSpacing(15);

        Label name = new Label("Control configurado a " + controller.getKey());

        Label configLabel = new Label("Presione la nueva tecla a configurar");
        Label inputLabel = new Label();

        //TODO obtener tecla presionada por el usuario

        ImageView saveButton = new ImageView(Controller.loadImage("/res/images/save.png"));
        saveButton.setFitHeight(60);
        saveButton.setFitWidth(60);
        saveButton.setOnMouseClicked(mouseEvent -> {
            if (!newKey.isBlank()){
                controller.setShootKey(newKey);
                stage.close();
            } else {
                Controller.showAlert("Presione una tecla", "Tecla requerida", Alert.AlertType.ERROR);
            }
        });


//        colocar estilo
        requiredInfoContainer.setId("container-background-color");
        name.setId("label-input");
        configLabel.setId("label-input");
        inputLabel.setId("label-input");

        requiredInfoContainer.getChildren().addAll(name, configLabel, inputLabel);
        mainLayout.getChildren().addAll(requiredInfoContainer, saveButton);



        Scene scene = new Scene(mainLayout, 600, 400);
        scene.setOnKeyPressed(keyEvent -> {
            newKey = keyEvent.getCode().toString();
            inputLabel.setText(newKey);
        });
        scene.getStylesheets().add(("file:///" + cwd + "/res/Style/style.css").replace(" ", "%20"));
        stage.setScene(scene);

        stage.setTitle("AirWar");
        stage.show();
    }


    public static void show() {
        new InputConfigurator().start(new Stage());
    }

}