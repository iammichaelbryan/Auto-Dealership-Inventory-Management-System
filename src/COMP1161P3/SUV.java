import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
/**
 * SUV Class
 */
public class SUV extends Vehicle implements Comparable<SUV>{ 
    private SUVListing sListing;
    private ArrayList<Feature> otherFeatures;
    private boolean fourWheelDrive;
    HashMap<String, String> featureMap;
    private double matchPercentage;
    private SUVListing thisForm;

    
/**
 * Constructor for SUV object
 * @param make
 * @param model
 * @param year
 * @param price
 * @param fourWheelDrive
 * @param otherFeatures
 */
    public SUV(String make, String model, int year, double price,  boolean fourWheelDrive, ArrayList<Feature> otherFeatures) {
        super(make, model, year, price);
        this.otherFeatures=otherFeatures;
        this.fourWheelDrive = fourWheelDrive;
        vehicleTypes+=", SUV";
        this.featureMap= new HashMap<>();
        for (Feature f:otherFeatures){
            featureMap.put(f.getFeatureType(),f.getFeatureDescription());
        }
        featureMap.put("Make", make);
        if (fourWheelDrive== true){
        featureMap.put("Four-Wheel-Drive","Yes");
        }
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
        if(!isFourWheelDrive()){
            return super.toString() +" Not four-wheel drive"+" "+"Additional Features: "+ getOtherFeatures();
        }
        return super.toString() +" is four-wheel drive"+" "+"Additional Features: "+ getOtherFeatures();
    }

    public boolean isFourWheelDrive() {
        return fourWheelDrive;
    }
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
@Override
    public int compareTo(SUV o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
/** Method updateLocalData is used to update attributes within a specific Truck object 
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
    boolean currFourWheel = isFourWheelDrive();
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

    String fourWheelEntered = JOptionPane.showInputDialog(thisForm, "Hit enter to keep offRoad at [" + currFourWheel + "] or enter new offRoad:");
    boolean fourWheel;
    if (fourWheelEntered.equals("")) {
        fourWheel = currFourWheel;
    } else {
        fourWheel = Boolean.parseBoolean(fourWheelEntered);
    }

    String featureEntered = JOptionPane.showInputDialog(thisForm, "Hit enter to keep features as [" + currFeatures + "] or enter new features (format: 'type,description,type,description,...'):");
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
    setMake(make);
    setModel(model);
    setYear(year);
    setPrice(price);
    setFourWheelDrive(fourWheel);
    setOtherFeatures(currFeatures);
}


public void setFourWheelDrive(boolean fourWheelDrive) {
    this.fourWheelDrive = fourWheelDrive;
}



    


    

}
