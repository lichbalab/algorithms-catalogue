package array_string;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RemoveDuplicatesTest2 extends TestHelper {

    protected String getTestCasesPath() {
        return "array-string/remove-duplicates2";
    }

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            int[] nums = parseIntArrayString(input.get(0));
            int expectedNum = Integer.parseInt(input.get(1));
            int k = new RemoveDuplicates2().removeDuplicates(nums);
            int[] expectedNums = parseIntArrayString(input.get(2));
            Assertions.assertEquals(expectedNum, k, "Wrong answer");
            for (int i = 0; i < k; i++) {
                Assertions.assertEquals(expectedNums[i], nums[i], "Wrong answer");
            }
        });
    }
}