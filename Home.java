import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame implements ActionListener {

    private JButton editProductsButton, cashierButton, salesReportButton, exitButton;

    public Home() {
        setTitle("Home");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        
        editProductsButton = new JButton("Edit Products");
        editProductsButton.addActionListener(this);
        panel.add(editProductsButton);

        cashierButton = new JButton("Cashier");
        cashierButton.addActionListener(this);
        panel.add(cashierButton);

        salesReportButton = new JButton("Sales Report");
        salesReportButton.addActionListener(this);
        panel.add(salesReportButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        panel.add(exitButton);

        add(panel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editProductsButton) {
            new EditProducts();
        } else if (e.getSource() == cashierButton) {
            new Cashier();
        } else if (e.getSource() == salesReportButton) {
            new SalesReport();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}
