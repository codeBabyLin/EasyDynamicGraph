package dataFactory.filters;



import dataFactory.sampleGraph.SampleGraph;

import java.util.HashSet;

public class NodePropertiesFilter implements InnerFilter {

    private HashSet<NodePropertyFilter> propertyFilters;

    public NodePropertiesFilter(HashSet<NodePropertyFilter> propertyFilters){
        this.propertyFilters = new HashSet<>();
        this.propertyFilters.addAll(propertyFilters);
    }

    public SampleGraph filter(SampleGraph sg) {
        SampleGraph temp = sg;
        for(NodePropertyFilter nodePropertyFilter: propertyFilters){
            temp = nodePropertyFilter.filter(temp);
        }
        return temp;
    }

}
