package two_heaps;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MeetingRoomsTest extends TestHelper {

    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            int[][] nums  = parseIntIntArrayString(input.get(0));
            int rooms =  Integer.parseInt(input.get(1));
            int expected = Integer.parseInt(input.get(2));

            int result  = MeetingRooms3.mostBooked(rooms, nums);
            Assertions.assertEquals(expected, result, "Wrong answer");
        });
    }

    protected String getTestCasesPath() {
        return "two_heaps/meeting_rooms_3";
    }
}
