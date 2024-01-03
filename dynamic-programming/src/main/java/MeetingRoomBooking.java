import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Set n intervals.
 * It is required to find the maximum number of mutually disjoint intervals.
 * <p>
 * Two intervals intersect if they have at least one common point.
 * <p>
 * Time: O(n*log(n))
 * Memory: O(n)
 */
public class MeetingRoomBooking {

    private static final LinkedList<Interval> INTERVALS = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int            n      = Integer.parseInt(reader.readLine());
        for (int i = 0; i < n; i++) {
            String[] interval = reader.readLine().split(" ");
            INTERVALS.add(new Interval(Integer.parseInt(interval[0]), Integer.parseInt(interval[1])));
        }

        INTERVALS.sort(Interval::compare);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(getMaxIntervals()));
        writer.close();
        reader.close();
    }

    public static int getMaxIntervals() {
        Iterator<Interval> iterator      = INTERVALS.iterator();
        Interval           superInterval = iterator.next();
        while (iterator.hasNext()) {
            Interval interval = iterator.next();
            if (superInterval.intersectedWith(interval)) {
                iterator.remove();
            } else {
                superInterval = interval;
            }
        }
        return INTERVALS.size();
    }

    static class Interval {
        Integer l;
        Integer r;

        public Interval(Integer l, Integer r) {
            this.l = l;
            this.r = r;
        }

        public static int compare(Interval interval1, Interval interval2) {
            return interval1.r.compareTo(interval2.r);
        }

        public boolean intersectedWith(Interval interval) {
            return r >= interval.l && l <= interval.r;
        }
    }
}
