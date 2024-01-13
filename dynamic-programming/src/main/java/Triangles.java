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
 * Petya has been studying in a mathematical circle for quite a long time, so he has already learned not only the rules for performing the simplest operations, but also such a rather complex concept as symmetry. In order to better study symmetry, Petya decided to start with the simplest geometric shapes - triangles. He soon realized that so-called isosceles triangles have axial symmetry. Therefore, now Petya is looking for such triangles everywhere.
 * Recall that a triangle is called isosceles if its area is positive and it has at least two equal sides.
 * Recently Petya entered the classroom and saw that there were n dots drawn on the board. Of course, he immediately wondered how many triplets there were of these points, which are the vertices of isosceles triangles.
 * It is required to write a program that solves the specified problem.
 *
 * <p>
 * Time: O(n^2)
 * Memory: O(n^2)
 */
public class Triangles {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int            n      = Integer.parseInt(reader.readLine());
        List<Point>    points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String[] interval = reader.readLine().split(" ");
            points.add(new Point(Integer.parseInt(interval[0]), Integer.parseInt(interval[1])));
        }

        int counter = 0;
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            Point point = points.get(i);
            Map<Double, List<Point>> distances = new HashMap<>();
            for (int j = 0; j < n; j++) {
                if (i != j){
                    Point itemPoint = points.get(j);
                    double distance  = matrix[i][j];
                    if (distance == 0){
                        distance = distance(point, itemPoint);
                        matrix[i][j] = distance;
                        matrix[j][i] = distance;
                    }
                    List<Point> list = distances.computeIfAbsent(distance, k -> new ArrayList<>());

                    if (!list.isEmpty()) {
                        for (Point p : list) {
                            if (!pointsAreOnTheSameLine(point, itemPoint, p)) {
                                counter++;
                            }
                        }
                    }
                    list.add(itemPoint);
                }
            }
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(counter));
        writer.close();
        reader.close();
    }

    static boolean pointsAreOnTheSameLine(Point p1, Point p2, Point p3) {
        return (p1.y - p2.y)*(p2.x  - p3.x) == (p2.y - p3.y) * (p1.x - p2.x);
    }

    static double distance(Point p1, Point p2) {
        return Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2);
    }


    record Point(int x, int y) {
    }
}