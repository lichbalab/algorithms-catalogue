package sliding_window;

public class MinWindowSequence {

    /**
     * <a href="https://leetcode.com/problems/minimum-window-substring/">...</a>
     * <p>
     * Time complexity: O(n * m)
     * Space complexity: O(1)
     */
    public static String minWindow(String s, String t) {
        int n1 = s.length();
        int n2 = t.length();
        int minLength = -1;
        String minSubSequence = "";
        int sInd  = 0;
        int tInd  = 0;
        int start = 0;
        int end;

        while (sInd < n1) {
            if (s.charAt(sInd) == t.charAt(tInd)) {
                if (tInd == 0) {
                    start = sInd;
                }
                tInd++;
            }
            sInd++;

            if (tInd == n2) {
                end = sInd;
                int length = end - start;
                if (minLength == -1 || minLength > length) {
                    minLength = length;
                    minSubSequence = s.substring(start, end);
                }
                sInd = start + 1;
                tInd = 0;
            }
        }
        return minSubSequence;
    }
}
