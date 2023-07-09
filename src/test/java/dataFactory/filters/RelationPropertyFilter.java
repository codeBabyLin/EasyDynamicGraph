package dataFactory.filters;

import dataFactory.filters.basicFilter.BasicFilter;
import dataFactory.sampleGraph.SampleGraph;

public class RelationPropertyFilter implements InnerFilter {
    private String key;
    private BasicFilter valueFilter;

    public RelationPropertyFilter(String key, BasicFilter valueFilter){
        this.key = key;
        this.valueFilter = valueFilter;
    }

    @Override
    public SampleGraph filter(SampleGraph sg) {
        SampleGraph temp = new SampleGraph();
        sg.getRels().forEach(rel -> {
            Object value = sg.getRelationProperty(rel,key);
            if(valueFilter.isFit(value)){
                int startNode = rel.getLeft();
                int endNode = rel.getKey();
                temp.addNode(startNode,sg.getNodeLabel(startNode),sg.getNodeProperties().get(startNode));
                temp.addNode(endNode,sg.getNodeLabel(endNode),sg.getNodeProperties().get(endNode));
                temp.addRel(rel,sg.getRelationType(rel),sg.getRelationProperties().get(rel));
            }
        });
        return temp;
    }
}
