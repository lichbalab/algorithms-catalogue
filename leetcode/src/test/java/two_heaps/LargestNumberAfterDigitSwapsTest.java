package two_heaps;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LargestNumberAfterDigitSwapsTest extends TestHelper {

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            int num =  Integer.parseInt(input.get(0));
            int expected = Integer.parseInt(input.get(1));

            int result  = new LargestNumberAfterDigitSwaps().largestInteger(num);
            Assertions.assertEquals(expected, result, "Wrong answer");
        });
    }

    protected String getTestCasesPath() {
        return "two_heaps/largest_number_after_digit_swaps";
    }
}
