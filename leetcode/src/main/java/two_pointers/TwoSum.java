package two_pointers;

/**
 * <a href="https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/?envType=study-plan-v2&envId=top-interview-150">...</a>
 * <p>
 * Time: O(n*log(n/2)/2)
 * Memory: O(1)
 *
 */
public class TwoSum {
    public int[] twoSum(int[] numbers, int target) {
        int n = numbers.length;
        int previousValue = numbers[0] - 1;
        for (int i = 0; i < n - 1; i++) {
            int value = numbers[i];

            if (value == previousValue) {
                continue;
            }

            previousValue = value;

            int j = binaryIndFin(numbers, target - numbers[i], i + 1);
            if (j != -1) {
                return new int[]{i + 1, j + 1};
            }
        }
        return new int[]{-1, -1};
    }

    int binaryIndFin(int[] numbers, int target, int left) {
        int low = left;
        int high = numbers.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int midValue = numbers[mid];

            if (midValue == target) {
                return mid;
            } else if (midValue > target) {
                high = high - 1;
            } else {
                low = low + 1;
            }
        }
        return -1;
    }
}