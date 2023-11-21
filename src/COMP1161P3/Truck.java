import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class Truck extends Vehicle {
    private ArrayList<Feature> otherFeatures;
    private boolean offRoad;
    HashMap<String, String> featureMap;
    private double matchPercentage;
    private TruckListing thisForm;
    

    

    public Truck(String make, String model, int year, double price, boolean offRoad, ArrayList<Feature> otherFeatures) {
        super(make, model, year, price);
        this.otherFeatures=otherFeatures;
        this.offRoad=offRoad;
        vehicleTypes+=", PICKUP TRUCK";
        this.featureMap= new HashMap<>();
        for (Feature f:otherFeatures){
            featureMap.put(f.getFeatureType(),f.getFeatureDescription());
        }
        featureMap.put("Make", make);
        if (offRoad== true){
            featureMap.put("Off-Road","Yes");
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
        return super.toString() +" "+"Additional Features: "+ getOtherFeatures();
    }

    public boolean isOffRoad() {
        return offRoad;
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
    /**  Method updateLocalData is used to update attributes within a specific Truck object 
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
        boolean currOffRoad = isOffRoad();
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
    
        String offRoadEntered = JOptionPane.showInputDialog(thisForm, "Hit enter to keep offRoad at [" + currOffRoad + "] or enter new offRoad:");
        boolean offRoad;
        if (offRoadEntered.equals("")) {
            offRoad = currOffRoad;
        } else {
            offRoad = Boolean.parseBoolean(offRoadEntered);
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
        setOffRoad(offRoad);
        setOtherFeatures(currFeatures);
    }
    
    public double getMatchPercentage() {
        return matchPercentage;
    }
    
    public void setOffRoad(boolean offRoad) {
        this.offRoad = offRoad;
    }
}    