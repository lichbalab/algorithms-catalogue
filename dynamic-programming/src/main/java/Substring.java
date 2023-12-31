import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class Substring {

    public static void main(String[] args) throws IOException {
        Input input = readInputData(System.in);
        Map.Entry<Integer, Integer> entry = getMaxSubSequence(input.charSequence, input.n, input.k);
        BufferedWriter                     writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(entry.getKey() + " " + entry.getValue());
        writer.close();
    }

    public static Map.Entry<Integer, Integer> getMaxSubSequence(char[] seq,int n, int k) {
        int length = 0;
        int index = 0;
        for (int i = 0; i < n; i++) {
            int startSubSec = i;
            int endSubSec = i;
            Map<Character, Integer> dict = new HashMap<>();
            Map<Character, Integer> positions = new HashMap<>();
            while (endSubSec < n) {
                char symbol = seq[endSubSec];
                positions.putIfAbsent(symbol, endSubSec);
                int count = dict.getOrDefault(symbol, 0);
                if (count < k) {
                    dict.put(symbol, ++count);
                    int subSecLength = endSubSec - startSubSec + 1;
                    if (length < subSecLength) {
                        length = subSecLength;
                        index = startSubSec;
                    }
                } else {
                    dict.clear();
                    dict.put(symbol, 1);
                    startSubSec = endSubSec;
                }
                endSubSec++;
            }
        }
        return new AbstractMap.SimpleEntry<>(length, index + 1);
    }


    static Input readInputData(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String[] numbers = reader.readLine().split(" ");
        int n = Integer.parseInt(numbers[0]);
        int k = Integer.parseInt(numbers[1]);
        char[] s =  reader.readLine().toCharArray();
        reader.close();

        return new Input(n, k, s);
    }

    static class Input{
        int n;
        int k;
        char[] charSequence;

        public Input(int n, int k, char[] charSequence) {
            this.n = n;
            this.k = k;
            this.charSequence = charSequence;
        }
    }





}
