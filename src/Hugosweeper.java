import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Hugosweeper {
    JFrame frame = new JFrame("Hugosweeper");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    int tileSize = 80;
    int numRows;
    int numCols;
    int boardWidth = numCols * tileSize;
    int boardHeight = numRows * tileSize;
    int hugoCount;
    private int difficulty;

    private class HugoTile extends JButton {
        int r;
        int c;
        public HugoTile(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    

    HugoTile[][] board = new HugoTile[numRows][numCols];
    ArrayList<HugoTile> hugoList;
    Random random = new Random();

    int tilesClicked = 0;
    boolean gameOver = false;

    public Hugosweeper(int difficulty) {
        // Set difficulty
        this.difficulty = difficulty;
    
        // Set numRows and numCols based on the difficulty
        numRows = difficulty; // Example: difficulty represents the number of rows
        numCols = difficulty; // Example: difficulty represents the number of columns
    
        // Initialize board dimensions
        boardWidth = numCols * tileSize;
        boardHeight = numRows * tileSize;
    
        // Set hugoCount based on difficulty
        hugoCount = difficulty;
    
        // Initialize JFrame
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    
        // Initialize textLabel
        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Hugosweeper with " + hugoCount + " Hugos");
        textLabel.setOpaque(true);
    
        // Add textLabel to textPanel
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);
    
        // Initialize boardPanel
        boardPanel.setLayout(new GridLayout(numRows, numCols));
        frame.add(boardPanel);
    
        // Initialize the board and hugoList after numRows and numCols are set
        board = new HugoTile[numRows][numCols];
        hugoList = new ArrayList<HugoTile>();
        
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                HugoTile tile = new HugoTile(r, c);
                board[r][c] = tile;
                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 30));
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (gameOver) {
                            return;
                        }
                        HugoTile tile = (HugoTile) e.getSource();
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (tile.getText().isEmpty()) {
                                if (hugoList.contains(tile)) {
                                    revealHugos();
                                } else {
                                    checkHugo(tile.r, tile.c);
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (tile.getText().isEmpty() && tile.isEnabled()) {
                                tile.setText("bruh");
                            } else if (tile.getText().equals("bruh")) {
                                tile.setText("");
                            }
                        }
                    }
                });
                boardPanel.add(tile);
            }
        }
        
        // Set Hugos
        setHugos();
        
        // Make frame visible
        frame.setVisible(true);
    }
    

    void setHugos() {
        hugoList = new ArrayList<HugoTile>();

        int hugoLeft = hugoCount;
        while (hugoLeft > 0) {
            int r = random.nextInt(numRows);
            int c = random.nextInt(numCols);

            HugoTile tile = board[r][c];
            if (!hugoList.contains(tile)) {
                hugoList.add(tile);
                --hugoLeft;
            }
        }
    }

    void revealHugos() {
        for (int i = 0; i < hugoList.size(); i++) {
            HugoTile tile = hugoList.get(i);
            tile.setText("cum");
        }
        gameOver = true;
        textLabel.setText("Game Over :(");
    }

    void checkHugo(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
            return;
        }

        HugoTile tile = board[r][c];

        if(!tile.isEnabled()) {
            return;
        }
        tile.setEnabled(false);
        ++tilesClicked;

        int hugosFound = 0;

        hugosFound += countHugo(r-1, c-1);
        hugosFound += countHugo(r-1, c);
        hugosFound += countHugo(r-1, c+1);
        
        hugosFound += countHugo(r, c-1);
        hugosFound += countHugo(r, c+1);

        hugosFound += countHugo(r+1, c-1);
        hugosFound += countHugo(r+1, c);
        hugosFound += countHugo(r+1, c+1);

        if (hugosFound > 0) {
            tile.setText(Integer.toString(hugosFound));
        }
        else {
            tile.setText("");

            checkHugo(r-1, c-1);
            checkHugo(r-1, c);
            checkHugo(r-1, c+1);

            checkHugo(r, c-1);
            checkHugo(r, c+1);

            checkHugo(r+1, c-1);
            checkHugo(r+1, c);
            checkHugo(r+1, c+1);
        }

        if (tilesClicked >= numRows * numCols - hugoList.size()) {
            gameOver = true;
            textLabel.setText("You WIN!?!!!");
        }
    }

    int countHugo(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
            return 0;
        }
        if (hugoList.contains(board[r][c])) {
            return 1;
        }
        return 0;
    }
}
