package linked_list;

import java.util.ArrayList;
import java.util.List;

/**
 <a href="https://leetcode.com/problems/reverse-nodes-in-k-group/description/">...</a>*
 Time complexity: O(n)
 * Space complexity: O(1)
 */
public class ReverseNodeKGroup {

    public static LinkedListNode reverseKGroups(LinkedListNode head, int k) {
        LinkedListNode prevGroup = null;
        int count = 0;
        LinkedListNode current = head;
        LinkedListNode startGroup = head;

        while(current != null){
            if (count == k - 1) {
                LinkedListNode headGroup = reverseGroup(startGroup, current);

                if (prevGroup == null) {
                    head = headGroup;
                } else {
                    prevGroup.next = headGroup;
                }
                prevGroup = startGroup;
                startGroup = startGroup.next;
                count = 0;
                current = startGroup;
            } else {
                count++;
                current = current.next;
            }
        }

        return head;
    }

    public static LinkedListNode reverseGroup(LinkedListNode head, LinkedListNode tail) {
        LinkedListNode prev = tail.next;
        LinkedListNode end = prev;
        LinkedListNode next;
        LinkedListNode current = head;

        while(current != end) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        return prev;

    }

    public static int[] toArray(LinkedListNode node) {
        List<Integer> list  = new ArrayList<>();
        while (node != null) {
            list.add(node.data);
            node = node.next;
        }

        return list.stream().mapToInt( i-> i).toArray();
    }


    public static class LinkedListNode {
        public int data;
        public LinkedListNode next;

        // Constructor will be used to make a LinkedListNode type object
        public LinkedListNode(int data) {
            this.data = data;
            this.next = null;
        }

    }

    public static class LinkedList<T> {
        public LinkedListNode head;

        // constructor will be used to make a LinkedList type object
        public LinkedList() {
            this.head = null;
        }

        // insertNodeAtHead method will insert a LinkedListNode at head
        // of a linked list.
        public void insertNodeAtHead(LinkedListNode node) {
            if (this.head == null) {
                this.head = node;
            } else {
                node.next = this.head;
                this.head = node;
            }
        }

        // createLinkedList method will create the linked list using the
        // given integer array with the help of InsertAthead method.
        public void createLinkedList(int[] lst) {
            for (int i = lst.length - 1; i >= 0; i--) {
                LinkedListNode newNode = new LinkedListNode(lst[i]);
                insertNodeAtHead(newNode);
            }
        }
    }

}
