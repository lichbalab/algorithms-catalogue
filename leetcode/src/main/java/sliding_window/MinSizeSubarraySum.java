package sliding_window;

public class MinSizeSubarraySum {
    public static int minSubArrayLen(int target, int[] nums) {
        int window = 0;
        int start = 0;
        int end = 0;
        int n = nums.length;
        int minSize = Integer.MAX_VALUE;

        while (end <= n) {
            if (window < target) {
                if (end < n) {
                    window += nums[end];
                }
                end++;
            } else {
                while(window >= target) {
                    window -= nums[start++];
                }
                int length = end - start + 1;
                if (length < minSize) {
                    minSize = length;
                }
            }
        }

        return minSize == Integer.MAX_VALUE ? 0 : minSize;
    }
}
