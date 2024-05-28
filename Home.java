import javax.swing.*; // Importing Swing components for GUI
import java.awt.*; // Importing AWT components for GUI
import java.awt.event.ActionEvent; // Importing ActionEvent for event handling
import java.awt.event.ActionListener; // Importing ActionListener for event handling

public class Home extends JFrame implements ActionListener {

    private JButton editProductsButton, cashierButton, salesReportButton, exitButton; // Buttons for different actions
    private JLabel welcomeLabel; // Label to display welcome text

    public Home() {
        setTitle("Home"); // Set the title of the window
        setSize(800, 600); // Set the size of the window
        getContentPane().setBackground(Color.decode("#A7D7C5")); // Set the background color of the content pane
        setResizable(false); // Disable window resizing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
        setLocationRelativeTo(null); // Center the window on the screen

        JPanel panel = new JPanel(null); // Create a panel with null layout
        panel.setSize(455, 455); // Set the size of the panel
        panel.setLocation(172, 72); // Set the location of the panel within the window
        panel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32)); // Set an empty border around the panel
        panel.setOpaque(false); // Make the panel transparent

        welcomeLabel = new JLabel("E.C.S.E."); // Create a label with text "E.C.S.E."
        welcomeLabel.setBounds(275, 81, 250, 47); // Set the bounds of the label
        welcomeLabel.setFont(new Font("Karla", Font.BOLD, 48)); // Set the font of the label text
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the text within the label
        panel.add(welcomeLabel); // Add the label to the panel

        editProductsButton = new JButton("Edit Products"); // Create a button for editing products
        editProductsButton.setBounds(272, 180, 255, 59); // Set the bounds of the button
        editProductsButton.setBackground(Color.decode("#84C7AE")); // Set the background color of the button
        editProductsButton.setForeground(Color.BLACK); // Set the text color of the button
        editProductsButton.setFont(new Font("Karla", Font.BOLD, 15)); // Set the font of the button text
        editProductsButton.setBorder(BorderFactory.createEmptyBorder()); // Remove the button border
        editProductsButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change the cursor to a hand when hovering over the button
        editProductsButton.setFocusPainted(false); // Remove the focus paint from the button
        editProductsButton.addActionListener(this); // Add an action listener to the button
        panel.add(editProductsButton); // Add the button to the panel

        cashierButton = new JButton("Cashier"); // Create a button for the cashier
        cashierButton.setBounds(272, 265, 255, 59); // Set the bounds of the button
        cashierButton.setBackground(Color.decode("#84C7AE")); // Set the background color of the button
        cashierButton.setForeground(Color.BLACK); // Set the text color of the button
        cashierButton.setFont(new Font("Karla", Font.BOLD, 15)); // Set the font of the button text
        cashierButton.setBorder(BorderFactory.createEmptyBorder()); // Remove the button border
        cashierButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change the cursor to a hand when hovering over the button
        cashierButton.setFocusPainted(false); // Remove the focus paint from the button
        cashierButton.addActionListener(this); // Add an action listener to the button
        panel.add(cashierButton); // Add the button to the panel

        salesReportButton = new JButton("Sales Report"); // Create a button for the sales report
        salesReportButton.setBounds(272, 360, 255, 59); // Set the bounds of the button
        salesReportButton.setBackground(Color.decode("#84C7AE")); // Set the background color of the button
        salesReportButton.setForeground(Color.BLACK); // Set the text color of the button
        salesReportButton.setFont(new Font("Karla", Font.BOLD, 15)); // Set the font of the button text
        salesReportButton.setBorder(BorderFactory.createEmptyBorder()); // Remove the button border
        salesReportButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change the cursor to a hand when hovering over the button
        salesReportButton.setFocusPainted(false); // Remove the focus paint from the button
        salesReportButton.addActionListener(this); // Add an action listener to the button
        panel.add(salesReportButton); // Add the button to the panel

        exitButton = new JButton("Exit"); // Create a button for exiting the application
        exitButton.setBounds(272, 455, 255, 59); // Set the bounds of the button
        exitButton.setBackground(Color.decode("#84C7AE")); // Set the background color of the button
        exitButton.setForeground(Color.BLACK); // Set the text color of the button
        exitButton.setFont(new Font("Karla", Font.BOLD, 15)); // Set the font of the button text
        exitButton.setBorder(BorderFactory.createEmptyBorder()); // Remove the button border
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change the cursor to a hand when hovering over the button
        exitButton.setFocusPainted(false); // Remove the focus paint from the button
        exitButton.addActionListener(this); // Add an action listener to the button
        panel.add(exitButton); // Add the button to the panel

        add(panel); // Add the panel to the frame

        setVisible(true); // Make the frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editProductsButton) { // If the edit products button is clicked
            new EditProducts(); // Open the EditProducts window
        } else if (e.getSource() == cashierButton) { // If the cashier button is clicked
            new Cashier(); // Open the Cashier window
        } else if (e.getSource() == salesReportButton) { // If the sales report button is clicked
            new SalesReport(); // Open the SalesReport window
        } else if (e.getSource() == exitButton) { // If the exit button is clicked
            System.exit(0); // Exit the application
        }
    }

    public static void main(String[] args) {
        new Home(); // Create and show the Home window
    }
}
