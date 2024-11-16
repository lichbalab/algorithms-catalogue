package two_heaps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 <a href="https://leetcode.com/problems/ipo/description/?envType=study-plan-v2&envId=top-interview-150">...</a>
 * Time complexity: O(n * log(n))
 * Space complexity: O(n)
 */
public class IPO {

    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int cap = w;
        int count = k;
        PriorityQueue<Integer> maxPq = new PriorityQueue<>((a, b) -> b - a);
        List<int[]> minPq = new ArrayList<>();

        for (int i = 0; i < capital.length; i++) {
            minPq.add(new int[] {profits[i], capital[i]});
        }

        minPq.sort(Comparator.comparingInt(a -> a[1]));

        int j = 0;
        while(count > 0) {
            while(j < minPq.size() && minPq.get(j)[1] <= cap) {
                maxPq.offer(minPq.get(j++)[0]);
            }

            if (maxPq.isEmpty()) {
                break;
            }

            cap += maxPq.poll();
            count--;

        }
        return cap;
    }
}
