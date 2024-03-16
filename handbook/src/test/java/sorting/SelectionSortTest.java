package sorting;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Test;

public class SelectionSortTest extends TestHelper {


    @Override
    protected String getTestCasesPath() {
        return "sorting";
    }

    @Test
    public void algTest() {
        super.testMainAlgorithmTest(() -> SelectionSort.main(new String[]{}), 2);
    }
}
