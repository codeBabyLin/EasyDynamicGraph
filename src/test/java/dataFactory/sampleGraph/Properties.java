package dataFactory.sampleGraph;

import java.util.HashMap;

public class Properties {
    private HashMap<Integer,HashMap<String,Object>> properties;
    private HashMap<String,Property> propertyHashMap;
    public Properties(){
        properties = new HashMap<>();
        propertyHashMap = new HashMap<>();
    }

    public void entitySetProperty(Integer e,String propertyName,Object value){
        if(properties.get(e) == null){
            HashMap<String,Object> ps = new HashMap<>();
            properties.put(e,ps);
        }
        properties.get(e).put(propertyName,value);
        if(propertyHashMap.get(propertyName) == null){
            Property p = new Property(propertyName);
            propertyHashMap.put(propertyName,p);
        }
        propertyHashMap.get(propertyName).addEntityProperty(e,value);
    }
    public void entityRemoveProperty(Integer e,String propertyName){
        if(properties.get(e) == null) return;
        if(properties.get(e).isEmpty()) return;
        properties.get(e).remove(propertyName);
        if(propertyHashMap.get(propertyName) == null) return;
        propertyHashMap.get(propertyName).removeEntityProperty(e);
    }

}
