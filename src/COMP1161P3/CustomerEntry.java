import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class CustomerEntry extends JFrame implements ActionListener {
    private JTextField txtName;
    private JTextField txtBudget;
    private JTextField txtFeatures;

    private JButton cmdSave;
    private JButton cmdClose;
    private CustomerListing customerListing;
    private JPanel pnlCommand;
    private JPanel pnlDisplay;

    public CustomerEntry(CustomerListing customerListing) {
        this.customerListing = customerListing;

        setTitle("Entering new Customer");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        pnlDisplay.add(new JLabel("Name:"));
        txtName = new JTextField(20);
        pnlDisplay.add(txtName);
        pnlDisplay.add(new JLabel("Budget:"));
        txtBudget = new JTextField(20);
        pnlDisplay.add(txtBudget);
        pnlDisplay.add(new JLabel("Preferred Features (format: 'type description type description,...'):"));
        txtFeatures = new JTextField(20);
        pnlDisplay.add(txtFeatures);
        pnlDisplay.setLayout(new GridLayout(3, 2, 1, 1));

        cmdSave = new JButton("Save");
        cmdClose = new JButton("Close");
        cmdClose.addActionListener(this);
        cmdSave.addActionListener(this);

        pnlCommand.add(cmdSave);
        pnlCommand.add(cmdClose);
        add(pnlDisplay, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cmdClose) {
            this.setVisible(false);
        } else if (e.getSource() == cmdSave) {
            String name = txtName.getText();
            int budget;
            try {
                budget = Integer.parseInt(txtBudget.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Budget must be a number.");
                JOptionPane.showMessageDialog(customerListing, "Budget must be a number.");
                return;
            }
            String featuresStr = txtFeatures.getText();
            String[] featureValues = featuresStr.split(" ");
            ArrayList<Feature> preferredFeatures = new ArrayList<Feature>();
            for (int i = 0; i < featureValues.length; i += 2) {
                String type = featureValues[i].trim();
                String description = featureValues[i + 1].trim();
                Feature feature = new Feature(type, description);
                preferredFeatures.add(feature);
            }
            try {
                customerListing.addCustomer(new Customer(name, budget, preferredFeatures));
                this.setVisible(false);
            } catch (IndexOutOfBoundsException iob) {
                System.out.println("Parameters inputted out of bounds");
            }
        }
    }
}

