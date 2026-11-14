import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Interval problems: the classic home of greedy algorithms.
 *
 * Greedy idea for scheduling: sort by END time and always take the activity that
 * finishes earliest, because it leaves the most room for the ones that follow.
 */
public class ActivitySelection {

    // Activity selection: the maximum number of non-overlapping intervals.
    // Each interval is {start, end}. O(n log n) time (the sort), O(1) extra space.
    static int maxNonOverlapping(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[1], b[1]));   // earliest end first

        int count = 1;
        int lastEnd = sorted[0][1];
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i][0] >= lastEnd) {            // starts after the last chosen one ends
                count++;
                lastEnd = sorted[i][1];
            }
        }
        return count;
    }

    // Minimum number of intervals to REMOVE so the rest don't overlap.
    // That is simply: total - maximum that can be kept. O(n log n)
    static int minRemovals(int[][] intervals) {
        return intervals.length - maxNonOverlapping(intervals);
    }

    // Merge overlapping intervals: sort by START, then extend or start a new one.
    // O(n log n) time, O(n) space for the result.
    static List<int[]> mergeIntervals(int[][] intervals) {
        List<int[]> merged = new ArrayList<>();
        if (intervals.length == 0) {
            return merged;
        }
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));

        int[] current = sorted[0].clone();
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i][0] <= current[1]) {
                current[1] = Math.max(current[1], sorted[i][1]);   // overlap: extend
            } else {
                merged.add(current);                               // gap: close this one
                current = sorted[i].clone();
            }
        }
        merged.add(current);
        return merged;
    }

    // Minimum number of meeting rooms (or train platforms) needed.
    // Sort starts and ends separately and sweep through time. O(n log n)
    static int minRooms(int[][] intervals) {
        int n = intervals.length;
        int[] starts = new int[n];
        int[] ends = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }
        Arrays.sort(starts);
        Arrays.sort(ends);

        int rooms = 0;
        int maxRooms = 0;
        int e = 0;
        for (int s = 0; s < n; s++) {
            if (starts[s] < ends[e]) {
                rooms++;                 // a meeting starts before any has ended
            } else {
                e++;                     // a meeting ended: reuse its room
            }
            maxRooms = Math.max(maxRooms, rooms);
        }
        return maxRooms;
    }

    public static void main(String[] args) {
        int[][] activities = {{1, 4}, {3, 5}, {0, 6}, {5, 7}, {3, 9}, {5, 9}, {6, 10}, {8, 11}};
        System.out.println("maxNonOverlapping: " + maxNonOverlapping(activities));
        System.out.println("minRemovals: " + minRemovals(activities));

        int[][] toMerge = {{1, 3}, {8, 10}, {2, 6}, {15, 18}, {17, 20}};
        System.out.print("mergeIntervals: ");
        for (int[] interval : mergeIntervals(toMerge)) {
            System.out.print(Arrays.toString(interval) + " ");
        }
        System.out.println();

        int[][] meetings = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println("minRooms: " + minRooms(meetings));
    }
}
