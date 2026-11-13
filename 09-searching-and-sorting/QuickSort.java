import java.util.Arrays;
import java.util.Random;

/**
 * Quick sort: divide and conquer.
 *
 * 1. Pick a PIVOT.
 * 2. Partition: move smaller values left of the pivot, larger values right.
 *    The pivot is now in its final position.
 * 3. Recursively sort the left and right parts.
 *
 * Time: O(n log n) average, O(n^2) worst case (bad pivots, e.g. already sorted
 *       data with the first/last element as the pivot).
 * Space: O(log n) average for the recursion stack. Sorts in place.
 * Stable: no.
 * A RANDOM pivot makes the worst case extremely unlikely.
 * In practice quick sort is usually the fastest general-purpose sort.
 */
public class QuickSort {

    private static final Random RANDOM = new Random();

    static void quickSort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        int pivotIndex = partition(arr, low, high);
        sort(arr, low, pivotIndex - 1);
        sort(arr, pivotIndex + 1, high);
    }

    // Lomuto partition with a random pivot: O(n)
    private static int partition(int[] arr, int low, int high) {
        int randomIndex = low + RANDOM.nextInt(high - low + 1);
        swap(arr, randomIndex, high);          // move the pivot to the end
        int pivot = arr[high];

        int i = low;                           // next slot for a value smaller than the pivot
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, high);                    // put the pivot in its final place
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Quickselect: find the kth smallest (0-based) without fully sorting.
    // O(n) average, O(n^2) worst case.
    static int quickSelect(int[] arr, int k) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int p = partition(arr, low, high);
            if (p == k) {
                return arr[p];
            } else if (p < k) {
                low = p + 1;
            } else {
                high = p - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {10, 7, 8, 9, 1, 5, 3};
        System.out.println("before: " + Arrays.toString(arr));
        quickSort(arr);
        System.out.println("after:  " + Arrays.toString(arr));

        int[] data = {7, 10, 4, 3, 20, 15};
        System.out.println("3rd smallest (quickselect): " + quickSelect(data, 2));
    }
}
