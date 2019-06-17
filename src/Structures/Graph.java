package Structures;

import Sprites.Airport;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Graph {

    private LinkedList<Airport> airportList;

    private Weight[][] weightMatrix;
    private Weight[][] distanceMatrix;
    private int[][] pathMatrix;

    public Graph(LinkedList<Airport> airports, int size) {
        this.airportList = airports;
        weightMatrix = new Weight[size][size];
        calculateWeights();
        floyd();
    }

    public static void main(String[] args) {

        LinkedList<Airport> list = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            list.add(new Airport(i, i + 10, i + 10));
        }

        Graph graph = new Graph(list, list.getSize());

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa el origen: ");
        int origin = scanner.nextInt();
        System.out.println("\n");
        System.out.print("Ingresa el final: ");
        int end = scanner.nextInt();

        System.out.println("Distancia de la ruta: " +
                graph.getRouteWeight(origin, end));
        System.out.println(graph.shortestRoute(origin, end));
    }

    private void updateGraph() {
        System.out.println("Updating matrixes to reflect the new weights..");
        floyd();
    }

    public void increaseRouteDanger(int id1, int id2) {
        double danger = 15;
        System.out.println("Increasing danger for route " + id1 + " & " + id2 + " by " + danger);
        weightMatrix[id1][id2].increaseDanger(danger);
        updateGraph();
    }

    private void calculateWeights() {
        System.out.println("Calculating weights..");

        int size = airportList.getSize();
        boolean emptyRow;

        fillMatrix(weightMatrix, size);

        for (int i = 0; i < size; i++) {
            emptyRow = true;
            for (int j = 0; j < size; j++) {
//                boolean createConnection = ThreadLocalRandom.current().nextBoolean();
                int range = ThreadLocalRandom.current().nextInt(0, 10);
                if ((range < 3) && (i != j)) {
                    emptyRow = false;
                    makeRoute(i, j);
                }
            }
            if (emptyRow) {
                makeRoute(i, getIndexExcl(i, size));
            }
        }
//        print();
    }

    public void floyd() {
        int size = airportList.getSize();

        distanceMatrix = new Weight[size][size];
        pathMatrix = new int[size][size];

        fillMatrix(distanceMatrix, size);

        //Initializing path matrix
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    pathMatrix[i][j] = j;
                } else {
                    pathMatrix[i][j] = -1;
                }
            }
        }

        //Copying weights to distance matrix
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double weight = weightMatrix[i][j].getWeight();
                if (weight == -1) {
                    distanceMatrix[i][j].setWeight(Integer.MAX_VALUE);
                } else {
                    distanceMatrix[i][j].setWeight(weight);
                }
            }
        }

        //Setting diagonal to zero
        for (int i = 0; i < size; i++) {
            distanceMatrix[i][i].setWeight(0);
        }

        //Floyd's algorithm
        for (int k=0; k<size; k++) {
            for (int i=0; i<size; i++) {
                for (int j=0; j<size; j++) {
                    if (distanceMatrix[i][j].getWeight() >
                            distanceMatrix[i][k].getWeight() +
                                    distanceMatrix[k][j].getWeight()) {
                        distanceMatrix[i][j].setWeight(
                                distanceMatrix[i][k].getWeight() +
                                        distanceMatrix[k][j].getWeight());
                        pathMatrix[i][j] = pathMatrix[i][k];
                    }
                }
            }
        }


        print();
    }

    private void fillMatrix(Weight[][] matrix, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = new Weight();
            }
        }
    }

    private void makeRoute(int i, int j) {
        System.out.println("Creating connection between " + i + " & " + j);
        Airport airportStart = airportList.get(i);
        Airport airportEnd = airportList.get(j);
        double weight = Math.sqrt(
                Math.pow(airportEnd.getPosX() - airportStart.getPosX(), 2) +
                        Math.pow(airportEnd.getPosY() - airportStart.getPosY(), 2));

        weightMatrix[i][j].setWeight(weight);
        weightMatrix[j][i].setWeight(weight);
    }

    private int getIndexExcl(int i, int size) {
        int index = ThreadLocalRandom.current().nextInt(0, size);
        return (index != i) ? index : getIndexExcl(i, size);
    }

    public double getRouteWeight(int origin, int end) {
        return distanceMatrix[origin][end].getWeight();
    }

    public Queue<Airport> shortestRoute(int origin, int end) {
        Queue<Airport> route = new Queue<>();

        route.enqueue(getAirportById(origin));

        while (origin != end) {
            origin = pathMatrix[origin][end];
            route.enqueue(getAirportById(origin));
        }

        return route;
    }

    public Airport getAirportById(int id) {
        int size = airportList.getSize();
        for (int i=0; i<size; i++) {
            Airport current = airportList.get(i);
            if (current.getId() == id) {
                return current;
            }
        }
        System.out.println("[AIRPORT NOT FOUND]");
        return null;
    }

    private void print() {
        System.out.println("\nWeight matrix\n");
        for (Weight[] row : weightMatrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("\nPath matrix\n");
        for (int[] row : pathMatrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("\nDistances matrix\n");
        for (Weight[] row : distanceMatrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}

class Weight {
    private double weight;
    private double danger;

    public Weight(double weight) {
        this.weight = weight;
        danger = 0;
    }

    Weight() {
//        weight = Integer.MAX_VALUE;
        weight = -1;
        danger = 0;
    }

    double getWeight() {
        return weight + danger;
    }

    void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDanger() {
        return danger;
    }

    void setDanger(double danger) {
        this.danger = danger;
    }

    void increaseDanger(double danger) {
        this.danger += danger;
    }

    void decreaseDanger(double danger) {
        this.danger -= danger;
    }

    @Override
    public String toString() {
        String doubleString = Double.toString(weight);
        return doubleString.substring(0, doubleString.indexOf(".") + 2);
    }
}
