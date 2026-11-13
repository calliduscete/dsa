import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Classic problems solved with Java's built-in HashMap and HashSet.
 *
 * The common idea: trade O(n) extra space for O(1) lookups, which turns many
 * O(n^2) brute-force solutions into O(n).
 */
public class HashProblems {

    // Contains duplicate: O(n) time, O(n) space
    static boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int x : nums) {
            if (!seen.add(x)) {      // add returns false if it was already there
                return true;
            }
        }
        return false;
    }

    // Character frequency: O(n) time, O(k) space (k = distinct characters)
    static Map<Character, Integer> charFrequency(String s) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        return freq;
    }

    // First non-repeating character: O(n) time, O(k) space
    static int firstUniqueChar(String s) {
        Map<Character, Integer> freq = charFrequency(s);
        for (int i = 0; i < s.length(); i++) {
            if (freq.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }

    // Two Sum: O(n) time, O(n) space
    static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> indexOf = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer j = indexOf.get(target - nums[i]);
            if (j != null) {
                return new int[]{j, i};
            }
            indexOf.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    // Group anagrams: O(n * k log k) time, k = longest word length
    // Words that are anagrams share the same sorted-letters key.
    static List<List<String>> groupAnagrams(String[] words) {
        Map<String, List<String>> groups = new HashMap<>();
        for (String word : words) {
            char[] letters = word.toCharArray();
            Arrays.sort(letters);
            String key = new String(letters);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
        }
        return new ArrayList<>(groups.values());
    }

    // Longest consecutive sequence: O(n) time, O(n) space
    // Only start counting from numbers that begin a sequence (x - 1 is absent).
    static int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int x : nums) {
            set.add(x);
        }
        int best = 0;
        for (int x : set) {
            if (!set.contains(x - 1)) {
                int length = 1;
                while (set.contains(x + length)) {
                    length++;
                }
                best = Math.max(best, length);
            }
        }
        return best;
    }

    // Count subarrays whose sum equals k (prefix sums + hash map): O(n) time, O(n) space
    static int subarraySumEqualsK(int[] nums, int k) {
        Map<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1);            // empty prefix
        int sum = 0;
        int count = 0;
        for (int x : nums) {
            sum += x;
            count += prefixCount.getOrDefault(sum - k, 0);
            prefixCount.put(sum, prefixCount.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println("containsDuplicate [1,2,3,1]: " + containsDuplicate(new int[]{1, 2, 3, 1}));
        System.out.println("containsDuplicate [1,2,3]: " + containsDuplicate(new int[]{1, 2, 3}));
        System.out.println("charFrequency(hello): " + new java.util.TreeMap<>(charFrequency("hello")));
        System.out.println("firstUniqueChar(leetcode): " + firstUniqueChar("leetcode"));
        System.out.println("twoSum: " + Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));

        List<List<String>> groups = groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
        System.out.println("groupAnagrams group count: " + groups.size());
        for (List<String> group : groups) {
            java.util.Collections.sort(group);
        }
        groups.sort((a, b) -> a.get(0).compareTo(b.get(0)));
        System.out.println("groupAnagrams: " + groups);

        System.out.println("longestConsecutive: " + longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
        System.out.println("subarraySumEqualsK: " + subarraySumEqualsK(new int[]{1, 2, 3, 1, 1}, 3));
    }
}
