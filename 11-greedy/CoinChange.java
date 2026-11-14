import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Making change with the fewest coins.
 *
 * Greedy idea: always take the largest coin that fits.
 *
 * Works for "canonical" coin systems such as US coins (1, 5, 10, 25) or
 * euro coins, but FAILS for some systems. Greedy is not always correct, so
 * you must be able to justify why it works for a given problem.
 */
public class CoinChange {

    // Greedy: O(n) time after sorting the coins (n = number of coin types)
    static List<Integer> greedyChange(int[] coins, int amount) {
        int[] sorted = coins.clone();
        Arrays.sort(sorted);
        List<Integer> used = new ArrayList<>();
        for (int i = sorted.length - 1; i >= 0; i--) {
            while (amount >= sorted[i]) {
                amount -= sorted[i];
                used.add(sorted[i]);
            }
        }
        return amount == 0 ? used : null;      // null means greedy got stuck
    }

    // Dynamic programming (a preview of the next folder): always correct.
    // O(amount * coins) time, O(amount) space.
    static int minCoinsDP(int[] coins, int amount) {
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

    public static void main(String[] args) {
        int[] us = {1, 5, 10, 25};
        System.out.println("US coins, 63 cents -> greedy: " + greedyChange(us, 63)
                + ", optimal count: " + minCoinsDP(us, 63));

        // A coin system where greedy goes wrong
        int[] odd = {1, 3, 4};
        List<Integer> greedy = greedyChange(odd, 6);
        System.out.println("coins {1,3,4}, amount 6 -> greedy: " + greedy
                + " (" + greedy.size() + " coins), optimal count: " + minCoinsDP(odd, 6));
        // greedy picks 4+1+1 (3 coins) but 3+3 uses only 2.
    }
}
