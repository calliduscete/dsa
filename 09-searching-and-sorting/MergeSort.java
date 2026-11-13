import java.util.Arrays;

/**
 * Merge sort: divide and conquer.
 *
 * 1. Split the array in half.
 * 2. Sort each half recursively.
 * 3. Merge the two sorted halves.
 *
 * Time: O(n log n) in ALL cases (best, average, worst).
 * Space: O(n) for the temporary arrays.
 * Stable: yes (equal elements keep their original order).
 * Good for linked lists and for data too big to fit in memory.
 */
public class MergeSort {

    static void mergeSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }
        int[] temp = new int[arr.length];
        sort(arr, temp, 0, arr.length - 1);
    }

    private static void sort(int[] arr, int[] temp, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        sort(arr, temp, left, mid);
        sort(arr, temp, mid + 1, right);
        merge(arr, temp, left, mid, right);
    }

    // Merge arr[left..mid] and arr[mid+1..right], both already sorted: O(n)
    private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {          // <= keeps the sort stable
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        for (k = left; k <= right; k++) {
            arr[k] = temp[k];
        }
    }

    public static void main(String[] args) {
        int[] arr = {38, 27, 43, 3, 9, 82, 10};
        System.out.println("before: " + Arrays.toString(arr));
        mergeSort(arr);
        System.out.println("after:  " + Arrays.toString(arr));
    }
}
