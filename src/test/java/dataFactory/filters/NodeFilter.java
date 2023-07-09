package dataFactory.filters;

import dataFactory.sampleGraph.SampleGraph;

public class NodeFilter implements InnerFilter {
    public LabelsFilter getLabelsFilter() {
        return labelsFilter;
    }

    public void setLabelsFilter(LabelsFilter labelsFilter) {
        this.labelsFilter = labelsFilter;
    }

    public LabelFilter getLabelFilter() {
        return labelFilter;
    }

    public void setLabelFilter(LabelFilter labelFilter) {
        this.labelFilter = labelFilter;
    }

    public NodePropertiesFilter getPropertiesFilter() {
        return propertiesFilter;
    }

    public void setPropertiesFilter(NodePropertiesFilter propertiesFilter) {
        this.propertiesFilter = propertiesFilter;
    }


    private LabelsFilter labelsFilter;
    private LabelFilter labelFilter;
    private NodePropertiesFilter propertiesFilter;
    //private RelationFilter relationFilter;

    public NodePropertyFilter getPropertyFilter() {
        return propertyFilter;
    }

    public void setPropertyFilter(NodePropertyFilter propertyFilter) {
        this.propertyFilter = propertyFilter;
    }

    private NodePropertyFilter propertyFilter;

    @Override
    public SampleGraph filter(SampleGraph sampleGraph) {

        SampleGraph temp = sampleGraph;
        if(this.labelFilter!=null){
            temp =this.labelFilter.filter(temp);
        }
        if(this.labelsFilter!=null){
            temp =this.labelsFilter.filter(temp);
        }
        if(this.propertyFilter!=null){
            temp =this.propertyFilter.filter(temp);
        }
        if(this.propertiesFilter!=null){
            temp =this.propertiesFilter.filter(temp);
        }
        return temp;
    }
}
