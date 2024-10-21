package linked_list;

import by.lichbalab.common.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReverseNodeKGroupTest extends TestHelper {
    @Test
    public void algTest() {
        super.customAlgorithmTest(input -> {
            int[] list  =  parseIntArrayString(input.get(0));
            int k = Integer.parseInt(input.get(1));
            int[]  expected  =  parseIntArrayString(input.get(2));

            ReverseNodeKGroup.LinkedList<Integer> linkedList = new ReverseNodeKGroup.LinkedList<>();
            linkedList.createLinkedList(list);



            ReverseNodeKGroup.LinkedListNode result  = ReverseNodeKGroup.reverseKGroups(linkedList.head, k);
            int[] resultList = ReverseNodeKGroup.toArray(result);
            Assertions.assertArrayEquals(expected, resultList, "Wrong answer");
        });
    }
    protected String getTestCasesPath() {
        return "linked_list/reverse-node-k-group";
    }

}
