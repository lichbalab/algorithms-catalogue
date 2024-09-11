package sliding_window;

import java.util.*;

/**
 * <a href="https://leetcode.com/problems/repeated-dna-sequences/description/">...</a>
 * <p>
 * Time complexity: O(n)
 * Space complexity: O(n)
 */

public class FindRepeatedSequences{

    private static final Map<Character, Integer> ENCODING = Map.of(
            'A', 1,
            'C', 2,
            'G', 3,
            'T', 4
    );

    private static final int BASE_VAR = 4;



    public static Set<String> findRepeatedSequences(String dna, int k) {
        Set<Integer> allHashes = new HashSet<>();
        Set<String> result = new HashSet<>();
        int n = dna.length();
        char[] chars = dna.toCharArray();
        int hash = 0;
        for(int i = 0; i <= n - k; i++) {
            if (i == 0) {
                hash = calculateHash(chars, k);
            } else {
                hash = calculateHash(hash, chars[i - 1], chars[i + k - 1], k);
            }

            if (allHashes.contains(hash)) {
                result.add(dna.substring(i, i + k));
            } else {
                allHashes.add(hash);
            }
        }

        return result;
    }

    private static Integer calculateHash(Integer prevHash, Character removedChar, Character addedChar, int k) {
        return (prevHash - (int)Math.pow(BASE_VAR, k - 1) * ENCODING.get(removedChar)) * BASE_VAR + ENCODING.get(addedChar);

    }

    private static Integer calculateHash(char[] chars, int k) {
        int hash = 0;
        for (int i =0; i < k; i++) {
            hash+= (int)Math.pow(BASE_VAR, k - (i + 1)) * ENCODING.get(chars[i]);
        }
        return hash;
    }
}