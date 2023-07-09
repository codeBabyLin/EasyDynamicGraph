package dataFactory.filters;


import dataFactory.sampleGraph.SampleGraph;

public class LabelFilter implements InnerFilter {

    private String label;
    private boolean isNeed = true;

    public LabelFilter(String label,boolean isNeed){
       this.label = label;
       this.isNeed = isNeed;
    }
    public LabelFilter(String label){
        this.label = label;
    }

    @Override
    public SampleGraph filter(SampleGraph sg) {
        SampleGraph temp = new SampleGraph();
        sg.getNodes().forEach(integer -> {
            if(sg.getNodeLabel(integer).equals(this.label)){
                temp.addNode(integer,sg.getNodeLabel(integer),sg.getNodeProperties().get(integer));
                sg.getNodes_rels().get(integer).forEach(rel -> {
                    temp.addRel(rel,sg.getRelationType(rel),sg.getRelationProperties().get(rel));
                });
            }
        });
        return temp;
    }
}
