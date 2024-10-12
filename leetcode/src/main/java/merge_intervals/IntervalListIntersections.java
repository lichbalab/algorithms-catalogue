package merge_intervals;

import java.util.ArrayList;
import java.util.List;

/**
 <a href="https://leetcode.com/problems/interval-list-intersections/">...</a>
 * Time complexity: O(n + m)
 * Space complexity: O(max(n, m))
 */
class IntervalListIntersections {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        return intervalsIntersection(firstList, secondList);
    }

    public static int[][] intervalsIntersection(int[][] intervalLista, int[][] intervalListb) {
        int i = 0;
        int j = 0;
        int n = intervalLista.length;
        int m = intervalListb.length;

        List<int[]> result = new ArrayList<>();
        while (i < n && j < m) {
            int[] a = intervalLista[i];
            int[] b = intervalListb[j];
            int[] intersection = intersection(a, b);

            if (a[1] <= b[1]) {
                i++;
            } else {
                j++;
            }

            if (intersection[0] != -1) {
                result.add(intersection);
            }
        }
        return result.toArray(new int[][]{});
    }

    static int[] intersection(int[] a, int[] b) {
        int[] result = new int[]{-1, -1};

        if (a[1] <= b[1] && a[1] >= b[0]) {
            result[1] = a[1];
            result[0] = Math.max(a[0], b[0]);
        } else if (b[1] <= a[1] && b[1] >= a[0]) {
            result[1] = b[1];
            result[0] = Math.max(a[0], b[0]);
        }
        return result;
    }


}