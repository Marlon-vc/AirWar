package Gui;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InitialWindow extends Application {
    private String cwd = System.getProperty("user.dir");
    private VBox mainLayout;


    @Override
    public void start(Stage stage) {
        mainLayout = new VBox();
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(10);
        mainLayout.setId("container-background");

        initInitialWindow();


        Scene scene = new Scene(mainLayout, 600, 400);
        scene.getStylesheets().add(("file:///" + cwd + "/res/Style/style.css").replace(" ", "%20"));
        stage.setScene(scene);

        stage.setTitle("AirWar");
        stage.show();
    }


    /**
     * Método para cargar la ventana de configuración del juego
     */
    private void initInitialWindow(){

        VBox requiredInfoContainer = new VBox();
        requiredInfoContainer.setAlignment(Pos.TOP_CENTER);
        requiredInfoContainer.setSpacing(15);

        Label title = new Label("Air War");

        Label name = new Label("Ingrese su nombre");
        TextField nameText = new TextField();
        nameText.setPrefSize(10,10);

        nameText.setPrefWidth(100);
        nameText.setMaxWidth(100);

        Label airportsLabel = new Label("Ingrese la cantidad de aeropuertos");
        Spinner<Integer> numberOfAirports  =  new Spinner<>();
        numberOfAirports.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 40, 1));
        numberOfAirports.setEditable(true);
        numberOfAirports.setPrefWidth(100);
        numberOfAirports.setMaxWidth(100);

        ImageView background = new ImageView(loadImg("res/images/airplane.png"));
        background.setFitHeight(200);
        background.setFitWidth(200);

        ImageView playButton = new ImageView(loadImg("res/images/game-control.png"));
        playButton.setFitHeight(60);
        playButton.setFitWidth(60);

        playButton.setOnMouseClicked(mouseEvent -> {
            String playerName = nameText.getText();
            if (!playerName.isBlank()) {
                int airports = numberOfAirports.getValue();
                System.out.println("Iniciando el juego de " + playerName + ", con " + airports + " aeropuertos...");
            } else {
                showAlert("Por favor ingrese su nombre", "Completar campos",
                        Alert.AlertType.ERROR);
            }
        });

        HBox topContainer = new HBox(title);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.setPadding(new Insets(5));

//        colocar estilo
        requiredInfoContainer.setId("container-background-color");
        topContainer.setId("container-background-color");
        title.setId("title");
        name.setId("label");
        airportsLabel.setId("label");
        numberOfAirports.setId ("text-field");
        nameText.setId("text-field");

        requiredInfoContainer.getChildren().addAll(name, nameText, airportsLabel, numberOfAirports);
        mainLayout.getChildren().addAll(topContainer, requiredInfoContainer, playButton);
    }

    /**
     * Método encargado de cargar una imagen desde la ruta especificada.
     * @param relativePath Ruta relativa de la imagen.
     * @return Instancia de la imagen.
     */

    private Image loadImg(String relativePath) {
        String cwd = System.getProperty("user.dir");
        return new Image("file://" + cwd + "/" + relativePath);
    }

    /**
     * Método para mostrar una alerta al usuario
     * @param message Mensaje a mostrar
     * @param title Título de la alerta
     * @param type Tipo de alerta
     */
    private void showAlert(String message, String title, Alert.AlertType type) {
        Alert showID = new Alert(type);
        showID.setTitle(title);
        showID.setHeaderText(null);
        showID.setContentText(message);
        showID.showAndWait();
    }

    /**
     * Método que muestra la ventana de creación de nuevo esquema.
     */

    public void show() {
        launch(InitialWindow.class);
    }
}

