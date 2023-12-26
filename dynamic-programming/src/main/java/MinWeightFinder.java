import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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