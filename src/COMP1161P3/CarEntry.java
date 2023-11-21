
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
/**
 * @author Michael Bryan
 * @build COMP1161 Lab 6
 */
public class CarEntry extends JFrame implements ActionListener
{
    private JTextField  txtMake;       
    private JTextField  txtModel;
    private JTextField  txtYear;
    private JTextField  txtPrice;
    private JTextField  txtOtherFeatures;
            
    private JButton     cmdSave;
    private JButton     cmdClose;
    private CarListing carListing;
    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;
  
    
  /** Constructor for CarEntry, that uses existing table of persons to 
   * add functionality to add Cars to the list
   * @param CarListing
   * @return CarEntry
   * 
   */
  public CarEntry(CarListing carListing)
  {
      this.carListing = carListing;
      
      setTitle("Entering new Car");
      pnlCommand = new JPanel();
      pnlDisplay = new JPanel();
      pnlDisplay.add(new JLabel("Make:")); 
      txtMake = new JTextField(20);
      pnlDisplay.add(txtMake); 
      pnlDisplay.add(new JLabel("Model:")); 
      txtModel = new JTextField(20);
      pnlDisplay.add(txtModel);
      pnlDisplay.add(new JLabel("Year:"));
      txtYear = new JTextField(3);
      pnlDisplay.add(txtYear);
      pnlDisplay.add(new JLabel("Price:")); 
      txtPrice = new JTextField(20);
      pnlDisplay.add(txtPrice);
      pnlDisplay.setLayout(new GridLayout(3, 2, 1,1));
      pnlDisplay.add(new JLabel("Other Features: in (format: 'type description type description,...'):"));
      pnlDisplay.add(txtOtherFeatures = new JTextField(20));
  
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
      
  
  

    /**
     * Catches event of interaction with "Save and "Close" buttons
     * gives the event power to provide functionality to buttons
     * 
     * @param ActionEvent
     * @return void
     * 
     */

     @Override
     public void actionPerformed(ActionEvent e) {
         if (e.getSource()==cmdClose){
             this.setVisible(false);
             //this.dispose(); //to close window if close is selected 
         }
         else if (e.getSource()== cmdSave){
             String make =txtMake.getText();
             String model =txtModel.getText();
             int year;
             double price;
             try {
                 year = Integer.parseInt(txtYear.getText());
                 price = Double.parseDouble(txtPrice.getText());
             } catch (NumberFormatException ex) {
                 System.out.println("Year and Price must be numbers.");
                 JOptionPane.showMessageDialog(carListing, "Year and Price must be numbers");
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
             try {
                 carListing.addCar(new Car(make,model,year,price,otherFeatures));
                 this.setVisible(false);// removes carEntry form after car is added
                                         // could also utilize this.dispose()?
             } catch (IndexOutOfBoundsException iob){
                 System.out.println("Parameters inputted out of bounds");
             }
         }
     }
    }
     
           
             
             


        
