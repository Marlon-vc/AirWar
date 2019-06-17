package Logic;

import Gui.GameWindow;
import Sprites.Airport;
import Sprites.Battery;
import Sprites.Missile;
import Sprites.Plane;
import Structures.AdjacencyMatrix;
import Structures.LinkedList;
import Structures.Queue;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;

import java.util.concurrent.ThreadLocalRandom;

public class Controller {
    private static Controller instance;
    private GameWindow gameWindow;
    private LinkedList<Airport> airportList;
    private LinkedList<Plane> planesList;
    private Battery battery;
    private AdjacencyMatrix graph;
    private Image airportImage;
    private Image planeImage;
    private boolean isGameRunning;
    private String playerName;
    private AdjacencyMatrix airportRoutes;
    private int planesDestroyed;
    private boolean right;

    private InputHandler inputHandler;
    private String shootKey;
    private LinkedList<Collision> collisionList;

    private LinkedList<Missile> missileList;

    private Controller() {
        this.shootKey = "SPACE";
        this.inputHandler = new InputHandler(shootKey, this);
        airportImage = loadImage("/res/images/airport2.png");
        planeImage = loadImage("/res/images/plane.png");
        planesList = new LinkedList<>();
        playerName = "";
        missileList = new LinkedList<>();
        planesDestroyed = 0;
        collisionList = new LinkedList<>();
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

    public InputHandler getInputHandler() {
        return this.inputHandler;
    }

    public void setShootKey(String shootKey) {
        this.shootKey = shootKey;
        inputHandler.setShootKey(shootKey);
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

    public void stopGame() {
        isGameRunning = false;
        Controller.showAlert("El juego a terminado", "Game over", Alert.AlertType.INFORMATION);
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
            // Se genera la bateria que es donde se dispara a los aviones
            generateBattery(gameWindow.getMainContainer());
            this.graph = new AdjacencyMatrix(airportList);
            System.out.println(this.graph);
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
            StringBuilder status;
            while(isGameRunning) {
                long currentTime = System.nanoTime();
                long passedTime = currentTime - prevTime;
                prevTime = currentTime;
                unprocessedSecs += passedTime /  1000000000.0;
                while (unprocessedSecs > secsPerTick) {
                    updateGame(unprocessedSecs / 2);
                    unprocessedSecs -= secsPerTick;
                    tickCount++;
                    if (tickCount % 60 == 0) {
                        status = new StringBuilder();
                        status.append("Status\n")
                                .append(frames).append(" FPS\n")
                                .append("Airport count: ").append(airportList.getSize()).append("\n")
                                .append("Planes count: ").append(planesList.getSize()).append("\n");
//                        System.out.println(status);
                        prevTime += 1000;
                        frames = 0;
                    }
                }
                render(gamePane);
                frames++;
            }
        });
        gameThread.setDaemon(true);
        gameThread.setName("Game thread");
        gameThread.start();
    }

    /**
     * Método para actualizar el estado del juego.
     * @param secs Tiempo transcurrido.
     */
    private void updateGame(double secs) {
        //Se cambia el estado de los aeropuertos
        int airportCount = airportList.getSize();
        for (int i=0; i<airportCount; i++) {
            Airport airport = airportList.get(i);
            if (!airport.hasTimeLeft()) {
                if (airport.isEmpty()) {
                    Plane plane = new Plane(planeImage, airport.getPosX(), airport.getPosY());
                    plane.setRouteOrigin(airport);
                    plane.setSize(25);
                    Queue<Airport> testRoute = new Queue<>();
                    testRoute.enqueue(graph.selectRandom(airport.getId()));
                    plane.setRoute(testRoute);
                    plane.setOnAir(true);
//                    plane.setRoute(graph.shortestRoute(airport.getId(), graph.selectRandom(airport.getId()).getId()));
                    planesList.add(plane);
                } else {
                    Plane plane = airport.getNextPlane();
                    plane.nextNode();
                    plane.setOnAir(true);
                }
                airport.setTime(ThreadLocalRandom.current().nextDouble(2, 15));
            } else {
                airport.decreaseTime(secs);
            }
        }

        //Se cambia el estado de los aviones
        int planeCount = planesList.getSize();
        for (int i=0; i<planeCount; i++) {
            Plane plane = planesList.get(i);
            plane.nextX();
            plane.nextY();

            //TODO check if plane is arriving to an airport
        }

        battery.checkPosition();
        battery.moveX();

        //remove collision

//        System.out.println("Collistion list " + collisionList       );
        for (int i=0; i<collisionList.getSize(); i++){
            Collision collision = collisionList.get(i);
            if (!collision.hasTimeLeft()) {
                Platform.runLater(() -> getGameWindow().getChildren().remove(collision.getExplosion()));
                collisionList.remove(collision);
            } else {
                collision.updateTime();
            }
        }

        //update missil

        int missileCount = missileList.getSize();
        LinkedList<Missile> toDelete = new LinkedList<>();
        for (int i=0; i<missileCount; i++){
            Missile missile = missileList.get(i);
            missile.moveY();

            Collision collision = checkCollision(missile.getPosY(), missile.getPosX());

            if (collision != null) {
                collisionList.add(collision);
                Plane planeToDelete = collision.getPlane();
                planesList.remove(planeToDelete);
                planesDestroyed++;
                Platform.runLater(()->gameWindow.setPlanesDestroyed(planesDestroyed));
                collision.setPos(planeToDelete.getPosX(), planeToDelete.getPosY());
                Platform.runLater(()->{
                    getGameWindow().getChildren().removeAll(planeToDelete.getImage(), missile.getImage());
                    getGameWindow().getChildren().add(collision.getExplosion());
                });
            }

            //todo crear lista de collisiones para eliminar explosiones
            if (missile.check()){
                toDelete.add(missile);
                Platform.runLater(()->getGameWindow().getChildren().remove(missile.getImage()));
            }
        }


        int deleteCount = toDelete.getSize();
        for (int i = 0; i<deleteCount; i++) {
            missileList.remove(toDelete.get(i));
        }
    }

