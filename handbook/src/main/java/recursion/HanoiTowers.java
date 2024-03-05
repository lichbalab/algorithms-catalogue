import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class HanoiTowers {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        List<Move> moves = new ArrayList<>();
        new HanoiTowers().moveDisks(n, 1, 3, moves);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(moves.size()));
        for (Move move : moves) {
            writer.newLine();
            writer.write(move.fromPeg + " " + move.toPeg);
        }
        writer.close();
        reader.close();
    }

    public void moveDisks(int n, int fromPeg, int toPeg, List<Move> moves) {
        if (n == 1) {
            moves.add(new Move(fromPeg, toPeg));
        } else {
            int unusedPeg = 6 - fromPeg - toPeg;
            moveDisks(n-1, fromPeg, unusedPeg, moves);
            moves.add(new Move(fromPeg, toPeg));
            moveDisks(n -1, unusedPeg, toPeg, moves);
        }
    }

    public class Move {
        public int fromPeg;
        public int toPeg;
        Move(int fromPeg, int toPeg){
            this.toPeg = toPeg;
            this.fromPeg = fromPeg;
        };
    }
}