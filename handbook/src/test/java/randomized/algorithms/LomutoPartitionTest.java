package randomized.algorithms;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Test;
import sorting.MergeSort;

public class LomutoPartitionTest extends TestHelper {


    @Override
    protected String getTestCasesPath() {
        return "lomuto-partition";
    }

    @Test
    public void algTest() {
        super.testMainAlgorithmTest(() -> LomutoPartition.main(new String[]{}), 2);
    }
}
