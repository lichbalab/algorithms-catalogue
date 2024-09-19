package sliding_window;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RepeatingCharacterReplacementTest extends TestHelper {

    @Override
    protected String getTestCasesPath() {
        return "sliding_window/character-replacement";
    }

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            String s = input.get(0);
            int k  = Integer.parseInt(input.get(1));
            int expectedValue = Integer.parseInt(input.get(2));
            int result  = RepeatingCharacterReplacement.longestRepeatingCharacterReplacement(s, k);
            Assertions.assertEquals(expectedValue, result, "Wrong answer");
        });
    }
}
