package two_heaps;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IPOTest extends TestHelper {

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            int[] capitals  =  parseIntArrayString(input.get(0));
            int[] profits  =  parseIntArrayString(input.get(1));

            int c = Integer.parseInt(input.get(2));
            int k = Integer.parseInt(input.get(3));
            int expected = Integer.parseInt(input.get(4));

            int result  = new IPO().findMaximizedCapital(k, c, profits, capitals);
            Assertions.assertEquals(expected, result, "Wrong answer");
        });
    }
    protected String getTestCasesPath() {
        return "two_heaps/ipo";
    }

}
