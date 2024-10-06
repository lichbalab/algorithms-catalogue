package sliding_window;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MinSizeSubarraySumTest extends TestHelper {

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            int[] nums  =  parseIntArrayString(input.get(0));
            int target = Integer.parseInt(input.get(1));
            Integer expectedValue = Integer.parseInt(input.get(2));
            Integer result  = MinSizeSubarraySum.minSubArrayLen(target, nums);
            Assertions.assertEquals(expectedValue, result, "Wrong answer");
        });
    }

    protected String getTestCasesPath() {
        return "sliding_window/min-size-subarray-sum";
    }

}
