import java.util.Arrays;

/**
 * Searching algorithms.
 *
 * Linear search works on any array: O(n).
 * Binary search needs a SORTED array and halves the range each step: O(log n).
 */
public class Searching {

    // Linear search: O(n) time, O(1) space
    static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // Binary search (iterative): O(log n) time, O(1) space
    static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;    // avoids overflow of (low + high)
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // Binary search (recursive): O(log n) time, O(log n) space for the call stack
    static int binarySearchRecursive(int[] arr, int target, int low, int high) {
        if (low > high) {
            return -1;
        }
        int mid = low + (high - low) / 2;
        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            return binarySearchRecursive(arr, target, mid + 1, high);
        }
        return binarySearchRecursive(arr, target, low, mid - 1);
    }

    // First occurrence of target in a sorted array with duplicates: O(log n)
    static int firstOccurrence(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        int result = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                result = mid;
                high = mid - 1;        // keep searching to the left
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    // Last occurrence: O(log n)
    static int lastOccurrence(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        int result = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                result = mid;
                low = mid + 1;         // keep searching to the right
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    // Search in a rotated sorted array, e.g. [4,5,6,7,0,1,2]: O(log n)
    // At every step, one half is always properly sorted.
    static int searchRotated(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                return mid;
            }
            if (arr[low] <= arr[mid]) {                       // left half is sorted
                if (arr[low] <= target && target < arr[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {                                          // right half is sorted
                if (arr[mid] < target && target <= arr[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }

    // Integer square root by binary search on the ANSWER: O(log n)
    // Binary search isn't only for arrays: use it whenever a yes/no check is monotonic.
    static int integerSqrt(int x) {
        int low = 0;
        int high = x;
        int result = 0;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (mid * mid <= x) {
                result = (int) mid;
                low = (int) mid + 1;
            } else {
                high = (int) mid - 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] unsorted = {7, 3, 9, 1, 5};
        System.out.println("linearSearch(9): " + linearSearch(unsorted, 9));

        int[] sorted = {1, 3, 5, 7, 9, 11, 13};
        System.out.println("binarySearch(7): " + binarySearch(sorted, 7));
        System.out.println("binarySearch(8): " + binarySearch(sorted, 8));
        System.out.println("binarySearchRecursive(13): "
                + binarySearchRecursive(sorted, 13, 0, sorted.length - 1));

        int[] dups = {1, 2, 2, 2, 2, 3, 4};
        System.out.println("firstOccurrence(2): " + firstOccurrence(dups, 2));
        System.out.println("lastOccurrence(2): " + lastOccurrence(dups, 2));
        System.out.println("count of 2: " + (lastOccurrence(dups, 2) - firstOccurrence(dups, 2) + 1));

        int[] rotated = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("searchRotated(0): " + searchRotated(rotated, 0));
        System.out.println("searchRotated(3): " + searchRotated(rotated, 3));

        System.out.println("integerSqrt(17): " + integerSqrt(17));
        System.out.println("integerSqrt(144): " + integerSqrt(144));
        System.out.println("Arrays.binarySearch (built in): " + Arrays.binarySearch(sorted, 11));
    }
}
