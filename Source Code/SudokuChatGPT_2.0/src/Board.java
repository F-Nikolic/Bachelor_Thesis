public class Board {
    private int size;
    private Cell[][] cells;

    public Board(int size) {
        this.size = size;
        cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(0, false, i, j); //Author: quick add of i and j and changed isEditable to false
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    // Author: had to prompt the AI to generate this, since this was called but no where to be found
    // Author: first prompt was wrong, had to scold it to give me the right method ;)
    public int getSubgridSize() {
        return (int) Math.sqrt(cells.length);
    }

    public void setCell(int row, int col, Cell cell) {
        cells[row][col] = cell;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(0, false, i, j);
            }
        }
    }

    public boolean isComplete() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (cells[row][col].getValue() == 0 || !isValid(row, col)) { // Author: had to call .getValue() instead of only cells[][]
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValid(int row, int col) {
        return isValidRow(row) && isValidColumn(col) && isValidSubGrid(row, col);
    }

    private boolean isValidRow(int row) {
        boolean[] used = new boolean[size];
        for (int col = 0; col < size; col++) {
            int value = cells[row][col].getValue(); // Author: had to call .getValue() instead of only cells[][]
            if (value != 0) {
                if (used[value - 1]) {
                    return false;
                } else {
                    used[value - 1] = true;
                }
            }
        }
        return true;
    }

    private boolean isValidColumn(int col) {
        boolean[] used = new boolean[size];
        for (int row = 0; row < size; row++) {
            int value = cells[row][col].getValue(); // Author: had to call .getValue() instead of only cells[][]
            if (value != 0) {
                if (used[value - 1]) {
                    return false;
                } else {
                    used[value - 1] = true;
                }
            }
        }
        return true;
    }

    private boolean isValidSubGrid(int row, int col) {
        int subGridSize = (int) Math.sqrt(size);
        int subGridRow = (row / subGridSize) * subGridSize;
        int subGridColumn = (col / subGridSize) * subGridSize;
        boolean[] used = new boolean[size];
        for (int i = 0; i < subGridSize; i++) {
            for (int j = 0; j < subGridSize; j++) {
                int value = cells[subGridRow + i][subGridColumn + j].getValue(); // Author: had to call .getValue() instead of only cells[][]
                if (value != 0) {
                    if (used[value - 1]) {
                        return false;
                    } else {
                        used[value - 1] = true;
                    }
                }
            }
        }
        return true;
    }

    // Author: added through new prompt
    public Cell findEmptyCell() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cells[i][j].getValue() == 0) {
                    return cells[i][j];
                }
            }
        }
        return null;
    }

    // Author: Added through new prompt
    public boolean isValuePossible(int row, int col, int value) {
        // Check row for duplicates
        for (int j = 0; j < 9; j++) {
            if (cells[row][j].getValue() == value) {
                return false;
            }
        }

        // Check column for duplicates
        for (int i = 0; i < 9; i++) {
            if (cells[i][col].getValue() == value) {
                return false;
            }
        }

        // Check subgrid for duplicates
        // Author: I changed subgridSize to getSubgridSize()
        int subgridRow = row - row % getSubgridSize();
        int subgridCol = col - col % getSubgridSize();
        for (int i = subgridRow; i < subgridRow + getSubgridSize(); i++) {
            for (int j = subgridCol; j < subgridCol + getSubgridSize(); j++) {
                if (cells[i][j].getValue() == value) {
                    return false;
                }
            }
        }

        return true;
    }


}

