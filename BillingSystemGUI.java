import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillingSystemGUI extends JFrame {
    private JTextArea billTextArea;
    private JTextField itemField, quantityField;
    private double totalCost;

    public BillingSystemGUI() {
      
        setTitle("Billing System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        JPanel inputPanel = new JPanel();
        JLabel itemLabel = new JLabel("Item:");
        itemField = new JTextField(15);
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(5);
        JButton addButton = new JButton("Add to Bill");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToBill();
            }
        });

        
        billTextArea = new JTextArea(10, 30);
        billTextArea.setEditable(false);

        
        JButton generateButton = new JButton("Generate Receipt");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReceipt();
            }
        });

     
        inputPanel.add(itemLabel);
        inputPanel.add(itemField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
        inputPanel.add(addButton);

  
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(billTextArea), BorderLayout.CENTER);
        add(generateButton, BorderLayout.SOUTH);
    }

    private void addToBill() {
        String item = itemField.getText();
        try {
            int quantity = Integer.parseInt(quantityField.getText());
           
            double itemCost = calculateItemCost(item, quantity);
            billTextArea.append(item + " x" + quantity + " = $" + itemCost + "\n");
            totalCost += itemCost;
            itemField.setText("");
            quantityField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a number.");
        }
    }

    private double calculateItemCost(String item, int quantity) {
        
        double itemCost = 10.0; 
        return itemCost * quantity;
    }

    private void generateReceipt() {
       
        billTextArea.append("Total: $" + totalCost + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BillingSystemGUI billingSystem = new BillingSystemGUI();
            billingSystem.setVisible(true);
        });
    }
}
