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

public class SUVListing extends JPanel {
    private JButton cmdAddSUV;
    private JButton cmdEditSUV;
    private JButton cmdDeleteSUV;
    private JButton cmdClose;
    private JButton cmdSortPriceLH;
    private JButton cmdSortPriceHL;
    private JButton cmdSortMake;
    
    private JPanel pnlCommand;
    private JPanel pnlDisplay;
    private ArrayList<SUV> suvList;
    private SUVListing thisForm;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;

    public SUVListing() {
        super(new GridLayout(2, 1));
        thisForm = this;
        suvList = new ArrayList<>();

        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        suvList = loadSuv("SUV.dat");
        String[] columnNames = {"ID No.","Make", "Model", "Year", "Price $$","Four Wheel Drive?" ,"Additional Features"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        showTable(suvList);

        table.setPreferredScrollableViewportSize(new Dimension(500, suvList.size() * 15 + 50));
        table.setFillsViewportHeight(true);
        

        scrollPane = new JScrollPane(table);
        
        add(scrollPane);

        cmdAddSUV = new JButton("Add SUV");
        cmdAddSUV.addActionListener(new AddButtonListener());
        pnlCommand.add(cmdAddSUV);

        cmdSortMake = new JButton("Sort by Make");
        cmdSortMake.addActionListener(new SortMakeListener());
        pnlCommand.add(cmdSortMake);

        cmdSortPriceHL = new JButton("Sort by Price: High to Low");
        cmdSortPriceHL.addActionListener(new SortPriceHLListener());
        pnlCommand.add(cmdSortPriceHL);

        
        cmdSortPriceLH = new JButton("Sort by Price: Low to High");
        pnlCommand.add(cmdSortPriceLH);

        cmdSortPriceLH.addActionListener(new SortPriceLHListener());
        cmdEditSUV = new JButton("Edit SUV Details");
        pnlCommand.add(cmdEditSUV);
        cmdEditSUV.addActionListener(new EditButtonListener());
        cmdDeleteSUV = new JButton("Delete SUV");
        cmdDeleteSUV.addActionListener(new DeleteSUVListener());
        pnlCommand.add(cmdDeleteSUV);

        cmdClose = new JButton("Close");
        CloseButtonListener listener = new CloseButtonListener();
        cmdClose.addActionListener(listener);
        pnlCommand.add(cmdClose);
        
        
        
        pnlCommand.setBackground(Color.GRAY);

        add(pnlCommand);
        
        table.setBackground(Color.LIGHT_GRAY);

        cmdAddSUV.setBackground(new Color(123, 50, 250));
        cmdAddSUV.setForeground(Color.WHITE);

        cmdSortPriceHL.setBackground(new Color(123, 50, 250));
        cmdSortPriceHL.setForeground(Color.WHITE);

        cmdSortPriceLH.setBackground(new Color(123, 50, 250));
        cmdSortPriceLH.setForeground(Color.WHITE);

        cmdSortMake.setBackground(new Color(123, 50, 250));
        cmdSortMake.setForeground(Color.WHITE);

        cmdEditSUV.setBackground(new Color(123, 50, 250));
        cmdEditSUV.setForeground(Color.WHITE);

        cmdClose.setBackground(Color.RED);
        cmdClose.setForeground(Color.WHITE);
    }
    /** 
     * @param SUVList
     */
    private void showTable(ArrayList<SUV> suvList) {
        model.setRowCount(0); // clear the model
        
        for (SUV suv : suvList) {
            addToTable(suv);
        }
    
        // Set the row height of the table
        table.setRowHeight(20); // Replace 30 with the desired row height in pixels
    
        // Set the preferred width of the "Additional Features" column
        TableColumn column = table.getColumnModel().getColumn(6);
        int preferredWidth = (int)table.getPreferredSize().getWidth()/3; // Divide the total width of the table by 5
        column.setPreferredWidth(preferredWidth);
    }
    
    
    private void addToTable(SUV suv)
    {
        String fourWheelDrive="No";
        if(suv.isFourWheelDrive()){
            fourWheelDrive= "Yes";
        }

       
        String[] item={String.valueOf(suv.getId()),suv.getMake(),suv.getModel(),String.valueOf(suv.getYear()),String.valueOf(suv.getPrice()),fourWheelDrive,String.valueOf(suv.getOtherFeatures())};
        model.addRow(item);        

    }
    

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("List of SUVs available");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        SUVListing newContentPane = new SUVListing();
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

    public void addSUV(SUV s)
    {
        suvList.add(s);
        addToTable(s);

    }

    public ArrayList<SUV> loadSuv(String cfile) {
        Scanner cscan = null;
        ArrayList<SUV> suvs = new ArrayList<>();
    
        try {
            cscan = new Scanner(new File(cfile));
            while (cscan.hasNext()) {
                String suvInfo = cscan.nextLine();
                String[] suvDetails = suvInfo.split(" ");
                String make = suvDetails[0];
                String model = suvDetails[1];
                int year = Integer.parseInt(suvDetails[2]);
                double price = Double.parseDouble(suvDetails[3]);
                boolean fourWheelDrive = Boolean.valueOf(suvDetails[4]);
    
                // Create a new list of features for each car
                ArrayList<Feature> otherFeatures = new ArrayList<>();
                for (int i = 5; i < suvDetails.length-1; i += 2) {
                   otherFeatures.add(new Feature(suvDetails[i], suvDetails[i + 1]));
                }
    
                SUV suv = new SUV(make, model, year, price, fourWheelDrive, otherFeatures);
                suvs.add(suv);
            }
            cscan.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("Cannot find that file.");
        } catch (IndexOutOfBoundsException iob) {
            System.out.println("Inappropriate number of elements for constructor");
        } catch (NumberFormatException nfe) {
            System.out.println("Year and Price must be numbers");
        }
        return suvs;
    }
    

/**
 * Inner class for the Add SUV button listener
 */
private class AddButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        new SUVEntry(thisForm);
    }
}

