import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class TruckListing extends JPanel {
    private JButton cmdAddTruck;
    private JButton cmdEditTruck;
    private JButton cmdDeleteTruck;
    private JButton cmdClose;
    private JButton cmdSortPriceLH;
    private JButton cmdSortPriceHL;
    private JButton cmdSortMake;
    
    private JPanel pnlCommand;
    private JPanel pnlDisplay;
    private ArrayList<Truck> truckList;
    private TruckListing thisForm;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;

    public TruckListing() {
        super(new GridLayout(2, 1));
        thisForm = this;
        truckList = new ArrayList<>();

        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        truckList = loadTruck("Truck.dat");
        String[] columnNames = {"ID No.","Make", "Model", "Year", "Price $$","Off Road?" ,"Additional Features"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        showTable(truckList);

        table.setPreferredScrollableViewportSize(new Dimension(500, truckList.size() * 15 + 50));
        table.setFillsViewportHeight(true);
        

        scrollPane = new JScrollPane(table);
        
        add(scrollPane);

        cmdAddTruck = new JButton("Add Truck");
        cmdAddTruck.addActionListener(new AddButtonListener());
        pnlCommand.add(cmdAddTruck);

        cmdSortMake = new JButton("Sort by Make");
        cmdSortMake.addActionListener(new SortMakeListener());
        pnlCommand.add(cmdSortMake);

        cmdSortPriceHL = new JButton("Sort by Price: High to Low");
        cmdSortPriceHL.addActionListener(new SortPriceHLListener());
        pnlCommand.add(cmdSortPriceHL);

        
        cmdSortPriceLH = new JButton("Sort by Price: Low to High");
        pnlCommand.add(cmdSortPriceLH);

        cmdSortPriceLH.addActionListener(new SortPriceLHListener());
        cmdEditTruck = new JButton("Edit Truck Details");
        pnlCommand.add(cmdEditTruck);
        cmdEditTruck.addActionListener(new EditButtonListener());
        cmdDeleteTruck = new JButton("Delete Truck");
        cmdDeleteTruck.addActionListener(new DeleteTruckListener());
        pnlCommand.add(cmdDeleteTruck);

        cmdClose = new JButton("Close");
        CloseButtonListener listener = new CloseButtonListener();
        cmdClose.addActionListener(listener);
        pnlCommand.add(cmdClose);
        
        
        
        pnlCommand.setBackground(Color.GRAY);

        add(pnlCommand);
        
        table.setBackground(Color.LIGHT_GRAY);

        cmdAddTruck.setBackground(new Color(123, 50, 250));
        cmdAddTruck.setForeground(Color.WHITE);

        cmdSortPriceHL.setBackground(new Color(123, 50, 250));
        cmdSortPriceHL.setForeground(Color.WHITE);

        cmdSortPriceLH.setBackground(new Color(123, 50, 250));
        cmdSortPriceLH.setForeground(Color.WHITE);

        cmdSortMake.setBackground(new Color(123, 50, 250));
        cmdSortMake.setForeground(Color.WHITE);

        cmdEditTruck.setBackground(new Color(123, 50, 250));
        cmdEditTruck.setForeground(Color.WHITE);

        cmdClose.setBackground(Color.RED);
        cmdClose.setForeground(Color.WHITE);
    }
    /** 
     * @param TruckList
     */
    private void showTable(ArrayList<Truck> truckList) {
        model.setRowCount(0); // clear the model
        
        for (Truck truck : truckList) {
            addToTable(truck);
        }
    
        // Set the row height of the table
        table.setRowHeight(20); 
    
        // Set the preferred width of the "Additional Features" column
        TableColumn column = table.getColumnModel().getColumn(6);
        int preferredWidth = (int)table.getPreferredSize().getWidth()/2; // Divide the total width of the table by 2
        column.setPreferredWidth(preferredWidth);
    }
    
    
    private void addToTable(Truck t)
    {
        String OffRoad="No";
        if(t.isOffRoad()){
           OffRoad= "Yes";
        }

       
        String[] item={String.valueOf(t.getId()),t.getMake(),t.getModel(),String.valueOf(t.getYear()),String.valueOf(t.getPrice()),OffRoad,String.valueOf(t.getOtherFeatures())};
        model.addRow(item);        

    }
    

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("List of Trucks available");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        TruckListing newContentPane = new TruckListing();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
    }

    public void addTruck(Truck s)
    {
        truckList.add(s);
        addToTable(s);

    }

    public ArrayList<Truck> loadTruck(String cfile) {
        Scanner cscan = null;
        ArrayList<Truck> trucks = new ArrayList<>();
    
        try {
            cscan = new Scanner(new File(cfile));
            while (cscan.hasNext()) {
                String truckInfo = cscan.nextLine();
                String[] truckDetails = truckInfo.split(" ");
                String make = truckDetails[0];
                String model = truckDetails[1];
                int year = Integer.parseInt(truckDetails[2]);
                double price = Double.parseDouble(truckDetails[3]);
                boolean fourWheelDrive = Boolean.valueOf(truckDetails[4]);
    
                // Create a new list of features for each car
                ArrayList<Feature> otherFeatures = new ArrayList<>();
                for (int i = 5; i < truckDetails.length-1; i += 2) {
                   otherFeatures.add(new Feature(truckDetails[i], truckDetails[i + 1]));
                }
    
                Truck truck = new Truck(make, model, year, price, fourWheelDrive, otherFeatures);
                trucks.add(truck);
            }
            cscan.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("Cannot find that file.");
        } catch (IndexOutOfBoundsException iob) {
            System.out.println("Inappropriate number of elements for constructor");
        } catch (NumberFormatException nfe) {
            System.out.println("Year and Price must be numbers");
        }
        return trucks;
    }
    

/**
 * Inner class for the Add Truck button listener
 */
private class AddButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        new TruckEntry(thisForm);
    }
}

