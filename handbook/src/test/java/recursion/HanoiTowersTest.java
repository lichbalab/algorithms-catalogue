import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HanoiTowersTest {

    @Test
    void testAlgorithmTest() throws IOException {
        List<File> files = readFiles();
        for (File file : files) {
            try (FileInputStream is = new FileInputStream(file)) {
                System.setIn(is);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outputStream));

                HanoiTowers.main(new String[]{});

                String result = outputStream.toString();

                List<String> allLines = Files.readAllLines(file.toPath());
                String expectedResult = String.join("\n", allLines.subList(1, allLines.size()));
                Assertions.assertEquals(expectedResult, result, "Wrong expected result for input: " + file.getName());
            }
        }
    }

    private static List<File> readFiles() throws IOException {
        try (Stream<Path> paths = Files.list(Paths.get("src/test/resources/recursion"))) {
            return paths.map(Path::toFile).collect(Collectors.toList());
        }
    }
}