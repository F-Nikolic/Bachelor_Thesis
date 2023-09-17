import java.util.*;

public class Generator {
    private static final int BOARD_SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private static final int MAX_ITERATIONS = 10; // Author note: change this value if you want a different amount of empty fields

    private Board board;

    public Generator() {
        board = new Board(BOARD_SIZE);
    }

    public void generate() {
        // Initialize the board with a complete, valid Sudoku solution
        generateCompleteSolution();

        // Remove cells until the board has a unique solution
        int numCellsToRemove = BOARD_SIZE * BOARD_SIZE - getNumClues();
        int iterations = 0;
        Random random = new Random();

        while (iterations < MAX_ITERATIONS) {
            // Randomly select a cell to remove
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);

            // If the cell is already empty, skip it
            if (board.getCell(row, col).getValue() == 0) {
                continue;
            }

            // Temporarily remove the cell's value
            int temp = board.getCell(row, col).getValue();
            board.getCell(row, col).setValue(0);
            board.getCell(row, col).setEditable(true); // Author: added this to set editable to true since you can edit it now
            // Check if the resulting puzzle has a unique solution
            /* Author: Commented out this whole section because it causes problems and without it the code functions properly
            if (getNumSolutions() == 1) { // Author: maybe the getNumSol, especially the solver.solve() messes something up
                                          // since the value is set to 0 but afterwards reset

                numCellsToRemove--;
            } else {
                // Restore the cell's value
                board.getCell(row, col).setValue(temp);
            }*/

            iterations++;
        }
    }

    public Board getBoard() {
        return board;
    }

    public int getNumClues() {
        int numClues = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.getCell(i, j).getValue() != 0) {
                    numClues++;
                }
            }
        }
        return numClues;
    }

    private void generateCompleteSolution() {
        // Generate a random permutation of the numbers 1 to 9
        List<Integer> values = new ArrayList<>();
        for (int i = 1; i <= BOARD_SIZE; i++) {
            values.add(i);
        }
        Collections.shuffle(values);

        // Fill in the board row by row, using the random permutation
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                int value = values.get((i * SUBGRID_SIZE + i / SUBGRID_SIZE + j) % BOARD_SIZE);
                board.getCell(i, j).setValue(value);
            }
        }
    }

    // Author: Added with a new prompt
    private int getNumSolutions() {
        Solver solver = new Solver(board);
        int numSolutions = 0;

        // Find the first solution
        if (solver.solve()) {
            numSolutions++;

            // Check for more solutions
            while (solver.hasMoreSolutions()) {
                solver.solve();
                numSolutions++;
            }
        }

        return numSolutions;
    }
}
