import javax.swing.*; // Importing Swing components for GUI
import java.awt.*; // Importing AWT components for GUI
import java.awt.event.ActionEvent; // Importing ActionEvent for event handling
import java.awt.event.ActionListener; // Importing ActionListener for event handling
import java.util.Map; // Importing Map interface for user database

public class Login extends JFrame implements ActionListener {

    private JTextField usernameField; // Text field for username input
    private JPasswordField passwordField; // Password field for password input
    private JButton loginButton; // Button for login action
    private Map<String, String> userDatabase; // Map to store user credentials

    public Login() {
        this.userDatabase = Signup.loadUserDatabase(); // Load user database from Signup class
        setTitle("Login"); // Set window title
        setSize(800, 600);  // Set window size to match Signup window
        getContentPane().setBackground(Color.decode("#A7D7C5"));  // Set background color
        setResizable(false); // Disable window resizing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        setLocationRelativeTo(null); // Center the window on the screen

        JPanel panel = new JPanel(null);  // Use null layout for custom positioning
        panel.setSize(455, 455); // Set panel size
        panel.setLocation(172, 72); // Set panel location
        panel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32)); // Set an empty border around the panel
        panel.setOpaque(false); // Make the panel transparent

        JLabel label = new JLabel("Welcome back!"); // Create label with text "Welcome back!"
        label.setBounds(225, 89, 350, 47);  // Adjust bounds for centering
        label.setFont(new Font("Karla", Font.BOLD, 48)); // Set font of the label
        label.setHorizontalAlignment(JLabel.CENTER);  // Set horizontal alignment to center
        panel.add(label); // Add the label to the panel

        JLabel labelUsername = new JLabel("Username:"); // Create label for username
        labelUsername.setBounds(212, 180, 76, 20); // Set bounds for the username label
        labelUsername.setFont(new Font("Karla", Font.PLAIN, 15)); // Set font of the username label
        panel.add(labelUsername); // Add the username label to the panel

        usernameField = new JTextField(); // Initialize the username text field
        usernameField.setBounds(212, 200, 375, 55); // Set bounds for the username text field
        usernameField.setBackground(Color.WHITE); // Set background color of the username text field
        usernameField.setForeground(Color.GRAY); // Set foreground color of the username text field
        usernameField.setFont(new Font("Karla", Font.PLAIN, 15)); // Set font of the username text field
        usernameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true)); // Set border of the username text field
        panel.add(usernameField); // Add the username text field to the panel

        JLabel labelPassword = new JLabel("Password:"); // Create label for password
        labelPassword.setBounds(212, 265, 76, 20); // Set bounds for the password label
        labelPassword.setFont(new Font("Karla", Font.PLAIN, 15)); // Set font of the password label
        panel.add(labelPassword); // Add the password label to the panel

        passwordField = new JPasswordField(); // Initialize the password field
        passwordField.setBounds(212, 285, 375, 55); // Set bounds for the password field
        passwordField.setBackground(Color.WHITE); // Set background color of the password field
        passwordField.setForeground(Color.GRAY); // Set foreground color of the password field
        passwordField.setFont(new Font("Karla", Font.PLAIN, 15)); // Set font of the password field
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true)); // Set border of the password field
        panel.add(passwordField); // Add the password field to the panel

        loginButton = new JButton("Login"); // Create the login button
        loginButton.setBounds(272, 360, 255, 59); // Set bounds for the login button
        loginButton.setBackground(Color.decode("#84C7AE")); // Set background color of the login button
        loginButton.setForeground(Color.BLACK); // Set text color of the login button
        loginButton.setFont(new Font("Karla", Font.BOLD, 15)); // Set font of the login button
        loginButton.setBorder(BorderFactory.createEmptyBorder()); // Remove border of the login button
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Set cursor to hand cursor when hovering over the button
        loginButton.setFocusPainted(false); // Remove focus paint from the login button
        loginButton.addActionListener(this); // Add action listener to the login button
        panel.add(loginButton); // Add the login button to the panel

        JLabel signupLabel = new JLabel("<HTML><U>Don't have an account?</U></HTML>", JLabel.CENTER); // Create label for signup link
        signupLabel.setForeground(Color.BLACK); // Set text color of the signup label
        signupLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Set cursor to hand cursor when hovering over the signup label
        signupLabel.setFont(new Font("Karla", Font.PLAIN, 14)); // Set font of the signup label
        signupLabel.setBounds(299, 425, 200, 47); // Set bounds for the signup label
        signupLabel.addMouseListener(new java.awt.event.MouseAdapter() { // Add mouse listener for the signup label
            public void mouseClicked(java.awt.event.MouseEvent evt) { // Mouse click event
                new Signup(); // Open the Signup window
                dispose(); // Close the current window
            }
        });
        panel.add(signupLabel); // Add the signup label to the panel

        add(panel); // Add the panel to the frame

        setVisible(true); // Make the frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) { // If the login button is clicked
            String username = usernameField.getText(); // Get the username from the text field
            String password = new String(passwordField.getPassword()); // Get the password from the password field

            if (validateLogin(username, password)) { // Validate the login credentials
                JOptionPane.showMessageDialog(this, "Login successful"); // Show success message
                new Home(); // Open the Home window
                dispose(); // Close the current window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
            }
        }
    }

    private boolean validateLogin(String username, String password) { // Method to validate login credentials
        if (userDatabase.containsKey(username)) { // Check if the username exists in the database
            return password.equals(userDatabase.get(username)); // Check if the password matches
        } else {
            return false; // Return false if the username does not exist
        }
    }

    public static void main(String[] args) {
        new Login(); // Create and show the Login window
    }
}
