package array_string;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RemoveElementTest extends TestHelper {

    protected String getTestCasesPath() {
        return "array-string/remove-element";
    }

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            int[] nums = parseIntArrayString(input.get(0));
            int val = Integer.parseInt(input.get(1));
            int k = new RemoveElement().removeElement(nums, val);
            int[] expectedNums = parseIntArrayString(input.get(2));

            Assertions.assertEquals(expectedNums.length, k, "Wrong answer");
            Arrays.sort(nums, 0, k); //
            for (int i = 0; i < k; i++) {
                Assertions.assertEquals(expectedNums[i], nums[i], "Wrong answer");
            }
        });
    }
}