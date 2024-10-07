package two_pointers;

/**
 * <a href="https://leetcode.com/problems/rotate-array/description/">...</a>
 * <p>
 * Time complexity: O(n)
 * Space complexity: O(1)
 */
public class RotateArray {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;

        reverseArray(0, n - 1, nums);
        reverseArray(0, k - 1, nums);
        reverseArray(k, n - 1, nums);
    }

    void reverseArray(int start, int end, int[] nums) {
        while (start < end) {
            swapElements(start++, end--, nums);
        }
    }

    void swapElements(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}