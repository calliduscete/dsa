import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Classic array techniques: hash map, two pointers, prefix sum, sliding window.
 */
public class ArrayProblems {

    // Two Sum (unsorted) using a hash map: O(n) time, O(n) space
    // Returns the indices of two numbers that add up to target.
    static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int needed = target - nums[i];
            if (seen.containsKey(needed)) {
                return new int[]{seen.get(needed), i};
            }
            seen.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }

    // Two Sum on a sorted array using two pointers: O(n) time, O(1) space
    static int[] twoSumSorted(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) {
                return new int[]{left, right};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{-1, -1};
    }

    // Move all zeros to the end, keeping order of the rest: O(n) time, O(1) space
    static void moveZeros(int[] nums) {
        int insertPos = 0;
        for (int x : nums) {
            if (x != 0) {
                nums[insertPos++] = x;
            }
        }
        while (insertPos < nums.length) {
            nums[insertPos++] = 0;
        }
    }

    // Remove duplicates from a sorted array in place: O(n) time, O(1) space
    // Returns the number of unique elements.
    static int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }
        return slow + 1;
    }

    // Prefix sums: O(n) to build, then any range sum in O(1)
    static int[] buildPrefixSum(int[] nums) {
        int[] prefix = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        return prefix;
    }

    // Sum of nums[left..right] inclusive, using the prefix array: O(1)
    static int rangeSum(int[] prefix, int left, int right) {
        return prefix[right + 1] - prefix[left];
    }

    // Maximum sum of any subarray of size k (sliding window): O(n) time, O(1) space
    static int maxSumSubarray(int[] nums, int k) {
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        int best = windowSum;
        for (int i = k; i < nums.length; i++) {
            windowSum += nums[i] - nums[i - k];
            best = Math.max(best, windowSum);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println("twoSum: " + Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));
        System.out.println("twoSumSorted: " + Arrays.toString(twoSumSorted(new int[]{1, 3, 4, 6, 9}, 10)));

        int[] zeros = {0, 1, 0, 3, 12};
        moveZeros(zeros);
        System.out.println("moveZeros: " + Arrays.toString(zeros));

        int[] dups = {1, 1, 2, 2, 2, 3};
        int unique = removeDuplicates(dups);
        System.out.println("removeDuplicates: " + unique + " unique, "
                + Arrays.toString(Arrays.copyOf(dups, unique)));

        int[] prefix = buildPrefixSum(new int[]{3, 1, 4, 1, 5, 9});
        System.out.println("rangeSum(1..3): " + rangeSum(prefix, 1, 3));

        System.out.println("maxSumSubarray(k=3): " + maxSumSubarray(new int[]{2, 1, 5, 1, 3, 2}, 3));
    }
}
