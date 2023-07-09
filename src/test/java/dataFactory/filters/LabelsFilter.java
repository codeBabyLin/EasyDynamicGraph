package dataFactory.filters;



import dataFactory.sampleGraph.SampleGraph;

import java.util.HashSet;

public class LabelsFilter implements InnerFilter {

    private HashSet<LabelFilter> labelsFilter;

    public LabelsFilter(HashSet<LabelFilter> labelsFilter){
        this.labelsFilter = new HashSet<>();
        this.labelsFilter.addAll(labelsFilter);
    }

    @Override
    public SampleGraph filter(SampleGraph sg) {

        SampleGraph temp = sg;
        for(LabelFilter labelFilter: labelsFilter){
            temp = labelFilter.filter(temp);
        }

        return temp;
    }
}
