package dataFactory.sampleGraph;

import java.util.HashSet;

public class Label {
    private String labelName;
    private HashSet<Integer> nodes;
    public Label(String labelName){
        this.labelName =labelName;
        this.nodes = new HashSet<>();
    }

    public void nodeSetLabel(Integer e){
        this.nodes.add(e);
    }
    public void nodeRemoveLabel(Integer e){
        this.nodes.remove(e);
    }
}
