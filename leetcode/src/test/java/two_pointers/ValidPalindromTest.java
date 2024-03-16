package two_pointers;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Test;

public class ValidPalindromTest extends TestHelper {

    @Override
    public String getTestCasesPath() {
        return "two-pointers/valid-palindrom";
    }


    @Test
    public void algTest() {
        super.testAlgorithmTest(input -> String.valueOf(VlaidPalindrom.isPalindrome(input.getFirst())), 1);
    }
}
