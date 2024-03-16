package two_pointers;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Test;


public class ContainerWithWaterTest extends TestHelper {

    @Override
    protected String getTestCasesPath() {
        return "two-pointers/container-with-water";
    }

    @Test
    public void algTest() {
        super.testAlgorithmTest(input -> {
            int[] numbers = parseIntArrayString(input.getFirst());
            return String.valueOf(new ContainerWithWater().maxArea(numbers));
        }, 1);
    }
}
