package array_string;

/**
 * <a href="https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/description/?envType=study-plan-v2&envId=top-interview-150">...</a>
 * <p>
 * Time: O(n)
 * Memory: O(1)
 *
 */

public class RemoveDuplicates2 {
    public int removeDuplicates(int[] nums) {
        int j = 1;
        int g = 0;
        int n  = 2;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[j] = nums[i];
                g = i;
                j++;
            } else if (i - g < n) {
                nums[j] = nums[i];
                j++;
            }
        }
        return j;
    }
}