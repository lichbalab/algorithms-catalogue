import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Time: O(n * m)
 * Memory: O(n * m)
 */
public class Stones {

    private static final String LOOSE = "Loose";
    private static final String WIN   = "Win";

    public static void main(String[] args) throws IOException {
        BufferedReader reader  = new BufferedReader(new InputStreamReader(System.in));
        String[]       numbers = reader.readLine().split(" ");
        int            n       = Integer.parseInt(numbers[0]);
        int            m       = Integer.parseInt(numbers[1]);


        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(strategy(n, m)));
        writer.close();
        reader.close();
    }

    private static String strategy(int n, int m) {
        if (n == 0 && m == 0) {
            return LOOSE;
        } else if (n == 0) {
            if (strategy(n, m - 1).equals(LOOSE)) {
                return WIN;
            } else {
                return LOOSE;
            }
        } else if (m == 0) {
            if (strategy(n - 1, m).equals(LOOSE)) {
                return WIN;
            } else {
                return LOOSE;
            }
        } else {
            if (strategy(n - 1, m).equals(WIN) && strategy(n, m - 1).equals(WIN) && strategy(n - 1, m - 1).equals(WIN)) {
                return LOOSE;
            } else {
                return WIN;
            }
        }
    }
}