package dataFactory.dataConfig;


import dataFactory.operation.SamepleGraphFilter;
import dataFactory.operation.VersionGraphOperation;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;

public interface QueryTest {
    void testQuerySingleVersion(SamepleGraphFilter[] versions, VersionGraphOperation qOper);
    void testQueryDelta(HashSet<Pair<SamepleGraphFilter, SamepleGraphFilter>> versions, VersionGraphOperation qOper);
    void testQueryMultiVersions(HashSet<SamepleGraphFilter[]> versions, VersionGraphOperation qOper);
    void testQueryDiffVersions(HashSet<Pair<SamepleGraphFilter, SamepleGraphFilter>> versions, VersionGraphOperation qOper);
    void testQuerySameVersions(HashSet<SamepleGraphFilter[]> versions, VersionGraphOperation qOper);
    void testAll();
}
