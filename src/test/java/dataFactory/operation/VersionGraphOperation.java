package dataFactory.operation;

import dataFactory.sampleGraph.SampleGraph;

public interface VersionGraphOperation {

    SampleGraph querySingleVersion(SamepleGraphFilter samepleGraphFilter);
    SampleGraph queryDelta(SamepleGraphFilter samepleGraphFilter1, SamepleGraphFilter samepleGraphFilter2);
    SampleGraph querySame(SamepleGraphFilter[] graphFilters);

    SampleGraph queryMultiVersions(SamepleGraphFilter[] graphFilters);
    SampleGraph queryDiffVersions(SamepleGraphFilter samepleGraphFilter1, SamepleGraphFilter samepleGraphFilter2);

}
