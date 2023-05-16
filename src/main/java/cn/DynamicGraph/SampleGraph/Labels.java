package cn.DynamicGraph.SampleGraph;

import java.util.HashMap;
import java.util.HashSet;

public class Labels {

    private HashMap<Integer, HashSet<String>> nodeLabels;
    private HashMap<String, Label> labelNodes;
    public Labels(){
        this.nodeLabels =new HashMap<>();
        this.labelNodes = new HashMap<>();
    }
    public void nodeAddLabels(Integer e, String[]labels){
        for(int i =0;i<labels.length;i++){
            nodeAddLabel(e,labels[i]);
        }
    }
    public void nodeRemoveLabels(Integer e ,String []labels){
        for(int i =0;i<labels.length;i++){
            nodeRemoveLabel(e,labels[i]);
        }
    }

    public void nodeAddLabel(Integer e,String label){
        if(nodeLabels.get(e) == null){
            HashSet<String> lbes = new HashSet<>();
            nodeLabels.put(e,lbes);
        }
        nodeLabels.get(e).add(label);
        if(labelNodes.get(label) == null){
            Label l = new Label(label);
            labelNodes.put(label,l);
        }
        labelNodes.get(label).nodeSetLabel(e);

    }
    public void nodeRemoveLabel(Integer e, String label){
        if(nodeLabels.get(e) == null){
            HashSet<String> lbes = new HashSet<>();
            nodeLabels.put(e,lbes);
            return;
        }
        if(nodeLabels.get(e).isEmpty()) return;
        nodeLabels.get(e).remove(label);
        labelNodes.get(label).nodeRemoveLabel(e);
    }


}
