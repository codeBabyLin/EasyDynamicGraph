package dataFactory.operation;

import dataFactory.sampleGraph.SampleGraph;

import java.util.HashSet;

public abstract class VersionGraphOperationDefaultImpl implements VersionGraphOperation{

    public <T> HashSet<T> setUnion(HashSet<T> A, HashSet<T> B){
        HashSet<T> C = new HashSet<>();
        C.addAll(A);
        C.addAll(B);
        return C;
    }
    public <T> HashSet<T> setDifference(HashSet<T> A,HashSet<T> B){
        HashSet<T> C = new HashSet<>();
        C.addAll(A);
        C.removeAll(B);
        return C;
    }

    public <T> HashSet<T> setIntersection(HashSet<T> A,HashSet<T> B){
        HashSet<T> C = new HashSet<>();
        C.addAll(A);
        C.retainAll(B);
        return C;
    }

    //ignore labels types properties

    SampleGraph graphUnion(SampleGraph g1, SampleGraph g2){
        SampleGraph g = new SampleGraph();
        g.setNodes(setUnion(g1.getNodes(),g2.getNodes()));
        g.setRels(setUnion(g1.getRels(),g2.getRels()));
        return g;
    }
    SampleGraph graphIntersection(SampleGraph g1, SampleGraph g2){
        SampleGraph g = new SampleGraph();
        g.setNodes(setIntersection(g1.getNodes(),g2.getNodes()));
        g.setRels(setIntersection(g1.getRels(),g2.getRels()));
        return g;
    }

    SampleGraph graphExcept(SampleGraph g1, SampleGraph g2){
        SampleGraph g = new SampleGraph();
        g.setNodes(setDifference(g1.getNodes(),g2.getNodes()));
        g.setRels(setDifference(g1.getRels(),g2.getRels()));
        return g;
    }


    public abstract SampleGraph querySingleVersion(SamepleGraphFilter samepleGraphFilter);
    @Override
    public SampleGraph queryDelta(SamepleGraphFilter samepleGraphFilter1, SamepleGraphFilter samepleGraphFilter2) {
        SampleGraph g1 = querySingleVersion(samepleGraphFilter1);
        SampleGraph g2 = querySingleVersion(samepleGraphFilter2);
        SampleGraph gtemp1 = graphExcept(g1,g2);
        SampleGraph gtemp2 = graphExcept(g2,g1);
        SampleGraph g = graphUnion(gtemp1,gtemp2);
        return g;
    }

    @Override
    public SampleGraph querySame(SamepleGraphFilter[] graphFilters) {
        SampleGraph g = new SampleGraph();
        for(int i = 0 ;i< graphFilters.length;i++){
            SampleGraph temp = querySingleVersion(graphFilters[i]);
            if(i == 0 ){
                g.setNodes(temp.getNodes());
                g.setRels(temp.getRels());
            }
            else {
                g = graphIntersection(g, temp);
            }
        }
        return g;
    }

    @Override
    public SampleGraph queryMultiVersions(SamepleGraphFilter[] graphFilters) {
        SampleGraph g = new SampleGraph();
        for(int i = 0 ;i< graphFilters.length;i++){
            SampleGraph temp = querySingleVersion(graphFilters[i]);
            g = graphUnion(g,temp);
        }
        return g;
    }

    @Override
    public SampleGraph queryDiffVersions(SamepleGraphFilter samepleGraphFilter1, SamepleGraphFilter samepleGraphFilter2) {

        SampleGraph g1 = querySingleVersion(samepleGraphFilter1);
        SampleGraph g2 = querySingleVersion(samepleGraphFilter2);
        SampleGraph g = graphExcept(g1,g2);
        return g;
    }
}
