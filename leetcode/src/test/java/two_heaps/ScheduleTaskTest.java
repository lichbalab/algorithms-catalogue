package two_heaps;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ScheduleTaskTest extends TestHelper {
    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            int[][] nums  = parseIntIntArrayString(input.get(0));
            int expected = Integer.parseInt(input.get(1));

            int result  = ScheduleTask.tasks(Arrays.stream(nums).map(num -> IntStream.of(num).boxed().collect(Collectors.toList()))
                    .collect(Collectors.toList()));
            Assertions.assertEquals(expected, result, "Wrong answer");
        });
    }
    protected String getTestCasesPath() {
        return "two_heaps/schedule_task";
    }

}
