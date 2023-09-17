public class SudokuValidator {
    public boolean isValidMove(SudokuBoard board, int row, int col, int value) {
        // Check if the value is already in the same row
        for (int i = 0; i < 9; i++) {
            if (board.getValue(row, i) == value) {
                return false;
            }
        }

        // Check if the value is already in the same column
        for (int i = 0; i < 9; i++) {
            if (board.getValue(i, col) == value) {
                return false;
            }
        }

        // Check if the value is already in the same 3x3 sub-grid
        int subgridRow = (row / 3) * 3;
        int subgridCol = (col / 3) * 3;
        for (int i = subgridRow; i < subgridRow + 3; i++) {
            for (int j = subgridCol; j < subgridCol + 3; j++) {
                if (board.getValue(i, j) == value) {
                    return false;
                }
            }
        }

        // If the value is not already in the same row, column, or sub-grid, the move is valid
        return true;
    }
}
