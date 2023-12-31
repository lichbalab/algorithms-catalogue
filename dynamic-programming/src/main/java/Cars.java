import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

        return new OperationOptimizer(carsCount, floorCarsCount, carsOrder);
    }

    public static class OperationOptimizer {
        private final int floorCarsCount;

        private Map<Integer, Integer> latsCarIndex = new HashMap<>();

        private final Map<Integer, Map<Set<Integer>, Integer>> floorCarsToOps = new HashMap<>();

        private final Map<Set<Integer>, Integer> ops = new HashMap<>();

        private final int           carsCount;
        private final List<Integer> carsOrder;


        public OperationOptimizer(int carsCount, int floorCarsCount, List<Integer> carsOrder) {
            this.carsCount = carsCount;
            this.floorCarsCount = floorCarsCount;
            this.carsOrder = carsOrder;

            for (int i = 0; i < carsOrder.size(); i++) {
                latsCarIndex.put(carsOrder.get(i), i);
            }
        }

        public int getMinOps(int minOps, int startIndex, Set<Integer> carsOnTheFloor) {
            if (startIndex == carsOrder.size()) {
                return minOps;
            }

            int car = carsOrder.get(startIndex);

            if (!carsOnTheFloor.contains(car)) {
                if (carsOnTheFloor.size() < floorCarsCount) {
                    carsOnTheFloor.add(car);
                    Map<Set<Integer>, Integer> ops = floorCarsToOps.getOrDefault(startIndex + 1, new HashMap<>());
                    if (!ops.containsKey(carsOnTheFloor)) {
                        ops.put(carsOnTheFloor, minOps + 1);
                        return getMinOps(minOps + 1, startIndex + 1, carsOnTheFloor);
                    } else {
                        return ops.get(carsOnTheFloor);
                    }
                } else {
                    int minReturnedOps = -1;
                    for (Integer item : carsOnTheFloor) {
                        Set<Integer> copy = new HashSet<>(carsOnTheFloor);
                        copy.remove(item);
                        copy.add(car);
                        Map<Set<Integer>, Integer> ops = floorCarsToOps.getOrDefault(startIndex + 1, new HashMap<>());
                        if (!ops.containsKey(copy) || ops.containsKey(copy) && ops.get(copy) > minOps + 1) {
                            ops.put(copy, minOps + 1);
                            int op = getMinOps(minOps + 1, startIndex + 1, copy);
                            if (minReturnedOps == -1 || op < minReturnedOps) {
                                minReturnedOps = op;
                            }
                        } else {
                            int op = ops.get(copy);
                            if (minReturnedOps == -1 || op < minReturnedOps) {
                                minReturnedOps = op;
                            }
                        }
                    }
                    return minReturnedOps;
                }
            }

            return getMinOps(minOps, startIndex + 1, carsOnTheFloor);
        }

        public int getMinOperations() {

            ops.put(new HashSet<>(), 0);
            for (int i = 0; i < carsOrder.size(); i++) {
                int car = carsOrder.get(i);
                Map<Set<Integer>, Integer> map = new HashMap<>();
                for (Map.Entry<Set<Integer>, Integer> entry : ops.entrySet()) {
                    Set<Integer> set      = entry.getKey();
                    int          opsCount = entry.getValue();
                    if (set.size() < floorCarsCount) {
                        if (!set.contains(car)) {
                            set.add(car);
                            opsCount++;
                        }
                        map.put(set, opsCount);
                    } else if (!set.contains(car)) {
                        opsCount++;
                        boolean exist = false;
                        for (Integer item : set) {
                            if (latsCarIndex.get(item) <= i){
                                exist = true;
                                Set<Integer> copy = new HashSet<>(set);
                                copy.remove(item);
                                copy.add(car);
                                Integer counter = map.get(copy);
                                if (counter == null || counter > opsCount) {
                                    map.put(copy, opsCount);
                                }
                            }
                        }

                        if (!exist) {
                            for (Integer item : set) {
                                Set<Integer> copy = new HashSet<>(set);
                                copy.remove(item);
                                copy.add(car);
                                Integer counter = map.get(copy);
                                if (counter == null || counter > opsCount) {
                                    map.put(copy, opsCount);
                                }
                            }
                        }
                    } else if (set.contains(car)) {
                        Integer counter = map.get(set);
                        if (counter == null || counter > opsCount) {
                            map.put(set, opsCount);
                        }
                    }
                }
                ops.clear();
                ops.putAll(map);
            }

            return Collections.min(ops.values());


            //return getMinOps(0, 0, new HashSet<>());
        }

        public Set<Integer> getFloorCars(Cursor cursor) {
            Set<Integer> floor = new HashSet<>();
            while (cursor.value < carsOrder.size()) {
                floor.add(carsOrder.get(cursor.value));
                cursor.value++;
                if (floor.size() == floorCarsCount) {
                    return floor;
                }
            }
            return floor;
        }
    }

    public static class Cursor {
        public int value = 0;
    }
}