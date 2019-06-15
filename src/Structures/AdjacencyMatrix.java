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
        int size = airports.getSize();

        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                boolean createRoute = ThreadLocalRandom.current().nextBoolean();
                if(createRoute && (i != j)) {
//                    System.out.println("Creating connection between airport " + i + " to " + "airport " + j);
                    Airport airportStart = airports.get(i);
                    Airport airportEnd = airports.get(j);
                    double weight = Math.sqrt(
                            Math.pow(airportEnd.getPosX() - airportStart.getPosX(), 2) +
                            Math.pow(airportEnd.getPosY() - airportStart.getPosY(), 2));
//                    System.out.println("Route weight: " + weight);

                    //Setting 2-ways route
                    matrix[i][j] = weight;
                    matrix[j][i] = weight;
                }
            }
//            TODO verificar que todos los nodos tengan al menos una conexión con otro nodo.
        }
        System.out.println("Done creating connections..");
    }

    public double getRouteWeight(int start, int end) {
        if (matrix[start][end] != 0.0) {
            return matrix[start][end];
        } else {
            return -1;
        }
    }
    public LinkedList<Airport> getBestPath(int id1, int id2, int[][] pathMatrix){
        LinkedList<Integer> bestPathList = new LinkedList<Integer>();
        while(id1 != id2){
            bestPathList.add(id1);
            id1 = pathMatrix[id1][id2];
        }
        bestPathList.add(id2);
        return ConvertIdToAirports(bestPathList);
    }
    public LinkedList<Airport> ConvertIdToAirports(LinkedList<Integer> identificadores){
        LinkedList<Airport> rutaAeropuertos = new LinkedList<>();
        for(int i=0; i < identificadores.getSize(); i++){
            for(int j=0; j < airports.getSize(); j++){
                if(airports.get(j).getId() == identificadores.get(i)){
                    rutaAeropuertos.add(airports.get(j));
                    break;
                }
            }
        }
        return rutaAeropuertos;
    }


    public LinkedList<Airport> shortestRoute(int id1, int id2) {
        LinkedList<Airport> route = new LinkedList<>();
        int N = airports.getSize();
        int[][] pathMatrix = new int[N][N];
        LinkedList<Airport> airports = new LinkedList<Airport>();


        if ((id1 < airports.getSize()) && (id2 < airports.getSize()) && (id1 >= 0) && (id2 >= 0)) {
            //TODO calcular la ruta mas corta desde el aeropuerto 1 al aeropuerto 2.
            pathMatrix = FloydAlgo(pathMatrix);
            route = getBestPath(id1, id2, pathMatrix);
        } else {
            System.out.println("Invalid airports id");
        }
        return route;
    }

    public int[][] FloydAlgo(int[][] pathMatrix) {
        LinkedList<Airport> route = new LinkedList<>();
        LinkedList<Airport> airports = new LinkedList<Airport>();


        for (int k = 0; k < airports.getSize(); k++) {
            for (int i = 0; i < airports.getSize(); i++) {
                for (int j = 0; j < airports.getSize(); j++) {
                    // to keep track.;
                    if (this.matrix[i][k] + this.matrix[k][j] < this.matrix[i][j]) {
                        this.matrix[i][j] = this.matrix[i][k] + this.matrix[k][j];
                        pathMatrix[i][j] = k;
                    }
                }
            }
        }
        return pathMatrix;

    }



    public void show() {
        int size = airports.getSize();
        int[][] m = new int[size][size];

        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                m[i][j] = (int) matrix[i][j];
            }
        }
        for (int[] row: m) System.out.println(Arrays.toString(row) + "\n");
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
                System.out.println("Route weight between " + i + " and " + j + ": " + matrix.getRouteWeight(i, j));
            }
        }
        matrix.show();

    }
}
