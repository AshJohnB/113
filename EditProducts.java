import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditProducts extends JFrame implements ActionListener {

    private JTextField productNameField, productPriceField;
    private JComboBox<String> productTypeComboBox;
    private JButton addProductButton, goHomeButton;
    private ArrayList<Product> products;

    public EditProducts() {
        products = new ArrayList<>();

        setTitle("Edit Products");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        
        panel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        panel.add(productNameField);

        panel.add(new JLabel("Product Price:"));
        productPriceField = new JTextField();
        panel.add(productPriceField);

        panel.add(new JLabel("Product Type:"));
        String[] productTypes = {"Beverage", "Snacks", "Meal", "School Supplies"};
        productTypeComboBox = new JComboBox<>(productTypes);
        panel.add(productTypeComboBox);

        addProductButton = new JButton("Add Product");
        addProductButton.addActionListener(this);
        panel.add(addProductButton);

        goHomeButton = new JButton("Go Home");
        goHomeButton.addActionListener(this);
        panel.add(goHomeButton);

        add(panel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addProductButton) {
            String name = productNameField.getText();
            String priceText = productPriceField.getText();
            String type = (String) productTypeComboBox.getSelectedItem();
            
            if (name.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                products.add(new Product(name, price, type));
                JOptionPane.showMessageDialog(this, "Product added successfully");
                productNameField.setText("");
                productPriceField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price format", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == goHomeButton) {
 
            dispose();
        }
    }
}
