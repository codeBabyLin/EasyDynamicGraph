package dataFactory.filters;

import dataFactory.filters.basicFilter.BasicFilter;
import dataFactory.sampleGraph.SampleGraph;

public class NodePropertyFilter implements InnerFilter {
    private String key;
    private BasicFilter valueFilter;

    public NodePropertyFilter(String key, BasicFilter valueFilter){
        this.key = key;
        this.valueFilter = valueFilter;
    }

    @Override
    public SampleGraph filter(SampleGraph sg) {
        SampleGraph temp = new SampleGraph();
        sg.getNodes().forEach(integer -> {
            Object value = sg.getNodeProperty(integer,key);
            if(valueFilter.isFit(value)){
                temp.addNode(integer,sg.getNodeLabel(integer),sg.getNodeProperties().get(integer));
                sg.getNodes_rels().get(integer).forEach(rel -> {
                    temp.addRel(rel,sg.getRelationType(rel),sg.getRelationProperties().get(rel));
                });
            }
        });
        return temp;
    }
}
