package Logic;

import Gui.GameWindow;
import Sprites.Airport;
import Sprites.Battery;
import Sprites.Plane;
import Structures.AdjacencyMatrix;
import Structures.LinkedList;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.time.Clock;
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

    private Controller() {
        airportImage = loadImage("/res/images/airport2.png");
        planeImage = loadImage("/res/images/plane.png");
        planesList = new LinkedList<>();
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
    public void load(int airportCount) {
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
                        System.out.println(frames + " FPS");
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
        //Se cambia el estado de los aeropuertos
        for (int i=0; i<airportList.getSize(); i++) {
            Airport airport = airportList.get(i);
            if (airport.hasTimeLeft()) {
                if (airport.isEmpty()) {
//                    lauchNewPlane(airport);
                    //Generate new plane
                    Plane plane = new Plane(planeImage, airport.getPosX(), airport.getPosY());
                    plane.setRoute(graph.shortestRoute(airport.getId(),
                            graph.selectRandom(airport.getId()).getId()));

                    planesList.add(plane);


                } else {
                    //TODO lanzar el avión en espera
                    lauchQueuePlane(airport);
                }
            } else {
                //TODO decrementar el tiempo restante.
                airport.decreaseTime(secs);
            }
        }

        //Se cambia el estado de los aviones
        for (int i=0; i<planesList.getSize(); i++) {
            Plane plane = planesList.get(i);

        }
    }

    private void lauchNewPlane(Airport current) {
        Plane plane = new Plane(planeImage, current.getPosX(), current.getPosY());


    }

    private void lauchQueuePlane(Airport current) {

    }

    /**
     * Método encargado de renderizar los componentes.
     * @param gamePane Contenedor principal de la intefaz.
     */
    private void render(Pane gamePane) {
        //TODO renderizar los cambios en la interfaz.
        for (int i=0; i<planesList.getSize(); i++) {
            Plane plane = planesList.get(i);
            if (!plane.isVisible()) {
                Platform.runLater(()->gamePane.getChildren().add(plane.getImage()));
                plane.setVisibility(true);
            }

            Platform.runLater(plane::updatePos);
        }
    }

    private void createBattery(Pane container) {
        Image turret = loadImage("res/images/turret2.png");
        Battery b = new Battery(turret, 100, 100);
        System.out.println("Se crea la bateria");
        Platform.runLater(() -> container.getChildren().add(b.getImage()));

    }

    /**
     * Método encargado de generar los aeropuertos con posiciones aleatorias.
     * @param count Cantidad de aeropuertos a generar.
     */
    private void generateAirports(int count) {

        System.out.println("Generating airports..");
        airportList = new LinkedList<>();

        for (int i=0; i<count; i++) {
            Airport airport = new Airport(airportImage, i,
                    ThreadLocalRandom.current().nextDouble(0, 1281),
                    ThreadLocalRandom.current().nextDouble(0, 721));
            airport.setTime(ThreadLocalRandom.current().nextDouble(0, 10));
            airport.setSize(25);
            airportList.add(airport);
        }
    }

    /**
     * Éste método se encarga de mostrar los aeropuertos generados en la interfaz.
     * @param container Contenedor principal de la interfaz.
     */
    private void renderAirports(Pane container) {
        System.out.println("Rendering airports..");
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

    /**
     * Método que guarda la referencia de la interfaz principal en una variable de clase.
     * @param gameWindow Interfaz principal.
     */
    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

//    public void generatePlane(){
//        Random random = new Random();
//        int index = random.nextInt(airportList.getSize()-1);
//        // El valor de cantidad se debe asociar a la cantidad de aeropuertos que escoja el jugador
//        LinkedList<Ruta> ruta = generateRuta(airportList, 3);
//        //Verificar que imagen es la definitiva para los aviones y si se utilizan todas se puede implementar una lista para accesar aleatoriamente y escoger la imagen
//        Image avion = loadImage("res/images/plane3.png");
//        //Se utiliza speed = 1 para realizar las pruebas iniciales, mas adelante se puede implementar un valor distinto
//        new Plane(avion, airportList.get(index).getPosX(), airportList.get(index).getPosY(), ruta ,1);
//    }


    public void randomTime(){
//        Duration tickDuration = Duration.ofNanos(250000);
//        Clock clock1 = Clock.tick(baseclock, tickDuration);
//        System.out.println("Clock : " + baseclock.instant());
//        System.out.println("Clock1 : " + clock1.instant());

    }
}
