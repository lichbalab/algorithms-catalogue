package two_heaps;

import java.util.*;

/**
 * <a href="https://leetcode.com/problems/sliding-window-median/description/">...</a>
 * Time complexity: O(n * log(n))
 * Space complexity: O(n)
 */
public class SlidingWindowMedian {
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
        private final Map<Integer, Integer> outNums = new HashMap<>();
        int k;
        int balance = 0;


        public MedianHolder(int[] nums, int k) {
            for (int i = 0; i < k; i++) {
                maxPq.offer(nums[i]);
            }

            for (int i = 0; i < k/2; i++) {
                minPq.offer(maxPq.poll());
            }

            this.k = k;
        }

        public void shiftWindow(int addedItem, int removedItem) {
            removeItem(removedItem);
            addItem(addedItem);
            rebalanceHeaps();
            removeElementFromHeap(maxPq);
            removeElementFromHeap(minPq);
            balance = 0;
        }

        public double getMedian() {
            if (k % 2 == 0) {
                return maxPq.peek()/2.0 + minPq.peek()/2.0;
                //return (double) ((long)maxPq.peek() + (long)minPq.peek()) * 0.5;
            } else {
                return maxPq.peek();
            }
        }

        void removeItem(int removedItem) {
            if (outNums.containsKey(removedItem)) {
                outNums.put(removedItem, outNums.get(removedItem) + 1);
            } else {
                outNums.put(removedItem, 1);
            }

            if (removedItem <= maxPq.peek()) {
                balance-=1;
            } else{
                balance+=1;
            }

        }

        void addItem (int addedItem) {
            if (addedItem <= maxPq.peek()) {
                maxPq.offer(addedItem);
                balance +=1;
            } else {
                minPq.offer(addedItem);
                balance -=1;
            }
        }

        void rebalanceHeaps() {
            if (balance  < 0 ) {
                maxPq.offer(minPq.poll());
            } else if (balance > 0) {
                minPq.offer(maxPq.poll());
            }
        }

        void removeElementFromHeap(PriorityQueue<Integer> pq) {
            while(outNums.containsKey(pq.peek())) {
                if (outNums.get(pq.peek()) == 1) {
                    outNums.remove(pq.poll());
                } else{
                    outNums.put(pq.peek(), outNums.get(pq.poll()) - 1);
                }
            }
        }
    }
}