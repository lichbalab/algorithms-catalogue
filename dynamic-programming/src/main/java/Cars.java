import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Cars {
    public static void main(String[] args) throws IOException {
        OperationOptimizer optimiser = readInputData(System.in);
        BufferedWriter     writer    = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(optimiser.getMinOperations()));
        writer.close();
    }

    public static OperationOptimizer readInputData(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String[] counts         = reader.readLine().split(" ");
        int      carsCount      = Integer.parseInt(counts[0]);
        int      floorCarsCount = Integer.parseInt(counts[1]);
        int      orderCount     = Integer.parseInt(counts[2]);
        int[]    carsOrder      = new int[orderCount];
        for (int i = 0; i < orderCount; i++) {
            String line = reader.readLine();
            carsOrder[i] = Integer.parseInt(line);
        }

        reader.close();

        return new OperationOptimizer(floorCarsCount, carsOrder);
    }

    public static class OperationOptimizer {
        private final int   floorCarsCount;
        private final int[] carsOrder;

        public OperationOptimizer(int floorCarsCount, int[] carsOrder) {
            this.floorCarsCount = floorCarsCount;
            this.carsOrder = carsOrder;
        }

        public int getMinOps(int minOps, int startIndex, Set<Integer> carsOnTheFloor) {
            if (startIndex == carsOrder.length) {
                return minOps;
            }

            int          car  = carsOrder[startIndex];
            Set<Integer> copy = new HashSet<>(carsOnTheFloor);

            if (!copy.contains(car)) {
                if (copy.size() < floorCarsCount) {
                    copy.add(car);
                    return getMinOps(minOps + 1, startIndex + 1, copy);
                } else {
                    int minReturnedOps = -1;
                    for (Integer item : carsOnTheFloor) {
                        copy = new HashSet<>(carsOnTheFloor);
                        copy.remove(item);
                        copy.add(car);
                        int ops = getMinOps(minOps + 1, startIndex + 1, copy);
                        if (minReturnedOps == -1 || ops < minReturnedOps) {
                            minReturnedOps = ops;
                        }
                    }
                    return minReturnedOps;
                }
            }

            return getMinOps(minOps, startIndex + 1, copy);
        }

        public int getMinOperations() {
            return getMinOps(0, 0, new HashSet<>());
        }

    }
}