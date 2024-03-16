package two_pointers;

/**
 * <a href="https://leetcode.com/problems/container-with-most-water/description/?envType=study-plan-v2&envId=top-interview-150">...</a>
 * <p>
 * Time complexity: O(n)
 * Space complexity: O(1)
 */
public class ContainerWithWater {
    public int maxArea(int[] height) {
        int maxAmount = 0;
        int left = 0;
        int right = height.length - 1;

        while (left < right) {
            maxAmount = Math.max(maxAmount, amountOfWater(left, right, height));
            int leftValue = height[left];
            int rightValue = height[right];
            if (leftValue > rightValue) {
                right--;
                while (rightValue >= height[right] && left < right) {
                    right--;
                }
            } else if (leftValue < rightValue) {
                left++;
                while (leftValue >= height[left] && left < right) {
                    left++;
                }
            } else {
                right--;
                while (rightValue >= height[right] && left < right) {
                    right--;
                }

                left++;
                while (leftValue >= height[left] && left < right) {
                    left++;
                }
            }
        }
        return maxAmount;
    }

    public int amountOfWater(int i, int j, int[] height) {
        return Math.min(height[i], height[j]) * (j - i);
    }
}