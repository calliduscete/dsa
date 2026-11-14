import java.util.Arrays;

/**
 * Fractional knapsack: you may take PART of an item.
 *
 * Greedy idea: take items in order of best value per kilogram (value / weight),
 * and take a fraction of the last one if it doesn't fit.
 * This is provably optimal when items can be split.
 *
 * Time: O(n log n) for the sort. Space: O(n).
 *
 * (If items can NOT be split, this is the 0/1 knapsack and greedy fails.
 *  That version needs dynamic programming, in the next folder.)
 */
public class FractionalKnapsack {

    static double maxValue(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        // best value-per-weight first
        Arrays.sort(order, (a, b) -> Double.compare(
                (double) values[b] / weights[b],
                (double) values[a] / weights[a]));

        double total = 0;
        int remaining = capacity;
        for (int index : order) {
            if (remaining == 0) {
                break;
            }
            if (weights[index] <= remaining) {
                total += values[index];                           // take the whole item
                remaining -= weights[index];
            } else {
                total += (double) values[index] * remaining / weights[index];   // take a fraction
                remaining = 0;
            }
        }
        return total;
    }

    public static void main(String[] args) {
        int[] weights = {10, 20, 30};
        int[] values = {60, 100, 120};
        System.out.println("max value (capacity 50): " + maxValue(weights, values, 50));
        // value per kg: 6, 5, 4  ->  take all of item 1 and 2 (60 + 100),
        // then 20/30 of item 3 (80)  ->  240
    }
}
