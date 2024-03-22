package sorting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <p>
 * Time complexity: O(n * log(n))
 * Space complexity: O(n)
 */
public class MergeSort {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(reader.readLine());
            String[] values = reader.readLine().split(" ");
            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = Integer.parseInt(values[i]);
            }

            int[] mergedLists = new MergeSort().sort(array, 0, n - 1);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            for (int j = 0; j < n; j++) {
                writer.write(String.valueOf(mergedLists[j]));
                if (j != n - 1) {
                    writer.write(" ");
                }
            }
            writer.close();
            reader.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    int[] sort(int[] array, int begin, int end) {
        if (begin == end) {
            return new int[]{array[begin]};
        }
        int midInd = (begin + end)/2;
        int[] sortedLeft = sort(array, begin, midInd);
        int[] sortedRight = sort(array, midInd + 1, end);
        return merge(sortedLeft, sortedRight);
    }

    int[] merge(int[] array1, int[] array2) {
        int i = 0;
        int j = 0;
        int end1 = array1.length;
        int end2 = array2.length;
        int[] mergedArray = new int[end1 + end2];
        int k = 0;
        while (k < mergedArray.length) {
            if (i < end1 && j < end2) {
                int value1 = array1[i];
                int value2 = array2[j];
                if (value1 < value2) {
                    mergedArray[k] = value1;
                    i++;
                } else {
                    mergedArray[k] = value2;
                    j++;
                }
            } else if (i < end1) {
                mergedArray[k] = array1[i];
                i++;
            } else if (j < end2) {
                mergedArray[k] = array2[j];
                j++;
            }
            k++;
        }
        return mergedArray;
    }
}