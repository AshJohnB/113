import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class SalesReport extends JFrame implements ActionListener {

    private JTextArea salesReportArea;
    private JButton viewProductStatsButton, viewTransactionHistoryButton, clearReportButton, goHomeButton;
    private JLabel totalSalesLabel;
    private static final String SALES_FILE_PATH = "sales_report.dat";
    private ArrayList<Transaction> transactions;

    public SalesReport() {
        transactions = loadTransactions();

        setTitle("Sales Report");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        totalSalesLabel = new JLabel("Total Sales: $" + calculateTotalSales());
        panel.add(totalSalesLabel, BorderLayout.NORTH);

        salesReportArea = new JTextArea();
        salesReportArea.setEditable(false);
        panel.add(new JScrollPane(salesReportArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        viewProductStatsButton = new JButton("View Product Statistics");
        viewProductStatsButton.addActionListener(this);
        buttonPanel.add(viewProductStatsButton);

        viewTransactionHistoryButton = new JButton("View Transaction History");
        viewTransactionHistoryButton.addActionListener(this);
        buttonPanel.add(viewTransactionHistoryButton);

        clearReportButton = new JButton("Clear Report");
        clearReportButton.addActionListener(this);
        buttonPanel.add(clearReportButton);

        goHomeButton = new JButton("Go Home");
        goHomeButton.addActionListener(this);
        buttonPanel.add(goHomeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewProductStatsButton) {
            viewProductStatistics();
        } else if (e.getSource() == viewTransactionHistoryButton) {
            viewTransactionHistory();
        } else if (e.getSource() == clearReportButton) {
            clearReport();
        } else if (e.getSource() == goHomeButton) {
            dispose();
        }
    }

    private double calculateTotalSales() {
        return transactions.stream().mapToDouble(Transaction::getTotalPrice).sum();
    }

    private void viewProductStatistics() {
        StringBuilder stats = new StringBuilder();
        for (Transaction transaction : transactions) {
            for (Map.Entry<Product, Integer> entry : transaction.getCart().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                stats.append(String.format("%s: %d sold at $%.2f each\n", product.getName(), quantity, product.getPrice()));
            }
        }
        salesReportArea.setText(stats.toString());
    }

    private void viewTransactionHistory() {
        StringBuilder history = new StringBuilder();
        for (Transaction transaction : transactions) {
            history.append("Transaction:\n");
            for (Map.Entry<Product, Integer> entry : transaction.getCart().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                history.append(String.format("%s: %d @ $%.2f each\n", product.getName(), quantity, product.getPrice()));
            }
            history.append(String.format("Total: $%.2f\n\n", transaction.getTotalPrice()));
        }
        salesReportArea.setText(history.toString());
    }

    private void clearReport() {
        transactions.clear();
        saveTransactions();
        totalSalesLabel.setText("Total Sales: $0.00");
        salesReportArea.setText("");
        JOptionPane.showMessageDialog(this, "Sales report cleared");
    }

    private void saveTransactions() {
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

    public static void main(String[] args) {
        new SalesReport();
    }
}
