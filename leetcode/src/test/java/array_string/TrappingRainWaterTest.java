package array_string;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Test;

public class TrappingRainWaterTest extends TestHelper {

    @Override
    protected String getTestCasesPath() {
        return "array-string/trapping-rain-water";
    }

    @Test
    public void algTest() {
        super.testAlgorithmTest(input -> {
            int[] numbers = parseIntArrayString(input.getFirst());
            return String.valueOf(new TrappingRainWaterBest().trap(numbers));
        }, 1);
    }
}

