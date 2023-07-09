package dataFactory.sampleGraph;

import java.util.HashMap;

public class Property {
    private String propertName;
    HashMap<Integer,Object> propertyValues;
    public Property(String propertName){
        this.propertName = propertName;
        this.propertyValues = new HashMap<>();
    }

    public void addEntityProperty(Integer e,Object value){
        propertyValues.put(e,value);
    }
    public void removeEntityProperty(Integer e){
        propertyValues.remove(e);
    }
}
