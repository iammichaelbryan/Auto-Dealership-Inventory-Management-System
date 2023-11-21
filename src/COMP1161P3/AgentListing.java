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
 * AgentListing class
 */
public class AgentListing extends JPanel {
    private JButton cmdGetCommission;
    private JButton cmdGetFinalSalary;
    private JButton cmdClose;
    private JButton cmdSortName;
    private JButton cmdDeleteAgent;
    private JPanel pnlCommand;
    private JPanel pnlDisplay;
    private ArrayList<Agent> agentList;
    private AgentListing thisForm;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;

    /**
     * Constructor for the AgentListing class
     */
    public AgentListing() {
        super(new GridLayout(2, 1));
        thisForm = this;
        agentList = new ArrayList<>();

        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        agentList = loadAgent("Agent.dat");
        String[] columnNames = {"ID No.","Name", "Vehicles Sold"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        showTable(agentList);

        table.setPreferredScrollableViewportSize(new Dimension(500, agentList.size() * 15 + 50));
        table.setFillsViewportHeight(true);
        
        
        

        scrollPane = new JScrollPane(table);
        

        add(scrollPane);

        

        cmdGetCommission=new JButton("Get Agent's Commission");
        cmdGetCommission.addActionListener(new GetCommissionistener());
        pnlCommand.add(cmdGetCommission);

        cmdGetFinalSalary = new JButton("Get Agent's Final Salary");
        cmdGetFinalSalary.addActionListener(new GetFinalListener());
        pnlCommand.add(cmdGetFinalSalary);

        cmdDeleteAgent = new JButton("Delete Agent");
        cmdDeleteAgent.addActionListener(new DeleteAgentListener());
        pnlCommand.add(cmdDeleteAgent);

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

        

        cmdGetCommission.setBackground(new Color(123, 50, 250));
        cmdGetCommission.setForeground(Color.WHITE);

        cmdGetFinalSalary.setBackground(new Color(123, 50, 250));
        cmdGetFinalSalary.setForeground(Color.WHITE);

        cmdSortName.setBackground(Color.blue);
        cmdSortName.setForeground(Color.WHITE);


        cmdClose.setBackground(Color.RED);
        cmdClose.setForeground(Color.WHITE);
    }

    
    
    /** 
     * @param AgentList
     */
    private void showTable(ArrayList<Agent> agentList) {
        model.setRowCount(0); // clear the model
        
        for (Agent agent:agentList){
            addToTable(agent);
        }
    
        // Set the row height of the table
        table.setRowHeight(20); 
    
        // Set the preferred width of the "Vehicles Sold" column
        TableColumn column = table.getColumnModel().getColumn(2);
        int preferredWidth = (int)table.getPreferredSize().getWidth()/2; // Divide the total width of the table by 2
        column.setPreferredWidth(preferredWidth);
    }
    
    private void addToTable(Agent agent)
    {
       
        String[] item={String.valueOf(agent.getId()),agent.getName(),String.valueOf(agent.getVehiclesSold())};
        model.addRow(item);        

    }
    

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("List of Agents");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        AgentListing newContentPane = new AgentListing();
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

    public void addAgent(Agent a)
    {
        agentList.add(a);
        addToTable(a);

    }
    /**
     * 
     * @param String cfile
     * @return ArrayList<Agent>
     */
    public ArrayList<Agent> loadAgent(String cfile){
        Scanner cscan = null;
        ArrayList<Agent> agents = new ArrayList<>();
    
        try{
            cscan = new Scanner(new File(cfile));
            while (cscan.hasNext()){
                String agentInfo = cscan.nextLine();
                String[] agentDetails= agentInfo.split(" ");
                String name = agentDetails[0]+" "+agentDetails[1];
                
                ArrayList<Vehicle> vehiclesSold = new ArrayList<>();
                for(int i = 2; i < agentDetails.length - 1; i += 4){
                    String make = agentDetails[i];
                    String model = agentDetails[i + 1];
                    int year = Integer.parseInt(agentDetails[i + 2]);
                    double price = Double.parseDouble(agentDetails[i + 3]);
                    Vehicle v = new Vehicle(make, model, year, price);
                    vehiclesSold.add(v);
                }
                
                Agent agent = new Agent(name, vehiclesSold);
                agents.add(agent);
            }
            cscan.close();
        } 
        catch(NumberFormatException nfe){
            System.out.println("Year and price of vehicle must be numbers");
        }
        catch(FileNotFoundException fnf) {
            System.out.println("Cannot find that file.");
        } 
        catch(IndexOutOfBoundsException iob) {
            System.out.println("Inappropriate number of elements for constructor");
        }
    
        return agents;
    }
    




/**
 * Inner class for the Delete Agent button listener
 */
private class DeleteAgentListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        int agentID = Integer.parseInt(JOptionPane.showInputDialog(thisForm, "Enter the ID number of the agent to delete:"));
        int agentIndex = -1;
        for (Agent agent:agentList) {
            if (agent.getId() == agentID) {
                agentIndex = agentList.indexOf(agent);
                break;
            }
        }
        if (agentIndex != -1) {
            int choice = JOptionPane.showConfirmDialog(thisForm, "Are you sure you want to delete the agent with ID " + agentID + "?");
            if (choice == JOptionPane.YES_OPTION) {
                model.removeRow(agentIndex);
                agentList.remove(agentIndex);
                JOptionPane.showMessageDialog(thisForm,"Agent with id " + agentID + " deleted successfully");
            }
        } else {
            JOptionPane.showMessageDialog(thisForm, "Agent with ID " + agentID + " not found.");
        }
    }
}

