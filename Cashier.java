import javax.swing.*; // Importing Swing components for GUI
import java.awt.*; // Importing AWT components for GUI
import java.awt.event.ActionEvent; // Importing ActionEvent for event handling
import java.awt.event.ActionListener; // Importing ActionListener for event handling
import java.io.*; // Importing IO components for file operations
import java.util.ArrayList; // Importing ArrayList for data storage
import java.util.HashMap; // Importing HashMap for data storage
import java.util.Map; // Importing Map interface

public class Cashier extends JFrame implements ActionListener {

    private JComboBox<String> productTypeComboBox, productComboBox; // Combo boxes for product type and product selection
    private JTextField quantityField, moneyField; // Text fields for quantity and money input
    private JButton enterButton, finishTransactionButton, goHomeButton; // Buttons for actions
    private JLabel totalLabel; // Label to display the total amount
    private JTextArea receiptArea; // Text area to display the receipt
    private ArrayList<Product> products; // List to store products
    private Map<Product, Integer> cart; // Map to store products added to the cart with their quantities
    private double totalPrice; // Variable to store the total price
    private static final String SALES_FILE_PATH = "sales_report.dat"; // File path for sales report
    private static final Color BACKGROUND_COLOR = new Color(175, 238, 219); // Background color for GUI

    public Cashier() {
        products = loadProducts(); // Load products from file
        cart = new HashMap<>(); // Initialize cart
        totalPrice = 0.0; // Initialize total price

        setTitle("Cashier"); // Set window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        setUndecorated(true); // Remove window decorations (optional)
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen mode

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)); // Main panel with border layout
        mainPanel.setBackground(BACKGROUND_COLOR); // Set background color
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Set border

        JPanel inputPanel = new JPanel(new GridBagLayout()); // Input panel with grid bag layout
        inputPanel.setBackground(BACKGROUND_COLOR); // Set background color
        GridBagConstraints gbc = new GridBagConstraints(); // GridBagConstraints for layout management
        gbc.fill = GridBagConstraints.BOTH; // Set fill to both
        gbc.insets = new Insets(10, 10, 10, 10); // Set insets
        gbc.weightx = 1.0; // Set weightx
        gbc.weighty = 1.0; // Set weighty

        totalLabel = new JLabel("Total: $0.00"); // Initialize total label
        totalLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font
        gbc.gridx = 0; // Set gridx
        gbc.gridy = 0; // Set gridy
        gbc.gridwidth = 2; // Set gridwidth
        inputPanel.add(totalLabel, gbc); // Add label to input panel

        gbc.gridwidth = 1; // Reset gridwidth

        gbc.gridx = 0; // Set gridx
        gbc.gridy = 1; // Set gridy
        inputPanel.add(new JLabel("Product Type:"), gbc); // Add product type label
        String[] productTypes = {"Beverage", "Snacks", "Meal", "School Supplies"}; // Array of product types
        productTypeComboBox = new JComboBox<>(productTypes); // Initialize product type combo box
        productTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font
        productTypeComboBox.addActionListener(this); // Add action listener
        gbc.gridx = 1; // Set gridx
        inputPanel.add(productTypeComboBox, gbc); // Add combo box to input panel

        gbc.gridx = 0; // Set gridx
        gbc.gridy = 2; // Set gridy
        inputPanel.add(new JLabel("Product:"), gbc); // Add product label
        productComboBox = new JComboBox<>(); // Initialize product combo box
        productComboBox.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font
        gbc.gridx = 1; // Set gridx
        inputPanel.add(productComboBox, gbc); // Add combo box to input panel

        gbc.gridx = 0; // Set gridx
        gbc.gridy = 3; // Set gridy
        inputPanel.add(new JLabel("Quantity:"), gbc); // Add quantity label
        quantityField = new JTextField(); // Initialize quantity field
        quantityField.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font
        gbc.gridx = 1; // Set gridx
        inputPanel.add(quantityField, gbc); // Add text field to input panel

        gbc.gridx = 0; // Set gridx
        gbc.gridy = 4; // Set gridy
        inputPanel.add(new JLabel("Money:"), gbc); // Add money label
        moneyField = new JTextField(); // Initialize money field
        moneyField.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font
        gbc.gridx = 1; // Set gridx
        inputPanel.add(moneyField, gbc); // Add text field to input panel

        enterButton = new JButton("Enter"); // Initialize enter button
        enterButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font
        enterButton.setBackground(new Color(34, 139, 34)); // Set background color
        enterButton.setForeground(Color.WHITE); // Set foreground color
        enterButton.addActionListener(this); // Add action listener
        gbc.gridx = 0; // Set gridx
        gbc.gridy = 5; // Set gridy
        inputPanel.add(enterButton, gbc); // Add button to input panel

        finishTransactionButton = new JButton("Finish Transaction"); // Initialize finish transaction button
        finishTransactionButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font
        finishTransactionButton.setBackground(new Color(0, 128, 128)); // Set background color
        finishTransactionButton.setForeground(Color.WHITE); // Set foreground color
        finishTransactionButton.addActionListener(this); // Add action listener
        gbc.gridx = 1; // Set gridx
        inputPanel.add(finishTransactionButton, gbc); // Add button to input panel

        goHomeButton = new JButton("Go Home"); // Initialize go home button
        goHomeButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font
        goHomeButton.setBackground(new Color(70, 130, 180)); // Set background color
        goHomeButton.setForeground(Color.WHITE); // Set foreground color
        goHomeButton.addActionListener(this); // Add action listener
        gbc.gridx = 0; // Set gridx
        gbc.gridy = 6; // Set gridy
        gbc.gridwidth = 2; // Set gridwidth
        inputPanel.add(goHomeButton, gbc); // Add button to input panel

        receiptArea = new JTextArea(); // Initialize receipt area
        receiptArea.setEditable(false); // Set editable to false
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 18)); // Set font
        JScrollPane scrollPane = new JScrollPane(receiptArea); // Initialize scroll pane for receipt area

        mainPanel.add(inputPanel, BorderLayout.CENTER); // Add input panel to main panel
        mainPanel.add(scrollPane, BorderLayout.EAST); // Add scroll pane to main panel

        add(mainPanel); // Add main panel to frame

        setVisible(true); // Set frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == productTypeComboBox) { // If product type combo box is selected
            updateProductComboBox(); // Update product combo box
        } else if (e.getSource() == enterButton) { // If enter button is clicked
            handleEnter(); // Handle enter action
        } else if (e.getSource() == finishTransactionButton) { // If finish transaction button is clicked
            handleFinishTransaction(); // Handle finish transaction action
        } else if (e.getSource() == goHomeButton) { // If go home button is clicked
            dispose(); // Close the window
        }
    }

    private void updateProductComboBox() {
        String selectedType = (String) productTypeComboBox.getSelectedItem(); // Get selected product type
        productComboBox.removeAllItems(); // Remove all items from product combo box
        for (Product product : products) { // Iterate through products
            if (product.getType().equals(selectedType)) { // If product type matches selected type
                productComboBox.addItem(product.getName()); // Add product name to combo box
            }
        }
    }

    private void handleEnter() {
        String selectedProduct = (String) productComboBox.getSelectedItem(); // Get selected product
        String quantityText = quantityField.getText(); // Get quantity text

        if (selectedProduct == null || quantityText.isEmpty()) { // If product or quantity is not selected
            JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
            return; // Return
        }

        try {
            int quantity = Integer.parseInt(quantityText); // Parse quantity text to integer
            Product product = findProductByName(selectedProduct); // Find product by name

            if (product != null) { // If product is found
                double totalCost = product.getPrice() * quantity; // Calculate total cost
                cart.put(product, cart.getOrDefault(product, 0) + quantity); // Add product to cart with quantity
                totalPrice += totalCost; // Add total cost to total price
                totalLabel.setText(String.format("Total: $%.2f", totalPrice)); // Update total label
                updateReceipt(); // Update receipt
                JOptionPane.showMessageDialog(this, "Product added to cart"); // Show confirmation message
                quantityField.setText(""); // Clear quantity field
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity format", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
        }
    }

    private void handleFinishTransaction() {
        String moneyText = moneyField.getText(); // Get money text

        if (moneyText.isEmpty()) { // If money field is empty
            JOptionPane.showMessageDialog(this, "Money field is required", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
            return; // Return
        }

        try {
            double money = Double.parseDouble(moneyText); // Parse money text to double

            if (money >= totalPrice) { // If money is sufficient
                double change = money - totalPrice; // Calculate change
                saveTransaction(new Transaction(new HashMap<>(cart), totalPrice)); // Save transaction
                cart.clear(); // Clear cart
                totalPrice = 0.0; // Reset total price
                totalLabel.setText("Total: $0.00"); // Reset total label
                moneyField.setText(""); // Clear money field
                updateReceipt(); // Update receipt
                JOptionPane.showMessageDialog(this, String.format("Transaction successful. Change: $%.2f", change)); // Show success message with change
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient money", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid money format", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
        }
    }

    private void updateReceipt() {
        receiptArea.setText(""); // Clear receipt area
        for (Product product : cart.keySet()) { // Iterate through products in cart
            receiptArea.append(product.getName() + " x " + cart.get(product) + " - $" + (product.getPrice() * cart.get(product)) + "\n"); // Append product details to receipt area
        }
    }

    private void saveTransaction(Transaction transaction) {
        ArrayList<Transaction> transactions = loadTransactions(); // Load transactions
        transactions.add(transaction); // Add transaction to list
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SALES_FILE_PATH))) {
            oos.writeObject(transactions); // Save transactions to file
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace in case of exception
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Transaction> loadTransactions() {
        File file = new File(SALES_FILE_PATH); // Create file object
        if (file.exists()) { // If file exists
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (ArrayList<Transaction>) ois.readObject(); // Load transactions from file
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace(); // Print stack trace in case of exception
            }
        }
        return new ArrayList<>(); // Return empty list if file does not exist
    }

    private Product findProductByName(String name) {
        for (Product product : products) { // Iterate through products
            if (product.getName().equals(name)) { // If product name matches
                return product; // Return product
            }
        }
        return null; // Return null if product is not found
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Product> loadProducts() {
        File file = new File("products.dat"); // Create file object
        if (file.exists()) { // If file exists
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (ArrayList<Product>) ois.readObject(); // Load products from file
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace(); // Print stack trace in case of exception
            }
        }
        return new ArrayList<>(); // Return empty list if file does not exist
    }

    public static void main(String[] args) {
        new Cashier(); // Create and show Cashier window
    }
}

class Product implements Serializable {
    private String name; // Product name
    private double price; // Product price
    private String type; // Product type

    public Product(String name, double price, String type) {
        this.name = name; // Initialize name
        this.price = price; // Initialize price
        this.type = type; // Initialize type
    }

    public String getName() {
        return name; // Return name
    }

    public double getPrice() {
        return price; // Return price
    }

    public String getType() {
        return type; // Return type
    }
}

class Transaction implements Serializable {
    private Map<Product, Integer> cart; // Map of products and quantities in the transaction
    private double totalPrice; // Total price of the transaction

    public Transaction(Map<Product, Integer> cart, double totalPrice) {
        this.cart = cart; // Initialize cart
        this.totalPrice = totalPrice; // Initialize total price
    }

    public Map<Product, Integer> getCart() {
        return cart; // Return cart
    }

    public double getTotalPrice() {
        return totalPrice; // Return total price
    }
}
