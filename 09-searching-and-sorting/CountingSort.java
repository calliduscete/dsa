import java.util.Arrays;

/**
 * Counting sort: a NON-comparison sort for small ranges of non-negative integers.
 *
 * Count how many times each value appears, then rebuild the array from the counts.
 *
 * Time: O(n + k), where k is the largest value. Space: O(k).
 * Beats O(n log n) when k is small compared with n,
 * but wasteful when the values are spread over a huge range.
 */
public class CountingSort {

    static void countingSort(int[] arr) {
        if (arr.length == 0) {
            return;
        }
        int max = arr[0];
        for (int x : arr) {
            if (x > max) {
                max = x;
            }
        }
        int[] counts = new int[max + 1];
        for (int x : arr) {
            counts[x]++;
        }
        int index = 0;
        for (int value = 0; value <= max; value++) {
            while (counts[value] > 0) {
                arr[index++] = value;
                counts[value]--;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {4, 2, 2, 8, 3, 3, 1, 0, 8};
        System.out.println("before: " + Arrays.toString(arr));
        countingSort(arr);
        System.out.println("after:  " + Arrays.toString(arr));
    }
}
