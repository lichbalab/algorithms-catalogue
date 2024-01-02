    import java.io.BufferedReader;
    import java.io.BufferedWriter;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.OutputStreamWriter;


    public class Rearrangement {
        public static void main(String[] args) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int n  = Integer.parseInt(reader.readLine());
            int p = 1;
            for (int i = 1; i < n + 1; i++) {
                p*=i;
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            writer.write(String.valueOf(p));
            writer.close();
        }
    }