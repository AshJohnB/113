import java.io.Serializable;
import java.util.Map;

public class Transaction implements Serializable {
    private Map<Product, Integer> cart;
    private double totalPrice;

    public Transaction(Map<Product, Integer> cart, double totalPrice) {
        this.cart = cart;
        this.totalPrice = totalPrice;
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
