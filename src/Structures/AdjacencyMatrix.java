package Structures;

import Logic.Controller;
import Sprites.Airport;
import Sprites.Plane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AdjacencyMatrix {
    private LinkedList<Airport> airports;
    private Weight[][] matrix;

    public AdjacencyMatrix(LinkedList<Airport> nodes) {
        int size = nodes.getSize();
        this.airports = nodes;
        this.matrix = new Weight[size][size];
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

        Weight routeWeight = new Weight(weight);
        matrix[i][j] = routeWeight;
        matrix[j][i] = routeWeight;
    }

    public double getRouteWeight(int start, int end) {
        if (matrix[start][end].getRouteWeight() != 0.0) {
            return matrix[start][end].getRouteWeight();
        } else {
            return -1;
        }
    }

    public Queue<Airport> shortestRoute(int id1, int id2) {
        Queue<Airport> route = new Queue<>();
        if ((id1 < airports.getSize()) && (id2 < airports.getSize()) && (id1 >= 0) && (id2 >= 0)) {
            //TODO calcular la ruta mas corta desde el aeropuerto 1 al aeropuerto 2.

        } else {
            System.out.println("Invalid airports id");
        }
        return route;
    }

    /**
     * Este metodo se encarga de escoger la ruta del avion, de tal manera que escoja un nuevo aeropuerto
     * diferente al que se encuentra en ese momento
     */
    public Airport selectRandom(int current) {
        Airport selected = airports.get(
                ThreadLocalRandom.current().nextInt(0, airports.getSize()));
        if (selected.getId() != current) {
            return selected;
        } else {
            return selectRandom(current);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int size = airports.getSize();

        for (Weight[] row: matrix) {
            builder.append(Arrays.toString(row));
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        LinkedList<Airport> list = new LinkedList<>();
        for (int i=0; i<5; i++) {
            list.add(new Airport(
                    null, i, i*13, i*13+1));
        }

        AdjacencyMatrix matrix = new AdjacencyMatrix(list);

        for (int i=0; i<list.getSize(); i++) {
            for (int j=0; j<list.getSize(); j++) {
//                System.out.println("Route weight between " + i + " and " + j + ": " + matrix.getRouteWeight(i, j));
            }
        }
        System.out.println(matrix);

    }
}

class Weight {
    private double routeWeight;
    private double routeDanger;

    public Weight() {
        this.routeWeight = 0;
        this.routeDanger = 0;
    }

    Weight(double routeWeight) {
        this.routeWeight = routeWeight;
        this.routeDanger = 0;
    }

    public double getRouteWeight() {
        return routeWeight + routeDanger;
    }

    public int getPrettyWeight() {
        return (int)(routeWeight + routeDanger);
    }

    public void setRouteWeight(double routeWeight) {
        this.routeWeight = routeWeight;
    }

    public double getRouteDanger() {
        return routeDanger;
    }

    public void increaseDanger(double danger) {
        this.routeDanger += danger;
    }

    public void decreaseDanger(double danger) {
        if (this.routeDanger > 0){
            this.routeDanger -= danger;
            if (this.routeDanger < 0) {
                this.routeDanger = 0;
            }
        }
    }

    @Override
    public String toString() {
        return Integer.toString((int)(routeWeight + routeDanger));
    }
}
