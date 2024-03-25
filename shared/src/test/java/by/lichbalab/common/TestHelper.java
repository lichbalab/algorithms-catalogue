package by.lichbalab.common;

import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class TestHelper {

    protected abstract String getTestCasesPath();

    protected void testAlgorithmTest(Function<List<String>, String> test, int inputLines) {
        List<File> files = readFiles();
        for (File file : files) {
            try {
                List<String> allLines       = Files.readAllLines(file.toPath());
                String result = test.apply(allLines.subList(0, inputLines));
                String       expectedResult = allLines.getLast();
                Assertions.assertEquals(expectedResult, result, "Wrong expected result for input: " + file.getName());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    protected void testMainAlgorithmTest(Runnable main, int inputLines) {
        List<File> files = readFiles();
        for (File file : files) {
            try (FileInputStream is = new FileInputStream(file)) {
                System.setIn(is);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outputStream));

                main.run();

                String result = outputStream.toString();

                List<String> allLines = Files.readAllLines(file.toPath());
                String expectedResult = String.join("\n", allLines.subList(inputLines, allLines.size()));
                Assertions.assertEquals(expectedResult, result, "Wrong expected result for input: " + file.getName());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    protected void testMainAlgorithmTest(Runnable main) {
        List<File> files = readFiles();
        for (File file : files) {
            try (FileInputStream is = new FileInputStream(file)) {
                System.setIn(is);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outputStream));

                main.run();

                String result = outputStream.toString();

                List<String> allLines = Files.readAllLines(file.toPath());
                String expectedResult = String.join("\n", allLines.subList(allLines.size() - 1, allLines.size()));
                Assertions.assertEquals(expectedResult, result, "Wrong expected result for input: " + file.getName());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    protected int[] parseIntArrayString(String string) {
        String[] values = string.substring(1, string.length() - 1).split("\\s*,\\s*");
        int[]    data   = new int[values.length];
        for (int i = 0; i < data.length; i++) {
            data[i] = Integer.parseInt(values[i]);
        }
        return data;
    }

    private List<File> readFiles() {
        try (Stream<Path> paths = Files.list(Paths.get("src/test/resources/" + getTestCasesPath()))) {
            return paths.map(Path::toFile).collect(Collectors.toList());
        } catch (Exception ex) {
            Assertions.fail("Failed to read files with testcases.");
            return Collections.emptyList();
        }
    }

}
