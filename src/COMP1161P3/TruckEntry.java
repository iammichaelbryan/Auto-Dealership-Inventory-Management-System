import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class TruckEntry extends JFrame implements ActionListener {
    private JTextField txtMake;
    private JTextField txtModel;
    private JTextField txtYear;
    private JTextField txtPrice;
    private JTextField txtOtherFeatures;
    private JCheckBox chkFourWheelDrive;

    private JButton cmdSave;
    private JButton cmdClose;
    private TruckListing truckListing;
    private JPanel pnlCommand;
    private JPanel pnlDisplay;

    public TruckEntry(TruckListing truckListing) {
        this.truckListing = truckListing;
        try {
            setTitle("Entering new Truck");
            pnlCommand = new JPanel();
            pnlDisplay = new JPanel();
            pnlDisplay.setLayout(new GridLayout(5, 1));

            JPanel pnlMake = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlMake.add(new JLabel("Make:"));
            txtMake = new JTextField(20);
            pnlMake.add(txtMake);
            pnlDisplay.add(pnlMake);

            JPanel pnlModel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlModel.add(new JLabel("Model:"));
            txtModel = new JTextField(20);
            pnlModel.add(txtModel);
            pnlDisplay.add(pnlModel);

            JPanel pnlYear = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlYear.add(new JLabel("Year:"));
            txtYear = new JTextField(3);
            pnlYear.add(txtYear);
            pnlDisplay.add(pnlYear);

            JPanel pnlPrice = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlPrice.add(new JLabel("Price:"));
            txtPrice = new JTextField(20);
            pnlPrice.add(txtPrice);
            pnlDisplay.add(pnlPrice);

            JPanel pnlFourWheelDrive = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlFourWheelDrive.add(new JLabel("Off Road?:"));
            chkFourWheelDrive = new JCheckBox();
            pnlFourWheelDrive.add(chkFourWheelDrive);
            pnlDisplay.add(pnlFourWheelDrive);

            JPanel pnlOtherFeatures = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlOtherFeatures.add(new JLabel("Preferred Features (format: 'type description type description,...'):"));
            txtOtherFeatures = new JTextField(20);
            pnlOtherFeatures.add(txtOtherFeatures);
            pnlDisplay.add(pnlOtherFeatures);

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
        } catch (NumberFormatException nfe) {
            System.out.println("Price and year must be numbers");
        }
    }

    // Rest of the code remains the same


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cmdClose) {
            this.setVisible(false);
        } else if (e.getSource() == cmdSave) {
            
            String make = txtMake.getText();
            String model = txtModel.getText();
            double price;
            int year;
            try {
                year = Integer.parseInt(txtYear.getText());
                price = Double.parseDouble(txtPrice.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Year and Price must be numbers.");
                JOptionPane.showMessageDialog(truckListing, "Year and Price must be numbers.");
                return;
                
            
            }
            String otherFeaturesStr = txtOtherFeatures.getText();
            String[] featureValues = otherFeaturesStr.split(" ");
           
            ArrayList<Feature> otherFeatures = new ArrayList<Feature>();
            for (int i = 0; i < featureValues.length; i += 2) {
                String type = featureValues[i].trim();
                String description = featureValues[i + 1].trim();
                Feature feature = new Feature(type, description);
                otherFeatures.add(feature);
            }

            boolean fourWheelDrive = chkFourWheelDrive.isSelected();

            try{
                truckListing.addTruck(new Truck(make, model, year, price, fourWheelDrive, otherFeatures));
                this.setVisible(false);
            }
                catch (IndexOutOfBoundsException iob) {
                    System.out.println("Parameters inputted out of bounds");
                    JOptionPane.showMessageDialog(truckListing,"Parameters inputted out of bounds");
    
                    }
            
           
            
        }
    }
}

