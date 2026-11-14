import java.util.Arrays;

/**
 * More classic greedy problems.
 *
 * A greedy algorithm makes the best-looking choice at each step and never
 * reconsiders it. It's fast and simple, but only correct when a locally best
 * choice leads to a globally best answer. That needs an argument or a proof.
 */
public class GreedyProblems {

    // Jump game: can you reach the last index? nums[i] = max jump length from i.
    // Track the furthest index reachable. O(n) time, O(1) space.
    static boolean canJump(int[] nums) {
        int furthest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > furthest) {
                return false;              // this index can't be reached
            }
            furthest = Math.max(furthest, i + nums[i]);
        }
        return true;
    }

    // Jump game II: the minimum number of jumps to reach the end. O(n) time, O(1) space.
    // Think in "waves": each jump covers a range of indices.
    static int minJumps(int[] nums) {
        int jumps = 0;
        int currentEnd = 0;       // furthest index reachable with `jumps` jumps
        int furthest = 0;         // furthest index reachable with one more jump
        for (int i = 0; i < nums.length - 1; i++) {
            furthest = Math.max(furthest, i + nums[i]);
            if (i == currentEnd) {
                jumps++;
                currentEnd = furthest;
            }
        }
        return jumps;
    }

    // Best time to buy and sell a stock, with unlimited transactions.
    // Collect every price rise. O(n) time, O(1) space.
    static int maxProfit(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }

    // Assign cookies: each child needs a minimum size, each cookie has a size.
    // Maximise the number of satisfied children. Sort both, then match smallest
    // adequate cookie to the least greedy child. O(n log n + m log m)
    static int assignCookies(int[] childNeeds, int[] cookieSizes) {
        Arrays.sort(childNeeds);
        Arrays.sort(cookieSizes);
        int child = 0;
        for (int cookie = 0; cookie < cookieSizes.length && child < childNeeds.length; cookie++) {
            if (cookieSizes[cookie] >= childNeeds[child]) {
                child++;
            }
        }
        return child;
    }

    // Gas station: gas[i] is fuel at station i, cost[i] is fuel to reach the next.
    // Return the starting station index for a full circuit, or -1.
    // If the total gas is enough, the answer is just after the point where the
    // running tank last went negative. O(n) time, O(1) space.
    static int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0;
        int tank = 0;
        int start = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            total += diff;
            tank += diff;
            if (tank < 0) {
                start = i + 1;             // can't start at or before i
                tank = 0;
            }
        }
        return total >= 0 ? start : -1;
    }

    public static void main(String[] args) {
        System.out.println("canJump [2,3,1,1,4]: " + canJump(new int[]{2, 3, 1, 1, 4}));
        System.out.println("canJump [3,2,1,0,4]: " + canJump(new int[]{3, 2, 1, 0, 4}));
        System.out.println("minJumps [2,3,1,1,4]: " + minJumps(new int[]{2, 3, 1, 1, 4}));
        System.out.println("maxProfit [7,1,5,3,6,4]: " + maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        System.out.println("assignCookies: "
                + assignCookies(new int[]{1, 2, 3}, new int[]{1, 1}));
        System.out.println("assignCookies: "
                + assignCookies(new int[]{1, 2}, new int[]{1, 2, 3}));
        System.out.println("gas station start: "
                + canCompleteCircuit(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}));
        System.out.println("gas station (impossible): "
                + canCompleteCircuit(new int[]{2, 3, 4}, new int[]{3, 4, 3}));
    }
}
