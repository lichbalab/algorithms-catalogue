package sorting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MergeCollections {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(reader.readLine());
            int[][] lists = new int[n][];
            int[] counts = new int[n];
            int resultSize = 0;
            for (int i = 0; i < n; i++) {
                int m = Integer.parseInt(reader.readLine());
                counts[i] = m;
                resultSize += m;
                String[] values = reader.readLine().split(" ");
                lists[i] = new int[m];
                for (int j = 0; j < m; j++) {
                    lists[i][j] = Integer.parseInt(values[j]);
                }
            }

            int[] mergedLists = new MergeCollections().sort(lists, counts, resultSize);
            //int[] mergedLists = new MergeCollections().mergeArrays(lists);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            for (int j = 0; j < resultSize; j++) {
                writer.write(String.valueOf(mergedLists[j]));
                if (j != resultSize - 1) {
                    writer.write(" ");
                }
            }
            writer.close();
            reader.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    int[] sort(int[][] array, int[] counts, int resultSize) {
        int[] cursors = new int[counts.length];
        for (int i = 0; i < counts.length; i++) {
            cursors[i] = 0;
        }

        int[] mergedArray = new int[resultSize];
        int k = 0;
        int minInd = 0;
        while (k < resultSize) {
            int min = -1;
            for (int i = 0; i < array.length; i++) {
                int value = min;
                if (cursors[i] < counts[i]) {
                    value = array[i][cursors[i]];
                }
                if (min == -1) {
                    min = value;
                    minInd = i;
                } else if (min > value) {
                    min = value;
                    minInd = i;
                }
            }

            mergedArray[k] = min;
            cursors[minInd]++;
            k++;
        }

        return mergedArray;
    }

    int[] merge(int[] array1, int[] array2) {
        int i = 0;
        int j = 0;
        int end1 = array1.length;
        int end2 = array2.length;
        int[] mergedArray = new int[end1 + end2];
        int k = 0;
        int value1 = -1;
        int value2 = -1;
        while (i < end1 || j < end2) {
            if (i < end1) {
                value1 = array1[i];
            }

            if (j < end2) {
                value2 = array2[j];
            }

            if (value1 < value2) {
                mergedArray[k] = value1;
                i++;
            } else {
                mergedArray[k] = value2;
                j++;
            }
            k++;
        }

        return mergedArray;
    }


    public static int[] mergeArrays(int[][] arrays) {
        PriorityQueue<ArrayElement> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.value));

        // Add the first element from each array to the min heap
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].length > 0) {
                minHeap.add(new ArrayElement(i, 0, arrays[i][0]));
            }
        }

        List<Integer> mergedList = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            ArrayElement min = minHeap.poll();
            mergedList.add(min.value);
            int nextIndex = min.index + 1;
            if (nextIndex < arrays[min.arrayIndex].length) {
                minHeap.add(new ArrayElement(min.arrayIndex, nextIndex, arrays[min.arrayIndex][nextIndex]));
            }
        }

        // Convert the merged list to an array
        int[] mergedArray = new int[mergedList.size()];
        for (int i = 0; i < mergedList.size(); i++) {
            mergedArray[i] = mergedList.get(i);
        }
        return mergedArray;
    }

    // Helper class to store array index, element index, and value
    static class ArrayElement {
        int arrayIndex;
        int index;
        int value;

        ArrayElement(int arrayIndex, int index, int value) {
            this.arrayIndex = arrayIndex;
            this.index = index;
            this.value = value;
        }
    }
}