/**
 * Inner class for the Edit SUV button listener
 */


private class EditButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        int suvID = Integer.parseInt(JOptionPane.showInputDialog(thisForm, "Enter the ID number of the suv to edit:"));
        for (SUV suv:suvList) {
            if (suv.getId() == suvID) {
               suv.updateLocalData();
               model.setRowCount(0);
               showTable(suvList);
                }
            }
        }
    }



/**
 * Inner class for the Delete SUV button listener
 */
private class DeleteSUVListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        int suvID = Integer.parseInt(JOptionPane.showInputDialog(thisForm, "Enter the ID number of the SUV to delete:"));
        int suvIndex = -1;
        for (SUV suv:suvList) {
            if (suv.getId() == suvID) {
                suvIndex=suv.findVehicle(suvList, suvID);
                break;
            }
        }
        if (suvIndex != -1) {
            int choice = JOptionPane.showConfirmDialog(thisForm, "Are you sure you want to delete the car with ID " + suvID + "?");
            if (choice == JOptionPane.YES_OPTION) {
                model.removeRow(suvIndex);
                suvList.remove(suvIndex);
                System.out.println("SUV with id " + suvID + " deleted successfully");
            }
        } else {
            JOptionPane.showMessageDialog(thisForm, "SUV with ID " + suvID + " not found.");
        }
    }
}





/**
 * Inner class for the Sort Price High to Low button listener
 */
private class SortPriceHLListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Collections.sort(suvList, new PriceComparatorHL());
        model.setRowCount(0);
        showTable(suvList);
    }
}

/**
 * Inner class for the Sort Price Low to High button listener
 */
private class SortPriceLHListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Collections.sort(suvList, new PriceComparatorLH());
        model.setRowCount(0);
        
        showTable(suvList);
       
    }
}

/**
 * Inner class for the Sort Make button listener
 */
private class SortMakeListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Collections.sort(suvList, new MakeComparator());
        model.setRowCount(0);
        showTable(suvList);
        
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
private class PriceComparatorHL implements Comparator<SUV> {
    public int compare(SUV car1, SUV car2) {
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
private class PriceComparatorLH implements Comparator<SUV> {
    public int compare(SUV car1, SUV car2) {
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
private class MakeComparator implements Comparator<SUV> {
    public int compare(SUV car1, SUV car2) {
        return car1.getMake().compareTo(car2.getMake());
    }
}

}
