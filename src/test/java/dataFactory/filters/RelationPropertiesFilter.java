package dataFactory.filters;

import dataFactory.sampleGraph.SampleGraph;

import java.util.HashSet;

public class RelationPropertiesFilter implements InnerFilter {
    private HashSet<RelationPropertyFilter> propertyFilters;

    public RelationPropertiesFilter(HashSet<RelationPropertyFilter> propertyFilters){
        this.propertyFilters = new HashSet<>();
        this.propertyFilters.addAll(propertyFilters);
    }

    public SampleGraph filter(SampleGraph sg) {
        SampleGraph temp = sg;
        for(RelationPropertyFilter r: propertyFilters){
            temp = r.filter(temp);
        }
        return temp;
    }
}
