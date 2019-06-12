package Logic;

import Gui.GameWindow;
import Sprites.Airport;
import Structures.AdjacencyMatrix;
import Structures.LinkedList;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {

    private static Controller instance;
    private GameWindow gameWindow;

    private LinkedList<Airport> airportList;
    private AdjacencyMatrix airportRoutes;

    private Image airportImage;

    private Controller() {
        airportImage = loadImage("/res/images/airport2.png");
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
            System.out.println("..done");
        });

        loadThread.setDaemon(true);
        loadThread.start();
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

    public static Image loadImage(String relativePath) {
        try {
            return new Image("file://" +
                    (System.getProperty("user.dir") + relativePath).replaceAll(" ", "%20"));
        } catch (IllegalArgumentException e) {
            System.out.println("Couln't load " + relativePath);
        }
        return null;
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
}
