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
import org.junit.jupiter.api.Test;

class EncryptionTest {

    @Test
    void testAlgorithm() throws IOException {
        List<File> files = readFiles();
        for (File file : files) {
            try (FileInputStream is = new FileInputStream(file)) {
                System.setIn(is);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outputStream));

                Encryption.main(new String[]{});

                String result = outputStream.toString();

                List<String> allLines       = Files.readAllLines(file.toPath());
                String       expectedResult = allLines.get(allLines.size() - 1);
                Assertions.assertEquals(expectedResult, result, "Wrong expected result for input: " + file.getName());
            }
        }

        FileOutputStream os   = new FileOutputStream("src/test/resources/output.txt");
        int[]          list   = new int[300000];
        Random         rand   = new Random();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

        for (int i = 0; i < 300000; i++) {
            list[i] = rand.nextInt(300000);
            if (list[i] == 0) {
                list[i] = 1;
            }
        }
//        writer.newLine();

        int[] encrypted = Encryption.encrypt(list, 300000, 300000);
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
//


        writer.close();
    }

    @Test
    void testAlgorithmBench() throws IOException {
        File file = new File("src/test/resources/bench_input/input7.txt");
        try (FileInputStream is = new FileInputStream(file)) {
            System.setIn(is);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            Encryption.main(new String[]{});

            String result = outputStream.toString();

            List<String> allLines       = Files.readAllLines(file.toPath());
            String       expectedResult = allLines.get(allLines.size() - 1);
            Assertions.assertEquals(expectedResult, result, "Wrong expected result for input: " + file.getName());
        }
    }

    @Test
    void testAlgorithmDecBench() throws IOException {
        File file = new File("src/test/resources/bench_input/input5.txt");
        try (FileInputStream is = new FileInputStream(file)) {
            System.setIn(is);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            Encryption.main(new String[]{});

            String result = outputStream.toString();

            List<String> allLines       = Files.readAllLines(file.toPath());
            String       expectedResult = allLines.get(allLines.size() - 1);
            Assertions.assertEquals(expectedResult, result, "Wrong expected result for input: " + file.getName());
        }
    }

    @Test
    void testAlgorithmDecBench1() throws IOException {
        File file = new File("src/test/resources/bench_input/input8.txt");
        try (FileInputStream is = new FileInputStream(file)) {
            System.setIn(is);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            Encryption.main(new String[]{});

            String result = outputStream.toString();

            List<String> allLines       = Files.readAllLines(file.toPath());
            String       expectedResult = allLines.get(allLines.size() - 1);
            Assertions.assertEquals(expectedResult, result, "Wrong expected result for input: " + file.getName());
        }
    }

    //@Test
    void testIterations() {
        //ArrayList<Integer> list1 = new ArrayList(300000);
        //ArrayList<Integer> list2 = new ArrayList(30000);

/*
        int[]                           list1 = new int[300000];
        int[] list2 = new int[300000];
*/

        for (int i = 0; i < 300000; i++) {
        }

/*
        for (int j = 0; j < 300000; j++) {
            list2[j] = j;
        }
*/



/*
        for (int i = 0; i < 300000; i++) {
            for (int j = 0; j < 300000; j++) {
                int b = list1[i] + list2[j];
            }
        }
*/

    }


    private static List<File> readFiles() throws IOException {
        try (Stream<Path> paths = Files.list(Paths.get("src/test/resources/input"))) {
            return paths.map(Path::toFile).collect(Collectors.toList());
        }
    }
}