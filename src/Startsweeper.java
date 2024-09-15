import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Startsweeper {
    JFrame frame = new JFrame("Startsweeper");
    JLabel textLabel = new JLabel();
    JLabel subTextlabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel centerPanel = new JPanel(); // Panel to center textPanel
    JPanel radioPanel = new JPanel(); // Panel for radio buttons
    JPanel buttonPanel = new JPanel(); // Panel for the start button
    ButtonGroup buttonGroup = new ButtonGroup(); // Group for radio buttons
    JRadioButton easyButton = new JRadioButton("Easy");
    JRadioButton mediumButton = new JRadioButton("Medium");
    JRadioButton hardButton = new JRadioButton("Hard");
    JButton startButton = new JButton("Start Game");
    int boardWidth = 500;
    int boardHeight = 500;
    int difficulty = 8;

    public Startsweeper() {
        // Frame setup
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title label
        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Hugosweeper");
        textLabel.setOpaque(true);

        // Subtitle label
        subTextlabel.setFont(new Font("Arial", Font.BOLD, 18));
        subTextlabel.setHorizontalAlignment(JLabel.CENTER);
        subTextlabel.setText("Choose difficulty:");
        subTextlabel.setOpaque(true);

        // Set layout for textPanel to BoxLayout for vertical stacking
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(textLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Adds vertical spacing
        textPanel.add(subTextlabel);

        // Create and configure radio buttons
        easyButton.setActionCommand("Easy");
        mediumButton.setActionCommand("Medium");
        hardButton.setActionCommand("Hard");
        
        // Set preferred size for radio buttons
        Dimension buttonSize = new Dimension(150, 30); // Width, Height
        easyButton.setPreferredSize(buttonSize);
        mediumButton.setPreferredSize(buttonSize);
        hardButton.setPreferredSize(buttonSize);

        // Add radio buttons to button group
        buttonGroup.add(easyButton);
        buttonGroup.add(mediumButton);
        buttonGroup.add(hardButton);

        // Set layout for radioPanel
        radioPanel.setLayout(new GridLayout(3, 1, 5, 5)); // 3 rows, 1 column, 5px gaps
        radioPanel.add(easyButton);
        radioPanel.add(mediumButton);
        radioPanel.add(hardButton);

        // Configure and add the start button
        startButton.setPreferredSize(new Dimension(150, 30)); // Width, Height

        easyButton.setSelected(true);

        // Add ActionListeners to radio buttons
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Easy difficulty selected");
                difficulty = 5;
            }
        });

        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Medium difficulty selected");
                difficulty = 8;
            }
        });

        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hard difficulty selected");
                difficulty = 12;
            }
        });

        // Add ActionListener to start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start Game button clicked");

                // Instantiate Hugosweep with the current difficulty
                SwingUtilities.invokeLater(() -> new Hugosweeper(difficulty));

                // Close the Startsweeper frame
                frame.dispose();
            }
        });

        // Set layout for buttonPanel and add start button
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(startButton);

        // Add radioPanel and buttonPanel to textPanel
        textPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Adds vertical spacing before radio buttons
        textPanel.add(radioPanel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Adds vertical spacing before start button
        textPanel.add(buttonPanel);

        // Center textPanel horizontally within centerPanel
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(textPanel);

        // Add centerPanel to the center of the frame
        frame.add(centerPanel, BorderLayout.CENTER);

        // Make frame visible after setting up all components
        frame.setVisible(true);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Startsweeper());
    }
}
