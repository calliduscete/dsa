import java.util.Arrays;

/**
 * Dynamic programming (DP) basics.
 *
 * DP solves a problem by breaking it into OVERLAPPING subproblems, solving each
 * subproblem ONCE, and reusing the stored answer. Two styles:
 *
 *   Top-down (memoisation): write the recursion, cache results in an array/map.
 *   Bottom-up (tabulation): fill a table from the smallest subproblem upward.
 *
 * Both give the same time complexity. Bottom-up avoids recursion depth limits
 * and often allows shrinking the memory usage.
 */
public class DPBasics {

    // --- Fibonacci three ways ---

    // Naive recursion: O(2^n) time. (For comparison only.)
    static long fibNaive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibNaive(n - 1) + fibNaive(n - 2);
    }

    // Top-down with memoisation: O(n) time, O(n) space
    static long fibMemo(int n, long[] memo) {
        if (n <= 1) {
            return n;
        }
        if (memo[n] != -1) {
            return memo[n];
        }
        memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
        return memo[n];
    }

    // Bottom-up with a table: O(n) time, O(n) space
    static long fibTable(int n) {
        if (n <= 1) {
            return n;
        }
        long[] dp = new long[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    // Bottom-up with O(1) space: only the last two values are ever needed
    static long fibOptimised(int n) {
        if (n <= 1) {
            return n;
        }
        long prev = 0;
        long curr = 1;
        for (int i = 2; i <= n; i++) {
            long next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }

    // Climbing stairs: how many ways to climb n steps taking 1 or 2 at a time?
    // ways(n) = ways(n-1) + ways(n-2). O(n) time, O(1) space.
    static int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int prev = 1;
        int curr = 2;
        for (int i = 3; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }

    // House robber: maximum money without robbing two adjacent houses.
    // At each house: rob it (skip the previous) or skip it.
    // dp[i] = max(dp[i-1], dp[i-2] + nums[i]). O(n) time, O(1) space.
    static int houseRobber(int[] nums) {
        int prev = 0;      // best up to two houses ago
        int curr = 0;      // best up to the previous house
        for (int money : nums) {
            int best = Math.max(curr, prev + money);
            prev = curr;
            curr = best;
        }
        return curr;
    }

    // Maximum subarray sum (Kadane's algorithm): O(n) time, O(1) space.
    // At each element: extend the previous subarray or start fresh here.
    static int maxSubarray(int[] nums) {
        int best = nums[0];
        int current = nums[0];
        for (int i = 1; i < nums.length; i++) {
            current = Math.max(nums[i], current + nums[i]);
            best = Math.max(best, current);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println("fibNaive(25): " + fibNaive(25));
        long[] memo = new long[51];
        Arrays.fill(memo, -1);
        System.out.println("fibMemo(50): " + fibMemo(50, memo));
        System.out.println("fibTable(50): " + fibTable(50));
        System.out.println("fibOptimised(50): " + fibOptimised(50));

        System.out.println("climbStairs(5): " + climbStairs(5));
        System.out.println("houseRobber [2,7,9,3,1]: " + houseRobber(new int[]{2, 7, 9, 3, 1}));
        System.out.println("maxSubarray: "
                + maxSubarray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }
}
