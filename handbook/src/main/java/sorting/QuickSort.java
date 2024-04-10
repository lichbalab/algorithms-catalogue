package sorting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * <p>
 * Time complexity: O(n * log(n))
 * Space complexity: O(n)
 */
public class QuickSort {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(reader.readLine());
            String[] values = reader.readLine().split(" ");
            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = Integer.parseInt(values[i]);
            }

            new QuickSort().sort(array, 0, n - 1);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            for (int j = 0; j < n; j++) {
                writer.write(String.valueOf(array[j]));
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

    void sort(int[] array, int begin, int end) {
        if (begin < end) {
            int k = doPartition(array, begin, end);
            sort(array, begin, k - 1);
            sort(array, k + 1, end);
        }
    }
    int doPartition(int []arr, int low,
                         int high)
    {
        int pivot = arr[high];

        // Index of smaller element
        int i = (low - 1);

        for (int j = low; j <= high- 1; j++)
        {
            // If current element is smaller
            // than or equal to pivot
            if (arr[j] <= pivot)
            {
                i++; // increment index of
                // smaller element
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}