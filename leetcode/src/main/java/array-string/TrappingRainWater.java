import java.util.List;
import java.util.Stack;

/**
 * <a href="https://leetcode.com/problems/trapping-rain-water/solutions/4865236/efficient-trapping-rainwater-solution-java-python-code/?envType=study-plan-v2&envId=top-interview-150">...</a>
 * <p>
 * Time: O(2 * n)
 * Memory: O(1)
 *
 */
public class TrappingRainWater {
    public int trap(int[] height) {
        int amountOfWater = 0;
        List<Integer> list = findExtremums(height).stream().toList();

        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            amountOfWater += getAmountOfWater(list.get(i), list.get(i + 1), height);
        }
        return amountOfWater;
    }

    Stack<Integer> findExtremums(int[] height) {
        int n = height.length;
        Stack<Integer> stack = new Stack<>();
        if (n == 1) {
            stack.add(height[0]);
            return stack;
        }
        int prevValue = height[0];
        int nextValue = height[1];
        int trend = nextValue <= prevValue ? -1 : 1;
        int leftInd = trend > 0 ? 1 : 0;
        int left = trend > 0 ? nextValue : prevValue;
        prevValue = nextValue;
        stack.add(leftInd);
        for (int i = 2; i < n; i++) {
            int value = height[i];

            if (value <= prevValue) {
                if (trend > 0) {
                    removeIntermediators(stack, prevValue, height, leftInd);
                    if (left <= prevValue) {
                        leftInd = i - 1;
                        left = prevValue;
                    }
                    stack.add(i - 1);
                    trend = -1;
                }
            } else {
                if (trend < 0) {
                    trend = 1;
                }

                if (i == n - 1) {
                    removeIntermediators(stack, value, height, leftInd);
                    stack.add(i);
                }
            }
            prevValue = value;
        }

        return stack;
    }

    void removeIntermediators(Stack<Integer> stack, int value, int[] height, int leftInd) {
        while (!stack.isEmpty() && height[stack.peek()] <= value && stack.peek() != leftInd) {
            stack.pop();
        }
    }

    int getAmountOfWater(int min, int high, int[] height) {
        int amountOfWater = 0;
        int value = Math.min(height[min], height[high]);
        int left = min + 1;
        while (left < high) {
            if (value > height[left]) {
                amountOfWater = amountOfWater + (value - height[left]);
            }
            left++;
        }
        return amountOfWater;
    }
}
