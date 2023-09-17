import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cell {
    private int value;
    private boolean isEditable;
    // Author: I added these two below
    private int row;
    private int col;

    public Cell(int value, boolean isEditable, int i, int j) { // Author: quick add of i and j
        this.value = value;
        this.isEditable = isEditable;
        // Author: Had to add these two below
        this.row = i;
        this.col = j;
    }

    // Author: new
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    // Author: added through new prompt
    public List<Integer> getPossibleValues(Board board) { // Author: had to add board as a parameter
        List<Integer> possibleValues = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            if (board.isValuePossible(getRow(), getCol(), i)) {
                possibleValues.add(i);
            }
        }
        return possibleValues;
    }
}
