package sorting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SelectionSort {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(reader.readLine());
            String[] values = reader.readLine().split(" ");
            int[]    array   = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = Integer.parseInt(values[i]);
            }

            new SelectionSort().sort(array);

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

    void sort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            int minInd = i;
            int min = array[minInd];
            for (int j = i +1; j < n; j++) {
                if (array[j] < min) {
                    min = array[j];
                    minInd = j;
                }
            }
            array[minInd] = array[i];
            array[i] = min;
        }
    }
}
