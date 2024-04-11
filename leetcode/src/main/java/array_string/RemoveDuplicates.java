package array_string;

/**
 * <a href="https://leetcode.com/problems/merge-sorted-array/solutions/3436053/beats-100-best-c-java-python-and-javascript-solution-two-pointer-stl/">...</a>
 * <p>
 * Time: O(n)
 * Memory: O(1)
 *
 */

public class RemoveDuplicates {
    public int removeDuplicates(int[] nums) {
        int k = 0;
        int j = 1;
        int n = nums.length;
        while (j < n) {
            if (nums[k] != nums[j] && ++k < j) {
                nums[k] = nums[j];
            }
            j++;
        }
        return k+1;
    }
}