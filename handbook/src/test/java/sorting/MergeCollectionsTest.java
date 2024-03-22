package sorting;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Test;

public class MergeCollectionsTest extends TestHelper {


    @Override
    protected String getTestCasesPath() {
        return "merge-collections";
    }

    @Test
    public void algTest() {
        super.testMainAlgorithmTest(() -> MergeCollections.main(new String[]{}));
    }
}
