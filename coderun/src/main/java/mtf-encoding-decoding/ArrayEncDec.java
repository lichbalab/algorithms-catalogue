import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * <p>
 * Time: O(n*log(m))
 * Memory: O(n + m)
 */
public class ArrayEncDec {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[]       params = reader.readLine().split(" ");
        int            n      = Integer.parseInt(params[0]);
        int            m      = Integer.parseInt(params[1]);
        int            type   = Integer.parseInt(params[2]);

        String[] values = reader.readLine().split(" ");
        int[]    data   = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = Integer.parseInt(values[i]);
        }

        int[] result;

        if (type == 1) {
            result = encrypt(data, n, m);
        } else {
            result = decrypt(data, n, m);
        }


        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int j = 0; j < n; j++) {
            writer.write(String.valueOf(result[j]));
            if (j != n - 1) {
                writer.write(" ");
            }
        }

        writer.close();
        reader.close();
    }


    public static int[] encrypt(int[] text, int n, int m) {
        int[] permutation = new int[m];
        for (int i = 0; i < m; i++) {
            permutation[i] = i + 1;
        }

        int[] encryptedText = new int[n];

        for (int i = 0; i < n; i++) {
            int x = text[i];
            int y = findIndexAndMoveToBeginning(permutation, x, m);
            encryptedText[i] = y;
        }

        return encryptedText;
    }


    public static int[] decrypt(int[] encryptedText, int n, int m) {

        int[] permutation = new int[m];
        for (int i = 0; i < m; i++) {
            permutation[i] = i + 1;
        }

        int[] decryptedText = new int[n];
        for (int i = 0; i < n; i++) {
            int y = encryptedText[i];
            int x = permutation[y - 1];
            decryptedText[i] = x;
            moveToBeginning(permutation, y - 1);
        }

        return decryptedText;
    }

    public static void moveToBeginning(int[] arr, int position) {
        // Save the element to move
        int toMove = arr[position];

        // Shift elements to the left to fill the gap
        for (int i = position; i > 0; i--) {
            arr[i] = arr[i - 1];
        }

        // Place the saved element at the beginning
        arr[0] = toMove;
    }

    public static int findIndexAndMoveToBeginning(int[] arr, int target, int m) {
        int previous = -1;
        for (int i = 0; i < m; i++) {
            int current = arr[i];
            if (previous != -1) {
                arr[i] = previous;
            }
            previous = current;
            if (current == target) {
                arr[0] = current;
                return i + 1;
            }
        }
        return -1; // Element not found
    }
}