import java.util.Arrays;

/**
 * Basic array operations.
 *
 * Arrays have a fixed size and store elements next to each other in memory,
 * which is why access by index is O(1).
 */
public class ArrayOperations {

    // Traverse and print: O(n) time, O(1) space
    static void printArray(int[] arr) {
        for (int x : arr) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

    // Linear search: O(n) time, O(1) space
    static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // Find maximum: O(n) time, O(1) space
    static int findMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    // Insert at index: O(n) time (shifting elements), O(n) space (new array)
    static int[] insertAt(int[] arr, int index, int value) {
        int[] result = new int[arr.length + 1];
        for (int i = 0; i < index; i++) {
            result[i] = arr[i];
        }
        result[index] = value;
        for (int i = index; i < arr.length; i++) {
            result[i + 1] = arr[i];
        }
        return result;
    }

    // Delete at index: O(n) time (shifting elements), O(n) space (new array)
    static int[] deleteAt(int[] arr, int index) {
        int[] result = new int[arr.length - 1];
        for (int i = 0, j = 0; i < arr.length; i++) {
            if (i != index) {
                result[j++] = arr[i];
            }
        }
        return result;
    }

    // Reverse in place with two pointers: O(n) time, O(1) space
    static void reverse(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    // Rotate right by k using the reversal trick: O(n) time, O(1) space
    static void rotateRight(int[] arr, int k) {
        int n = arr.length;
        k = k % n;
        reverseRange(arr, 0, n - 1);
        reverseRange(arr, 0, k - 1);
        reverseRange(arr, k, n - 1);
    }

    private static void reverseRange(int[] arr, int left, int right) {
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        int[] arr = {4, 8, 15, 16, 23, 42};

        System.out.print("Original: ");
        printArray(arr);
        System.out.println("Index of 15: " + linearSearch(arr, 15));
        System.out.println("Max: " + findMax(arr));

        System.out.println("Insert 99 at index 2: " + Arrays.toString(insertAt(arr, 2, 99)));
        System.out.println("Delete index 0: " + Arrays.toString(deleteAt(arr, 0)));

        reverse(arr);
        System.out.println("Reversed: " + Arrays.toString(arr));

        rotateRight(arr, 2);
        System.out.println("Rotated right by 2: " + Arrays.toString(arr));
    }
}
