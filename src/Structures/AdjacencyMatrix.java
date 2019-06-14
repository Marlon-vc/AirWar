package Structures;

import Logic.Controller;
import Sprites.Airport;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class AdjacencyMatrix {
    private LinkedList<Airport> airports;
    private double[][] matrix;

    public AdjacencyMatrix(LinkedList<Airport> nodes) {
        int size = nodes.getSize();
        this.airports = nodes;
        this.matrix = new double[size][size];
        buildMatrix();
    }

    private void buildMatrix() {
        System.out.println("Building adjacency matrix..");
        int size = airports.getSize();
        boolean emptyRow;

        for (int i=0; i<size; i++) {
            emptyRow = true;
            for (int j=0; j<size; j++) {
                boolean route = ThreadLocalRandom.current().nextBoolean();
                if(route && (i != j)) {
                    emptyRow = false;
                    createRoute(i, j);
                }
            }
            if (emptyRow) {
                createRoute(i, size-1);
            }
        }
    }

    private void createRoute(int i, int j) {
        Airport airportStart = airports.get(i);
        Airport airportEnd = airports.get(j);
        double weight = Math.sqrt(
                Math.pow(airportEnd.getPosX() - airportStart.getPosX(), 2) +
                        Math.pow(airportEnd.getPosY() - airportStart.getPosY(), 2));

        matrix[i][j] = weight;
        matrix[j][i] = weight;
    }

    public double getRouteWeight(int start, int end) {
        if (matrix[start][end] != 0.0) {
            return matrix[start][end];
        } else {
            return -1;
        }
    }

    public LinkedList<Airport> shortestRoute(int id1, int id2) {
        LinkedList<Airport> route = new LinkedList<>();
        if ((id1 < airports.getSize()) && (id2 < airports.getSize()) && (id1 >= 0) && (id2 >= 0)) {
            //TODO calcular la ruta mas corta desde el aeropuerto 1 al aeropuerto 2.
        } else {
            System.out.println("Invalid airports id");
        }
        return route;
    }

    public void show() {
        int size = airports.getSize();
        int[][] m = new int[size][size];

        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                m[i][j] = (int) matrix[i][j];
            }
        }
        for (int[] row: m) System.out.println(Arrays.toString(row));
    }

    public static void main(String[] args) {
        LinkedList<Airport> list = new LinkedList<>();
        for (int i=0; i<5; i++) {
            list.add(new Airport(i, i*13, i*13+1));
        }

        AdjacencyMatrix matrix = new AdjacencyMatrix(list);

        for (int i=0; i<list.getSize(); i++) {
            for (int j=0; j<list.getSize(); j++) {
//                System.out.println("Route weight between " + i + " and " + j + ": " + matrix.getRouteWeight(i, j));
            }
        }
        matrix.show();

    }
}
