/**
 * Sudoku solver using backtracking.
 *
 * Find an empty cell, try the digits 1 to 9, and keep a digit only if it breaks
 * no rule. If a later cell gets stuck, undo the digit and try the next one.
 *
 * Worst case is exponential, but constraint checking prunes most branches,
 * so typical puzzles solve in milliseconds.
 */
public class SudokuSolver {

    static boolean solve(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {                    // 0 means empty
                    for (int digit = 1; digit <= 9; digit++) {
                        if (isSafe(board, row, col, digit)) {
                            board[row][col] = digit;           // choose
                            if (solve(board)) {
                                return true;
                            }
                            board[row][col] = 0;               // undo
                        }
                    }
                    return false;                              // no digit fits: backtrack
                }
            }
        }
        return true;                                           // no empty cells left: solved
    }

    private static boolean isSafe(int[][] board, int row, int col, int digit) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == digit || board[i][col] == digit) {
                return false;
            }
        }
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                if (board[r][c] == digit) {
                    return false;
                }
            }
        }
        return true;
    }

    static void print(int[][] board) {
        for (int r = 0; r < 9; r++) {
            if (r % 3 == 0 && r != 0) {
                System.out.println("------+-------+------");
            }
            StringBuilder line = new StringBuilder();
            for (int c = 0; c < 9; c++) {
                if (c % 3 == 0 && c != 0) {
                    line.append("| ");
                }
                line.append(board[r][c]).append(' ');
            }
            System.out.println(line.toString().trim());
        }
    }

    public static void main(String[] args) {
        int[][] board = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        if (solve(board)) {
            System.out.println("Solved:");
            print(board);
        } else {
            System.out.println("No solution");
        }
    }
}
