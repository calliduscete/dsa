import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Backtracking: build a solution step by step, and UNDO ("backtrack") a step
 * when it leads to a dead end or after recording a full solution.
 *
 * The standard template:
 *
 *   void backtrack(state, choices) {
 *       if (state is a complete solution) { record it; return; }
 *       for (each choice available) {
 *           if (choice is valid) {
 *               make the choice
 *               backtrack(new state)
 *               undo the choice          <- the "backtrack"
 *           }
 *       }
 *   }
 *
 * Complexity is usually exponential or factorial because the number of
 * solutions grows that fast. Pruning (skipping hopeless branches) helps a lot.
 */
public class BacktrackingProblems {

    // Subsets (the power set): 2^n subsets.  O(n * 2^n) time, O(n) extra space
    static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        subsetsHelper(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private static void subsetsHelper(int[] nums, int start, List<Integer> current,
                                      List<List<Integer>> result) {
        result.add(new ArrayList<>(current));          // every path is a valid subset
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);                      // choose
            subsetsHelper(nums, i + 1, current, result);
            current.remove(current.size() - 1);        // undo
        }
    }

    // Permutations: n! orderings.  O(n * n!) time, O(n) extra space
    static List<List<Integer>> permutations(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permuteHelper(nums, new boolean[nums.length], new ArrayList<>(), result);
        return result;
    }

    private static void permuteHelper(int[] nums, boolean[] used, List<Integer> current,
                                      List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            used[i] = true;
            current.add(nums[i]);                      // choose
            permuteHelper(nums, used, current, result);
            current.remove(current.size() - 1);        // undo
            used[i] = false;
        }
    }

    // Combinations: choose k numbers from 1..n.  O(k * C(n, k)) time
    static List<List<Integer>> combinations(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        combineHelper(n, k, 1, new ArrayList<>(), result);
        return result;
    }

    private static void combineHelper(int n, int k, int start, List<Integer> current,
                                      List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        // pruning: stop early if there aren't enough numbers left to fill k slots
        for (int i = start; i <= n - (k - current.size()) + 1; i++) {
            current.add(i);
            combineHelper(n, k, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    // Combination sum: pick numbers (reuse allowed) that add up to target.
    static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);                       // sorting enables pruning
        sumHelper(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private static void sumHelper(int[] candidates, int remaining, int start,
                                  List<Integer> current, List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > remaining) {
                break;                                 // pruning: all later ones are bigger
            }
            current.add(candidates[i]);
            sumHelper(candidates, remaining - candidates[i], i, current, result);  // i, not i+1: reuse
            current.remove(current.size() - 1);
        }
    }

    // Generate all valid parentheses strings with n pairs: Catalan(n) results
    static List<String> generateParentheses(int n) {
        List<String> result = new ArrayList<>();
        parenHelper(n, 0, 0, new StringBuilder(), result);
        return result;
    }

    private static void parenHelper(int n, int open, int close, StringBuilder current,
                                    List<String> result) {
        if (current.length() == 2 * n) {
            result.add(current.toString());
            return;
        }
        if (open < n) {                                // can still add "("
            current.append('(');
            parenHelper(n, open + 1, close, current, result);
            current.deleteCharAt(current.length() - 1);
        }
        if (close < open) {                            // can add ")" only if one is open
            current.append(')');
            parenHelper(n, open, close + 1, current, result);
            current.deleteCharAt(current.length() - 1);
        }
    }

    // Word search in a grid: can the word be traced through adjacent cells
    // (up/down/left/right) without reusing a cell?
    // O(R * C * 4^L) worst case, where L is the word length. O(L) space.
    static boolean wordSearch(char[][] board, String word) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (searchFrom(board, word, 0, r, c)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean searchFrom(char[][] board, String word, int index, int r, int c) {
        if (index == word.length()) {
            return true;
        }
        if (r < 0 || c < 0 || r >= board.length || c >= board[0].length
                || board[r][c] != word.charAt(index)) {
            return false;
        }
        char saved = board[r][c];
        board[r][c] = '#';                             // choose: mark as used
        boolean found = searchFrom(board, word, index + 1, r + 1, c)
                || searchFrom(board, word, index + 1, r - 1, c)
                || searchFrom(board, word, index + 1, r, c + 1)
                || searchFrom(board, word, index + 1, r, c - 1);
        board[r][c] = saved;                           // undo
        return found;
    }

    public static void main(String[] args) {
        System.out.println("subsets of [1,2,3]: " + subsets(new int[]{1, 2, 3}));
        System.out.println("permutations of [1,2,3]: " + permutations(new int[]{1, 2, 3}));
        System.out.println("combinations(4, 2): " + combinations(4, 2));
        System.out.println("combinationSum([2,3,6,7], 7): "
                + combinationSum(new int[]{2, 3, 6, 7}, 7));
        System.out.println("generateParentheses(3): " + generateParentheses(3));

        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        System.out.println("wordSearch(ABCCED): " + wordSearch(board, "ABCCED"));
        System.out.println("wordSearch(SEE): " + wordSearch(board, "SEE"));
        System.out.println("wordSearch(ABCB): " + wordSearch(board, "ABCB"));
    }
}
