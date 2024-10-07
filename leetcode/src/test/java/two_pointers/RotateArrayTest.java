package two_pointers;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RotateArrayTest extends TestHelper {
    @Test
    public void algTest() {
        super.testAlgorithmTest(input -> {
            int[] numbers = parseIntArrayString(input.get(0));
            int k = Integer.parseInt(input.get(1));
            new RotateArray().rotate(numbers, k);
            return Arrays.toString(numbers).replaceAll(" ", "");
        }, 2);
    }

    @Override
    public String getTestCasesPath() {
        return "two-pointers/rotate-array";
    }
}
