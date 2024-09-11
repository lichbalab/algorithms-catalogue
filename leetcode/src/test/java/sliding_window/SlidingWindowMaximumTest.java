package sliding_window;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SlidingWindowMaximumTest extends TestHelper {

    @Override
    public String getTestCasesPath() {
        return "sliding_window/sliding-window-max";
    }


    @Test
    public void algTest() {
        super.testAlgorithmTest(input -> {
            int w = Integer.parseInt(input.get(0));
            int[] nums = parseIntArrayString(input.get(1));
            return Arrays.toString(SlidingWindowMaximum.findMaxSlidingWindow(nums, w)).replaceAll(" ", "");
        }, 2);
    }
}
