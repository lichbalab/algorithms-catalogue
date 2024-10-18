package merge_intervals;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MeetingRoom2Test extends TestHelper {

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            int[][] a  =  parseIntIntArrayString(input.get(0));
            int  expected  =  Integer.parseInt(input.get(1));

            int result  = MeetingRoom2.findSets(a);
            Assertions.assertEquals(expected, result, "Wrong answer");
        });
    }

    protected String getTestCasesPath() {
        return "merge_intervals/meeting-room-2";
    }

}
