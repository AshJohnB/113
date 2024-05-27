import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Login extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Map<String, String> userDatabase;

    public Login() {
        this.userDatabase = Signup.loadUserDatabase();
        setTitle("Login");
        setSize(800, 600);  // Set size to match Signup window
        getContentPane().setBackground(Color.decode("#A7D7C5"));  // Set background color
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);  // Use null layout for custom positioning
        panel.setSize(455, 455);
        panel.setLocation(172, 72);
        panel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        panel.setOpaque(false);

        // Change the bounds and alignment of the "Welcome back!" label
        JLabel label = new JLabel("Welcome back!");
        label.setBounds(225, 89, 350, 47);  // Adjusted bounds for centering
        label.setFont(new Font("Karla", Font.BOLD, 48));
        label.setHorizontalAlignment(JLabel.CENTER);  // Set horizontal alignment to center
        panel.add(label);

        JLabel labelUsername = new JLabel("Username:");
        labelUsername.setBounds(212, 180, 76, 20);
        labelUsername.setFont(new Font("Karla", Font.PLAIN, 15));
        panel.add(labelUsername);

        usernameField = new JTextField();
        usernameField.setBounds(212, 200, 375, 55);
        usernameField.setBackground(Color.WHITE);
        usernameField.setForeground(Color.GRAY);
        usernameField.setFont(new Font("Karla", Font.PLAIN, 15));
        usernameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        panel.add(usernameField);

        JLabel labelPassword = new JLabel("Password:");
        labelPassword.setBounds(212, 265, 76, 20);
        labelPassword.setFont(new Font("Karla", Font.PLAIN, 15));
        panel.add(labelPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(212, 285, 375, 55);
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(Color.GRAY);
        passwordField.setFont(new Font("Karla", Font.PLAIN, 15));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(272, 360, 255, 59);
        loginButton.setBackground(Color.decode("#84C7AE"));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Karla", Font.BOLD, 15));
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this);
        panel.add(loginButton);

        JLabel signupLabel = new JLabel("<HTML><U>Don't have an account?</U></HTML>", JLabel.CENTER);
        signupLabel.setForeground(Color.BLACK);
        signupLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupLabel.setFont(new Font("Karla", Font.PLAIN, 14));
        signupLabel.setBounds(299, 425, 200, 47);
        signupLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new Signup();
                dispose();
            }
        });
        panel.add(signupLabel);

        add(panel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (validateLogin(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful");
                new Home();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateLogin(String username, String password) {
        if (userDatabase.containsKey(username)) {
            return password.equals(userDatabase.get(username));
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
