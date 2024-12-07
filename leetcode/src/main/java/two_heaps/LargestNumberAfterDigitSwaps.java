package two_heaps;

import java.util.Collections;
import java.util.PriorityQueue;

public class LargestNumberAfterDigitSwaps {
    public int largestInteger(int num) {
        String numChars = Integer.toString(num);
        PriorityQueue<Integer> oddPq = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> evenPq = new PriorityQueue<>(Collections.reverseOrder());
        int n = numChars.length();
        int [] digits = new int[n];

        for (int i = 0 ; i < n ; i++) {
            int digit = numChars.charAt(i) - '0';
            digits[i] = digit;
            if (digit % 2 == 1) {
                oddPq.offer(digit);
            } else {
                evenPq.offer(digit);
            }
        };

        StringBuilder sb = new StringBuilder();;
        for (int digit : digits) {
            if (digit % 2 == 1) {
                sb.append(oddPq.poll());
            } else {
                sb.append(evenPq.poll());
            }
        }

        return Integer.parseInt(sb.toString());
    }
}