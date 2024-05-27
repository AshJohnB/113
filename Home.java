import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame implements ActionListener {

    private JButton editProductsButton, cashierButton, salesReportButton, exitButton;
    private JLabel welcomeLabel;

    public Home() {
        setTitle("Home");
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

        welcomeLabel = new JLabel("E.C.S.E.");
        welcomeLabel.setBounds(275, 81, 250, 47);
        welcomeLabel.setFont(new Font("Karla", Font.BOLD, 48));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(welcomeLabel);

        editProductsButton = new JButton("Edit Products");
        editProductsButton.setBounds(272, 180, 255, 59);
        editProductsButton.setBackground(Color.decode("#84C7AE"));
        editProductsButton.setForeground(Color.BLACK);
        editProductsButton.setFont(new Font("Karla", Font.BOLD, 15));
        editProductsButton.setBorder(BorderFactory.createEmptyBorder());
        editProductsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editProductsButton.setFocusPainted(false);
        editProductsButton.addActionListener(this);
        panel.add(editProductsButton);

        cashierButton = new JButton("Cashier");
        cashierButton.setBounds(272, 265, 255, 59);
        cashierButton.setBackground(Color.decode("#84C7AE"));
        cashierButton.setForeground(Color.BLACK);
        cashierButton.setFont(new Font("Karla", Font.BOLD, 15));
        cashierButton.setBorder(BorderFactory.createEmptyBorder());
        cashierButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cashierButton.setFocusPainted(false);
        cashierButton.addActionListener(this);
        panel.add(cashierButton);

        salesReportButton = new JButton("Sales Report");
        salesReportButton.setBounds(272, 360, 255, 59);
        salesReportButton.setBackground(Color.decode("#84C7AE"));
        salesReportButton.setForeground(Color.BLACK);
        salesReportButton.setFont(new Font("Karla", Font.BOLD, 15));
        salesReportButton.setBorder(BorderFactory.createEmptyBorder());
        salesReportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        salesReportButton.setFocusPainted(false);
        salesReportButton.addActionListener(this);
        panel.add(salesReportButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(272, 455, 255, 59);
        exitButton.setBackground(Color.decode("#84C7AE"));
        exitButton.setForeground(Color.BLACK);
        exitButton.setFont(new Font("Karla", Font.BOLD, 15));
        exitButton.setBorder(BorderFactory.createEmptyBorder());
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(this);
        panel.add(exitButton);

        add(panel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editProductsButton) {
            new EditProducts();
        } else if (e.getSource() == cashierButton) {
            new Cashier();
        } else if (e.getSource() == salesReportButton) {
            new SalesReport();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}
