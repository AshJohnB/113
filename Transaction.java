import java.io.Serializable; // Importing Serializable interface for object serialization
import java.util.Map; // Importing Map interface for storing cart items

public class Transaction implements Serializable { // The Transaction class implements Serializable to allow object serialization
    private Map<Product, Integer> cart; // Map to store products and their quantities in the transaction
    private double totalPrice; // Variable to store the total price of the transaction

    // Constructor to initialize the transaction with a cart and total price
    public Transaction(Map<Product, Integer> cart, double totalPrice) {
        this.cart = cart; // Set the cart
        this.totalPrice = totalPrice; // Set the total price
    }

    // Getter method to get the cart
    public Map<Product, Integer> getCart() {
        return cart; // Return the cart
    }

    // Getter method to get the total price
    public double getTotalPrice() {
        return totalPrice; // Return the total price
    }
}
