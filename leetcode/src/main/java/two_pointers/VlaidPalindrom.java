package two_pointers;

/**
 * <a href="https://leetcode.com/problems/valid-palindrome/description/?envType=study-plan-v2&envId=top-interview-150">...</a>
 * <p>
 * Time: O(n/2)
 * Memory: O(n)
 *
 */
public class VlaidPalindrom {

    public static boolean isPalindrome(String s) {
        char[] chars = s.toLowerCase().toCharArray();
        int i = 0;
        int j = chars.length -1 ;

        while(i < j) {
            char left = chars[i];
            char right = chars[j];

            if (!Character.isLetter(left) && !Character.isDigit(left)) {
                i++;
            }

            if (!Character.isLetter(right) && !Character.isDigit(right)) {
                j--;
                continue;
            }

            if (chars[i] != chars[j]) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}