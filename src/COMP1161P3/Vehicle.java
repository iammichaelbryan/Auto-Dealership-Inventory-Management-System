import java.util.ArrayList;

public class Vehicle {
	
	private String make;
	private String model;
	private int year;
	private double price;
	private int id;
	private static int nextId = 1000;
	protected String vehicleTypes;
	
		
	public Vehicle(String make, String model, int year, double price){
		this.setMake(make);
		this.setModel(model);
		this.setYear(year);
		this.setPrice(price);
		this.id= nextId;
		nextId++;
		this.vehicleTypes = "BASEVEHICLE:";
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getVehicleTypes() {
		return vehicleTypes;
	}
	
	public void setVehicleTypes(String vehicleTypes) {
		this.vehicleTypes = vehicleTypes;
	}
	
	public String getMake() {
		return make;
	}
	
	public String getModel() {
		return model;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public void setYear(int year) {
		if (year < 2000 || year > 2023) {
			throw new IllegalArgumentException("Year must be between 2000 and 2023");
		}
		this.year = year;
	}
	
	
	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice() {
		return price;
	}
	public String getStrPrice(){
        double price=getPrice();
		return String.format("%,.2f", price);

    }
	
	@Override
	public String toString() {
		return make + " " + model + ", " + year + " selling for $" + getStrPrice();
	}
	
	public static int getNextId() {
		return nextId++;
	}

	public int findVehicle(ArrayList< ?extends Vehicle>vehicles, int id){
        int pos=-1;
        for (Vehicle v:vehicles){
        if (v.getId()==id){
            pos = vehicles.indexOf(v);
        }
    }
        return pos;
    }
}