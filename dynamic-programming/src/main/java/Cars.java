import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cars {
    public static void main(String[] args) throws IOException {
        OperationOptimizer optimiser = readInputData(System.in);
        BufferedWriter     writer    = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(optimiser.getMinOperations()));
        writer.close();
    }

    public static OperationOptimizer readInputData(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String[]      counts         = reader.readLine().split(" ");
        int           carsCount      = Integer.parseInt(counts[0]);
        int           floorCarsCount = Integer.parseInt(counts[1]);
        int           orderCount     = Integer.parseInt(counts[2]);
        List<Integer> carsOrder      = new ArrayList<>();
        for (int i = 0; i < orderCount; i++) {
            String line = reader.readLine();
            carsOrder.add(Integer.parseInt(line));
        }

        reader.close();

        return new OperationOptimizer(floorCarsCount, carsOrder);
    }

    public static class OperationOptimizer {


        private final int           floorCarsCount;
        private final List<Integer> carsOrder;

        private final Set<Integer> carsOnTheFloor = new HashSet<>();

        private final HashMap<Integer, Integer> carsPriorities;

        public OperationOptimizer(int floorCarsCount, List<Integer> carsOrder) {
            this.floorCarsCount = floorCarsCount;
            this.carsOrder = carsOrder;
            normalizeCarsOrder();
            HashMap<Integer, Integer>  priorities = new HashMap<>();
            for (Integer car : carsOrder) {
                Integer priority = priorities.getOrDefault(car, 0);
                priority++;
                priorities.put(car, priority);
            }
            this.carsPriorities = priorities;
        }

        public int getMinOperations() {
            int minOps = 0;

            for (int i = 0; i < carsOrder.size(); i += floorCarsCount) {
                int          step   = i + floorCarsCount <= carsOrder.size() ? floorCarsCount : carsOrder.size() - i;
                List<Integer> subArray = carsOrder.subList(i, i + step);
                Set<Integer> subSet = new HashSet<>(subArray);
                Set<Integer> intersection = new HashSet<>(carsOnTheFloor);
                intersection.removeAll(subSet);
                subSet.removeAll(carsOnTheFloor);
                int ops = subSet.size();
                if (ops > 0) {
                    List<Integer> carsForRemoving = getCarsToPutOnTheShelf(intersection);
                    for (int j = 0; j < Math.min(ops, intersection.size()); j++ ) {
                        int car = carsForRemoving.get(j);
                        carsOnTheFloor.remove(car);
                    }

                    carsOnTheFloor.addAll(subSet);
                    minOps += ops;
                }

                subArray = carsOrder.subList(i, i + step);
                for (Integer car : subArray) {
                    carsPriorities.put(car, carsPriorities.get(car) - 1);
                }
            }

            return minOps;
        }

        private List<Integer> getCarsToPutOnTheShelf(Set<Integer> cars) {
            HashMap<Integer, Integer> floorPriorities = new HashMap<>();

            for (int car : cars) {
                floorPriorities.put(car, carsPriorities.get(car));
            }

            List<Map.Entry<Integer, Integer>>       entries         = new ArrayList<>(floorPriorities.entrySet());
            Comparator<Map.Entry<Integer, Integer>> valueComparator = Map.Entry.comparingByValue();
            entries.sort(valueComparator);
            return entries.stream().map(Map.Entry::getKey).toList();
        }

        private void normalizeCarsOrder() {
            int size = carsOrder.size();

            for (int i = size - 1; i > 0; i--) {
                if (carsOrder.get(i).equals(carsOrder.get(i - 1))) {
                    carsOrder.remove(i);
                }
            }        }

        private void addCarrOnTheFloor(int car) {
            carsOnTheFloor.add(car);
        }

    }

}