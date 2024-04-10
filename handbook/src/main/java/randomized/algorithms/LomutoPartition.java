package randomized.algorithms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LomutoPartition {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(reader.readLine());
            String[] values = reader.readLine().split(" ");
            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = Integer.parseInt(values[i]);
            }

            new LomutoPartition().doPartition(array);

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

    void doPartition(int[] arr) {
        int m = arr[0];
        int k = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < m) {
               swap(arr, k, i);
               k++;
            }
        }
        swap(arr, 0, --k);
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}