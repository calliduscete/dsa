import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DP on strings and sequences. The table usually has one row per character of
 * the first string and one column per character of the second.
 */
public class StringDP {

    // Longest common subsequence (LCS) length.
    // dp[i][j] = LCS of the first i chars of a and the first j chars of b.
    // O(m * n) time, O(m * n) space.
    static int lcsLength(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;                   // chars match: extend
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);   // drop one char
                }
            }
        }
        return dp[a.length()][b.length()];
    }

    // Recover one actual LCS string by walking back through the table. O(m * n)
    static String lcs(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        int i = a.length();
        int j = b.length();
        while (i > 0 && j > 0) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                sb.append(a.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] >= dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        return sb.reverse().toString();
    }

    // Edit distance (Levenshtein): the fewest insert / delete / replace operations
    // to turn a into b. dp[i][j] = distance between a[0..i) and b[0..j).
    // O(m * n) time, O(m * n) space.
    static int editDistance(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;                    // delete all i characters
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;                    // insert all j characters
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],            // replace
                            Math.min(dp[i - 1][j],                       // delete
                                     dp[i][j - 1]));                     // insert
                }
            }
        }
        return dp[m][n];
    }

    // Longest increasing subsequence (LIS), simple DP: O(n^2) time, O(n) space.
    // dp[i] = length of the LIS ending at index i.
    static int lisQuadratic(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int best = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            best = Math.max(best, dp[i]);
        }
        return best;
    }

    // LIS in O(n log n) with binary search ("patience sorting").
    // tails[k] = the smallest possible last value of an increasing subsequence of length k+1.
    static int lisFast(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;
        for (int x : nums) {
            int low = 0;
            int high = size;
            while (low < high) {
                int mid = (low + high) / 2;
                if (tails[mid] < x) {
                    low = mid + 1;
                } else {
                    high = mid;
                }
            }
            tails[low] = x;
            if (low == size) {
                size++;
            }
        }
        return size;
    }

    // Word break: can the string be split into dictionary words?
    // dp[i] = can the first i characters be split? O(n^2) time (ignoring hashing), O(n) space.
    static boolean wordBreak(String s, List<String> dictionary) {
        Set<String> words = new HashSet<>(dictionary);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && words.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    public static void main(String[] args) {
        System.out.println("lcsLength(abcde, ace): " + lcsLength("abcde", "ace"));
        System.out.println("lcs(AGGTAB, GXTXAYB): " + lcs("AGGTAB", "GXTXAYB"));
        System.out.println("editDistance(kitten, sitting): " + editDistance("kitten", "sitting"));
        System.out.println("editDistance(horse, ros): " + editDistance("horse", "ros"));

        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("lisQuadratic: " + lisQuadratic(nums));
        System.out.println("lisFast: " + lisFast(nums));

        List<String> dictionary = new ArrayList<>(List.of("leet", "code"));
        System.out.println("wordBreak(leetcode): " + wordBreak("leetcode", dictionary));
        System.out.println("wordBreak(leetcodes): " + wordBreak("leetcodes", dictionary));
    }
}
