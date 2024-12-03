package two_heaps;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MeetingRooms3 {

    /**
     * <a href="https://leetcode.com/problems/meeting-rooms-iii/">...</a>
     * <p>
     * Time complexity: O(n * log(n) + n * log(rooms))
     * Space complexity: O(n)
     */
    public static int mostBooked( int rooms, int[][] meetings) {
        // int[] = new int[3];
        // int[0] = room
        // int[1] = end of current meeting
        // int[2] = count of meetings
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (a[1] == b[1]) {
                return a[0] - b[0];
            } else {
                return a[1] - b[1];
            }
        });
        PriorityQueue<int[]> unusedRooms = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        for (int i = 0; i < rooms; i++) {
            unusedRooms.offer(new int[] {i, 0, 0});
        }

        Arrays.sort(meetings, Comparator.comparingInt(a -> a[0]));
        int[] topRoom = unusedRooms.peek();

        for (int[] meeting : meetings) {
            while(!pq.isEmpty() && pq.peek()[1] <= meeting[0]) {
                unusedRooms.offer(pq.poll());
            }

            int[] currentRoom;
            if (!unusedRooms.isEmpty()) {
                currentRoom = unusedRooms.poll();
            } else {
                currentRoom = pq.poll();
            }

            if (currentRoom[1] <= meeting[0]) {
                currentRoom[1] = meeting[1];
            } else {
                currentRoom[1] = currentRoom[1] + meeting[1] - meeting[0];
            }
            currentRoom[2] = ++currentRoom[2];

            if (topRoom[2] < currentRoom[2] || topRoom[2] == currentRoom[2] && topRoom[0] > currentRoom[0]) {
                topRoom = currentRoom;
            }

            pq.offer(currentRoom);
        }
        return topRoom[0];
    }
}