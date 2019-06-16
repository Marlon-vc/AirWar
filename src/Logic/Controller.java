package Logic;

import Gui.GameWindow;
import Sprites.Airport;
import Sprites.Plane;
import Structures.AdjacencyMatrix;
import Structures.LinkedList;
import Structures.Queue;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {

    private static Controller instance;
    private GameWindow gameWindow;
    private LinkedList<Airport> airportList;
    private LinkedList<Plane> planesList;
    private AdjacencyMatrix graph;

    private Image airportImage;
    private Image planeImage;
    private boolean isGameRunning;

    private String playerName;

    private AdjacencyMatrix airportRoutes;
    private ColorUtils colorUtils = new ColorUtils();


    private Controller() {
        airportImage = loadImage("/res/images/airport2.png");
        planeImage = loadImage("/res/images/plane.png");
        planesList = new LinkedList<>();
        playerName = "";
    }

    /**
     * Método que devuelve la instancia de controller.
     * @return Instancia de controller.
     */
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * Método que inicializa los componentes del juego.
     * @param airportCount Cantidad de aeropuertos a generar.
     */
    public void load(int airportCount, String playerName) {
        this.playerName = playerName;
        GameWindow.show();
        Thread loadThread = new Thread(() -> {
            generateAirports(airportCount);
            this.graph = new AdjacencyMatrix(airportList);
            renderAirports(gameWindow.getMainContainer());
            System.out.println("Starting game thread..");
            startGameThread(gameWindow.getMainContainer());
            System.out.println("..done");
        });


        loadThread.setDaemon(true);
        loadThread.start();
    }

    /**
     * Método que inicia el hilo principal del juego.
     * @param gamePane Contenedor principal de la interfaz.
     */
    private void startGameThread(Pane gamePane) {
        isGameRunning = true;

        Thread gameThread = new Thread(() -> {
            int frames = 0;
            double unprocessedSecs = 0;
            long prevTime = System.nanoTime();
            double secsPerTick = 1 / 60.0;
            int tickCount = 0;
            while(isGameRunning) {
                long currentTime = System.nanoTime();
                long passedTime = currentTime - prevTime;
                prevTime = currentTime;
                unprocessedSecs += passedTime /  1000000000.0;
                while (unprocessedSecs > secsPerTick) {
                    updateGame(unprocessedSecs);
                    unprocessedSecs -= secsPerTick;
                    tickCount++;
                    if (tickCount % 60 == 0) {
//                        System.out.println(frames + " FPS");
                        prevTime += 1000;
                        frames = 0;
                    }
                }
                render(gamePane);
                frames++;
            }
        });
        gameThread.setDaemon(true);
        gameThread.start();
    }

    /**
     * Método para actualizar el estado del juego.
     * @param secs Tiempo transcurrido.
     */
    private void updateGame(double secs) {
        //TODO actualizar el estado del juego
        for (int i=0; i<airportList.getSize(); i++) {
            Airport airport = airportList.get(i);
            if (airport.hasTimeLeft()) {
                if (airport.isEmpty()) {
                    Plane plane = new Plane(planeImage, airport.getPosX(), airport.getPosY());
                    //TODO inicializar el movimiento del avión.
                } else {
                    //TODO lanzar el avión en espera
                }
            } else {
                //TODO decrementar el tiempo restante.
            }
        }
    }

    /**
     * Método encargado de renderizar los componentes.
     * @param gamePane Contenedor principal de la intefaz.
     */
    private void render(Pane gamePane) {
        //TODO renderizar los cambios en la interfaz.
        for (int i=0; i<planesList.getSize(); i++) {
            planesList.get(i).updatePos();
        }
    }

    /**
     * Método encargado de generar los aeropuertos con posiciones aleatorias.
     * @param count Cantidad de aeropuertos a generar.
     */
    private void generateAirports(int count) {
        airportList = new LinkedList<>();

        for (int i=0; i<count; i++) {
            Airport airport = new Airport(i,
                    ThreadLocalRandom.current().nextDouble(20, 1260),
                    ThreadLocalRandom.current().nextDouble(20, 700));
            airport.setTime(ThreadLocalRandom.current().nextDouble(0, 10));


            PixelReader pixelReader = gameWindow.getPixelReader();

            int colorInt = pixelReader.getArgb((int) airport.getPosX(), (int) airport.getPosY());
            if (colorInt == -14996115){
                airport.setImage(Controller.loadImage("/res/images/aircraft-carrier.png"));
                airport.setSize(35);
                airport.setCarrier(true);
//                System.out.println("Color of airport :" + airport.getId() + " " +colorInt);
//                System.out.println("Adding carrier.. \n");
            } else {
                airport.setImage(Controller.loadImage("/res/images/airport2.png"));
                airport.setSize(25);
                airport.setCarrier(false);
//                System.out.println("Color of airport :" + airport.getId() + " " +colorInt);
//                System.out.println("Adding airport.. \n");
            }
            airportList.add(airport);
        }

        testPlane();
    }

    private void testPlane() {
        Image image = new Image("file://" + System.getProperty("user.dir") +"/res/images/plane.png", 25, 25, false, false);
        Plane plane = new Plane(image, 10, 10);
        Queue<Airport> route = new Queue<>();
        for (int i=0; i<4; i++){
            route.enqueue(airportList.get(i));
        }

        plane.setRoute(route);
        System.out.println(plane.getRoute().getSize());

        Platform.runLater(()->getGameWindow().getChildren().add(plane.getImage()));
    }

    public void moveAirport(){
        Airport airport = airportList.get(0);
        Thread thread = new Thread(()->{
            double posX = airport.getPosX();
            for (int i = 0; i<1200; i++){
                double finalPosX = posX;
                Platform.runLater(()->airport.getImage().setX(finalPosX +1));
                if (i == 100){
                    Platform.runLater(()->airport.setTextToTooltip("hola"));
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                posX++;
            }
        });

        thread.setDaemon(true);
        thread.start();
    }


    /**
     * Éste método se encarga de mostrar los aeropuertos generados en la interfaz.
     * @param container Contenedor principal de la interfaz.
     */


    public void renderAirports(Pane container) {
        //TODO mostrar los aeropuertos en la ventana principal.
        for (int i=0; i<airportList.getSize(); i++) {
            int finalI = i;
            Platform.runLater(() -> container.getChildren().add(airportList.get(finalI).getImage()));
        }
    }


    /**
     * Método encargado de cargar una imagen desde la ruta especificada.
     * @param relativePath Ruta relativa de la imagen.
     * @return Instancia de la imagen.
     */
    public static Image loadImage(String relativePath) {
        try {
            return new Image("file://" +
                    (System.getProperty("user.dir") + relativePath).replaceAll(" ", "%20"));
        } catch (IllegalArgumentException e) {
            System.out.println("Couln't load " + relativePath);
        }
        return null;
    }

    /**
     * Método para mostrar una alerta al usuario
     * @param message Mensaje a mostrar
     * @param title Título de la alerta
     * @param type Tipo de alerta
     */
    public static void showAlert(String message, String title, Alert.AlertType type) {
        Alert showID = new Alert(type);
        showID.setTitle(title);
        showID.setHeaderText(null);
        showID.setContentText(message);
        showID.showAndWait();
    }

    public static LinkedList<Ruta> generateRuta(LinkedList<Airport> aeropuertos, int cantidad){
        //cantidad es la variable para ver cuantos aeropuertos quiero
        LinkedList<Ruta> listaRutas = new LinkedList<>();
        Random rand = new Random();
        Ruta nuevaRuta = new Ruta();

        for(int i = 0; i < cantidad; i++){
            int rand_int1 = rand.nextInt(aeropuertos.getSize());
            int rand_int2 = rand.nextInt(aeropuertos.getSize());
            if(rand_int1 != rand_int2) {
                nuevaRuta.setX1(aeropuertos.get(rand_int1).getPosX());
                nuevaRuta.setY1(aeropuertos.get(rand_int1).getPosY());
                nuevaRuta.setX2(aeropuertos.get(rand_int2).getPosX());
                nuevaRuta.setY2(aeropuertos.get(rand_int2).getPosY());
                listaRutas.add(nuevaRuta);
            }
            else {
                i--;
            }
        }
        return listaRutas;
    }
    /**
     * Este metodo se encarga de escoger la ruta del avion, de tal manera que escoja un nuevo aereopuerto
     * diferente al que se encuentra en ese momento
     */
    public void selectAirport(LinkedList<Airport> airports, Plane plane){
        if (plane.isEnd()){
            Random rn = new Random();
            int index;
            index = rn.nextInt(airports.getSize());
            for (int i = 0; i < airports.getSize()-1;i++) {
                if (airports.get(index).getPosX() == plane.getPosX() && airports.get(index).getPosY() == plane.getPosY()){
                    index = rn.nextInt(airports.getSize());
                }else{
                    plane.getRoute().enqueue(airports.get(index));
                    break;
                }
            }
        }
    }


    /**
     * Método que guarda la referencia de la interfaz principal en una variable de clase.
     * @param gameWindow Interfaz principal.
     */
    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public Pane getGameWindow() {
        return gameWindow.getMainContainer();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
