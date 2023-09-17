import javax.swing.*;
import java.awt.*;

public class SudokuGUI {
    private SudokuBoard board;
    private JFrame frame;
    private JPanel panel;
    private JTextField[][] textFields;

    public SudokuGUI(SudokuBoard board) {
        this.board = board;

        // Create the frame
        frame = new JFrame("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);

        // Create the panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(9, 9));
        frame.getContentPane().add(panel);

        // Create the text fields
        textFields = new JTextField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textFields[i][j] = new JTextField(1);
                textFields[i][j].setHorizontalAlignment(JTextField.CENTER);
                textFields[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                panel.add(textFields[i][j]);
            }
        }

        /* Author: This code is for some reason not used and since we don't want to mess with complex prompts in this version we just ignore it as long as the rest compiles (It compiles uncommented too)
        // Add a button to check the solution
        JButton checkButton = new JButton("Check Solution");
        checkButton.addActionListener(e -> checkSolution());
        panel.add(checkButton);

        // Add a button to reset the game
        JButton resetButton = new JButton("Reset Game");
        resetButton.addActionListener(e -> resetGame());
        panel.add(resetButton);
        /*
         */

        // Display the frame
        frame.setVisible(true);
    }

    //Author: Some empty method that serves no purpose and is only used in the commented code above
    /*
    private void checkSolution() {
        // TODO: Implement the logic to check the solution <- Author: Note how it does not give us the implementation and only a to do, this case is handled in version two and the case that led to most bugs and problems
    }

    // Author: Another unused method that would have been used by the same code like in "checkSolution()" but since this functionality is not provided it is essentially useless here
    private void resetGame() {
        // Clear the text fields and the board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textFields[i][j].setText("");
                board.setValue(i, j, 0);
            }
        }
    }
     */
}

