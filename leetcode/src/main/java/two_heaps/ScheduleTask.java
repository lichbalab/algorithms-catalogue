package two_heaps;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * We are given an input array, tasks, which contains the start and end times of
 *  tasks. Your task is to find the minimum number of machines required to complete these
 *  tasks.
 * <p>
 *
 * Time complexity: O(n * log(n))
 * Space complexity: O(n)
 */
class ScheduleTask {
    public static int tasks(List<List<Integer>> tasksList) {

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        tasksList.sort(Comparator.comparingInt(List::getFirst));
        pq.offer(tasksList.getFirst().get(1));
        for (int i = 1; i < tasksList.size(); i++) {
            if (pq.peek() <= tasksList.get(i).get(0)) {
                pq.poll();
            }
            pq.offer(tasksList.get(i).get(1));
        }
        return pq.size();
    }
}