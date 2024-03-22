package sorting;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Test;

public class MergeSortTest extends TestHelper {


    @Override
    protected String getTestCasesPath() {
        return "merge-sort";
    }

    @Test
    public void algTest() {
        super.testMainAlgorithmTest(() -> MergeSort.main(new String[]{}), 2);
    }
}
