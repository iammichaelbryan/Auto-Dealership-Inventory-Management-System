import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.Collections;
import java.awt.Color;



/**
 * CarListing class
 */
public class CarListing extends JPanel {
    private JButton cmdAddCar;
    private JButton cmdEditCar;
    private JButton cmdDeleteCar;
    private JButton cmdClose;
    private JButton cmdSortPriceLH;
    private JButton cmdSortPriceHL;
    private JButton cmdSortMake;
    private JPanel pnlCommand;
    private JPanel pnlDisplay;
    private ArrayList<Car> carList;
    private CarListing thisForm;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<Customer> customers;

    /**
     * Constructor for the CarListing class
     */
    public CarListing() {
        super(new GridLayout(2, 1));
        thisForm = this;
        carList = new ArrayList<>();

        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        carList = loadCars("Car.dat");
        String[] columnNames = {"ID No.","Make", "Model", "Year", "Price $$", "Additional Features"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        showTable(carList);

        table.setPreferredScrollableViewportSize(new Dimension(500, carList.size() * 15 + 50));
        table.setFillsViewportHeight(true);
        
        
        

        scrollPane = new JScrollPane(table);
        

        add(scrollPane);

        cmdAddCar = new JButton("Add Car");
        cmdAddCar.addActionListener(new AddButtonListener());
        pnlCommand.add(cmdAddCar);

        cmdSortMake = new JButton("Sort by Make");
        cmdSortMake.addActionListener(new SortMakeListener());
        cmdSortPriceHL = new JButton("Sort by Price: High to Low");
        cmdSortPriceHL.addActionListener(new SortPriceHLListener());
        cmdSortPriceLH = new JButton("Sort by Price: Low to High");
        cmdSortPriceLH.addActionListener(new SortPriceLHListener());
        cmdEditCar = new JButton("Edit Car Details");
        cmdEditCar.addActionListener(new EditButtonListener());
        cmdDeleteCar = new JButton("Delete Car");
        cmdDeleteCar.addActionListener(new DeleteCarListener());
        cmdClose = new JButton("Close");
        CloseButtonListener listener = new CloseButtonListener();
        cmdClose.addActionListener(listener);
        pnlCommand.add(cmdSortMake);
        pnlCommand.add(cmdSortPriceHL);
        pnlCommand.add(cmdSortPriceLH);
        pnlCommand.add(cmdEditCar);
        pnlCommand.add(cmdDeleteCar);
        pnlCommand.add(cmdClose);
        pnlCommand.setBackground(Color.GRAY);

        add(pnlCommand);
        

        table.setBackground(Color.LIGHT_GRAY);

        cmdAddCar.setBackground(new Color(123, 50, 250));
        cmdAddCar.setForeground(Color.WHITE);

        cmdSortPriceHL.setBackground(new Color(123, 50, 250));
        cmdSortPriceHL.setForeground(Color.WHITE);

        cmdSortPriceLH.setBackground(new Color(123, 50, 250));
        cmdSortPriceLH.setForeground(Color.WHITE);

        cmdSortMake.setBackground(new Color(123, 50, 250));
        cmdSortMake.setForeground(Color.WHITE);

        cmdEditCar.setBackground(new Color(123, 50, 250));
        cmdEditCar.setForeground(Color.WHITE);

        cmdClose.setBackground(Color.RED);
        cmdClose.setForeground(Color.WHITE);
    }

    
    
    /** 
     * @param CarList
     */
    private void showTable(ArrayList<Car> carList) {
        model.setRowCount(0); // clear the model
        
        for (Car car : carList) {
            addToTable(car);
        }
    
        // Set the row height of the table
        table.setRowHeight(20); // Replace 30 with the desired row height in pixels
    
        // Set the preferred width of the "Additional Features" column
        TableColumn column = table.getColumnModel().getColumn(5);
        int preferredWidth = (int)table.getPreferredSize().getWidth()/2; // Divide the total width of the table by 2
        column.setPreferredWidth(preferredWidth);
    }
    
    private void addToTable(Car car)
    {
       
        String[] item={String.valueOf(car.getId()),car.getMake(),car.getModel(),String.valueOf(car.getYear()),String.valueOf(car.getPrice()),String.valueOf(car.getOtherFeatures())};
        model.addRow(item);        

    }
    

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("List of Cars available");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        CarListing newContentPane = new CarListing();
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

    public void addCar(Car c)
    {
        carList.add(c);
        addToTable(c);

    }
    /**
     * 
     * @param cfile
     * @return  ArrayList<Car>
     * Generates a list of cars from data in a text file
     */
    public ArrayList<Car> loadCars(String cfile){
        Scanner cscan = null;
       

        try{
            cscan = new Scanner(new File(cfile));
            while (cscan.hasNext()){
                String carInfo = cscan.nextLine();
                String[] carDetails= carInfo.split(" ");
                String make = carDetails[0];
                String model = carDetails[1];
                int year = Integer.parseInt(carDetails[2]);
                double price = Double.parseDouble(carDetails[3]);

                // Create a new list of features for each car
                ArrayList<Feature> otherFeatures = new ArrayList<>();
                for(int i=4; i<carDetails.length-1; i+=2){
                    Feature f = new Feature(carDetails[i], carDetails[i+1]);
                    otherFeatures.add(f);
                }

                Car c = new Car(make, model, year, price, otherFeatures);
                carList.add(c);
            }
            cscan.close();
        } catch(FileNotFoundException fnf) {
            System.out.println("Cannot find that file.");
        } catch(IndexOutOfBoundsException iob) {
            System.out.println("Inappropriate number of elements for constructor");
        } catch(NumberFormatException nfe) {
            System.out.println("Year and Price must be numbers");
        }

        return carList;
    }
    
    

/**
 * Inner class for the Add Car button listener
 */
private class AddButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        new CarEntry(thisForm);
    }
}

