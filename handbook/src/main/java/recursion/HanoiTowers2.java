import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HanoiTowers2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(new HanoiTowers2().hanoi4Pegs(n)));
        writer.close();
        reader.close();
    }
    public int hanoi4Pegs(int n) {
        if (n == 1) {
            return 1;
        }

        int minMoves = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            int moves = 2 * hanoi4Pegs(i) + hanoi3Pegs(n - i);
            minMoves = Math.min(minMoves, moves);
        }

        return minMoves;
    }
    public int hanoi3Pegs(int n) {
        if (n == 1) {
            return 1;
        }
        return 2 * hanoi3Pegs(n - 1) + 1;
    }
}