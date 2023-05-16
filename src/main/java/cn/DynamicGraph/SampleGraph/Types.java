package cn.DynamicGraph.SampleGraph;

import java.util.HashMap;

public class Types {
    private HashMap<Integer, String> relsType;
    private HashMap<String, Label> types;

    public void relSetType(Integer e, String type){
        relsType.put(e,type);
        types.get(type).nodeSetLabel(e);
    }


}
