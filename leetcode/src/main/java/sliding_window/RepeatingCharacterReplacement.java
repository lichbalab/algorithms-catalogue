package sliding_window;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 <a href="https://leetcode.com/problems/longest-repeating-character-replacement/description/">...</a> * <p>
 * Time complexity: O(n * k)
 * Space complexity: O(k)
 */

public class RepeatingCharacterReplacement {

    public static int longestRepeatingCharacterReplacement(String s, int k) {
        int n = s.length();
        int start = 0;
        int end = 0;
        Map<Character, Integer> frequencies = new HashMap<>();
        int replaces = 0;

        while (end < n) {
            while(end < n && replaces <= k) {
                replaces = replaces(s, start, end++, frequencies);
            }

            if (end < n) {
                replaces = updateWindowReplaces(s, ++start, end++, frequencies);
            }
        }

        if (replaces <= k) {
            return end - start;
        } else {
            return end - start - 1;
        }
    }

    static int updateWindowReplaces(String s, int start, int end, Map<Character, Integer> map){
        int removedElFrequncey = map.get(s.charAt(start - 1));
        removedElFrequncey--;
        if (removedElFrequncey == 0) {
            map.remove(s.charAt(start - 1));
        } else {
            map.put(s.charAt(start - 1), removedElFrequncey);
        }

        map.put(s.charAt(end), map.getOrDefault(s.charAt(end), 0) + 1);

        return end - start + 1 - getMax(map.values());
    }

    static int replaces(String s, int start, int end, Map<Character, Integer> map) {
        map.put(s.charAt(end), map.getOrDefault(s.charAt(end), 0) + 1);
        return end - start + 1 - getMax(map.values());
    }

    private static int getMax(Collection<Integer> values) {
        int max  = 0;
        for (int value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}