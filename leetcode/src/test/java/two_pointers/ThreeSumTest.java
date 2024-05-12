package two_pointers;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ThreeSumTest extends TestHelper {
    @Override
    public String getTestCasesPath() {
        return "two-pointers/three-sum";
    }


    @Test
    public void algTest() {
        super.testAlgorithmTest(input -> {
            int[] nums = parseIntArrayString(input.getFirst());
            return new ThreeSum().convertThreeSum(nums);
        }, 1);
    }
}
