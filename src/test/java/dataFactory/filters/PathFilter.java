package dataFactory.filters;

import dataFactory.sampleGraph.SampleGraph;

public class PathFilter extends LineFilter implements InnerFilter {

    @Override
    public SampleGraph filter(SampleGraph sampleGraph) {
        SampleGraph temp = sampleGraph;
        if(this.getPreFilter()!=null){
            temp = this.getPreFilter().filter(temp);
        }
        if(this.getCurrentFilter()!=null){
            temp = this.getCurrentFilter().filter(temp);
        }
        if(this.getLaterFilter()!=null){
            temp = this.getLaterFilter().filter(temp);
        }
        return temp;
    }
}
