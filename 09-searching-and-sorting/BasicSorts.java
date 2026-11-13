import java.util.Arrays;

/**
 * The three simple O(n^2) sorts. Easy to understand, fine for small inputs.
 *
 *              Best     Average   Worst    Space   Stable
 * Bubble       O(n)     O(n^2)    O(n^2)   O(1)    Yes
 * Selection    O(n^2)   O(n^2)    O(n^2)   O(1)    No
 * Insertion    O(n)     O(n^2)    O(n^2)   O(1)    Yes
 */
public class BasicSorts {

    // Bubble sort: repeatedly swap adjacent out-of-order pairs, so the largest
    // value "bubbles" to the end. Stops early if a full pass makes no swaps.
    static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int pass = 0; pass < n - 1; pass++) {
            boolean swapped = false;
            for (int i = 0; i < n - 1 - pass; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                return;        // already sorted: this is the O(n) best case
            }
        }
    }

    // Selection sort: find the smallest remaining value and put it in place.
    // Always O(n^2) comparisons, but only O(n) swaps.
    static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    // Insertion sort: grow a sorted section on the left, inserting each new
    // value into its correct place (like sorting playing cards in your hand).
    // Very fast on nearly sorted data.
    static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];       // shift larger values right
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        int[] original = {64, 25, 12, 22, 11, 90, 3};

        int[] a = original.clone();
        bubbleSort(a);
        System.out.println("bubble:    " + Arrays.toString(a));

        int[] b = original.clone();
        selectionSort(b);
        System.out.println("selection: " + Arrays.toString(b));

        int[] c = original.clone();
        insertionSort(c);
        System.out.println("insertion: " + Arrays.toString(c));
    }
}
