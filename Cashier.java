import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cashier extends JFrame implements ActionListener {

    private JComboBox<String> productTypeComboBox, productComboBox;
    private JTextField quantityField, moneyField;
    private JButton enterButton, finishTransactionButton, goHomeButton;

    public Cashier() {
        setTitle("Cashier");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        
        panel.add(new JLabel("Product Type:"));
        String[] productTypes = {"Beverage", "Snacks", "Meal", "School Supplies"};
        productTypeComboBox = new JComboBox<>(productTypes);
        panel.add(productTypeComboBox);

        panel.add(new JLabel("Product:"));
        productComboBox = new JComboBox<>();  // Should be populated based on selected product type
        panel.add(productComboBox);

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        panel.add(new JLabel("Money:"));
        moneyField = new JTextField();
        panel.add(moneyField);

        enterButton = new JButton("Enter");
        enterButton.addActionListener(this);
        panel.add(enterButton);

        finishTransactionButton = new JButton("Finish Transaction");
        finishTransactionButton.addActionListener(this);
        panel.add(finishTransactionButton);

        goHomeButton = new JButton("Go Home");
        goHomeButton.addActionListener(this);
        panel.add(goHomeButton);

        add(panel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enterButton) {
            // Logic to handle product selection, quantity, and money
        } else if (e.getSource() == finishTransactionButton) {
            // Logic to finish transaction, calculate change, and store profit data
        } else if (e.getSource() == goHomeButton) {
            dispose();
        }
    }
}
