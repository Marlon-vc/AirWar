package Structures;

import Sprites.Airport;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class AdjacencyMatrix {
    private LinkedList<Airport> airports;
    private Weight[][] distancesMatrix;
    private int[][] routesMatrix;


    public AdjacencyMatrix(LinkedList<Airport> nodes) {
        int size = nodes.getSize();
        this.airports = nodes;
        this.distancesMatrix = new Weight[size][size];
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
        distancesMatrix[i][j] = routeWeight;
        distancesMatrix[j][i] = routeWeight;
    }

    public double getRouteWeight(int start, int end) {
        //TODO eliminar verificación si es null
        if (distancesMatrix[start][end] != null) {
            if (distancesMatrix[start][end].getRouteWeight() != 0.0) {
                return distancesMatrix[start][end].getRouteWeight();
            }
        }
        return -1;
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

        for (Weight[] row: distancesMatrix) {
            builder.append(Arrays.toString(row)).append("\n");
        }
        return builder.toString();
    }
}

class Weight {
    private double routeWeight;
    private double routeDanger;

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
