import java.io.Serializable; // Importing Serializable interface for object serialization

public class Product implements Serializable { // The Product class implements Serializable to allow object serialization
    private String name; // Variable to store the name of the product
    private double price; // Variable to store the price of the product
    private String type; // Variable to store the type/category of the product

    // Constructor to initialize the product with a name, price, and type
    public Product(String name, double price, String type) {
        this.name = name; // Set the name of the product
        this.price = price; // Set the price of the product
        this.type = type; // Set the type of the product
    }

    // Getter method to get the name of the product
    public String getName() {
        return name; // Return the name of the product
    }

    // Getter method to get the price of the product
    public double getPrice() {
        return price; // Return the price of the product
    }

    // Getter method to get the type of the product
    public String getType() {
        return type; // Return the type of the product
    }

    // Override the toString method to provide a string representation of the product
    @Override
    public String toString() {
        return name + " - $" + price; // Return the product's name and price in a formatted string
    }
}
