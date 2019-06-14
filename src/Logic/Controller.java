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
    private AdjacencyMatrix airportRoutes;

    private Image airportImage;
    Clock baseclock;


    private Controller() {
        airportImage = loadImage("/res/images/airport2.png");
        /*Cuando se construya una instancia de controller se iniciara el reloj base que se encarga de poner un tiempo inicial
        con el que se va a tomar como referencia para futuras acciones
        */
        Clock baseclock = Clock.systemDefaultZone();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void load(int airportCount, Pane container) {
        System.out.println("Loading..");
        Thread loadThread = new Thread(() -> {
            generateAirports(airportCount);
            this.airportRoutes = new AdjacencyMatrix(airportList);
            renderAirports(container);
            createBattery(container);
            System.out.println("..done");
        });

        loadThread.setDaemon(true);
        loadThread.start();
    }

    private void createBattery(Pane container) {
        Image turret = loadImage("res/images/turret2.png");
        Battery b = new Battery(turret, 100, 100);
        System.out.println("Se crea la bateria");
        Platform.runLater(() -> container.getChildren().add(b.getImage()));

    }

    private void generateAirports(int count) {
        airportList = new LinkedList<>();

        for (int i=0; i<count; i++) {
            Airport airport = new Airport(airportImage, i,
                    ThreadLocalRandom.current().nextDouble(0, 1281),
                    ThreadLocalRandom.current().nextDouble(0, 721));
            airport.setSize(25);

            //TODO verificar si la posición (x, y) corresponde a tierra o no, para asignar la imagen respectiva.

            airportList.add(airport);
        }
    }

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

    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }
    public void generatePlane(){
        Random random = new Random();
        int index = random.nextInt(airportList.getSize()-1);
        // El valor de cantidad se debe asociar a la cantidad de aeropuertos que escoja el jugador
        LinkedList<Ruta> ruta = generateRuta(airportList, 3);
        //Verificar que imagen es la definitiva para los aviones y si se utilizan todas se puede implementar una lista para accesar aleatoriamente y escoger la imagen
        Image avion = loadImage("res/images/plane3.png");
        //Se utiliza speed = 1 para realizar las pruebas iniciales, mas adelante se puede implementar un valor distinto
        new Plane(avion, airportList.get(index).getPosX(), airportList.get(index).getPosY(), ruta ,1);
    }

    public void randomTime(){
//        Duration tickDuration = Duration.ofNanos(250000);
//        Clock clock1 = Clock.tick(baseclock, tickDuration);
//        System.out.println("Clock : " + baseclock.instant());
//        System.out.println("Clock1 : " + clock1.instant());

    }
}