/**
 * Inner class for the Edit Truck button listener
 */
private class EditButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        int truckID = Integer.parseInt(JOptionPane.showInputDialog(thisForm, "Enter the ID number of the truck to edit:"));
        for (Truck truck:truckList) {
            if (truck.getId() == truckID) {
               truck.updateLocalData();
               model.setRowCount(0);
               showTable(truckList);
                }
            }
        }
    }


/**
 * Inner class for the Delete Truck button listener
 */
private class DeleteTruckListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        int truckID = Integer.parseInt(JOptionPane.showInputDialog(thisForm, "Enter the ID number of the Truck to delete:"));
        int truckIndex = -1;
        for (Truck truck:truckList) {
            if (truck.getId() == truckID) {
                truckIndex =truck.findVehicle(truckList,truckID);
                break;
            }
        }
        if (truckIndex != -1) {
            int choice = JOptionPane.showConfirmDialog(thisForm, "Are you sure you want to delete the car with ID " + truckID + "?");
            if (choice == JOptionPane.YES_OPTION) {
                model.removeRow(truckIndex);
                truckList.remove(truckIndex);
                System.out.println("Truck with id " + truckID + " deleted successfully");
            }
        } else {
            JOptionPane.showMessageDialog(thisForm, "Truck with ID " + truckID + " not found.");
        }
    }
}





/**
 * Inner class for the Sort Price High to Low button listener
 */
private class SortPriceHLListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Collections.sort(truckList, new PriceComparatorHL());
        model.setRowCount(0);
        showTable(truckList);
    }
}

/**
 * Inner class for the Sort Price Low to High button listener
 */
private class SortPriceLHListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Collections.sort(truckList, new PriceComparatorLH());
        model.setRowCount(0);
        
        showTable(truckList);
       
    }
}

/**
 * Inner class for the Sort Make button listener
 */
private class SortMakeListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Collections.sort(truckList, new MakeComparator());
        model.setRowCount(0);
        showTable(truckList);
        
    }
}

/**
 * Inner class for the Close button listener
 */
private class CloseButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}

/**
 * Inner class for sorting cars by price high to low
 */
private class PriceComparatorHL implements Comparator<Truck> {
    public int compare(Truck car1, Truck car2) {
        if (car1.getPrice() > car2.getPrice())
            return -1;
        else if (car1.getPrice() < car2.getPrice())
            return 1;
        else
            return 0;
    }
}

/**
 * Inner class for sorting cars by price low to high
 */
private class PriceComparatorLH implements Comparator<Truck> {
    public int compare(Truck car1, Truck car2) {
        if (car1.getPrice() < car2.getPrice())
            return -1;
        else if (car1.getPrice() > car2.getPrice())
            return 1;
        else
            return 0;
    }
}

/**
 * Inner class for sorting cars by make
 */
private class MakeComparator implements Comparator<Truck> {
    public int compare(Truck car1, Truck car2) {
        return car1.getMake().compareTo(car2.getMake());
    }
}

}

