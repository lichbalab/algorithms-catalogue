package two_pointers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <a href="https://leetcode.com/problems/container-with-most-water/description/?envType=study-plan-v2&envId=top-interview-150">...</a>
 * <p>
 * Time complexity: O(n^2)
 * Space complexity: O(n)
 */
public class ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int count = removeDuplicates(nums);
        Set<List<Integer>> result = new HashSet<>();
        for (int i = 0; i < count - 2; i++) {
            int low = i + 1;
            int high = count - 1;
            int a = nums[i];
            int b = nums[low];
            int c = nums[high];
            while (low < high) {
                int sum = a + b + c;
                if (sum == 0) {
                    List<Integer> list = List.of(a, b, c);
                    result.add(list);
                    c = nums[--high];
                    b = nums[++low];
                } else if (sum > 0) {
                    c = nums[--high];
                } else {
                    b = nums[++low];
                }
            }
        }
        return result.stream().toList();
    }

    public int removeDuplicates(int[] nums) {
        int j = 1;
        int g = 0;
        int n = 2;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[j] = nums[i];
                g = i;
                j++;
            } else if (i - g < n ||  nums[i] == 0 && i - g < n + 1) {
                nums[j] = nums[i];
                j++;
            }
        }
        return j;
    }


    public String convertThreeSum(int[] nums) {
        List<List<Integer>> inputList = threeSum(nums);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < inputList.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("[");
            List<Integer> row = inputList.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (j > 0) {
                    sb.append(",");
                }
                sb.append(row.get(j));
            }
            sb.append("]");
        }
        sb.append("]");
        return sb.toString();
    }
}