/**
 * Inner class for the Sort Name button listener
 */
private class SortNameListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Collections.sort(agentList, new NameComparator());
        model.setRowCount(0);
        showTable(agentList);
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
 * Inner class for sorting agents by name
 */
private class NameComparator implements Comparator<Agent> {
    public int compare(Agent agent1, Agent agent2) {
        return agent1.getName().compareTo(agent2.getName());
    }
}
/**
 * 
 *ActionListener that gives get commission button its functionality
 */
private class GetCommissionistener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        int agentID = Integer.parseInt(JOptionPane.showInputDialog(thisForm, "Enter the ID number of the agent to get Commision:"));
        Agent selectedAgent = null;
        for (Agent agent : agentList) {
            if (agent.getId() == agentID) {
                selectedAgent = agent;
                break;
            }
        }
        if (selectedAgent != null) {
            ArrayList<?extends Vehicle> vehicleSold= selectedAgent.getVehiclesSold();
            if (!vehicleSold.isEmpty()) {
                JOptionPane.showMessageDialog(thisForm, "Commission for agent with ID " + agentID + ":\n" +"$"+ selectedAgent.getStrCommission(vehicleSold));
                } else {
                JOptionPane.showMessageDialog(thisForm, "Agent with ID " + agentID + "probably did not sell any vehicles");
            }
        }
        else{
            JOptionPane.showMessageDialog(thisForm, "Agent with ID " + agentID+" not found");
        }
    }
}

/**
 * 
 *ActionListener that gives get final salary button its functionality
 */
private class GetFinalListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        int agentID = Integer.parseInt(JOptionPane.showInputDialog(thisForm, "Enter the ID number of the agent to get Final Salary:"));
        Agent selectedAgent = null;
        for (Agent agent : agentList) {
            if (agent.getId() == agentID) {
                selectedAgent = agent;
                break;
            }
        }
        if (selectedAgent != null) {
            ArrayList<?extends Vehicle> vehicleSold= selectedAgent.getVehiclesSold();
            if (!vehicleSold.isEmpty()) {
                JOptionPane.showMessageDialog(thisForm, "Final Salary for agent with ID " + agentID + ":\n" + "$"+selectedAgent.getStrFinalSalary(vehicleSold));
                } else {
                JOptionPane.showMessageDialog(thisForm, "Agent with ID " + agentID + " probably did not sell any vehicles, Final Salary is "+"$"+selectedAgent.getStrBaseSalary()+"0");
            }
        }
        else{
            JOptionPane.showMessageDialog(thisForm, "Agent with ID " + agentID+" not found");
        }
    }
}

    }