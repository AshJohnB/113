import javax.swing.*; // Importing Swing components for GUI
import javax.swing.table.DefaultTableModel; // Importing DefaultTableModel for table data management
import java.awt.*; // Importing AWT components for GUI
import java.awt.event.ActionEvent; // Importing ActionEvent for event handling
import java.awt.event.ActionListener; // Importing ActionListener for event handling
import java.io.*; // Importing IO components for file operations
import java.util.ArrayList; // Importing ArrayList for data storage
import java.util.HashMap; // Importing HashMap for data storage
import java.util.Map; // Importing Map interface

public class SalesReport extends JFrame implements ActionListener {

    private JTextArea salesReportArea; // Text area to display sales report
    private JButton viewProductStatsButton, viewTransactionHistoryButton, clearReportButton, goHomeButton; // Buttons for actions
    private JLabel totalSalesLabel; // Label to display total sales
    private static final String SALES_FILE_PATH = "sales_report.dat"; // File path for sales report data
    private ArrayList<Transaction> transactions; // List to store transactions

    public SalesReport() {
        transactions = loadTransactions(); // Load transactions from file

        setTitle("Sales Report"); // Set window title
        setSize(800, 600); // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel(new BorderLayout()); // Main panel with border layout

        totalSalesLabel = new JLabel("Total Sales: $" + calculateTotalSales()); // Initialize total sales label
        panel.add(totalSalesLabel, BorderLayout.NORTH); // Add total sales label to the top of the panel

        salesReportArea = new JTextArea(); // Initialize sales report area
        salesReportArea.setEditable(false); // Make the sales report area non-editable
        panel.add(new JScrollPane(salesReportArea), BorderLayout.CENTER); // Add sales report area with scroll pane to the center of the panel

        JPanel buttonPanel = new JPanel(); // Panel for buttons
        viewProductStatsButton = new JButton("View Product Statistics"); // Initialize view product statistics button
        viewProductStatsButton.addActionListener(this); // Add action listener to the button
        buttonPanel.add(viewProductStatsButton); // Add button to the button panel

        viewTransactionHistoryButton = new JButton("View Transaction History"); // Initialize view transaction history button
        viewTransactionHistoryButton.addActionListener(this); // Add action listener to the button
        buttonPanel.add(viewTransactionHistoryButton); // Add button to the button panel

        clearReportButton = new JButton("Clear Report"); // Initialize clear report button
        clearReportButton.addActionListener(this); // Add action listener to the button
        buttonPanel.add(clearReportButton); // Add button to the button panel

        goHomeButton = new JButton("Go Home"); // Initialize go home button
        goHomeButton.addActionListener(this); // Add action listener to the button
        buttonPanel.add(goHomeButton); // Add button to the button panel

        panel.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom of the main panel

        add(panel); // Add main panel to the frame

        setVisible(true); // Make the frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewProductStatsButton) { // If view product statistics button is clicked
            viewProductStatistics(); // Call method to view product statistics
        } else if (e.getSource() == viewTransactionHistoryButton) { // If view transaction history button is clicked
            viewTransactionHistory(); // Call method to view transaction history
        } else if (e.getSource() == clearReportButton) { // If clear report button is clicked
            clearReport(); // Call method to clear the report
        } else if (e.getSource() == goHomeButton) { // If go home button is clicked
            dispose(); // Close the window
        }
    }

    private double calculateTotalSales() {
        return transactions.stream().mapToDouble(Transaction::getTotalPrice).sum(); // Calculate total sales from all transactions
    }

    private void viewProductStatistics() {
        HashMap<String, Integer> productQuantities = new HashMap<>(); // Map to store quantities of products sold
        HashMap<String, Double> productSales = new HashMap<>(); // Map to store total sales of products

        for (Transaction transaction : transactions) { // Iterate through transactions
            for (Map.Entry<Product, Integer> entry : transaction.getCart().entrySet()) { // Iterate through products in the transaction
                Product product = entry.getKey(); // Get the product
                int quantity = entry.getValue(); // Get the quantity sold
                productQuantities.put(product.getName(), productQuantities.getOrDefault(product.getName(), 0) + quantity); // Update quantity sold
                productSales.put(product.getName(), productSales.getOrDefault(product.getName(), 0.0) + (quantity * product.getPrice())); // Update total sales
            }
        }

        String[] columnNames = {"Product Name", "Quantity Sold", "Total Sales"}; // Column names for the table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // Table model for product statistics

        for (String productName : productQuantities.keySet()) { // Iterate through product names
            int quantitySold = productQuantities.get(productName); // Get quantity sold
            double totalSales = productSales.get(productName); // Get total sales
            Object[] row = {productName, quantitySold, String.format("%.2f Php", totalSales)}; // Create a row for the table
            model.addRow(row); // Add row to the table model
        }

        JTable table = new JTable(model); // Create table with the model
        table.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font for the table
        table.setRowHeight(30); // Set row height
        JScrollPane scrollPane = new JScrollPane(table); // Create scroll pane for the table

        JDialog dialog = new JDialog(this, "Product Statistics", true); // Create dialog for displaying product statistics
        dialog.setSize(800, 400); // Set dialog size
        dialog.setLocationRelativeTo(this); // Center the dialog
        dialog.add(scrollPane); // Add scroll pane to the dialog
        dialog.setVisible(true); // Make the dialog visible
    }

    private void viewTransactionHistory() {
        StringBuilder history = new StringBuilder(); // StringBuilder to build transaction history
        for (Transaction transaction : transactions) { // Iterate through transactions
            history.append("Transaction:\n"); // Append transaction label
            for (Map.Entry<Product, Integer> entry : transaction.getCart().entrySet()) { // Iterate through products in the transaction
                Product product = entry.getKey(); // Get the product
                int quantity = entry.getValue(); // Get the quantity sold
                history.append(String.format("%s: %d @ $%.2f each\n", product.getName(), quantity, product.getPrice())); // Append product details
            }
            history.append(String.format("Total: $%.2f\n\n", transaction.getTotalPrice())); // Append total price of the transaction
        }
        salesReportArea.setText(history.toString()); // Set the text of the sales report area
    }

    private void clearReport() {
        transactions.clear(); // Clear the list of transactions
        saveTransactions(); // Save the empty list to the file
        totalSalesLabel.setText("Total Sales: $0.00"); // Reset total sales label
        salesReportArea.setText(""); // Clear the sales report area
        JOptionPane.showMessageDialog(this, "Sales report cleared"); // Show confirmation message
    }

    private void saveTransactions() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SALES_FILE_PATH))) { // Create output stream to save transactions
            oos.writeObject(transactions); // Write transactions to file
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace in case of exception
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Transaction> loadTransactions() {
        File file = new File(SALES_FILE_PATH); // Create file object for sales report data
        if (file.exists()) { // If file exists
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) { // Create input stream to read transactions
                return (ArrayList<Transaction>) ois.readObject(); // Load transactions from file
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace(); // Print stack trace in case of exception
            }
        }
        return new ArrayList<>(); // Return empty list if file does not exist
    }

    public static void main(String[] args) {
        new SalesReport(); // Create and show SalesReport window
    }
}

class Transaction implements Serializable { // Transaction class implements Serializable for object serialization
    private Map<Product, Integer> cart; // Map to store products and their quantities
    private double totalPrice; // Variable to store total price of the transaction

    public Transaction(Map<Product, Integer> cart, double totalPrice) { // Constructor to initialize transaction with cart and total price
        this.cart = cart; // Set the cart
        this.totalPrice = totalPrice; // Set the total price
    }

    public Map<Product, Integer> getCart() {
        return cart; // Return the cart
    }

    public double getTotalPrice() {
        return totalPrice; // Return the total price
    }
}

class Product implements Serializable { // Product class implements Serializable for object serialization
    private String name; // Variable to store the name of the product
    private double price; // Variable to store the price of the product
    private String type; // Variable to store the type/category of the product

    public Product(String name, double price, String type) { // Constructor to initialize product with name, price, and type
        this.name = name; // Set the name
        this.price = price; // Set the price
        this.type = type; // Set the type
    }

    public String getName() {
        return name; // Return the name
    }

    public double getPrice() {
        return price; // Return the price
    }

    public String getType() {
        return type; // Return the type
    }
}
