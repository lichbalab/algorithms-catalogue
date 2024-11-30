package two_heaps;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SlidingWindowMedianTest extends TestHelper {
    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            int[] nums  =  parseIntArrayString(input.get(0));
            int k = Integer.parseInt(input.get(1));
            double[] expected = parseDoubleArrayString(input.get(2));

            double[] result  = SlidingWindowMedianSlower.medianSlidingWindow(nums, k);
            Assertions.assertArrayEquals(expected, result, "Wrong answer");
        });
    }
    protected String getTestCasesPath() {
        return "two_heaps/sliding_window_median";
    }
}
