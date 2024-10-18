package merge_intervals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * We are given an input array of meeting time intervals, intervals,
 * where each interval has a start time and an end time. Your task is
 * to find the minimum number of meeting rooms required to hold these meetings.
 * <p>
 * An important thing to note here is that the specified end time for each meeting is exclusive.
 * <p>
 * Time complexity: O(n*log(n))
 * Space complexity: O(n)
 */
public class MeetingRoom2 {
    public static int findSets(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.offer(intervals[0][1]);
        int n = intervals.length;
        for (int i = 1; i < n; i++) {
            int minEnd = heap.peek();
            int[] interval = intervals[i];
            if (interval[0] >= minEnd) {
                heap.poll();
                heap.offer(interval[1]);
            } else {
                heap.offer(interval[1]);
            }
        }
        return heap.size();
    }
}