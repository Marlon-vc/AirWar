package Gui;

import Logic.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.concurrent.atomic.LongAccumulator;

public class ConfiguracionWindow extends Application{

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        VBox stack = new VBox();
        stack.setAlignment(Pos.CENTER);
        stack.setSpacing(10);
        stack.setMaxSize(500,400);
//        stack.setBackground(new Background(Color.PINK,
//                CornerRadii.EMPTY, Insets.EMPTY));

        stack.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        VBox vStack = new VBox();
        vStack.setSpacing(10);
//        vStack.setBackground(new Background(new BackgroundFill(Color.SEAGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
//        vStack.setMinSize(200,100);
        vStack.setAlignment(Pos.BOTTOM_CENTER);


        primaryStage.setTitle("Configuration Window");
        Label actualKey = new Label("La letra actual es: ");
//        actualKey.setMaxSize(200,50);
        actualKey.setAlignment(Pos.TOP_RIGHT);
        actualKey.setStyle("-fx-border-color: black;");
        actualKey.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD ,15));
        actualKey.setBackground(new Background(new BackgroundFill(Color.rgb(32,178,170), CornerRadii.EMPTY, Insets.EMPTY)));
        actualKey.setPadding(new Insets(2,2,2,2));
        stack.getChildren().add(actualKey);

        Label actuaControl = new Label(root.getOnKeyPressed().toString());//Aqui iria lo que Pao tiene como scene.setOnKeyPress() en lugar de un set un get
        actuaControl.setAlignment(Pos.TOP_RIGHT);
        actuaControl.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD ,17));
        actuaControl.setBackground(new Background(new BackgroundFill(Color.rgb(32,178,170), CornerRadii.EMPTY, Insets.EMPTY)));
        actuaControl.setStyle("-fx-border-color: black;");
        actuaControl.setPadding(new Insets(2,2,2,2));
        stack.getChildren().add(actuaControl);



        Label changeKey = new Label("Cambiar la tecla por: ");
        changeKey.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD ,15));
        changeKey.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD ,15));
        changeKey.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD ,15));

        changeKey.setMaxSize(200,50);
        changeKey.setAlignment(Pos.BOTTOM_LEFT);
        changeKey.setPadding(new Insets(2,2,2,2));

        stack.getChildren().add(changeKey);

        TextField textFldChange = new TextField("Cambia el control");
        textFldChange.setAlignment(Pos.BOTTOM_RIGHT);
        textFldChange.setBackground(new Background(new BackgroundFill(Color.rgb(102,205,170), CornerRadii.EMPTY, Insets.EMPTY)));
        textFldChange.setStyle("-fx-border-color: black;");
        textFldChange.setMaxSize(150,50);
        textFldChange.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD ,15));

        textFldChange.setPadding(new Insets(2,2,2,2));

        stack.getChildren().add(textFldChange);

        Button btnSend = new Button();
        btnSend.setText("Save Changes");
        btnSend.setFont(Font.font("Arial", FontWeight.BOLD, 20 ));
        btnSend.setAlignment(Pos.BOTTOM_CENTER);
        vStack.getChildren().add(btnSend);
        btnSend.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Save Changes");
                String teclaCambiada = textFldChange.getText();
                System.out.println("La tecla presionada fue: " + teclaCambiada);
                root.setOnKeyPressed(keyEvent -> controller.shooMissile(teclaCambiada, mainLayout));
            }
        });

//        stack.getChildren().add(btnSend);

        root.getChildren().addAll(stack, vStack);
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }
    public void show() {
        launch(ConfiguracionWindow.class);
    }

}

