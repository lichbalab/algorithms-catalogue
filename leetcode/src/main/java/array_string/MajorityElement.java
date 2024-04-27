package array_string;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.com/problems/majority-element/description/?envType=study-plan-v2&envId=top-interview-150">...</a>
 * <p>
 * Time: O(n)
 * Memory: O(1)
 */

public class MajorityElement {
    public int majorityElement(int[] nums) {
        int n = nums.length;
        int maxNumber = nums[0];
        int maxValue = 0;
        Map<Integer, Integer> map = new HashMap<>(n/2);

        for (int k : nums) {
            int value = map.getOrDefault(k, 0);
            map.put(k, ++value);
            if (value > maxValue) {
                maxNumber = k;
                maxValue = value;
            }
        }
        return maxNumber;
    }
}