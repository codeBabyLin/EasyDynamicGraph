package dataFactory.filters;


import dataFactory.sampleGraph.SampleGraph;

public interface InnerFilter{
    SampleGraph filter(SampleGraph sg);
}
