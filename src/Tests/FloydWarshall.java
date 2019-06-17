package Tests;

import static java.lang.String.format;

import java.util.Arrays;

public class FloydWarshall {

    public static void main(String[] args) {
        int[][] weights = {
                {1, 3, -2},
                {2, 1, 4},
                {2, 3, 3},
                {3, 4, 2},
                {4, 2, -1}};

        int numVertices = 4;

        floydWarshall(weights, numVertices);

    }

    private static void floydWarshall(int[][] weights, int numVertices) {

        double[][] dist = new double[numVertices][numVertices];
        for (double[] row : dist)
            Arrays.fill(row, Double.POSITIVE_INFINITY);

        for (int[] w : weights)
            dist[w[0] - 1][w[1] - 1] = w[2];

        int[][] next = new int[numVertices][numVertices];
        for (int i = 0; i < next.length; i++) {
            for (int j = 0; j < next.length; j++)
                if (i != j)
                    next[i][j] = j + 1;
        }

        for (int k = 0; k < numVertices; k++)
            for (int i = 0; i < numVertices; i++)
                for (int j = 0; j < numVertices; j++)
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }

//        printResult(dist, next);
    }

}
