package sorting;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Test;

public class QuickSortTest extends TestHelper {


    @Override
    protected String getTestCasesPath() {
        return "quick-sort";
    }

    @Test
    public void algTest() {
        super.testMainAlgorithmTest(() -> QuickSort.main(new String[]{}), 2);
    }
}
