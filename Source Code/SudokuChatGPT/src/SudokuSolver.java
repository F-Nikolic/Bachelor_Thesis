public class SudokuSolver {
    public static boolean solve(SudokuBoard board) {
        // Find an empty cell
        int row = -1;
        int col = -1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getValue(i, j) == 0) {
                    row = i;
                    col = j;
                    break;
                }
            }
            if (row != -1) {
                break;
            }
        }

        // If there are no empty cells, the puzzle is solved
        if (row == -1) {
            return true;
        }

        // Try each value from 1 to 9 in the empty cell
        for (int value = 1; value <= 9; value++) {
            if (isValidMove(board, row, col, value)) {
                board.setValue(row, col, value);
                if (solve(board)) {
                    return true;
                }
                board.setValue(row, col, 0);
            }
        }

        // If no value works, backtrack
        return false;
    }

    private static boolean isValidMove(SudokuBoard board, int row, int col, int value) {
        // Check row and column
        for (int i = 0; i < 9; i++) {
            if (board.getValue(row, i) == value || board.getValue(i, col) == value) {
                return false;
            }
        }

        // Check 3x3 box
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board.getValue(i, j) == value) {
                    return false;
                }
            }
        }

        return true;
    }
}

