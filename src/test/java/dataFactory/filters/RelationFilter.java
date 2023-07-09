package dataFactory.filters;

import dataFactory.sampleGraph.SampleGraph;

public class RelationFilter implements InnerFilter {


    public TypeFilter getTypeFilter() {
        return typeFilter;
    }

    public void setTypeFilter(TypeFilter typeFilter) {
        this.typeFilter = typeFilter;
    }

    public RelationPropertyFilter getRelationPropertyFilter() {
        return relationPropertyFilter;
    }

    public void setRelationPropertyFilter(RelationPropertyFilter relationPropertyFilter) {
        this.relationPropertyFilter = relationPropertyFilter;
    }

    public RelationPropertiesFilter getRelationPropertiesFilter() {
        return relationPropertiesFilter;
    }

    public void setRelationPropertiesFilter(RelationPropertiesFilter relationPropertiesFilter) {
        this.relationPropertiesFilter = relationPropertiesFilter;
    }

    private TypeFilter typeFilter;
    private RelationPropertyFilter relationPropertyFilter;
    private RelationPropertiesFilter relationPropertiesFilter;


    @Override
    public SampleGraph filter(SampleGraph sg) {
        SampleGraph temp = sg;
        if(this.typeFilter!=null){
            temp = this.typeFilter.filter(temp);
        }
        if(this.relationPropertyFilter!=null){
            temp = this.relationPropertyFilter.filter(temp);
        }
        if(this.relationPropertiesFilter!=null){
            temp = this.relationPropertiesFilter.filter(temp);
        }
        return temp;
    }
}
