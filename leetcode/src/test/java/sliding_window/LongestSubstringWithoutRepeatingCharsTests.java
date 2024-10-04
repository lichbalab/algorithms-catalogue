package sliding_window;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LongestSubstringWithoutRepeatingCharsTests extends TestHelper {

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            String s = input.get(0);
            Integer expectedValue = Integer.parseInt(input.get(1));
            Integer result  = LongestSubstringWithoutRepeatingChars.findLongestSubstring(s);
            Assertions.assertEquals(expectedValue, result, "Wrong answer");
        });
    }

    protected String getTestCasesPath() {
        return "sliding_window/longest-substring-without-repeating-chars";
    }


}
