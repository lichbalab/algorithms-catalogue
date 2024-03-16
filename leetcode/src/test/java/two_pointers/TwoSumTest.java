package two_pointers;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TwoSumTest extends TestHelper {
    @Override
    public String getTestCasesPath() {
        return "two-pointers/two-sum";
    }


    @Test
    public void algTest() {
        super.testAlgorithmTest(input -> {
            int target = Integer.parseInt(input.get(0));
            int[] numbers = parseIntArrayString(input.get(1));
            return Arrays.toString(new TwoSum().twoSum(numbers, target));
        }, 2);
    }
}
