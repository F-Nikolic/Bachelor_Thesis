import java.util.Random; //Author: Had to import this since I only asked for the generateBoard method

public class SudokuBoard {
    private int BOARD_SIZE; //Author: Had to provide this myself
    private int[][] board;

    public SudokuBoard(int boardSize) {
        this.BOARD_SIZE = boardSize; //Author: Changed this.board = [9][9] to the current line to make it work
                                     //with the generateBoard method that was newly introduced since it was missing
    }

    public int getValue(int row, int col) {
        return board[row][col];
    }

    public void setValue(int row, int col, int value) {
        board[row][col] = value;
    }

    public boolean isFilled(int row, int col) {
        return board[row][col] != 0;
    }

    public void generateBoard() {
        // Start with an empty board
        board = new int[BOARD_SIZE][BOARD_SIZE];
        // Fill in the diagonal boxes
        fillDiagonalBoxes();
        // Fill in the remaining cells
        fillRemaining(0, 3);
    }

    private void fillDiagonalBoxes() {
        for (int box = 0; box < BOARD_SIZE; box += 3) {
            int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9};
            shuffleArray(values);
            int index = 0;
            for (int row = box; row < box + 3; row++) {
                for (int col = box; col < box + 3; col++) {
                    board[row][col] = values[index++];
                }
            }
        }
    }

    private boolean fillRemaining(int row, int col) {
        if (col >= BOARD_SIZE && row < BOARD_SIZE - 1) {
            row++;
            col = 0;
        }
        if (row >= BOARD_SIZE && col >= BOARD_SIZE) {
            return true;
        }
        if (row < 3) {
            if (col < 3) {
                col = 3;
            }
        } else if (row < 6) {
            if (col == (int) (row / 3) * 3) {
                col += 3;
            }
        } else {
            if (col == 6) {
                row++;
                col = 0;
                if (row >= BOARD_SIZE) {
                    return true;
                }
            }
        }
        for (int num = 1; num <= BOARD_SIZE; num++) {
            if (isValid(row, col, num)) {
                board[row][col] = num;
                if (fillRemaining(row, col + 1)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }
        return false;
    }

    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private void shuffleArray(int[] arr) {
        Random rand = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

}
