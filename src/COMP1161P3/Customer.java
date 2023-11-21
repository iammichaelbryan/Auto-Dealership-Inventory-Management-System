import java.util.ArrayList;
import java.util.HashMap;
/**
 * Customer Object class
 * 
 */
public class Customer implements Comparable<Customer> {

    private String name;
    private int budget;
    private int cusID;
	private static int nextId = 0001;
    private ArrayList<Feature> prefferedFeatures;
    HashMap<String, String> featureMap;

    public int getCusID() {
        return cusID;
    }
    public void setCusID(int cusID) {
        this.cusID = cusID;
    }
    public Customer(){
        prefferedFeatures= new ArrayList<Feature>();
    }

    /**
     * Constructor for customer object
     * @param name
     * @param budget
     * @param prefferedFeatures
     * @return Customer Object
     */

    public Customer(String name, int budget, ArrayList<Feature> prefferedFeatures) {
       
        this.name = name;
        this.budget = budget;
        this.cusID=getNextId();
        this.prefferedFeatures = prefferedFeatures;
        this.featureMap= new HashMap<>();
        for (Feature f:prefferedFeatures){
            featureMap.put(f.getFeatureType(),f.getFeatureDescription());
        }
    }
    public static int getNextId() {
		return nextId++;
	}
    public String getName() {
        return name;
    }
    public HashMap<String, String> getFeatureMap() {
        return featureMap;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getBudget() {
        return budget;
    }
    public void setBudget(int budget) {
        this.budget = budget;
    }
    public ArrayList<Feature> getPrefferedFeatures() {
        return prefferedFeatures;
    }
    public void setPrefferedFeatures(ArrayList<Feature> prefferedFeatures) {
        this.prefferedFeatures = prefferedFeatures;
    }
    
    @Override
    public String toString() {
        return "" + name + " has a budget of:" + budget + " and preferences of:" + prefferedFeatures;
}
/**
 * returns list of cars and their percentage match based on customer's prefferences 
 * @param carList
 * @return ArrayList<Car>
 */
public ArrayList<Car> getRecommendedCars(ArrayList<Car> carList) {
    ArrayList<Car> recommendedCars = new ArrayList<Car>();

    // Filter cars based on budget
    ArrayList<Car> affordableCars = new ArrayList<Car>();
    for (Car car : carList) {
        if (car.getPrice() <= getBudget()) {
            affordableCars.add(car);
        }
    }

    // Filter affordable cars based on preferred features and calculate percentage match
    for (Car car : affordableCars) {
        int matchCount = 0;
        for (Feature feature : getPrefferedFeatures()) {
            if (car.getFeatureMap().containsKey(feature.getFeatureType()) &&
                car.getFeatureMap().get(feature.getFeatureType()).equals(feature.getFeatureDescription())) {
                matchCount++;
            }
            
        }
        int matchPercentage = (int)matchCount / getPrefferedFeatures().size();
        car.setMatchPercentage(matchPercentage);
        recommendedCars.add(car);
    }

    // Sort recommended cars by percentage match, in descending order
    recommendedCars.sort((c1, c2) -> Double.compare(c2.getMatchPercentage(this), c1.getMatchPercentage(this)));

    // Display recommended cars with match percentage
    for (Car car : recommendedCars) {
        System.out.println(car + " - " + car.getMatchPercentage(this) * 100 + "% match");
    }

    return recommendedCars;
}
/**
 * returns list of suvs and their percentage match based on customer's prefferences 
 * @param suvList
 * @return ArrayList<SUV>
 */
public ArrayList<SUV> getRecommendedSUVs(ArrayList<SUV> suvList) {
    ArrayList<SUV> recommendedSUVs = new ArrayList<>();

    // Filter SUVs based on budget
    ArrayList<SUV> affordableSUVs = new ArrayList<>();
    for (SUV suv : suvList) {
        if (suv.getPrice() <= getBudget()) {
            affordableSUVs.add(suv);
        }
    }

    // Filter affordable SUVs based on preferred features and calculate percentage match
    for (SUV suv : affordableSUVs) {
        int matchCount = 0;
        for (Feature feature : getPrefferedFeatures()) {
            if (suv.getFeatureMap().containsKey(feature.getFeatureType()) &&
                suv.getFeatureMap().get(feature.getFeatureType()).equals(feature.getFeatureDescription())) {
                matchCount++;
            }
        }
        int matchPercentage = (int)matchCount / getPrefferedFeatures().size();
        suv.setMatchPercentage(matchPercentage);
        recommendedSUVs.add(suv);
    }

    // Sort recommended SUVs by percentage match, in descending order
    recommendedSUVs.sort((s1, s2) -> Double.compare(s2.getMatchPercentage(this), s1.getMatchPercentage(this)));

    // Display recommended SUVs with match percentage
    for (SUV suv : recommendedSUVs) {
        System.out.println(suv + " - " + suv.getMatchPercentage(this) * 100 + "% match");
    }

    return recommendedSUVs;
}
/**
 * returns list of trucks and their percentage match based on customer's prefferences 
 * @param truckList
 * @return ArrayList<Truck>
 */
public ArrayList<Truck> getRecommendedTrucks(ArrayList<Truck> truckList) {
    ArrayList<Truck> recommendedTrucks = new ArrayList<>();

    // Filter trucks based on budget
    ArrayList<Truck> affordableTrucks = new ArrayList<>();
    for (Truck truck : truckList) {
        if (truck.getPrice() <= getBudget()) {
            affordableTrucks.add(truck);
        }
    }

    // Filter affordable trucks based on preferred features and calculate percentage match
    for (Truck truck : affordableTrucks) {
        int matchCount = 0;
        for (Feature feature : getPrefferedFeatures()) {
            if (truck.getFeatureMap().containsKey(feature.getFeatureType()) &&
                truck.getFeatureMap().get(feature.getFeatureType()).equals(feature.getFeatureDescription())) {
                matchCount++;
            }
        }
        int matchPercentage = (int)matchCount / getPrefferedFeatures().size();
        truck.setMatchPercentage(matchPercentage);
        recommendedTrucks.add(truck);
    }

    // Sort recommended trucks by percentage match, in descending order
    recommendedTrucks.sort((t1, t2) -> Double.compare(t2.getMatchPercentage(this), t1.getMatchPercentage(this)));

    // Display recommended trucks with match percentage
    for (Truck truck : recommendedTrucks) {
        System.out.println(truck + " - " + truck.getMatchPercentage(this) * 100 + "% match");
    }

    return recommendedTrucks;
}

@Override
public int compareTo(Customer o) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
}
}
