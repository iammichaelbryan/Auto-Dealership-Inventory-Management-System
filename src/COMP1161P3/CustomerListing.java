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
 * CustomerListing class
 */
public class CustomerListing extends JPanel {
    private JButton cmdAddCustomer;
    private JButton cmdgetRecCar;
    private JButton cmdgetRecSUV;
    private JButton cmdgetRecTruck;
    private JButton cmdClose;
    private JButton cmdSortName;
    private JButton cmdSortBudget;
    private JButton cmdDeleteCustomer;
    private JPanel pnlCommand;
    private JPanel pnlDisplay;
    private ArrayList<Customer> customerList;
    private CustomerListing thisForm;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;
    private CarListing cListing= new CarListing();
    private SUVListing sListing = new SUVListing();
    private TruckListing tListing =  new TruckListing();

    /**
     * Constructor for the CustomerListing class
     */
    public CustomerListing() {
        super(new GridLayout(2, 1));
        thisForm = this;
        customerList = new ArrayList<>();

        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        customerList = loadCustomer("Customer.dat");
        String[] columnNames = {"ID No.","Name", "Budget", " Preffered Features"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        showTable(customerList);

        table.setPreferredScrollableViewportSize(new Dimension(500, customerList.size() * 15 + 50));
        table.setFillsViewportHeight(true);
        
        
        

        scrollPane = new JScrollPane(table);
        

        add(scrollPane);

        cmdAddCustomer = new JButton("Add Customer");
        cmdAddCustomer.addActionListener(new AddButtonListener());
        pnlCommand.add(cmdAddCustomer);

        cmdgetRecCar=new JButton("Get Recommended Car");
        cmdgetRecCar.addActionListener(new GetRecCarListener());
        pnlCommand.add(cmdgetRecCar);

        cmdgetRecSUV = new JButton("Get Recommended SUV");
        cmdgetRecSUV.addActionListener(new GetRecSUVListener());
        pnlCommand.add(cmdgetRecSUV);

        cmdgetRecTruck = new JButton("Get Recommended Truck");
        cmdgetRecTruck.addActionListener(new GetRecTruckListener());
        pnlCommand.add(cmdgetRecTruck);

        cmdDeleteCustomer = new JButton("Delete Customer");
        cmdDeleteCustomer.addActionListener(new DeleteCustomerListener());
        pnlCommand.add(cmdDeleteCustomer);


        cmdSortBudget= new JButton("Sort By Budget");
        cmdSortBudget.addActionListener(new SortBudgetListener());
        pnlCommand.add(cmdSortBudget);

        cmdSortName= new JButton("Sort By Name");
        cmdSortName.addActionListener(new SortNameListener());
        pnlCommand.add(cmdSortName);


        cmdClose = new JButton("Close");
        CloseButtonListener listener = new CloseButtonListener();
        cmdClose.addActionListener(listener);
        pnlCommand.add(cmdClose);
        
        
        
        
        pnlCommand.setBackground(Color.WHITE);

        add(pnlCommand);
        

        table.setBackground(Color.lightGray);

        cmdAddCustomer.setBackground(new Color(123, 50, 250));
        cmdAddCustomer.setForeground(Color.WHITE);

        cmdgetRecCar.setBackground(new Color(123, 50, 250));
        cmdgetRecCar.setForeground(Color.WHITE);

        cmdgetRecSUV.setBackground(new Color(123, 50, 250));
        cmdgetRecSUV.setForeground(Color.WHITE);

        cmdgetRecTruck.setBackground(new Color(123, 50, 250));
        cmdgetRecTruck.setForeground(Color.WHITE);


        cmdClose.setBackground(Color.RED);
        cmdClose.setForeground(Color.WHITE);
    }

    
    
    /** 
     * @param CustomerList
     */
    private void showTable(ArrayList<Customer> customerList) {
        model.setRowCount(0); // clear the model
        
        for (Customer customer : customerList) {
            addToTable(customer);
        }
    
        // Set the row height of the table
        table.setRowHeight(20); 
    
        // Set the preferred width of the "Preffered Features" column
        TableColumn column = table.getColumnModel().getColumn(3);
        int preferredWidth = (int)table.getPreferredSize().getWidth()/2; // Divide the total width of the table by 2
        column.setPreferredWidth(preferredWidth);
    }
    
    private void addToTable(Customer customer)
    {
       
        String[] item={String.valueOf(customer.getCusID()),customer.getName(),String.valueOf(customer.getBudget()),String.valueOf(customer.getPrefferedFeatures())};
        model.addRow(item);        

    }
    

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("List of Customers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        CustomerListing newContentPane = new CustomerListing();
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

    public void addCustomer(Customer c)
    {
        customerList.add(c);
        addToTable(c);

    }
     /**
     * 
     * @param cfile
     * @return  ArrayList<Customer>
     * Generates a list of customers from data in a text file
     */
    public ArrayList<Customer> loadCustomer(String cfile) {
        Scanner cscan = null;

        try {
            cscan = new Scanner(new File(cfile));
            while (cscan.hasNext()) {
                String customerInfo = cscan.nextLine();
                String[] customerDetails= customerInfo.split(" ");
                String name = customerDetails[0] +customerDetails[1];
                int budget = Integer.parseInt(customerDetails[2]);

                // Create a new list of features for each customer
                ArrayList<Feature> otherFeatures = new ArrayList<>();
                for(int i=3; i<customerDetails.length-1; i+=2) {
                    Feature f = new Feature(customerDetails[i], customerDetails[i+1]);
                    otherFeatures.add(f);
                }

                Customer customer = new Customer(name, budget, otherFeatures);
                customerList.add(customer);
            }
            cscan.close();
        } catch(FileNotFoundException fnf) {
            System.out.println("Cannot find that file.");
        } catch(IndexOutOfBoundsException iob) {
            System.out.println("Inappropriate number of elements for constructor");
        } catch(NumberFormatException nfe) {
            System.out.println("budget must be numeric");
        }

        return customerList;
        }
    

/**
 * Inner class for the Add Customer button listener
 */
private class AddButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        new CustomerEntry(thisForm);
    }
}


/**
 * Inner class for the Delete Customer button listener
 */
private class DeleteCustomerListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        int customerID = Integer.parseInt(JOptionPane.showInputDialog(thisForm, "Enter the ID number of the customer to delete:"));
        int customerIndex = -1;
        for (Customer customer:customerList) {
            if (customer.getCusID() == customerID) {
                customerIndex = customerList.indexOf(customer);
                break;
            }
        }
        if (customerIndex != -1) {
            int choice = JOptionPane.showConfirmDialog(thisForm, "Are you sure you want to delete the customer with ID " + customerID + "?");
            if (choice == JOptionPane.YES_OPTION) {
                model.removeRow(customerIndex);
                customerList.remove(customerIndex);
                JOptionPane.showMessageDialog(thisForm,"Customer with id " + customerID + " deleted successfully");
            }
        } else {
            JOptionPane.showMessageDialog(thisForm, "Customer with ID " + customerID + " not found.");
        }
    }
}





