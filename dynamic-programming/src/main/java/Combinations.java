import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


    public class Combinations {
        public static void main(String[] args) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String[] input = reader.readLine().split(" ");
            int n  = Integer.parseInt(input[0]);
            int k  = Integer.parseInt(input[1]);

            int c = rearrangement(n)/rearrangement(k)/rearrangement((n-k));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            writer.write(String.valueOf(c));
            writer.close();
            reader.close();
        }

        private static int rearrangement(int m) {
            int p = 1;
            for (int i = 1; i < m + 1; i++) {
                p*=i;
            }

            return p;
        }
    }