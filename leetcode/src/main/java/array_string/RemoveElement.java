package array_string;

public class RemoveElement {

    /**
     * <a href="https://leetcode.com/problems/remove-element/description/">...</a>
     * <p>
     * Time: O(n)
     * Memory: O(1)
     *
     */

    public int removeElement(int[] nums, int val) {
        int i = 0;
        int j = nums.length - 1;
        int k = 0;

        if (j == 0 && nums[0] != val) {
            return 1;
        }

        while (i <= j) {
            if (nums[i] == val) {
                while (j > i && nums[j] == val) {
                    j--;
                }

                if (j==0) {
                    return 0;
                }

                if (i < j) {
                    k++;
                    nums[i] = nums[j];
                    nums[j] = val;
                    j--;
                }
            } else {
                k++;
            }
            i++;
        }
        return k;
    }
}