package sliding_window;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BestTimeToBuyAndSellStockTest extends TestHelper {

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            int[] nums  =  parseIntArrayString(input.get(0));
            Integer expectedValue = Integer.parseInt(input.get(1));
            Integer result  = BestTimeToBuyAndSellStock.maxProfit(nums);
            Assertions.assertEquals(expectedValue, result, "Wrong answer");
        });
    }

    protected String getTestCasesPath() {
        return "sliding_window/best-time-to-buy-and-sell-stock";
    }

}
