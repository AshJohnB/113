import javax.swing.*; // Importing Swing components for GUI
import java.awt.*; // Importing AWT components for GUI
import java.awt.event.ActionEvent; // Importing ActionEvent for event handling
import java.awt.event.ActionListener; // Importing ActionListener for event handling
import java.io.*; // Importing IO components for file operations
import java.util.ArrayList; // Importing ArrayList for data storage
import java.util.HashMap; // Importing HashMap for data storage

public class EditProducts extends JFrame implements ActionListener {

    private JTextField productNameField, productPriceField; // Text fields for product name and price
    private JComboBox<String> productTypeComboBox; // Combo box for product type selection
    private JButton addProductButton, goHomeButton, editProductButton; // Buttons for actions
    private ArrayList<Product> products; // List to store products
    private static final String FILE_PATH = "products.dat"; // File path for storing product data
    private static final Color BACKGROUND_COLOR = new Color(175, 238, 219); // Background color for GUI

    public EditProducts() {
        products = loadProducts(); // Load products from file

        setTitle("Edit Products"); // Set window title
        setSize(1024, 768);  // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        setLocationRelativeTo(null); // Center the window

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

        gbc.gridx = 0; // Set gridx
        gbc.gridy = 0; // Set gridy
        inputPanel.add(new JLabel("Product Name:"), gbc); // Add product name label
        productNameField = new JTextField(); // Initialize product name field
        productNameField.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font
        gbc.gridx = 1; // Set gridx
        inputPanel.add(productNameField, gbc); // Add text field to input panel

        gbc.gridx = 0; // Set gridx
        gbc.gridy = 1; // Set gridy
        inputPanel.add(new JLabel("Product Price:"), gbc); // Add product price label
        productPriceField = new JTextField(); // Initialize product price field
        productPriceField.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font
        gbc.gridx = 1; // Set gridx
        inputPanel.add(productPriceField, gbc); // Add text field to input panel

        gbc.gridx = 0; // Set gridx
        gbc.gridy = 2; // Set gridy
        inputPanel.add(new JLabel("Product Type:"), gbc); // Add product type label
        String[] productTypes = {"Beverage", "Snacks", "Meal", "School Supplies"}; // Array of product types
        productTypeComboBox = new JComboBox<>(productTypes); // Initialize product type combo box
        productTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font
        gbc.gridx = 1; // Set gridx
        inputPanel.add(productTypeComboBox, gbc); // Add combo box to input panel

        addProductButton = new JButton("Add Product"); // Initialize add product button
        addProductButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font
        addProductButton.setBackground(new Color(34, 139, 34)); // Set background color
        addProductButton.setForeground(Color.WHITE); // Set foreground color
        addProductButton.addActionListener(this); // Add action listener
        gbc.gridx = 0; // Set gridx
        gbc.gridy = 3; // Set gridy
        gbc.gridwidth = 1; // Set gridwidth
        inputPanel.add(addProductButton, gbc); // Add button to input panel

        editProductButton = new JButton("Edit Existing Product"); // Initialize edit product button
        editProductButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font
        editProductButton.setBackground(new Color(255, 165, 0)); // Set background color
        editProductButton.setForeground(Color.WHITE); // Set foreground color
        editProductButton.addActionListener(this); // Add action listener
        gbc.gridx = 1; // Set gridx
        inputPanel.add(editProductButton, gbc); // Add button to input panel

        goHomeButton = new JButton("Go Home"); // Initialize go home button
        goHomeButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font
        goHomeButton.setBackground(new Color(70, 130, 180)); // Set background color
        goHomeButton.setForeground(Color.WHITE); // Set foreground color
        goHomeButton.addActionListener(this); // Add action listener
        gbc.gridx = 0; // Set gridx
        gbc.gridy = 4; // Set gridy
        inputPanel.add(goHomeButton, gbc); // Add button to input panel

        mainPanel.add(inputPanel, BorderLayout.CENTER); // Add input panel to main panel

        add(mainPanel); // Add main panel to frame

        setVisible(true); // Set frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addProductButton) { // If add product button is clicked
            String name = productNameField.getText(); // Get product name
            String priceText = productPriceField.getText(); // Get product price text
            String type = (String) productTypeComboBox.getSelectedItem(); // Get selected product type
            
            if (name.isEmpty() || priceText.isEmpty()) { // If name or price is empty
                JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
                return; // Return
            }

            try {
                double price = Double.parseDouble(priceText); // Parse price text to double
                Product product = new Product(name, price, type); // Create new product
                products.add(product); // Add product to list
                saveProducts(); // Save products to file
                JOptionPane.showMessageDialog(this, "Product added successfully"); // Show confirmation message
                productNameField.setText(""); // Clear product name field
                productPriceField.setText(""); // Clear product price field
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price format", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
            }
        } else if (e.getSource() == editProductButton) { // If edit product button is clicked
            displayProductsForEditing(); // Display products for editing
        } else if (e.getSource() == goHomeButton) { // If go home button is clicked
            dispose(); // Close the window
        }
    }

    private void displayProductsForEditing() {
        HashMap<String, ArrayList<Product>> categorizedProducts = new HashMap<>(); // Map to categorize products by type
        for (Product product : products) { // Iterate through products
            categorizedProducts.computeIfAbsent(product.getType(), k -> new ArrayList<>()).add(product); // Categorize products
        }

        JDialog dialog = new JDialog(this, "Edit Existing Product", true); // Create modal dialog
        dialog.setSize(600, 400); // Set dialog size
        dialog.setLocationRelativeTo(this); // Center the dialog

        JPanel panel = new JPanel(new BorderLayout(10, 10)); // Panel with border layout
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Set border

        JPanel categoryPanel = new JPanel(new GridLayout(0, 1)); // Panel with grid layout
        ButtonGroup buttonGroup = new ButtonGroup(); // Group for radio buttons

        for (String category : categorizedProducts.keySet()) { // Iterate through categories
            categoryPanel.add(new JLabel(category)); // Add category label
            for (Product product : categorizedProducts.get(category)) { // Iterate through products in category
                JRadioButton radioButton = new JRadioButton(product.getName() + " - $" + product.getPrice()); // Create radio button for product
                radioButton.setActionCommand(product.getName()); // Set action command to product name
                buttonGroup.add(radioButton); // Add radio button to group
                categoryPanel.add(radioButton); // Add radio button to panel
            }
        }

        JButton selectButton = new JButton("Select"); // Initialize select button
        selectButton.addActionListener(e -> { // Add action listener
            String selectedProductName = buttonGroup.getSelection().getActionCommand(); // Get selected product name
            for (Product product : products) { // Iterate through products
                if (product.getName().equals(selectedProductName)) { // If product name matches
                    productNameField.setText(product.getName()); // Set product name field
                    productPriceField.setText(String.valueOf(product.getPrice())); // Set product price field
                    productTypeComboBox.setSelectedItem(product.getType()); // Set product type combo box
                    dialog.dispose(); // Close dialog
                    break; // Break loop
                }
            }
        });

        panel.add(new JScrollPane(categoryPanel), BorderLayout.CENTER); // Add category panel to scroll pane
        panel.add(selectButton, BorderLayout.SOUTH); // Add select button to panel

        dialog.add(panel); // Add panel to dialog
        dialog.setVisible(true); // Set dialog visible
    }

    private void saveProducts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) { // Create output stream
            oos.writeObject(products); // Write products to file
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace in case of exception
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Product> loadProducts() {
        File file = new File(FILE_PATH); // Create file object
        if (file.exists()) { // If file exists
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) { // Create input stream
                return (ArrayList<Product>) ois.readObject(); // Load products from file
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace(); // Print stack trace in case of exception
            }
        }
        return new ArrayList<>(); // Return empty list if file does not exist
    }

    public static void main(String[] args) {
        new EditProducts(); // Create and show EditProducts window
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

    public void setName(String name) {
        this.name = name; // Set name
    }

    public double getPrice() {
        return price; // Return price
    }

    public void setPrice(double price) {
        this.price = price; // Set price
    }

    public String getType() {
        return type; // Return type
    }

    public void setType(String type) {
        this.type = type; // Set type
    }
}
