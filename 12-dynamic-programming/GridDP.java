/**
 * DP on grids: dp[r][c] depends on the cells above and to the left.
 * Moves are restricted to right and down, which makes the subproblems clean.
 *
 * R = rows, C = columns. All of these are O(R * C) time.
 */
public class GridDP {

    // Unique paths from the top-left to the bottom-right moving right or down.
    // paths(r, c) = paths(r-1, c) + paths(r, c-1). O(R * C) time, O(C) space (one row).
    static int uniquePaths(int rows, int cols) {
        int[] dp = new int[cols];
        java.util.Arrays.fill(dp, 1);                   // first row: one way to reach each cell
        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                dp[c] += dp[c - 1];                     // from above (old dp[c]) + from the left
            }
        }
        return dp[cols - 1];
    }

    // Unique paths with obstacles (1 = blocked). O(R * C) time, O(C) space.
    static int uniquePathsWithObstacles(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[] dp = new int[cols];
        dp[0] = grid[0][0] == 1 ? 0 : 1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1) {
                    dp[c] = 0;                          // blocked: no paths through here
                } else if (c > 0) {
                    dp[c] += dp[c - 1];
                }
            }
        }
        return dp[cols - 1];
    }

    // Minimum path sum from the top-left to the bottom-right (right/down moves).
    // O(R * C) time, O(R * C) space.
    static int minPathSum(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] dp = new int[rows][cols];
        dp[0][0] = grid[0][0];
        for (int c = 1; c < cols; c++) {
            dp[0][c] = dp[0][c - 1] + grid[0][c];
        }
        for (int r = 1; r < rows; r++) {
            dp[r][0] = dp[r - 1][0] + grid[r][0];
        }
        for (int r = 1; r < rows; r++) {
            for (int c = 1; c < cols; c++) {
                dp[r][c] = grid[r][c] + Math.min(dp[r - 1][c], dp[r][c - 1]);
            }
        }
        return dp[rows - 1][cols - 1];
    }

    // Largest square of 1s in a binary grid (returns the AREA).
    // dp[r][c] = side length of the largest square whose bottom-right corner is (r, c).
    static int maximalSquare(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] dp = new int[rows][cols];
        int best = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    if (r == 0 || c == 0) {
                        dp[r][c] = 1;
                    } else {
                        dp[r][c] = 1 + Math.min(dp[r - 1][c - 1],
                                Math.min(dp[r - 1][c], dp[r][c - 1]));
                    }
                    best = Math.max(best, dp[r][c]);
                }
            }
        }
        return best * best;
    }

    public static void main(String[] args) {
        System.out.println("uniquePaths(3, 7): " + uniquePaths(3, 7));
        System.out.println("uniquePaths(3, 3): " + uniquePaths(3, 3));

        int[][] blocked = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        System.out.println("uniquePathsWithObstacles: " + uniquePathsWithObstacles(blocked));

        int[][] costs = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };
        System.out.println("minPathSum: " + minPathSum(costs));

        char[][] ones = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };
        System.out.println("maximalSquare area: " + maximalSquare(ones));
    }
}
