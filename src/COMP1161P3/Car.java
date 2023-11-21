import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;


/**
 * Car Object class
 * 
 */
public class Car extends Vehicle implements Comparable<Car> {
    CarListing thisForm;
    HashMap<String, String> featureMap;
    private ArrayList<Feature> otherFeatures;
    private double matchPercentage;
    
    

    
/**
 * Constructor for car object
 * @param String make
    @param String model
    @param int year
    @param double price
    @param ArrayList<Feature> otherFeatures

    @return Car Object
 */
    

    public Car(String make, String model, int year, double price, ArrayList<Feature> otherFeatures) {
       super(make, model, year, price);
        this.otherFeatures=otherFeatures;
        vehicleTypes+=", CAR";
        this.featureMap= new HashMap<>();
        for (Feature f:otherFeatures){
            featureMap.put(f.getFeatureType(),f.getFeatureDescription());
        }
        featureMap.put("Make", make);
    }

    public HashMap<String, String> getFeatureMap() {
        return featureMap;
    }

    public ArrayList<Feature> getOtherFeatures() {
        return otherFeatures;
    }

    public void setOtherFeatures(ArrayList<Feature> otherFeatures) {
        this.otherFeatures = otherFeatures;
    }

    @Override
    public String toString() {
        return super.toString() + " Additional Features: "+ getOtherFeatures();
    }

    @Override
    public int compareTo(Car o) {
        return  (int)(this.getYear()-o.getYear());
    }
    /** Method updateLocalData is used to update attributes within a specific Car object 
     * 
   * 
   * @param 
   * 
   * @return void
   * 
   */
    public void updateLocalData() {
        String currMake = getMake();
        String currModel = getModel();
        int currYear = getYear();
        double currPrice = getPrice();
        ArrayList<Feature> currFeatures = getOtherFeatures();
    
        String make = JOptionPane.showInputDialog(thisForm, "Hit enter to keep make as [" + currMake + "], or enter new make:");
        if (make.equals("")) {
            make = currMake;
        }
    
        String model = JOptionPane.showInputDialog(thisForm, "Hit enter to keep model as [" + currModel + "], or enter new model:");
        if (model.equals("")) {
            model = currModel;
        }
    
        String yearEntered = JOptionPane.showInputDialog(thisForm, "Hit enter to keep year at [" + currYear + "] or enter new year:");
        int year;
        if (yearEntered.equals("")) {
            year = currYear;
        } else {
            year = Integer.parseInt(yearEntered);
        }
    
        String priceEntered = JOptionPane.showInputDialog(thisForm, "Hit enter to keep price at [" + currPrice + "] or enter new price:");
        double price;
        if (priceEntered.equals("")) {
            price = currPrice;
        } else {
            price = Double.parseDouble(priceEntered);
        }
    
        String featureEntered = JOptionPane.showInputDialog(thisForm, "Hit enter to keep features as [" + currFeatures.toString().substring(0, 3)+ "....] or enter new features (format: 'type,description,type,description,...'):");
        if (!featureEntered.equals("")) {
            String[] featureValues = featureEntered.split(",");
            ArrayList<Feature> features = new ArrayList<Feature>();
            for (int i = 0; i < featureValues.length; i += 2) {
                String type = featureValues[i].trim();
                String description = featureValues[i + 1].trim();
                Feature feature = new Feature(type, description);
                features.add(feature);
            }
            currFeatures = features;
        }
        int choice = JOptionPane.showConfirmDialog(thisForm, "Are you sure you want to make these changes the car with ID"+ getId()+"?");
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(thisForm,"Car with id " + getId() + " edited successfully");
            setMake(make);
            setModel(model);
            setYear(year);
            setPrice(price);
            setOtherFeatures(currFeatures);
        }
    }

    /**
     * 
     * @param customer
     * @return double 
     * Method that gives a match percentage based on features of a car and customer's preffered features
     */
    public double getMatchPercentage(Customer customer) {
		// Calculate the percentage of preferred features that are present in the car
		int numPreferredFeatures = customer.getPrefferedFeatures().size();
		int numMatchingFeatures = 0;
		for (Feature feature : customer.getPrefferedFeatures()) {
			if (featureMap.containsKey(feature.getFeatureType()) && featureMap.get(feature.getFeatureType()).equals(feature.getFeatureDescription())) {
				numMatchingFeatures++;
			}
		}
		double featureMatchPercentage = (double) numMatchingFeatures / numPreferredFeatures;
		
		// Calculate the percentage of budget that the car fits into
		double budgetMatchPercentage = getPrice() <= customer.getBudget() ? 1.0 : 0.0;
		
		// Calculate the total match percentage as the average of the feature and budget match percentages
		double totalMatchPercentage = (featureMatchPercentage + budgetMatchPercentage) / 2;
		
		return totalMatchPercentage;
	}

    public void setMatchPercentage(double matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

	
	
	

}

	
    

