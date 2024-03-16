package dynamic_programming;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * <p>
 * Time: O(n * m)
 * Memory: O(n * m)
 */
public class Stones2 {

    private static final int LOOSE = 0;
    private static final int WIN   = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader reader  = new BufferedReader(new InputStreamReader(System.in));
        String[]       numbers = reader.readLine().split(" ");
        int            n       = Integer.parseInt(numbers[0]);
        int            m       = Integer.parseInt(numbers[1]);


        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(getStrategyResult(n, m) == 0 ? "Loose" : "Win");
        writer.close();
        reader.close();
    }

    private static int getStrategyResult(int n, int m) {
        int [][] results = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                results[i][j] = -1;
            }
        }
        int result = results[n][m];
        if (result != -1) {
            return result;
        }

        return strategy(n, m, results);
    }

    private static int strategy(int n, int m, int [][] results) {
        int result = results[n][m];
        if (result != -1) {
            return result;
        }

        if (n == 0 && m == 0) {
            result = LOOSE;
        } else if (n == 1 && m == 1) {
            result = LOOSE;
        } else if (n == 0 && m == 1) {
            result = WIN;
        } else if (n == 1 && m == 0) {
            result = WIN;
        } else if (n == 0) {
            if (strategy(0, m - 1, results) == WIN &&
                     strategy(0, m - 2, results) == WIN) {
                result = LOOSE;
            } else {
                result = WIN;
            }
        } else if (m == 0) {
            if (strategy(n - 1, 0, results) == WIN &&
                     strategy(n - 2, 0, results) == WIN) {
                result = LOOSE;
            } else {
                result = WIN;
            }
        } else if (n == 1) {
            if (strategy(0, m, results) == WIN &&
                     strategy(1, m - 1, results) == WIN &&
                     strategy(1, m - 2, results) == WIN &&
                     strategy(0, m - 2, results) == WIN) {
                result = LOOSE;
            } else {
                result = WIN;
            }
        } else if (m == 1) {
            if (strategy(n - 1, 1, results) == (WIN) &&
                     strategy(n, 0, results) == WIN &&
                     strategy(n - 2, 1, results) == WIN &&
                     strategy(n - 2, 0, results) == WIN) {
                result = LOOSE;
            } else {
                result = WIN;
            }
        } else {
            if (strategy(n - 1, m, results) == WIN &&
                     strategy(n, m - 1, results) == WIN &&
                     strategy(n - 2, m, results) == WIN &&
                     strategy(n, m - 2, results) == WIN &&
                     strategy(n - 2, m - 1, results) == WIN &&
                     strategy(n - 1, m - 2, results) == WIN
            ) {
                result = LOOSE;
            } else {
                result = WIN;
            }
        }

        results[n][m] = result;
        return result;
    }
}