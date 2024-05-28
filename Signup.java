import javax.swing.*; // Importing Swing components for GUI
import java.awt.*; // Importing AWT components for GUI
import java.awt.event.ActionEvent; // Importing ActionEvent for event handling
import java.awt.event.ActionListener; // Importing ActionListener for event handling
import java.io.*; // Importing IO components for file operations
import java.util.HashMap; // Importing HashMap for data storage
import java.util.Map; // Importing Map interface

public class Signup extends JFrame implements ActionListener {

    private JTextField usernameField; // Text field for username input
    private JPasswordField passwordField; // Password field for password input
    private JButton signupButton; // Button for signup action
    private JLabel loginLabel; // Label for login link
    private static final String FILE_PATH = "user_database.txt"; // File path for storing user data
    private static Map<String, String> userDatabase = loadUserDatabase(); // Load user database from file

    public Signup() {
        setTitle("Signup"); // Set window title
        setSize(800, 600); // Set window size
        getContentPane().setBackground(Color.decode("#A7D7C5")); // Set background color
        setResizable(false); // Disable window resizing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel(null); // Use null layout for custom positioning
        panel.setSize(455, 455); // Set panel size
        panel.setLocation(172, 72); // Set panel location
        panel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32)); // Set an empty border around the panel
        panel.setOpaque(false); // Make the panel transparent

        JLabel label = new JLabel("Welcome!"); // Create label with text "Welcome!"
        label.setBounds(275, 72, 250, 47); // Set bounds for the label
        label.setFont(new Font("Karla", Font.BOLD, 48)); // Set font of the label
        label.setHorizontalAlignment(JLabel.CENTER); // Center align the text within the label
        add(label); // Add the label to the frame

        JLabel label2 = new JLabel("<HTML>Sign up now to streamline your checkout process and enjoy a seamless experience!<HTML>", JLabel.CENTER); // Create label with additional text
        label2.setBounds(246, 132, 308, 39); // Set bounds for the label
        label2.setFont(new Font("Karla", Font.PLAIN, 15)); // Set font of the label
        label2.setHorizontalAlignment(JLabel.CENTER); // Center align the text within the label
        add(label2); // Add the label to the frame

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

        signupButton = new JButton("Signup"); // Create the signup button
        signupButton.setBounds(272, 360, 255, 59); // Set bounds for the signup button
        signupButton.setBackground(Color.decode("#84C7AE")); // Set background color of the signup button
        signupButton.setForeground(Color.BLACK); // Set text color of the signup button
        signupButton.setFont(new Font("Karla", Font.BOLD, 15)); // Set font of the signup button
        signupButton.setBorder(BorderFactory.createEmptyBorder()); // Remove border of the signup button
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Set cursor to hand cursor when hovering over the button
        signupButton.setFocusPainted(false); // Remove focus paint from the signup button
        signupButton.addActionListener(this); // Add action listener to the signup button
        panel.add(signupButton); // Add the signup button to the panel

        loginLabel = new JLabel("<HTML><U>Already have an account?</U></HTML>", JLabel.CENTER); // Create label for login link
        loginLabel.setForeground(Color.BLACK); // Set text color of the login label
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Set cursor to hand cursor when hovering over the login label
        loginLabel.setFont(new Font("Karla", Font.PLAIN, 14)); // Set font of the login label
        loginLabel.setBounds(299, 425, 200, 47); // Set bounds for the login label
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() { // Add mouse listener for the login label
            public void mouseClicked(java.awt.event.MouseEvent evt) { // Mouse click event
                new Login(); // Open the Login window
                dispose(); // Close the current window
            }
        });
        panel.add(loginLabel); // Add the login label to the panel

        add(panel); // Add the panel to the frame

        setVisible(true); // Make the frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) { // If the signup button is clicked
            String username = usernameField.getText(); // Get the username from the text field
            String password = new String(passwordField.getPassword()); // Get the password from the password field

            if (username.isEmpty() || password.isEmpty()) { // Check if username or password is empty
                JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
                return; // Return without proceeding
            }

            if (userDatabase.containsKey(username)) { // Check if username already exists
                JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
                return; // Return without proceeding
            }

            // Save new user to database
            saveUser(username, password); // Save the new user to the database
            JOptionPane.showMessageDialog(this, "Signup successful"); // Show success message
            dispose(); // Close the current window
            new Login(); // Open the Login window
        }
    }

    private void saveUser(String username, String password) { // Method to save user
        userDatabase.put(username, password); // Add the user to the database
        saveUserDatabase(); // Save the user database to file
    }

    private static void saveUserDatabase() { // Method to save user database to file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) { // Create output stream
            oos.writeObject(userDatabase); // Write the user database to file
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace in case of exception
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> loadUserDatabase() { // Method to load user database from file
        File file = new File(FILE_PATH); // Create file object
        if (file.exists()) { // Check if file exists
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) { // Create input stream
                return (Map<String, String>) ois.readObject(); // Load the user database from file
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace(); // Print stack trace in case of exception
            }
        }
        return new HashMap<>(); // Return new empty HashMap if file does not exist
    }

    public static void main(String[] args) {
        new Signup(); // Create and show Signup window
    }
}
