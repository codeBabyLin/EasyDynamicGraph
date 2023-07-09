package dataFactory.operation;

import dataFactory.filters.InnerFilter;
import dataFactory.filters.OuterFilter;
import dataFactory.sampleGraph.SampleGraph;

public class SamepleGraphFilter implements InnerFilter {

    public OuterFilter getOuterFilter() {
        return outerFilter;
    }

    public void setOuterFilter(OuterFilter outerFilter) {
        this.outerFilter = outerFilter;
    }

    public InnerFilter getInnerFilter() {
        return innerFilter;
    }

    public void setInnerFilter(InnerFilter innerFilter) {
        this.innerFilter = innerFilter;
    }

    private OuterFilter outerFilter;
    private InnerFilter innerFilter;

    @Override
    public SampleGraph filter(SampleGraph sg) {
        SampleGraph temp = sg;
        if(innerFilter!=null){
            temp = innerFilter.filter(temp);
        }
        return temp;
    }
}
