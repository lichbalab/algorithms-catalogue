package merge_intervals;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sliding_window.MinSizeSubarraySum;

import java.util.Arrays;

public class IntervalListIntersectionsTest extends TestHelper {

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            int[][] a  =  parseIntIntArrayString(input.get(0));
            int[][] b  =  parseIntIntArrayString(input.get(1));
            int[][] target  =  parseIntIntArrayString(input.get(2));

            int[][] result  = IntervalListIntersections.intervalsIntersection(a, b);
            Assertions.assertTrue(Arrays.deepEquals(target, result), "Wrong answer");
        });
    }

    protected String getTestCasesPath() {
        return "merge_intervals/interval-list-intersections";
    }

}
