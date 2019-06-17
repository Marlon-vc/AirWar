package Structures;

import Sprites.Airport;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class AdjacencyMatrix {
    private LinkedList<Airport> airports;

    private Weight[][] weightMatrix;
    private Weight[][] distMatrix;
    private int[][] pathMatrix;

    public AdjacencyMatrix(LinkedList<Airport> nodes) {
        int size = nodes.getSize();
        this.airports = nodes;
        this.weightMatrix = new Weight[size][size];
        buildMatrix();
    }

    public static void main(String[] args) {
        LinkedList<Airport> list = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            list.add(new Airport(i, i * 13, i * 13 + 1));
        }
        AdjacencyMatrix graph = new AdjacencyMatrix(list);

        System.out.println(graph);
    }

    private void buildMatrix() {
        System.out.println("Building adjacency matrix..");
        int size = airports.getSize();
        boolean emptyRow;

        for(int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                Weight weight = new Weight();
                weightMatrix[i][j] = weight;
            }
        }

        for (int i = 0; i < size; i++) {
            emptyRow = true;
            for (int j = 0; j < size; j++) {
                boolean route = getRandomBoolean();

                if (route && (i != j)) {

                    emptyRow = false;
                    createRoute(i, j);
                }
            }
            if (emptyRow) {

                createRoute(i, getRandomIndex(i, size));
            }
        }
        System.out.println(this);

        floyd();
    }

    private int getRandomIndex(int i, int size) {
        int gen = ThreadLocalRandom.current().nextInt(0, size);
        if (gen != i) {
            return gen;
        } else {
            return getRandomIndex(i, size);
        }
    }

    private boolean getRandomBoolean() {
        int num = ThreadLocalRandom.current().nextInt(0, 100);
        return num < 20;
    }

    private void createRoute(int i, int j) {
        System.out.println("Creating connection between " + i + " & " + j);
        Airport airportStart = airports.get(i);
        Airport airportEnd = airports.get(j);
        double weight = Math.sqrt(
                Math.pow(airportEnd.getPosX() - airportStart.getPosX(), 2) +
                        Math.pow(airportEnd.getPosY() - airportStart.getPosY(), 2));

        weightMatrix[i][j].setRouteWeight(weight);
        weightMatrix[j][i].setRouteWeight(weight);
    }

    public double getRouteWeight(int start, int end) {
        return weightMatrix[start][end].getWeight();
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

    private void floyd() {
        int size = airports.getSize();

        distMatrix = new Weight[size][size];
        pathMatrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    pathMatrix[i][j] = j + 1;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            Weight weight = new Weight();
            weight.setRouteWeight(0);
            distMatrix[i][i] = weight;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                distMatrix[i][j] = weightMatrix[i][j];
            }
        }

        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (distMatrix[i][j].getWeight() >
                            distMatrix[i][k].getWeight() + distMatrix[k][j].getWeight()) {
                        distMatrix[i][j].setRouteWeight(
                                distMatrix[i][k].getWeight() + distMatrix[k][j].getWeight());
                        pathMatrix[i][j] = pathMatrix[i][k];
                    }
                }
            }
        }

        printMatrixes();
    }

    private void printMatrixes() {

        int size = airports.getSize();

        System.out.println("\nWeight Matrix:\n");
        for (Weight[] row : weightMatrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("\nDistance Matrix\n");
        for (Weight[] row : distMatrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("\nPath Matrix\n");
        for (int[] row : pathMatrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("\n");
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

        for (Weight[] row : weightMatrix) {
            builder.append(Arrays.toString(row)).append("\n");
        }
        return builder.toString();
    }
}

class Weight {
    private double routeWeight;
    private double routeDanger;

    Weight() {
        this.routeWeight = 0;
        this.routeDanger = 0;
    }

    Weight(double routeWeight) {
        this.routeWeight = routeWeight;
        this.routeDanger = 0;
    }

    public double getWeight() {
        return routeWeight + routeDanger;
    }

    public int getPrettyWeight() {
        return (int) (routeWeight + routeDanger);
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
        if (this.routeDanger > 0) {
            this.routeDanger -= danger;
            if (this.routeDanger < 0) {
                this.routeDanger = 0;
            }
        }
    }

    @Override
    public String toString() {
        return Integer.toString((int) (routeWeight + routeDanger));
    }
}
