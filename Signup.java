import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Signup extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signupButton;
    private JLabel loginLabel;
    private static final String FILE_PATH = "user_database.txt";
    private static Map<String, String> userDatabase = loadUserDatabase();

    public Signup() {
        setTitle("Signup");
        setSize(800, 600);
        getContentPane().setBackground(Color.decode("#A7D7C5"));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);
        panel.setSize(455, 455);
        panel.setLocation(172, 72);
        panel.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        panel.setOpaque(false);

        JLabel label = new JLabel("Welcome!");
        label.setBounds(275, 72, 250, 47);
        label.setFont(new Font("Karla", Font.BOLD, 48));
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);

        JLabel label2 = new JLabel("<HTML>Sign up now to streamline your checkout process and enjoy a seamless experience!<HTML>", JLabel.CENTER);
        label2.setBounds(246, 132, 308, 39);
        label2.setFont(new Font("Karla", Font.PLAIN, 15));
        label2.setHorizontalAlignment(JLabel.CENTER);
        add(label2);

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

        signupButton = new JButton("Signup");
        signupButton.setBounds(272, 360, 255, 59);
        signupButton.setBackground(Color.decode("#84C7AE"));
        signupButton.setForeground(Color.BLACK);
        signupButton.setFont(new Font("Karla", Font.BOLD, 15));
        signupButton.setBorder(BorderFactory.createEmptyBorder());
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupButton.setFocusPainted(false);
        signupButton.addActionListener(this);
        panel.add(signupButton);

        loginLabel = new JLabel("<HTML><U>Already have an account?</U></HTML>", JLabel.CENTER);
        loginLabel.setForeground(Color.BLACK);
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.setFont(new Font("Karla", Font.PLAIN, 14));
        loginLabel.setBounds(299, 425, 200, 47);
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new Login();
                dispose();
            }
        });
        panel.add(loginLabel);

        add(panel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userDatabase.containsKey(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save new user to database
            saveUser(username, password);
            JOptionPane.showMessageDialog(this, "Signup successful");
            dispose();
            new Login();
        }
    }

    private void saveUser(String username, String password) {
        userDatabase.put(username, password);
        saveUserDatabase();
    }

    private static void saveUserDatabase() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(userDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> loadUserDatabase() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (Map<String, String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>();
    }
}
