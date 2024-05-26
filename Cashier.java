import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cashier extends JFrame implements ActionListener {

    private JComboBox<String> productTypeComboBox, productComboBox;
    private JTextField quantityField, moneyField;
    private JButton enterButton, finishTransactionButton, goHomeButton;
    private JLabel totalLabel;
    private ArrayList<Product> products;
    private Map<Product, Integer> cart;
    private double totalPrice;
    private static final String SALES_FILE_PATH = "sales_report.dat";

    public Cashier() {
        products = loadProducts();
        cart = new HashMap<>();
        totalPrice = 0.0;

        setTitle("Cashier");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2));

        totalLabel = new JLabel("Total: $0.00");
        panel.add(totalLabel);
        panel.add(new JLabel(""));

        panel.add(new JLabel("Product Type:"));
        String[] productTypes = {"Beverage", "Snacks", "Meal", "School Supplies"};
        productTypeComboBox = new JComboBox<>(productTypes);
        productTypeComboBox.addActionListener(this);
        panel.add(productTypeComboBox);

        panel.add(new JLabel("Product:"));
        productComboBox = new JComboBox<>();
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
        if (e.getSource() == productTypeComboBox) {
            updateProductComboBox();
        } else if (e.getSource() == enterButton) {
            handleEnter();
        } else if (e.getSource() == finishTransactionButton) {
            handleFinishTransaction();
        } else if (e.getSource() == goHomeButton) {
            dispose();
        }
    }

    private void updateProductComboBox() {
        String selectedType = (String) productTypeComboBox.getSelectedItem();
        productComboBox.removeAllItems();
        for (Product product : products) {
            if (product.getType().equals(selectedType)) {
                productComboBox.addItem(product.getName());
            }
        }
    }

    private void handleEnter() {
        String selectedProduct = (String) productComboBox.getSelectedItem();
        String quantityText = quantityField.getText();

        if (selectedProduct == null || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            Product product = findProductByName(selectedProduct);

            if (product != null) {
                double totalCost = product.getPrice() * quantity;
                cart.put(product, cart.getOrDefault(product, 0) + quantity);
                totalPrice += totalCost;
                totalLabel.setText(String.format("Total: $%.2f", totalPrice));
                JOptionPane.showMessageDialog(this, "Product added to cart");
                quantityField.setText("");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleFinishTransaction() {
        String moneyText = moneyField.getText();

        if (moneyText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Money field is required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double money = Double.parseDouble(moneyText);

            if (money >= totalPrice) {
                double change = money - totalPrice;
                saveTransaction(new Transaction(new HashMap<>(cart), totalPrice));
                cart.clear();
                totalPrice = 0.0;
                totalLabel.setText("Total: $0.00");
                moneyField.setText("");
                JOptionPane.showMessageDialog(this, String.format("Transaction successful. Change: $%.2f", change));
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient money", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid money format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveTransaction(Transaction transaction) {
        ArrayList<Transaction> transactions = loadTransactions();
        transactions.add(transaction);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SALES_FILE_PATH))) {
            oos.writeObject(transactions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Transaction> loadTransactions() {
        File file = new File(SALES_FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (ArrayList<Transaction>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    private Product findProductByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Product> loadProducts() {
        File file = new File("products.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (ArrayList<Product>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        new Cashier();
    }
}