/**
 * Inner class for the Sort Budgets High to Low button listener
 */
private class SortBudgetListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Collections.sort(customerList, new BudgetCompare());
        model.setRowCount(0);
        showTable(customerList);
    }
}


/**
 * Inner class for the Sort Name button listener
 */
private class SortNameListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Collections.sort(customerList, new NameComparator());
        model.setRowCount(0);
        showTable(customerList);
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
 * Inner class for sorting customers by budget high to low
 */
private class BudgetCompare implements Comparator<Customer> {
    public int compare(Customer customer1, Customer customer2) {
        if (customer1.getBudget()> customer2.getBudget())
            return -1;
        else if (customer1.getBudget() < customer2.getBudget())
            return 1;
        else
            return 0;
    }
}



/**
 * Inner class for sorting customers by name
 */
private class NameComparator implements Comparator<Customer> {
    public int compare(Customer customer1, Customer customer2) {
        return customer1.getName().compareTo(customer2.getName());
    }
}

private class GetRecCarListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        int customerID = Integer.parseInt(JOptionPane.showInputDialog(thisForm, "Enter the ID number of the customer to recommend a car:"));
        Customer selectedCustomer = null;
        for (Customer customer : customerList) {
            if (customer.getCusID() == customerID) {
                selectedCustomer = customer;
                break;
            }
        }
        if (selectedCustomer != null) {
            ArrayList<Car> recommendedCars = selectedCustomer.getRecommendedCars(cListing.loadCars("Car.dat"));
            if (!recommendedCars.isEmpty()) {
                JOptionPane.showMessageDialog(thisForm, "Recommended car for customer with ID " + customerID + ":\n" + recommendedCars.get(0)+" "+"\n"+recommendedCars.get(0).getMatchPercentage(selectedCustomer)* 100 + "% match");
            } else {
                JOptionPane.showMessageDialog(thisForm, "No recommended car found for customer with ID " + customerID);
            }
        }
        else{
            JOptionPane.showMessageDialog(thisForm, "Customer with ID " + customerID+" not found");
        }
    }
}


private class GetRecSUVListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        int customerID = Integer.parseInt(JOptionPane.showInputDialog(thisForm, "Enter the ID number of the customer to recommend a SUV:"));
        Customer selectedCustomer = null;
        for (Customer customer : customerList) {
            if (customer.getCusID() == customerID) {
                selectedCustomer = customer;
                break;
            }
        }
        if (selectedCustomer != null) {
            ArrayList<SUV> recommendedSUVs = selectedCustomer.getRecommendedSUVs(sListing.loadSuv("SUV.dat"));
            if (!recommendedSUVs.isEmpty()) {
                JOptionPane.showMessageDialog(thisForm, "Recommended SUV for customer with ID " + customerID + ":\n" + recommendedSUVs.get(0) + "\n" + recommendedSUVs.get(0).getMatchPercentage(selectedCustomer)* 100 + "% match");
            } else {
                JOptionPane.showMessageDialog(thisForm, "No recommended SUV found for customer with ID " + customerID);
            }
        } else {
            JOptionPane.showMessageDialog(thisForm, "Customer with ID " + customerID + " not found");
        }
    }
}

private class GetRecTruckListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        int customerID = Integer.parseInt(JOptionPane.showInputDialog(thisForm, "Enter the ID number of the customer to recommend a Truck:"));
        Customer selectedCustomer = null;
        for (Customer customer : customerList) {
            if (customer.getCusID() == customerID) {
                selectedCustomer = customer;
                break;
            }
        }
        if (selectedCustomer != null) {
            ArrayList<Truck> recommendedTrucks = selectedCustomer.getRecommendedTrucks(tListing.loadTruck("Truck.dat"));
            if (!recommendedTrucks.isEmpty()) {
                JOptionPane.showMessageDialog(thisForm, "Recommended truck for customer with ID " + customerID + ":\n" + recommendedTrucks.get(0) + "\n" + recommendedTrucks.get(0).getMatchPercentage(selectedCustomer)* 100 + "% match");
            } else {
                JOptionPane.showMessageDialog(thisForm, "No recommended truck found for customer with ID " + customerID);
            }
        } else {
            JOptionPane.showMessageDialog(thisForm, "Customer with ID " + customerID + " not found");
        }
    }
}

}