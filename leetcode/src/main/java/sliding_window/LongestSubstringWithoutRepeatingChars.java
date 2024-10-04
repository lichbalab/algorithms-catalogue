package sliding_window;

import java.util.HashSet;
import java.util.Set;

/**
 <a href="https://leetcode.com/problems/longest-substring-without-repeating-characters/">...</a>
 * Time complexity: O(n)
 * Space complexity: O(1)
 */
public class LongestSubstringWithoutRepeatingChars {

    public static int findLongestSubstring(String str) {
        int start = 0;
        int n = str.length();
        Set<Character> window = new HashSet<>();
        int longestSubstring = 0;

        for (int end = 0; end < n; end++) {
            char currentChar = str.charAt(end);

            if (window.contains(currentChar)) {
                int length = end - start;
                if (length > longestSubstring) {
                    longestSubstring = length;
                }
                char startChar = str.charAt(start);
                while (startChar != currentChar) {
                    window.remove(startChar);
                    startChar = str.charAt(++start);
                }
                start++;
            } else {
                window.add(currentChar);
            }
        }

        int length = n - start;
        if (length > longestSubstring) {
            longestSubstring = length;
        }
        // Replace this placeholder return statement with your code
        return longestSubstring;
    }
}
