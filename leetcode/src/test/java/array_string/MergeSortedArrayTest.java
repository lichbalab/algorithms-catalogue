package array_string;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MergeSortedArrayTest extends TestHelper {
    @Override
    protected String getTestCasesPath() {
        return "array-string/merge-sorted-array";
    }

    @Test
    public void algTest() {
        super.testAlgorithmTest(input -> {
            int[] nums1 = parseIntArrayString(input.get(0));
            int m = Integer.parseInt(input.get(1));
            int[] nums2 = parseIntArrayString(input.get(2));
            int n  = Integer.parseInt(input.get(3));
            new MergeSortedArray().merge(nums1, m, nums2, n);
            return Arrays.toString(nums1).replace(" ", "");
        }, 4);
    }

}
