
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class TestHelper {

    protected abstract String getTestCasesPath();

    protected void testAlgorithmTest(Function<String, String> test) {
        List<File> files = readFiles();
        for (File file : files) {
            try {
                List<String> allLines       = Files.readAllLines(file.toPath());
                String result = test.apply(allLines.getFirst());
                String       expectedResult = allLines.getLast();
                Assertions.assertEquals(expectedResult, result, "Wrong expected result for input: " + file.getName());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
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
