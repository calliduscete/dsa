import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Classic heap problems using Java's built-in PriorityQueue.
 *
 * PriorityQueue is a MIN-heap by default.
 *   Max-heap:           new PriorityQueue<>(Collections.reverseOrder())
 *   Custom ordering:    new PriorityQueue<>((a, b) -> ...)
 * offer(): O(log n)   poll(): O(log n)   peek(): O(1)
 */
public class HeapProblems {

    // Kth largest element: keep a min-heap of the k largest seen so far.
    // O(n log k) time, O(k) space
    static int kthLargest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int x : nums) {
            heap.offer(x);
            if (heap.size() > k) {
                heap.poll();         // drop the smallest, keep the k largest
            }
        }
        return heap.peek();
    }

    // K smallest elements: use a max-heap of size k. O(n log k) time, O(k) space
    static List<Integer> kSmallest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        for (int x : nums) {
            heap.offer(x);
            if (heap.size() > k) {
                heap.poll();         // drop the largest, keep the k smallest
            }
        }
        List<Integer> result = new ArrayList<>(heap);
        Collections.sort(result);
        return result;
    }

    // Top k most frequent elements: O(n log k) time, O(n) space
    static List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) {
            freq.merge(x, 1, Integer::sum);
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>(
                (a, b) -> freq.get(a) - freq.get(b));    // least frequent on top
        for (int x : freq.keySet()) {
            heap.offer(x);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        List<Integer> result = new ArrayList<>(heap);
        result.sort((a, b) -> freq.get(b) - freq.get(a));
        return result;
    }

    // Last stone weight: repeatedly smash the two heaviest stones.
    // O(n log n) time, O(n) space
    static int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        for (int s : stones) {
            heap.offer(s);
        }
        while (heap.size() > 1) {
            int a = heap.poll();
            int b = heap.poll();
            if (a != b) {
                heap.offer(a - b);
            }
        }
        return heap.isEmpty() ? 0 : heap.peek();
    }

    // Merge k sorted arrays: O(N log k) time, where N = total elements, O(k) space
    static List<Integer> mergeKSorted(int[][] arrays) {
        // Each heap entry is {value, which array, index in that array}
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].length > 0) {
                heap.offer(new int[]{arrays[i][0], i, 0});
            }
        }
        List<Integer> result = new ArrayList<>();
        while (!heap.isEmpty()) {
            int[] top = heap.poll();
            result.add(top[0]);
            int arrayIndex = top[1];
            int next = top[2] + 1;
            if (next < arrays[arrayIndex].length) {
                heap.offer(new int[]{arrays[arrayIndex][next], arrayIndex, next});
            }
        }
        return result;
    }

    // Running median with two heaps: add O(log n), median O(1)
    //   lower: max-heap holding the smaller half
    //   upper: min-heap holding the larger half
    static class MedianFinder {
        private final PriorityQueue<Integer> lower = new PriorityQueue<>(Collections.reverseOrder());
        private final PriorityQueue<Integer> upper = new PriorityQueue<>();

        void add(int num) {
            lower.offer(num);
            upper.offer(lower.poll());           // move the largest of the lower half up
            if (upper.size() > lower.size()) {
                lower.offer(upper.poll());       // keep lower the same size or one bigger
            }
        }

        double median() {
            if (lower.size() > upper.size()) {
                return lower.peek();
            }
            return (lower.peek() + upper.peek()) / 2.0;
        }
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 1, 5, 6, 4};
        System.out.println("kthLargest (k=2): " + kthLargest(nums, 2));
        System.out.println("kSmallest (k=3): " + kSmallest(nums, 3));
        System.out.println("topKFrequent (k=2): "
                + topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2));
        System.out.println("lastStoneWeight: "
                + lastStoneWeight(new int[]{2, 7, 4, 1, 8, 1}));
        System.out.println("mergeKSorted: "
                + mergeKSorted(new int[][]{{1, 4, 7}, {2, 5, 8}, {3, 6, 9}}));

        MedianFinder mf = new MedianFinder();
        for (int x : new int[]{5, 15, 1, 3}) {
            mf.add(x);
            System.out.println("added " + x + ", median = " + mf.median());
        }
    }
}
