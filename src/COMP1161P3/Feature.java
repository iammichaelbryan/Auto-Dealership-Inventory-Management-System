public class Feature {
    private String featureType;
    private String featureDescription;

    public Feature(){

    }

    public Feature(String fType, String fDescription){
        this.featureType=fType;
        this.featureDescription=fDescription;

    }

    public String getFeatureType() {
        return featureType;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public void setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
    }

    @Override
    public String toString() {
        return  featureType + ": "+ featureDescription;
    }
    
}
