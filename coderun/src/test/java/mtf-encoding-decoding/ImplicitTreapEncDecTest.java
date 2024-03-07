import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ImplicitTreapEncDecTest {

    static final String TEST_DIR = "mtf-encoding-decoding";
    static final String BASE_DIR = "src/test/resources/" + TEST_DIR;

    static PrintStream ps = System.out;

    @Test
    @Order(1)
    void testAlgorithm() throws IOException {
        List<File> files = readFiles();
        for (File file : files) {
            try (FileInputStream is = new FileInputStream(file)) {
                System.setIn(is);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outputStream));

                ImplicitTreapEncDec.main(new String[]{});

                String result = outputStream.toString();

                List<String> allLines       = Files.readAllLines(file.toPath());
                String       expectedResult = allLines.get(allLines.size() - 1);
                Assertions.assertEquals(expectedResult, result, "Wrong expected result for input: " + file.getName());
            }
        }

        FileOutputStream os   = new FileOutputStream(BASE_DIR + "/output.txt");
        int[]          list   = new int[300000];
        Random         rand   = new Random();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

        for (int i = 0; i < 300000; i++) {
            list[i] = rand.nextInt(300000);
            if (list[i] == 0) {
                list[i] = 1;
            }
        }

        int[] encrypted = ImplicitTreapEncDec.encode(list, 300000, 300000);
        for (int i = 0; i < 300000; i++) {

            writer.write(String.valueOf(encrypted[i]));
            if (i != 300000 - 1) {
                writer.write(" ");
            }
        }

        writer.newLine();

        for (int i = 0; i < 300000; i++) {
            writer.write(String.valueOf(list[i]));
            if (i != 300000 - 1) {
                writer.write(" ");
            }
        }
        writer.close();
    }

    @Test
    @Order(0)
    void testAlgorithmBench() throws IOException {
        long start = System.currentTimeMillis();
        File file = new File(BASE_DIR + "/bench-input/input7.txt");
        try (FileInputStream is = new FileInputStream(file)) {
            System.setIn(is);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            ImplicitTreapEncDec.main(new String[]{});
            long end = System.currentTimeMillis();
            System.setOut(System.out);
            ps.println((end - start) + "ms");

            String result = outputStream.toString();

            List<String> allLines       = Files.readAllLines(file.toPath());
            String       expectedResult = allLines.get(allLines.size() - 1);
            Assertions.assertEquals(expectedResult, result, "Wrong expected result for input: " + file.getName());
        }
    }

    @Test
    @Order(2)
    void testAlgorithmDecBench30000() throws IOException {
        File file = new File(BASE_DIR + "/bench-input/input6.txt");
        try (FileInputStream is = new FileInputStream(file)) {
            System.setIn(is);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            ImplicitTreapEncDec.main(new String[]{});

            String result = outputStream.toString();

            List<String> allLines       = Files.readAllLines(file.toPath());
            String       expectedResult = allLines.get(allLines.size() - 1);
            Assertions.assertEquals(expectedResult, result, "Wrong expected result for input: " + file.getName());
        }
    }

    @Test
    @Order(3)
    void testAlgorithmDecBench300000() throws IOException {
        long start = System.currentTimeMillis();
        File file = new File(BASE_DIR + "/bench-input/input8.txt");
        try (FileInputStream is = new FileInputStream(file)) {
            System.setIn(is);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            ImplicitTreapEncDec.main(new String[]{});
            long end = System.currentTimeMillis();
            ps.println((end - start) + "ms");

            String result = outputStream.toString();

            List<String> allLines       = Files.readAllLines(file.toPath());
            String       expectedResult = allLines.get(allLines.size() - 1);
            Assertions.assertEquals(expectedResult, result, "Wrong expected result for input: " + file.getName());
        }
    }

    private static List<File> readFiles() throws IOException {
        try (Stream<Path> paths = Files.list(Paths.get(BASE_DIR + "/input"))) {
            return paths.map(Path::toFile).collect(Collectors.toList());
        }
    }
}