/**
 * Inner class for the Edit Car button listener
 */
private class EditButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        int carID = Integer.parseInt(JOptionPane.showInputDialog(thisForm, "Enter the ID number of the car to edit:"));
        for (Car car:carList) {
            if (car.getId() == carID) {
               car.updateLocalData();
               model.setRowCount(0);
               showTable(carList);
                }
            }
        }
    }

/**
 * Inner class for the Delete Car button listener
 */
private class DeleteCarListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        int carID = Integer.parseInt(JOptionPane.showInputDialog(thisForm, "Enter the ID number of the car to delete:"));
        int carIndex = -1;
        for (Car car:carList) {
            if (car.getId() == carID) {
                carIndex = car.findVehicle(carList,carID);
                break;
            }
        }
        if (carIndex != -1) {
            int choice = JOptionPane.showConfirmDialog(thisForm, "Are you sure you want to delete the car with ID " + carID + "?");
            if (choice == JOptionPane.YES_OPTION) {
                model.removeRow(carIndex);
                carList.remove(carIndex);
                JOptionPane.showMessageDialog(thisForm,"Car with id " + carID + " deleted successfully");
            }
        } else {
            JOptionPane.showMessageDialog(thisForm, "Car with ID " + carID + " not found.");
        }
    }
}





/**
 * Inner class for the Sort Price High to Low button listener
 */
private class SortPriceHLListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Collections.sort(carList, new PriceComparatorHL());
        model.setRowCount(0);
        showTable(carList);
    }
}

/**
 * Inner class for the Sort Price Low to High button listener
 */
private class SortPriceLHListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Collections.sort(carList, new PriceComparatorLH());
        model.setRowCount(0);
        showTable(carList);
    }
}

/**
 * Inner class for the Sort Make button listener
 */
private class SortMakeListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Collections.sort(carList, new MakeComparator());
        model.setRowCount(0);
        showTable(carList);
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
private class PriceComparatorHL implements Comparator<Car> {
    public int compare(Car car1, Car car2) {
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
private class PriceComparatorLH implements Comparator<Car> {
    public int compare(Car car1, Car car2) {
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
private class MakeComparator implements Comparator<Car> {
    public int compare(Car car1, Car car2) {
        return car1.getMake().compareTo(car2.getMake());
    }
}

}
