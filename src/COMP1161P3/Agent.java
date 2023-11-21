import java.util.ArrayList;
/**
 * Agent class
 */
public class Agent {
    private String name;
    private ArrayList<? extends Vehicle> vehiclesSold;
    private final double baseSalary= 1200000;
    private int id;
	private static int nextId = 1000;
    public Agent(){
    }
    /**
 * Agent Constructor
 * @param String name
 * @param ArrayList<? extends Vehicle>
 * @return Agent
 */
    public Agent(String name, ArrayList<? extends Vehicle> vehiclesSold) {
        this.name = name;
        this.vehiclesSold = vehiclesSold;
        this.id=getNextId();
    }

    public double getBaseSalary() {
        return Double.parseDouble(String.format("%.2f", baseSalary));
    }

    public String getName() {
        return name;
    }

    public ArrayList<? extends Vehicle> getVehiclesSold() {
        return vehiclesSold;
    }

    public void setVehiclesSold(ArrayList<? extends Vehicle> vehiclesSold) {
        this.vehiclesSold = vehiclesSold;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSales(ArrayList<? extends Vehicle> vehiclesSold){
        double total=0;
        for (Vehicle v: vehiclesSold){
            total+=v.getPrice();
        }
        return total;
    }
    public int getId() {
        return id;
    }

    public static int getNextId() {
        return ++nextId;
    }

    public double getCommission(ArrayList<? extends Vehicle> vehiclesSold) {
        double commission = 0;
        for (Vehicle v : vehiclesSold) {
            commission += (v.getPrice() * 0.10);
        }
        return Double.parseDouble(String.format("%2f", commission));
    }
    

    public double getFinalSalary(ArrayList<? extends Vehicle> vehiclesSold){
        double finalSalary = getBaseSalary() + getCommission(vehiclesSold);
        return Double.parseDouble(String.format("%2f", finalSalary));
    }

    public String getStrSales(ArrayList<? extends Vehicle> vehiclesSold) {
        double sales = getSales(vehiclesSold);
        return String.format("%,.2f", sales);
    }
    public String getStrCommission(ArrayList<? extends Vehicle> vehiclesSold){
        double commission =getCommission(vehiclesSold);
        return String.format("%,.2f", commission);

    }
    public String getStrFinalSalary(ArrayList<? extends Vehicle> vehiclesSold){
        double finalSalary =getFinalSalary(vehiclesSold);
            return String.format("%,.2f", finalSalary);

    }

    public String getStrBaseSalary(){
        double baseSalary =getBaseSalary();
        return String.format("%,.2f", baseSalary);

    }


    @Override
    public String toString() {
        return "Agent: "+getName()+" has a base salary of $"+getBaseSalary()+", has sold: "+vehiclesSold +" which is $"+getStrSales(vehiclesSold)+" in Sales, therefore earned 10% commision of $"+getStrCommission(vehiclesSold)+" therefore their final salary is: $"+getStrFinalSalary(vehiclesSold);
    }
   
}
    