    public Pane getMainPane() {
        return gameWindow.getMainContainer();
    }

    /**
     * Método encargado de renderizar los componentes.
     * @param gamePane Contenedor principal de la intefaz.
     */
    private void render(Pane gamePane) {
        for (int i = 0; i < planesList.getSize(); i++) {
            Plane plane = planesList.get(i);

            if (plane.isOnAir()) {
                if (!plane.isVisible()) {
                    plane.setVisibility(true);
                    Platform.runLater(() -> gamePane.getChildren().add(plane.getImage()));
                }
                plane.updatePos();
            }
        }
        for (int i=0; i<planesList.getSize(); i++) {
            planesList.get(i).updatePos();
        }

        //mover bateria
        battery.updatePos();

        //mover misil

        int missileCount = missileList.getSize();
        for (int i=0; i<missileCount; i++){
            Missile missile = missileList.get(i);
            missile.updatePos();
        }
        for (int i=0; i<planesList.getSize(); i++) {
            planesList.get(i).updatePos();
        }
    }

    public void shootMissile(double speed){

        Image missileImage = loadImage("/res/images/missile1.png");
        Missile missile = new Missile(missileImage, battery.getPosX() + 25, battery.getPosY()-20);
        missile.setSize(10);
        missile.setMoveStep(speed);
        Platform.runLater(() -> getGameWindow().getChildren().add(missile.getImage()));
        missileList.add(missile);

    }



    private Collision checkCollision(double posY, double posX) {
        posX+=5;
        int size = planesList.getSize();
        for (int i=0; i<size; i++){
            Plane actual = planesList.get(i);
            if (actual.isOnAir()) {
                if (actual.getPosX() < posX && (actual.getPosX() + 25) > posX) {
                    if (actual.getPosY() < posY && (actual.getPosY() + 25) > posY) {
                        Collision collision = new Collision(loadImage("/res/images/explosion.png"));
                        collision.setPlane(actual);
                        collision.setHit(true);
                        return collision;
                    }
                }
            }
        }
        return null;
    }

        /**
     * Método encargado de generar los aeropuertos con posiciones aleatorias.
     * @param count Cantidad de aeropuertos a generar.
     */
    private void generateAirports(int count) {

        System.out.println("Generating airports..");
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

//        testPlane();
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
        for (int i=0; i<airportList.getSize(); i++) {
            int finalI = i;
            Platform.runLater(() -> container.getChildren().add(airportList.get(finalI).getImage()));
        }
    }

    /**
     * Este metodo genera una instancia de la bateria antiaerea
     * @param container es donde se agrega el objeto a la pantalla de juego
     */
    public void generateBattery(Pane container){
        Image turret = loadImage("/res/images/turret2.png");
        battery = new Battery(turret,10,635);
        battery.setSize(50);
        Platform.runLater(() -> {
            container.getChildren().add(battery.getImage());
        });
    }

    public Pane getGameWindow() {
        return gameWindow.getMainContainer();
    }


    /**
     * Método que guarda la referencia de la interfaz principal en una variable de clase.
     * @param gameWindow Interfaz principal.
     */
    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public double getWeight(int idStart, int idEnd) {
        return graph.getRouteWeight(idStart, idEnd);
    }

    public AdjacencyMatrix getGraph() {
        return this.graph;
    }

    public String getKey() {
        return inputHandler.getShootKey();
    }
}
