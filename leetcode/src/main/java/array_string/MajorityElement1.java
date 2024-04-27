package array_string;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.com/problems/majority-element/description/?envType=study-plan-v2&envId=top-interview-150">...</a>
 * <p>
 * Time: O(n)
 * Memory: O(1)
 */

public class MajorityElement1 {
    public int majorityElement(int[] nums) {
        int candidate = 0;
        int votes = 0;

        for (int k : nums) {
            if (votes == 0) {
                candidate = k;
            }

            if (k == candidate){
                votes++;
            } else {
                votes --;
            }
        }
        return candidate;
    }
}