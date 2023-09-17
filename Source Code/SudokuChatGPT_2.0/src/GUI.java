import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame {

    private Board board;
    private JPanel boardPanel;
    private JButton newGameButton;
    private int incorrectMoves;
    private JLabel triesLabel;

    private static final int MAX_INCORRECT_MOVES = 3;

    public GUI() {
        super("Sudoku");

        // Create the board and generator objects
        Generator generator = new Generator();

        // Generate a new Sudoku puzzle
        generator.generate();
        board = generator.getBoard();

        // Create the board panel and add it to the frame
        boardPanel = new JPanel(new GridLayout(board.getSize(), board.getSize()));
        add(boardPanel);

        // Initialize the incorrect moves counter
        incorrectMoves = 0;

        // Add each cell to the board panel
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Cell cell = board.getCell(i, j);
                JTextField textField = new JTextField();
                textField.setFont(new Font("Arial", Font.PLAIN, 20));
                textField.setHorizontalAlignment(JTextField.CENTER);

                // Disable the text field for cells that are pre-filled
                // Author: Had to change this from cell.isValid to cell.isEditable
                if (!cell.isEditable()) {
                    textField.setText(Integer.toString(cell.getValue()));
                    textField.setEditable(false);
                    textField.setBackground(Color.LIGHT_GRAY); // Author: Changed color from red to grey
                }

                // Add an action listener to the text field to validate the input
                textField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String input = textField.getText();
                        int value = 0;

                        // Validate the input
                        try {
                            value = Integer.parseInt(input);
                            if (value < 1 || value > board.getSize()) {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(GUI.this, "Please enter a valid number between 1 and " + board.getSize() + ".");
                            textField.setText("");
                            return;
                        }

                        // Set the value of the cell and validate the board
                        cell.setValue(value);
                        Validator validator = new Validator(board);
                        if (!validator.isValid()) {
                            JOptionPane.showMessageDialog(GUI.this, "Invalid move. Please try again.");
                            textField.setText("");
                            cell.setValue(0);
                            incorrectMoves++;
                            updateTriesLabel();
                            if (incorrectMoves >= MAX_INCORRECT_MOVES) {
                                int option = JOptionPane.showConfirmDialog(GUI.this, "You lost the game. Do you want to start a new game?", "Game Over", JOptionPane.YES_NO_OPTION);
                                if (option == JOptionPane.YES_OPTION) {
                                    newGame();
                                } else {
                                    // Exit the game
                                    System.exit(0);
                                }
                            }
                            return;
                        } else {
                            textField.setBackground(Color.CYAN); // Author: Changed color from green to cyan
                        }

                        // Check if the board is solved
                        Solver solver = new Solver(board);
                        if (solver.solve()) {
                            int option = JOptionPane.showConfirmDialog(GUI.this, "Congratulations! You solved the puzzle. Do you want to start a new game?", "Sudoku", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.YES_OPTION) {
                                newGame();
                            } else {
                                // Exit the game
                                System.exit(0);
                            }
                        }
                    }
                });

                boardPanel.add(textField);
            }
        }

        // Add a new game button and tries label to the frame
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(GUI.this, "Are you sure you want to start a new game? Any progress in the current game will be lost.", "New Game", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    newGame();
                }
            }
        });
        add(newGameButton, BorderLayout.SOUTH);

        triesLabel = new JLabel("Tries Left: " + (MAX_INCORRECT_MOVES - incorrectMoves));
        triesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(triesLabel, BorderLayout.NORTH);

        // Set the size of the frame and make it visible
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void newGame() {
        Generator generator = new Generator();
        generator.generate();
        board = generator.getBoard();
        incorrectMoves = 0;

        // Clear the board panel
        boardPanel.removeAll();

        // Add each cell to the board panel
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Cell cell = board.getCell(i, j);
                JTextField textField = new JTextField();
                textField.setFont(new Font("Arial", Font.PLAIN, 20));
                textField.setHorizontalAlignment(JTextField.CENTER);

                // Disable the text field for cells that are pre-filled
                if (!cell.isEditable()) {
                    textField.setText(Integer.toString(cell.getValue()));
                    textField.setEditable(false);
                    textField.setBackground(Color.LIGHT_GRAY); // changed color from red to grey
                }

                // Add an action listener to the text field to validate the input
                textField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String input = textField.getText();
                        int value = 0;

                        // Validate the input
                        try {
                            value = Integer.parseInt(input);
                            if (value < 1 || value > board.getSize()) {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(GUI.this, "Please enter a valid number between 1 and " + board.getSize() + ".");
                            textField.setText("");
                            return;
                        }

                        // Set the value of the cell and validate the board
                        cell.setValue(value);
                        Validator validator = new Validator(board);
                        if (!validator.isValid()) {
                            JOptionPane.showMessageDialog(GUI.this, "Invalid move. Please try again.");
                            textField.setText("");
                            cell.setValue(0);
                            incorrectMoves++;
                            updateTriesLabel();
                            if (incorrectMoves >= MAX_INCORRECT_MOVES) {
                                int option = JOptionPane.showConfirmDialog(GUI.this, "You lost the game. Do you want to start a new game?", "Game Over", JOptionPane.YES_NO_OPTION);
                                if (option == JOptionPane.YES_OPTION) {
                                    newGame();
                                } else {
                                    // Exit the game
                                    System.exit(0);
                                }
                            }
                            return;
                        } else {
                            textField.setBackground(Color.CYAN); // Author: changed color from green to cyan
                        }

                        // Check if the board is solved
                        Solver solver = new Solver(board);
                        if (solver.solve()) {
                            int option = JOptionPane.showConfirmDialog(GUI.this, "Congratulations! You solved the puzzle. Do you want to start a new game?", "Sudoku", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.YES_OPTION) {
                                newGame();
                            } else {
                                // Exit the game
                                System.exit(0);
                            }
                        }
                    }
                });

                boardPanel.add(textField);
            }
        }

        // Reset the tries label
        updateTriesLabel();

        // Repaint the board panel
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void updateTriesLabel() {
        int triesLeft = MAX_INCORRECT_MOVES - incorrectMoves;
        triesLabel.setText("Tries Left: " + triesLeft);
    }

    public static void main(String[] args) {
        new GUI();
    }
}
