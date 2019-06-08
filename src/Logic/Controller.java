package Logic;

import Gui.GameWindow;
import Sprites.Airport;
import Structures.LinkedList;
import javafx.scene.image.Image;

import java.util.concurrent.ThreadLocalRandom;

public class Controller {

    private static Controller instance;

    private GameWindow gameWindow;
    private LinkedList<Airport> airportList;

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

    public void load(int airportCount) {
        System.out.println("Loading..");
        Thread loadThread = new Thread(() -> {
            generateAirports(airportCount);
            renderAirports();
            System.out.println("..done");
        });

        loadThread.setDaemon(true);
        loadThread.start();
    }

    public void generateAirports(int count) {
        airportList = new LinkedList<>();

        for (int i=0; i<count; i++) {
            Airport airport = new Airport(airportImage,
                    ThreadLocalRandom.current().nextDouble(0, 1281),
                    ThreadLocalRandom.current().nextDouble(0, 721));
            airport.setSize(25);

            //TODO verificar si la posición (x, y) corresponde a tierra o no, para asignar la imagen respectiva.

            airportList.add(airport);
        }
    }

    public void renderAirports() {
        //TODO mostrar los aeropuertos en la ventana principal.
    }

    public static Image loadImage(String relativePath) {
        return new Image("file://" +
                (System.getProperty("user.dir") + relativePath).replaceAll(" ", "%20"));
    }

    public void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }
}
