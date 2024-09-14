package sliding_window;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class MinWindowSequenceTest extends TestHelper {
    protected String getTestCasesPath() {
        return "sliding_window/min-window-sequence";
    }

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            String s = input.get(0);
            String t  = input.get(1);
            String expectedValue = input.get(2);
            String result  = MinWindowSequence.minWindow(s, t);
            Assertions.assertEquals(expectedValue, result, "Wrong answer");
        });
    }
}
