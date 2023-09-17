import java.util.HashSet; // Author: had to import this myself
import java.util.Set; // Author: had to import this myself

public class Validator {
    private Board board;

    public Validator(Board board) {
        this.board = board;
    }

    public boolean isValid() {
        // Check rows and columns for duplicates
        for (int i = 0; i < board.getSize(); i++) {
            if (!isValidRow(i) || !isValidColumn(i)) {
                return false;
            }
        }

        // Check subgrids for duplicates
        for (int i = 0; i < board.getSize(); i += board.getSubgridSize()) {
            for (int j = 0; j < board.getSize(); j += board.getSubgridSize()) {
                if (!isValidSubgrid(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isValidRow(int row) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < board.getSize(); i++) {
            int value = board.getCell(row, i).getValue();
            if (value == 0) {
                continue;
            }
            if (set.contains(value)) {
                return false;
            }
            set.add(value);
        }
        return true;
    }

    private boolean isValidColumn(int col) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < board.getSize(); i++) {
            int value = board.getCell(i, col).getValue();
            if (value == 0) {
                continue;
            }
            if (set.contains(value)) {
                return false;
            }
            set.add(value);
        }
        return true;
    }

    private boolean isValidSubgrid(int row, int col) {
        Set<Integer> set = new HashSet<>();
        for (int i = row; i < row + board.getSubgridSize(); i++) {
            for (int j = col; j < col + board.getSubgridSize(); j++) {
                int value = board.getCell(i, j).getValue();
                if (value == 0) {
                    continue;
                }
                if (set.contains(value)) {
                    return false;
                }
                set.add(value);
            }
        }
        return true;
    }
}
