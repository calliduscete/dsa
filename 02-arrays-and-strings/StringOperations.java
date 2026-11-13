import java.util.Arrays;

/**
 * Common string operations in Java.
 *
 * Strings in Java are immutable: every "change" creates a new String.
 * Use StringBuilder when building or modifying text in a loop.
 */
public class StringOperations {

    // Reverse a string: O(n) time, O(n) space
    static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    // Check palindrome with two pointers: O(n) time, O(1) space
    static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // Count vowels: O(n) time, O(1) space
    static int countVowels(String s) {
        int count = 0;
        for (char c : s.toLowerCase().toCharArray()) {
            if ("aeiou".indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }

    // Check if two strings are anagrams using a frequency array: O(n) time, O(1) space
    // (assumes lowercase letters a-z only)
    static boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        int[] freq = new int[26];
        for (int i = 0; i < a.length(); i++) {
            freq[a.charAt(i) - 'a']++;
            freq[b.charAt(i) - 'a']--;
        }
        for (int count : freq) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    // First non-repeating character: O(n) time, O(1) space (lowercase a-z only)
    static int firstUniqueChar(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        for (int i = 0; i < s.length(); i++) {
            if (freq[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }

    // Reverse each word in a sentence: O(n) time, O(n) space
    static String reverseWords(String sentence) {
        String[] words = sentence.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]);
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("reverse(hello): " + reverse("hello"));
        System.out.println("isPalindrome(racecar): " + isPalindrome("racecar"));
        System.out.println("isPalindrome(hello): " + isPalindrome("hello"));
        System.out.println("countVowels(Data Structures): " + countVowels("Data Structures"));
        System.out.println("isAnagram(listen, silent): " + isAnagram("listen", "silent"));
        System.out.println("firstUniqueChar(leetcode): " + firstUniqueChar("leetcode"));
        System.out.println("reverseWords: " + reverseWords("the sky is blue"));
    }
}
