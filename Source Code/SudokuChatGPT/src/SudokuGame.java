import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SudokuGame {
    private static final int BOARD_SIZE = 9;
    private static final int SUBSECTION_SIZE = 3;
    private static final int CELL_SIZE = 60;
    private static final int BOARD_PADDING = 20;
    private static final int BOARD_WIDTH = BOARD_PADDING * 2 + CELL_SIZE * BOARD_SIZE;
    private static final int BOARD_HEIGHT = BOARD_PADDING * 2 + CELL_SIZE * BOARD_SIZE;
    private static final Color BOARD_COLOR = Color.BLACK;
    private static final Color CELL_COLOR = Color.WHITE;
    private static final Color FIXED_CELL_COLOR = Color.LIGHT_GRAY;
    private static final Color ERROR_COLOR = Color.RED;
    private static final Font FONT = new Font("Arial", Font.BOLD, 24);

    private JFrame frame;
    private JPanel boardPanel;
    private JTextField[][] cells;
    private SudokuBoard board;

    public static void main(String[] args) {
        SudokuGame game = new SudokuGame();
        game.createBoard();
    }

    private void createBoard() {
        board = new SudokuBoard(9);
        board.generateBoard();

        frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        boardPanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        boardPanel.setBackground(BOARD_COLOR);
        cells = new JTextField[BOARD_SIZE][BOARD_SIZE];
        CellHandler cellHandler = new CellHandler();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(FONT);
                cell.setBackground(CELL_COLOR);
                cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                cell.addMouseListener(cellHandler);
                boardPanel.add(cell);
                cells[row][col] = cell;
                int value = board.getValue(row, col);
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false);
                    cell.setBackground(FIXED_CELL_COLOR);
                }
            }
        }

        frame.getContentPane().add(boardPanel, BorderLayout.CENTER);

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (solve()) {
                    JOptionPane.showMessageDialog(frame, "Puzzle solved!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Unable to solve puzzle.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(solveButton);

        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private boolean solve() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                String text = cells[row][col].getText();
                if (text.isEmpty()) {
                    board.setValue(row, col, 0);
                } else {
                    try {
                        int value = Integer.parseInt(text);
                        if (value < 1 || value > 9) {
                            cells[row][col].setBackground(ERROR_COLOR);
                            return false;
                        }
                        board.setValue(row, col, value);
                    } catch (NumberFormatException ex) {
                        cells[row][col].setBackground(ERROR_COLOR);
                        return false;
                    }
                }
            }
        }

        SudokuSolver solver = new SudokuSolver(); //Author: Here board was passed as an argument but I had to take it away since no argument was expected
        if (solver.solve(board)) { //Author: Had to pass board as an argument here since it is required in SudokuSolver
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    cells[row][col].setText(String.valueOf(board.getValue(row, col)));
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private class CellHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            JTextField cell = (JTextField) e.getSource();
            cell.setBackground(CELL_COLOR);
        }
    }
}
