package dataFactory.operation;

import dataFactory.sampleGraph.SampleGraph;

public interface VersionGraphStore {
    void begin();
    void storeGraph(SampleGraph sampleGraph, int version);
    void finish();
}
