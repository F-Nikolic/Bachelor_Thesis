public class Solver {
    private Board board;

    public Solver(Board board) {
        this.board = board;
    }

    public boolean solve() {
        int row = -1;
        int col = -1;
        boolean isEmpty = false; // Author: Had to change this to false

        // Find the first empty cell on the board
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getCell(i, j).getValue() == 0) {
                    row = i;
                    col = j;
                    isEmpty = true; // Author: Had to change this to true
                    break;
                }
            }
            if (isEmpty) {
                break;
            }
        }

        // If the board is already solved, return true
        if (isEmpty) {
            return false; // Author: Had to change this to false from true since it is not full yet
        } else { // Author: Added this here to return true when the board has no empty boxes anymore
            return true;
        }

        // Author: This implementation does not work so I commented it out
        /*/ Try each possible value for the empty cell
        for (int num = 1; num <= board.getSize(); num++) {
            if (isValid(row, col, num)) {
                board.getCell(row, col).setValue(num);

                // Recursively solve the board
                if (solve()) {
                    return true;
                }

                // Backtrack if a solution cannot be found
                board.getCell(row, col).setValue(0);
            }
        }*/

        // Return false if no solution is found
        //return false
    }

    private boolean isValid(int row, int col, int num) {
        // Check row for duplicates
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getCell(row, i).getValue() == num) {
                return false;
            }
        }

        // Check column for duplicates
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getCell(i, col).getValue() == num) {
                return false;
            }
        }

        // Check subgrid for duplicates
        int subgridRow = row - row % board.getSubgridSize();
        int subgridCol = col - col % board.getSubgridSize();
        for (int i = subgridRow; i < subgridRow + board.getSubgridSize(); i++) {
            for (int j = subgridCol; j < subgridCol + board.getSubgridSize(); j++) {
                if (board.getCell(i, j).getValue() == num) {
                    return false;
                }
            }
        }

        // Return true if no duplicates are found
        return true;
    }

    public boolean hasMoreSolutions() {
        // Create a copy of the board to work with
        Board copy = board; // Author: Was previously new Board(board); before I changed it

        // Find the next empty cell to start the search from
        Cell emptyCell = copy.findEmptyCell();
        if (emptyCell == null) {
            // The board is already filled, so there are no more solutions
            return false;
        }

        // Try each possible value for the empty cell and recursively solve the new board
        for (int value : emptyCell.getPossibleValues(copy)) {
            emptyCell.setValue(value);
            Solver solver = new Solver(copy);
            if (solver.solve()) {
                return true;
            }
        }

        // If none of the possible values for the empty cell result in a valid solution, there are no more solutions
        return false;
    }
}
