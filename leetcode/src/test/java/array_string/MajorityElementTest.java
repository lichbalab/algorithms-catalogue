package array_string;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MajorityElementTest extends TestHelper {

    protected String getTestCasesPath() {
        return "array-string/majority-element";
    }

    @Test
    public void algTest() {
        super.testAlgorithmTest(input -> {
            int[] numbers = parseIntArrayString(input.getFirst());
            return String.valueOf(new MajorityElement1().majorityElement(numbers));
        }, 1);
    }
}