package sliding_window;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <a href="https://leetcode.com/problems/minimum-window-substring/description/">...</a>
 * Time complexity: O(n * k)
 * Space complexity: O(1)
 */

public class MinimumWindowSubstring {
    public static String minWindow(String s, String t) {
        int start = 0;
        int end = 0;
        int l = s.length();
        String minWindow = "";
        Map<Character, Integer> currentFrcs = getFrequencies(t);

        while (end < l) {
            Character currentChar = s.charAt(end);

            if (currentFrcs.containsKey(currentChar)) {
                currentFrcs.put(currentChar, currentFrcs.get(currentChar) - 1);
            }

            if (allCharsInSubstring(currentFrcs)) {
                start = trimWindow(s, currentFrcs, start, end);
                minWindow = minWindow(s, minWindow, start, end);

                currentFrcs.put(s.charAt(start), currentFrcs.getOrDefault(currentChar, 0) + 1);
                start++;
            }

            end++;
        }

        if (start < l && allCharsInSubstring(currentFrcs)) {
            start = trimWindow(s, currentFrcs, start, end);
            minWindow = minWindow(s, minWindow, start, end);
        }

        return minWindow;
    }

    private static int trimWindow(String s, Map<Character, Integer> currentFrcs, int start, int end) {
        while (start < end) {
            char ch = s.charAt(start);
            Integer frequency = currentFrcs.get(ch);

            if (frequency != null && frequency  >= 0){
                break;
            }

            if (frequency != null) {
                currentFrcs.put(ch, frequency + 1);
            }

            start++;
        }
        return start;
    }

    private static String minWindow(String s, String minWindow, int start, int end) {
        if (minWindow.isEmpty() || minWindow.length() > end - start + 1) {
            minWindow = s.substring(start, end + 1);
        }

        return minWindow;
    }

    private static boolean allCharsInSubstring(Map<Character, Integer> currentFrcs) {
        for (Integer value : currentFrcs.values()){
            if (value > 0) {
                return false;
            }
        }
        return true;
    }

    private static Map<Character, Integer> getFrequencies(String s) {
        int n = s.length();
        Map<Character, Integer> frequencies = new HashMap<>();
        for (int i = 0; i < n; i++) {
            Character currentChar = s.charAt(i);
            frequencies.put(currentChar, frequencies.getOrDefault(currentChar, 0) + 1);
        }
        return frequencies;
    }

}
