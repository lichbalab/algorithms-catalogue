package sliding_window;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MinWindowSubstringTests extends TestHelper {

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            String s = input.get(0);
            String t  = input.get(1);
            String expectedValue = input.get(2).replaceAll("\"", "");
            String result  = MinimumWindowSubstring.minWindow(s, t);
            Assertions.assertEquals(expectedValue, result, "Wrong answer");
        });
    }

    protected String getTestCasesPath() {
        return "sliding_window/min-window-substring";
    }


}
