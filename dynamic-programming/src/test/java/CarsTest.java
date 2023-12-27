import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CarsTest {

    @Test
    void testAlgorithm() throws IOException {
        List<File> files = readFiles();
        for (File file : files) {
            try (FileInputStream is = new FileInputStream(file)){
                Cars.OperationOptimizer optimizer   = Cars.readInputData(is);
                List<String>                       allLines = Files.readAllLines(file.toPath());
                int expectedWeight = Integer.parseInt(allLines.get(allLines.size() -1));
                Assertions.assertEquals(expectedWeight, optimizer.getMinOperations(), "Wrong expected value for input: " + file.getName());
            }
        }
    }


    private static List<File> readFiles() throws IOException {
        try (Stream<Path> paths = Files.list(Paths.get("src/test/resources/cars"))) {
            return paths.map(Path::toFile).collect(Collectors.toList());
        }
    }


}
