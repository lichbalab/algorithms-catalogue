import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * Time: O(n^3)
 * Memory: O(n)
 */
public class TrianglesAlternative {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int            n      = Integer.parseInt(reader.readLine());
        List<Point>    points = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            String[] interval = reader.readLine().split(" ");
            points.add(new Point(Integer.parseInt(interval[0]), Integer.parseInt(interval[1])));
        }


        int coiunter = 0;

        for (int i = 0; i < n; i++){
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(j);
                    Point p3 = points.get(k);
                    double edge1 = distance(p1, p2);
                    double edge2 = distance(p1, p3);
                    double edge3 = distance(p2, p3);

                    if (edge1 == edge2 || edge1 == edge3 || edge2 == edge3) {
                        coiunter++;
                    }
                }
            }
        }


        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(coiunter));
        writer.close();
        reader.close();
    }
    static double distance(Point p1, Point p2) {
        return Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2);
    }


    record Point(int x, int y) {
    }
}

