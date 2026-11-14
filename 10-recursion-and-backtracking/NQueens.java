import java.util.ArrayList;
import java.util.List;

/**
 * N-Queens: place n queens on an n x n chessboard so that no two queens attack
 * each other (no shared row, column or diagonal).
 *
 * We place one queen per row, trying each column, and backtrack when a
 * placement leaves no safe spot in a later row.
 *
 * Time: roughly O(n!) in the worst case, though pruning cuts it down a lot.
 * Space: O(n) for the board state.
 */
public class NQueens {

    static List<List<String>> solveNQueens(int n) {
        List<List<String>> solutions = new ArrayList<>();
        int[] queenColumn = new int[n];                // queenColumn[row] = column of that row's queen
        boolean[] columns = new boolean[n];            // column already has a queen?
        boolean[] diagonals1 = new boolean[2 * n];     // "/" diagonals: row + col is constant
        boolean[] diagonals2 = new boolean[2 * n];     // "\" diagonals: row - col + n is constant
        place(0, n, queenColumn, columns, diagonals1, diagonals2, solutions);
        return solutions;
    }

    private static void place(int row, int n, int[] queenColumn, boolean[] columns,
                              boolean[] diagonals1, boolean[] diagonals2,
                              List<List<String>> solutions) {
        if (row == n) {
            solutions.add(draw(queenColumn, n));
            return;
        }
        for (int col = 0; col < n; col++) {
            int d1 = row + col;
            int d2 = row - col + n;
            if (columns[col] || diagonals1[d1] || diagonals2[d2]) {
                continue;                              // attacked: skip (pruning)
            }
            queenColumn[row] = col;                    // choose
            columns[col] = diagonals1[d1] = diagonals2[d2] = true;
            place(row + 1, n, queenColumn, columns, diagonals1, diagonals2, solutions);
            columns[col] = diagonals1[d1] = diagonals2[d2] = false;   // undo
        }
    }

    private static List<String> draw(int[] queenColumn, int n) {
        List<String> board = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            StringBuilder line = new StringBuilder();
            for (int col = 0; col < n; col++) {
                line.append(queenColumn[row] == col ? 'Q' : '.');
            }
            board.add(line.toString());
        }
        return board;
    }

    public static void main(String[] args) {
        List<List<String>> solutions = solveNQueens(4);
        System.out.println("4-Queens has " + solutions.size() + " solutions:");
        for (List<String> board : solutions) {
            for (String line : board) {
                System.out.println(line);
            }
            System.out.println();
        }
        for (int n = 1; n <= 8; n++) {
            System.out.println(n + "-Queens: " + solveNQueens(n).size() + " solutions");
        }
    }
}
