import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EntryScreen extends JFrame {
    private JButton carButton;
    private JButton customerButton;
    private JButton suvButton;
    private JButton truckButton;
    private JButton agentButton;

    public EntryScreen() {
        super("Entry Screen");

        // Create buttons
        carButton = new JButton("Car Listing");
        customerButton = new JButton("Customer Listing");
        suvButton = new JButton("SUV Listing");
        truckButton = new JButton("Truck Listing");
        agentButton = new JButton("Agent Listing");

        // Add button listeners
        carButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame carFrame = new JFrame("Car Listing");
                carFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                carFrame.getContentPane().add(new CarListing());
                carFrame.pack();
                carFrame.setLocationRelativeTo(null);
                carFrame.setVisible(true);
            }
        });

        customerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame customerFrame = new JFrame("Customer Listing");
                customerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                customerFrame.getContentPane().add(new CustomerListing());
                customerFrame.pack();
                customerFrame.setLocationRelativeTo(null);
                customerFrame.setVisible(true);
            }
        });

        suvButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame suvFrame = new JFrame("SUV Listing");
                suvFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                suvFrame.getContentPane().add(new SUVListing());
                suvFrame.pack();
                suvFrame.setLocationRelativeTo(null);
                suvFrame.setVisible(true);
            }
        });

        truckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame truckFrame = new JFrame("Truck Listing");
                truckFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                truckFrame.getContentPane().add(new TruckListing());
                truckFrame.pack();
                truckFrame.setLocationRelativeTo(null);
                truckFrame.setVisible(true);
            }
        });

        agentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame agentFrame = new JFrame("Agent Listing");
                agentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                agentFrame.getContentPane().add(new AgentListing());
                agentFrame.pack();
                agentFrame.setLocationRelativeTo(null);
                agentFrame.setVisible(true);
            }
        });

        // Add buttons to panel
        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.add(carButton);
        panel.add(customerButton);
        panel.add(suvButton);
        panel.add(truckButton);
        panel.add(agentButton);

        // Add panel to frame
        add(panel);

        // Set frame properties
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EntryScreen();
    }
}
