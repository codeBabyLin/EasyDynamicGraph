package dataFactory.filters;

import dataFactory.sampleGraph.SampleGraph;

public class TypeFilter implements InnerFilter {

    private String type;
    private boolean isNedd = true;

    public TypeFilter(String type){
        this.type = type;
    }

    public TypeFilter(String type,boolean isNedd){
        this.type = type;
        this.isNedd = isNedd;
    }

    public SampleGraph filter(SampleGraph sg) {
        SampleGraph temp = new SampleGraph();
        sg.getRels().forEach(rel -> {
            if(sg.getRelationType(rel).equals(type)){
                int startNode = rel.getLeft();
                int endNode = rel.getRight();
                temp.addNode(startNode,sg.getNodeLabel(startNode),sg.getNodeProperties().get(startNode));
                temp.addNode(endNode,sg.getNodeLabel(endNode),sg.getNodeProperties().get(endNode));
                temp.addRel(rel,sg.getRelationType(rel),sg.getRelationProperties().get(rel));
            }
        });
        //sg = temp;
        return temp;
    }
}
