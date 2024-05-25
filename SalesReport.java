import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SalesReport extends JFrame implements ActionListener {

    private JTextArea salesReportArea;
    private JButton goHomeButton;

    public SalesReport() {
        setTitle("Sales Report");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        salesReportArea = new JTextArea();
        salesReportArea.setEditable(false);

        goHomeButton = new JButton("Go Home");
        goHomeButton.addActionListener(this);

        add(new JScrollPane(salesReportArea), BorderLayout.CENTER);
        add(goHomeButton, BorderLayout.SOUTH);

        // Logic to populate salesReportArea with sales data

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == goHomeButton) {
            dispose();
        }
    }
}
