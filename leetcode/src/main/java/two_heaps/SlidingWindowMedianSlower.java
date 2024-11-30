package two_heaps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class SlidingWindowMedianSlower {
    public static double[] medianSlidingWindow(int[] nums, int k) {
        MedianHolder holder = new MedianHolder(nums, k);
        double[] result = new double[nums.length - k + 1];
        result[0] = holder.getMedian();
        for (int i = 1; i < nums.length - k + 1; i++) {
            holder.shiftWindow(nums[i + k - 1], nums[i - 1]);

            result[i] = holder.getMedian();
        }
        return result;
    }

    public static class MedianHolder {
        private final PriorityQueue<Integer> minPq = new PriorityQueue<>();
        private final PriorityQueue<Integer> maxPq = new PriorityQueue<>(Collections.reverseOrder());

        public MedianHolder(int[] nums, int k) {
            for (int i = 0; i < k; i++) {
                maxPq.offer(nums[i]);
            }

            for (int i = 0; i < k/2; i++) {
                minPq.offer(maxPq.poll());
            }
        }

        public void shiftWindow(int addedItem, int removedItem) {
            removeItem(removedItem);
            rebalanceHeaps();
            addItem(addedItem);
            rebalanceHeaps();
        }

        public double getMedian() {
            if (maxPq.size() == minPq.size()) {
                return maxPq.peek()/2.0 + minPq.peek()/2.0;
                //return (double) ((long)maxPq.peek() + (long)minPq.peek()) * 0.5;
            } else {
                return maxPq.peek();
            }
        }

        void removeItem(int removedItem) {
            if(removedItem <= maxPq.peek()) {
                maxPq.remove(removedItem);
            } else {
                minPq.remove(removedItem);
            }

        }

        void addItem (int addedItem) {
            if (maxPq.isEmpty() || addedItem <= maxPq.peek()) {
                maxPq.offer(addedItem);
            } else {
                minPq.offer(addedItem);
            }
        }

        void rebalanceHeaps() {
            if (minPq.size() > maxPq.size()) {
                maxPq.offer(minPq.poll());
            } else if (maxPq.size() > minPq.size() + 1) {
                minPq.offer(maxPq.poll());
            }
        }
    }
}