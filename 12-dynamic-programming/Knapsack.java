import java.util.Arrays;

/**
 * Knapsack-style DP problems: for each item, TAKE it or SKIP it.
 *
 * These are the problems where greedy fails (0/1 knapsack, coin change with
 * unusual coins) and DP gives the correct answer.
 */
public class Knapsack {

    // 0/1 knapsack: items can't be split. Maximise total value within capacity.
    // dp[i][w] = best value using the first i items with capacity w.
    // O(n * W) time, O(n * W) space.
    static int knapsack2D(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i - 1][w];                              // skip item i
                if (weights[i - 1] <= w) {                            // or take it
                    dp[i][w] = Math.max(dp[i][w],
                            dp[i - 1][w - weights[i - 1]] + values[i - 1]);
                }
            }
        }
        return dp[n][capacity];
    }

    // Same answer with a single row: O(n * W) time, O(W) space.
    // Loop the capacity BACKWARDS so each item is used at most once.
    static int knapsack1D(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];
        for (int i = 0; i < weights.length; i++) {
            for (int w = capacity; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }
        return dp[capacity];
    }

    // Subset sum: can some subset add up to the target? O(n * target) time, O(target) space.
    static boolean subsetSum(int[] nums, int target) {
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;                                  // the empty subset makes 0
        for (int num : nums) {
            for (int s = target; s >= num; s--) {
                dp[s] = dp[s] || dp[s - num];
            }
        }
        return dp[target];
    }

    // Partition equal subset sum: can the array be split into two equal-sum halves?
    // That means: is there a subset summing to total / 2?
    static boolean canPartition(int[] nums) {
        int total = Arrays.stream(nums).sum();
        return total % 2 == 0 && subsetSum(nums, total / 2);
    }

    // Coin change (minimum coins). Coins can be reused. -1 if impossible.
    // dp[a] = fewest coins to make amount a. O(amount * coins) time, O(amount) space.
    static int coinChangeMin(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int a = 1; a <= amount; a++) {
            for (int coin : coins) {
                if (coin <= a && dp[a - coin] != Integer.MAX_VALUE) {
                    dp[a] = Math.min(dp[a], dp[a - coin] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    // Coin change (number of ways to make the amount). Order doesn't matter.
    // Loop over coins on the OUTSIDE so each combination is counted once.
    // O(amount * coins) time, O(amount) space.
    static int coinChangeWays(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;                                     // one way to make 0: use nothing
        for (int coin : coins) {
            for (int a = coin; a <= amount; a++) {
                dp[a] += dp[a - coin];
            }
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        int[] weights = {1, 3, 4, 5};
        int[] values = {1, 4, 5, 7};
        System.out.println("knapsack2D (cap 7): " + knapsack2D(weights, values, 7));
        System.out.println("knapsack1D (cap 7): " + knapsack1D(weights, values, 7));

        System.out.println("subsetSum([3,34,4,12,5,2], 9): "
                + subsetSum(new int[]{3, 34, 4, 12, 5, 2}, 9));
        System.out.println("canPartition [1,5,11,5]: " + canPartition(new int[]{1, 5, 11, 5}));
        System.out.println("canPartition [1,2,3,5]: " + canPartition(new int[]{1, 2, 3, 5}));

        System.out.println("coinChangeMin {1,2,5}, 11: " + coinChangeMin(new int[]{1, 2, 5}, 11));
        System.out.println("coinChangeMin {1,3,4}, 6: " + coinChangeMin(new int[]{1, 3, 4}, 6));
        System.out.println("coinChangeMin {2}, 3: " + coinChangeMin(new int[]{2}, 3));
        System.out.println("coinChangeWays {1,2,5}, 5: " + coinChangeWays(new int[]{1, 2, 5}, 5));
    }
}
