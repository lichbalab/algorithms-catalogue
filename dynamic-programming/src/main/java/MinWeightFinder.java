import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * In each cell of the rectangular table
 * N×M is a number. Initially, the player is in the upper left cell. In one move, he is allowed to move to an adjacent cell either to the right or down (it is prohibited to move to the left and up). When passing through a cell, the player is taken as many kilograms of food as the number written in this cell (food is also taken for the first and last cells of his path).
 * You need to find the minimum weight of food in kilograms, giving which the player can get into the lower right corner.
 */

public class MinWeightFinder {
    public static void main(String[] args) throws IOException {
        CheapestPathFinder finder = readInputData(System.in);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(finder.cheapestPath()));
        writer.close();
    }

    public static CheapestPathFinder readInputData(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String[] tableSize = reader.readLine().split(" ");
        int linesCount = Integer.parseInt(tableSize[0]);
        int rowsCount = Integer.parseInt(tableSize[1]);
        int[][] weights =  new int[linesCount][rowsCount];
        for (int i = 0; i < linesCount; i++) {
            String[] line  = reader.readLine().split(" ");
            for (int j = 0; j < rowsCount; j++) {
                weights[i][j] = Integer.parseInt(line[j]);
            }
        }
        reader.close();

        return new CheapestPathFinder(linesCount, rowsCount, weights);
    }

    public static class CheapestPathFinder {

        private final int     linesNumber;
        private final int     rowsNumber;
        private final int[][] weights;

        public CheapestPathFinder(int linesNumber, int rowsNumber, int[][] weights) {
            this.linesNumber = linesNumber;
            this.rowsNumber = rowsNumber;
            this.weights = weights;
        }

        public int cheapestPath() {
            return cheapestPath(linesNumber - 1, rowsNumber - 1);
        }

        public int cheapestPath(int line, int row) {
            if (line == 0 && row == 0) {
                return weights[0][0];
            } else if (line == 0 && row > 0) {
                return cheapestPath(line, row - 1) + weights[line][row];
            } else if (line > 0 && row == 0) {
                return cheapestPath(line - 1, row) + weights[line][row];
            } else {
                return Math.min(cheapestPath(line - 1, row), cheapestPath(line, row - 1)) + weights[line][row];
            }
        }
    }
}