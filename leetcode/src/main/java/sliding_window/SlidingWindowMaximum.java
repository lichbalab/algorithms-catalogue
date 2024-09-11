package sliding_window;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <a href="https://leetcode.com/problems/sliding-window-maximum/description/">...</a>
 * <p>
 * Time complexity: O(n)
 * Space complexity: O(w)
 */

public class SlidingWindowMaximum {

    public static int[] findMaxSlidingWindow(int[] nums, int w) {
        int n = nums.length;
        int[] result = new int[n - w + 1];
        Deque<Integer> window = new ArrayDeque<>();
        for (int i = 0; i < n - w  + 1; i++) {
            if (i == 0) {
                initWindow(window, nums, w);
            } else {
                updateWindow(window, nums, i, w);
            }
            result[i] = nums[window.getFirst()];
        }
        return result;
    }

    private static void initWindow(Deque<Integer> window, int[] nums, int w) {
        window.addLast(0);
        for (int i = 1; i < w; i++) {
            while (!window.isEmpty() && nums[window.getLast()] < nums[i]) {
                window.removeLast();
            }
            window.addLast(i);
        }
    }

    private static void updateWindow(Deque<Integer> window, int[] nums, int i, int w) {
        int newInd = i + w - 1;
        while (!window.isEmpty() && nums[window.getLast()] < nums[newInd] ) {
            window.removeLast();
        }

        if (!window.isEmpty() && window.getFirst() < i) {
            window.removeFirst();
        }

        window.addLast(newInd);
    }
}