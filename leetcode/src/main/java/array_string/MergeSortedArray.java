package array_string;

/**
 * <a href="https://leetcode.com/problems/remove-duplicates-from-sorted-array/description/">...</a>
 * <p>
 * Time: O(n  + m)
 * Memory: O(m)
 *
 */

public class MergeSortedArray {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int c1 = 0;
        int c2 = 0;
        int c = 0;
        int[] buffer = new int[m];
        int bufferCursor = 0;
        int bufferEnd = 0;
        int k = n + m;

        if (n == 0) {
            return;
        }

        while (c < k) {
            int n1 = Integer.MAX_VALUE;
            int n2 = Integer.MAX_VALUE;
            int n3 = Integer.MAX_VALUE;

            if (c1 < m) {
                n1 = nums1[c1];
            }

            if (c2 < n) {
                n2 = nums2[c2];
            }

            if (bufferCursor < bufferEnd) {
                n3 = buffer[bufferCursor];
            }

            if (n1 <= n2 && n1 <= n3) {
                c1++;
            } else if (n2 <= n1 && n2 <= n3) {
                if (c < m) {
                    buffer[bufferEnd] = n1;
                    bufferEnd++;
                }
                c2++;
                c1++;
                nums1[c] = n2;
            } else {
                if (c < m) {
                    buffer[bufferEnd] = n1;
                    bufferEnd++;
                }
                nums1[c] = n3;
                bufferCursor++;
                c1++;
            }
            c++;
        }
    }
}