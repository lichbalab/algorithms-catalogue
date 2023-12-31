import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubstringTest {

    @Test
    void testAlgorithm() throws IOException {
        List<File> files = readFiles();
        for (File file : files) {
            try (FileInputStream is = new FileInputStream(file)){
                Substring.Input input   = Substring.readInputData(is);
                Map.Entry<Integer, Integer> entry = Substring.getMaxSubSequence(input.charSequence, input.n, input.k);
                List<String>                       allLines = Files.readAllLines(file.toPath());
                String[] results = allLines.get(allLines.size() -1).split(" ");
                int expectedLength = Integer.parseInt(results[0]);
                int expectedIndex = Integer.parseInt(results[1]);
                Assertions.assertEquals(expectedLength, entry.getKey(), "Wrong expected length for input: " + file.getName());
                Assertions.assertEquals(expectedIndex, entry.getValue(), "Wrong expected start index for input: " + file.getName());
            }
        }
    }

    private static List<File> readFiles() throws IOException {
        try (Stream<Path> paths = Files.list(Paths.get("src/test/resources/substring"))) {
            return paths.map(Path::toFile).collect(Collectors.toList());
        }
    }
}