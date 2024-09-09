package sliding_window;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class FindRepeatedSequencesTest extends TestHelper {

    protected String getTestCasesPath() {
        return "sliding_window/repeated-sequence";
    }

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            String dna = input.get(0);
            int k  = Integer.parseInt(input.get(1));
            Set<String> expectedValue = parseStringArray(input.get(2));
            Set<String> result  = FindRepeatedSequences.findRepeatedSequences(dna, k);
            Assertions.assertEquals(expectedValue, result, "Wrong answer");
        });
    }
}