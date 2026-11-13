import java.util.Arrays;

/**
 * Heap sort: sorts an array in place using a MAX-heap.
 *
 * 1. Turn the array into a max-heap (the largest value is at index 0): O(n)
 * 2. Repeatedly swap the root (largest) with the last unsorted element,
 *    shrink the heap by one, and sink the new root: n times O(log n)
 *
 * Time: O(n log n) in the best, average and worst case.
 * Space: O(1) extra, because it sorts in place.
 * Not stable (equal elements may change their relative order).
 */
public class HeapSort {

    static void heapSort(int[] arr) {
        int n = arr.length;

        // Build the max-heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(arr, n, i);
        }

        // Move the largest to the end, shrink the heap, repeat
        for (int end = n - 1; end > 0; end--) {
            swap(arr, 0, end);
            siftDown(arr, end, 0);
        }
    }

    // Sink arr[i] within the first `size` elements (max-heap version)
    private static void siftDown(int[] arr, int size, int i) {
        while (true) {
            int largest = i;
            int l = 2 * i + 1;
            int r = 2 * i + 2;
            if (l < size && arr[l] > arr[largest]) {
                largest = l;
            }
            if (r < size && arr[r] > arr[largest]) {
                largest = r;
            }
            if (largest == i) {
                return;
            }
            swap(arr, i, largest);
            i = largest;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7, 1, 20};
        System.out.println("before: " + Arrays.toString(arr));
        heapSort(arr);
        System.out.println("after:  " + Arrays.toString(arr));
    }
}
