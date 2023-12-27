import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Cars {
    public static void main(String[] args) throws IOException {
        OperationOptimizer optimiser = readInputData(System.in);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(optimiser.getMinOperations()));
        writer.close();
    }

    public static OperationOptimizer readInputData(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String[] counts = reader.readLine().split(" ");
        int carsCount = Integer.parseInt(counts[0]);
        int floorCarsCount = Integer.parseInt(counts[1]);
        int orderCount = Integer.parseInt(counts[2]);
        int[] carsOrder =  new int[orderCount];
        for (int i = 0; i < orderCount; i++) {
            String line = reader.readLine();
            carsOrder[i] = Integer.parseInt(line);
        }

        reader.close();

        return new OperationOptimizer(floorCarsCount, carsOrder);
    }

    public static class OperationOptimizer {

        private final int     floorCarsCount;
        private final int[] carsOrder;

        private final HashMap<Integer, Integer> carsPriorities;

        private final Set<Integer> carsOnTheFloor = new HashSet<>();

        public OperationOptimizer(int floorCarsCount, int[] carsOrder) {
            this.floorCarsCount = floorCarsCount;
            this.carsOrder = carsOrder;

            HashMap<Integer, Integer>  priorities = new HashMap<>();
            for (int i = 0; i < carsOrder.length; i++) {
                int car = carsOrder[i];
                int priority = priorities.getOrDefault(car, 0);
                priority++;
                priorities.put(car, priority);
            }
            this.carsPriorities = priorities;
        }

        public int getMinOperations() {
            int minOps = 0;
            for (int car : carsOrder) {
                if (carsOnTheFloor.contains(car)) {
                    carsPriorities.put(car, carsPriorities.get(car) - 1);
                    continue;
                }

                if (carsOnTheFloor.size() == floorCarsCount) {
                    int carToRemove = getCarToPutOnTheShelf();
                    carsOnTheFloor.remove(carToRemove);
                }

                carsOnTheFloor.add(car);
                minOps++;

                carsPriorities.put(car, carsPriorities.get(car) - 1);
            }

            return minOps;
        }

        private int getCarToPutOnTheShelf() {
            HashMap<Integer, Integer> floorPriorities = new HashMap<>();

            for (int car : carsOnTheFloor) {
                floorPriorities.put(car, carsPriorities.get(car));
            }

            List<Map.Entry<Integer, Integer>>       entries         = new ArrayList<>(floorPriorities.entrySet());
            Comparator<Map.Entry<Integer, Integer>> valueComparator = Map.Entry.comparingByValue();
            entries.sort(valueComparator);
            return entries.get(0).getKey();
       }
    }
}