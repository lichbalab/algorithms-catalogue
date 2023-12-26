import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MinWeightFinderAlternative {
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
            for (int i = 0; i < linesNumber; i++) {
                for (int j = 0; j < rowsNumber; j++) {
                    if (i == 0 && j > 0) {
                        weights[i][j] = weights[i][j - 1] + weights[i][j];
                    } else if (i > 0 && j == 0) {
                        weights[i][j] = weights[i - 1][j] + weights[i][j];
                    } else if (i > 0) {
                        weights[i][j] = Math.min(weights[i -1][j], weights[i][j - 1]) + weights[i][j];
                    }
                }
            }
            return weights[linesNumber - 1][rowsNumber - 1];
        }
    }
}