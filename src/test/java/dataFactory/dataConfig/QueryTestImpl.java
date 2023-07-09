package dataFactory.dataConfig;

import dataFactory.filters.AtVersionFilter;
import dataFactory.filters.OuterFilter;

import dataFactory.operation.SamepleGraphFilter;
import dataFactory.operation.VersionGraphOperation;
import org.apache.commons.lang3.tuple.Pair;
import dataFactory.sampleGraph.SampleGraph;

import java.util.HashMap;
import java.util.HashSet;

public class QueryTestImpl implements QueryTest{
    private QueryGenerator qg;
    private VersionGraphOperation qOper;
    public QueryTestImpl(QueryGenerator qg, VersionGraphOperation qOper){
        this.qg = qg;
        this.qOper = qOper;
    }

    @Override
    public void testQuerySingleVersion(SamepleGraphFilter[] versions, VersionGraphOperation qOper) {
        //HashSet<Integer> nodes = new HashSet<>();
        //HashSet<Pair<Integer,Integer>> rels = new HashSet<>();
        String res;

        HashMap<Integer,Integer> timeCostMap = new HashMap<>();
        long t1 = System.currentTimeMillis();
        for(int i =0;i<versions.length;i++){
            //nodes.clear();
            //rels.clear();
            SamepleGraphFilter sgf = versions[i];
            SampleGraph sg = qOper.querySingleVersion(sgf);
            res = String.format("query %d  nodeSize: %d  relSize: %d",i,sg.getNodes().size(),sg.getRels().size());
            System.out.println(res);
            long t2 = System.currentTimeMillis();
            timeCostMap.put(i, (int) (t2-t1));
        }
        String res1 = timeCostMap.values().toString();
        System.out.println(res1);
    }

    @Override
    public void testQueryDelta(HashSet<Pair<SamepleGraphFilter, SamepleGraphFilter>> versions, VersionGraphOperation qOper) {
        HashMap<Integer,Integer> timeCostMap = new HashMap<>();
        long t1 = System.currentTimeMillis();


        int cnt = 0;
        for(Pair<SamepleGraphFilter,SamepleGraphFilter>integerIntegerPair:versions) {
            cnt = cnt + 1;
            String res;

            SamepleGraphFilter v1 = integerIntegerPair.getLeft();
            SamepleGraphFilter v2 = integerIntegerPair.getRight();
            SampleGraph sg = qOper.queryDelta(v1, v2);
            res = String.format("query %d  nodeSize: %d  relSize: %d",cnt,sg.getNodes().size(),sg.getRels().size());
            System.out.println(res);
            long t2 = System.currentTimeMillis();
            timeCostMap.put(cnt, (int) (t2-t1));
        }
        String res1 = timeCostMap.values().toString();
        System.out.println(res1);
        //});
    }

    @Override
    public void testQueryMultiVersions(HashSet<SamepleGraphFilter[]> versions, VersionGraphOperation qOper) {
        //HashSet<Integer> nodes = new HashSet<>();
        HashMap<Integer,Integer> timeCostMap = new HashMap<>();
        long t1 = System.currentTimeMillis();
        //versions.forEach(integerIntegerPair -> {
        int cnt = 0;
        //versions.forEach(integers -> {
        for(SamepleGraphFilter[]integers:versions ) {
            cnt = cnt +1;
            String res;
            //nodes.clear();
            //rels.clear();

            SampleGraph sg = qOper.queryMultiVersions(integers);
            res = String.format("query %d  nodeSize: %d  relSize: %d",cnt,sg.getNodes().size(),sg.getRels().size());
            System.out.println(res);
            long t2 = System.currentTimeMillis();
            timeCostMap.put(cnt, (int) (t2-t1));
        }
        // });
        String res1 = timeCostMap.values().toString();
        System.out.println(res1);
    }

    @Override
    public void testQueryDiffVersions(HashSet<Pair<SamepleGraphFilter, SamepleGraphFilter>> versions, VersionGraphOperation qOper) {
        //HashSet<Integer> diffNodes = new HashSet<>();
        //HashSet<Pair<Integer,Integer>> diffRels = new HashSet<>();

        HashMap<Integer,Integer> timeCostMap = new HashMap<>();
        long t1 = System.currentTimeMillis();
        //versions.forEach(integerIntegerPair -> {
        int cnt = 0;


        //versions.forEach(integerIntegerPair -> {
        for(Pair<SamepleGraphFilter,SamepleGraphFilter> integerIntegerPair: versions) {

            cnt = cnt + 1;
            String str;
            // diffNodes.clear();
            // diffRels.clear();
            SamepleGraphFilter v1 = integerIntegerPair.getLeft();
            SamepleGraphFilter v2 = integerIntegerPair.getRight();
            SampleGraph sg = qOper.queryDiffVersions(v1, v2);
            String res = String.format("query %d  nodeSize: %d  relSize: %d",cnt,sg.getNodes().size(),sg.getRels().size());
            System.out.println(res);
            long t2 = System.currentTimeMillis();
            timeCostMap.put(cnt, (int) (t2-t1));
        }
        // });
        String res1 = timeCostMap.values().toString();
        System.out.println(res1);
    }

    @Override
    public void testQuerySameVersions(HashSet<SamepleGraphFilter[]> versions, VersionGraphOperation qOper) {
        //HashSet<Integer> diffNodes = new HashSet<>();
        // HashSet<Pair<Integer,Integer>> diffRels = new HashSet<>();

        HashMap<Integer,Integer> timeCostMap = new HashMap<>();
        long t1 = System.currentTimeMillis();
        //versions.forEach(integerIntegerPair -> {
        int cnt = 0;

        //versions.forEach(integerIntegerPair -> {
        for(SamepleGraphFilter[]integerIntegerPair:versions){
            cnt = cnt + 1;
            String str;
            // diffNodes.clear();
            // diffRels.clear();

            SampleGraph sg = qOper.querySame(integerIntegerPair);
            String res = String.format("query %d  nodeSize: %d  relSize: %d",cnt,sg.getNodes().size(),sg.getRels().size());
            System.out.println(res);
            long t2 = System.currentTimeMillis();
            timeCostMap.put(cnt, (int) (t2-t1));
        }
        // });
        String res1 = timeCostMap.values().toString();
        System.out.println(res1);
    }
    public SamepleGraphFilter buildFilterByVersion(int version){
        OuterFilter outerFilter = new AtVersionFilter(version);
        //NodeFilter nodeFilter = new NodeFilter(outerFilter);
        //LabelFilter labelFilter = new LabelFilter("author",true);
        //TypeFilter typeFilter = new TypeFilter("coauthor");
        //nodeFilter.setLabelFilter(labelFilter);
        //InnerFilter nodeFilter = new NodeFilter();
        //relationFilter.setTypeFilter(typeFilter);
        SamepleGraphFilter samepleGraphFilter = new SamepleGraphFilter();
        samepleGraphFilter.setOuterFilter(outerFilter);
        return samepleGraphFilter;
    }
    @Override
    public void testAll() {
        int [] singleVersions = new int [this.qg.getQueryConfig().getSingleVersionQuerySize()];

        HashSet<Pair<Integer,Integer>> deltaVersions = new HashSet<>();


        HashSet<int[]> multiVersions = new HashSet<>();


        HashSet<Pair<Integer,Integer>> diffVersions = new HashSet<>();

        HashSet<int[]> sameVersions = new HashSet<>();

        //CoauthorQueryGenerator cqg = new CoauthorQueryGenerator();
        this.qg.readFromFile(singleVersions,deltaVersions,multiVersions,diffVersions,sameVersions,this.qg.getQueryConfig().getQueryFileName());

        SamepleGraphFilter [] singleGraphFilters = new SamepleGraphFilter[singleVersions.length];
        for(int i = 0;i<singleVersions.length;i++){
            singleGraphFilters[i] = buildFilterByVersion(singleVersions[i]);
        }
        HashSet<Pair<SamepleGraphFilter,SamepleGraphFilter>> deltaGraphFilters = new HashSet<>();

        for(Pair<Integer,Integer> pair: deltaVersions){
            SamepleGraphFilter left = buildFilterByVersion(pair.getLeft());
            SamepleGraphFilter right = buildFilterByVersion(pair.getRight());
            deltaGraphFilters.add(Pair.of(left,right));
        }

        HashSet<SamepleGraphFilter[]> multiGraphFilters = new HashSet<>();
        for(int [] versions: multiVersions){
            SamepleGraphFilter [] samepleGraphFilters1 = new SamepleGraphFilter[versions.length];
            for(int i = 0;i<versions.length;i++){
                samepleGraphFilters1[i] = buildFilterByVersion(versions[i]);
            }
            multiGraphFilters.add(samepleGraphFilters1);
        }


        HashSet<Pair<SamepleGraphFilter,SamepleGraphFilter>> diffGraphFilters = new HashSet<>();

        for(Pair<Integer,Integer> pair: diffVersions){
            SamepleGraphFilter left = buildFilterByVersion(pair.getLeft());
            SamepleGraphFilter right = buildFilterByVersion(pair.getRight());
            diffGraphFilters.add(Pair.of(left,right));
        }

        HashSet<SamepleGraphFilter[]> sameGraphFilters = new HashSet<>();

        for(int [] versions: sameVersions){
            SamepleGraphFilter [] samepleGraphFilters1 = new SamepleGraphFilter[versions.length];
            for(int i = 0;i<versions.length;i++){
                samepleGraphFilters1[i] = buildFilterByVersion(versions[i]);
            }
            sameGraphFilters.add(samepleGraphFilters1);
        }


        System.out.println("-----------deltaQuery------------");
        long t1 = System.currentTimeMillis();
        testQueryDelta(deltaGraphFilters,qOper);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("cost time: %d ms  QPS: %f",t2-t1,deltaGraphFilters.size()*1000.0/(t2-t1)));

        System.out.println("-----------diffQuery------------");
        t1 = System.currentTimeMillis();
        testQueryDiffVersions(diffGraphFilters,qOper);
        t2 = System.currentTimeMillis();
        System.out.println(String.format("cost time: %d ms QPS: %f",t2-t1,diffGraphFilters.size()*1000.0/(t2-t1)));

        System.out.println("-----------multiQuery------------");
        t1 = System.currentTimeMillis();
        testQueryMultiVersions(multiGraphFilters,qOper);
        t2 = System.currentTimeMillis();
        System.out.println(String.format("cost time: %d ms QPS: %f",t2-t1,multiGraphFilters.size()*1000.0/(t2-t1)));

        System.out.println("-----------SingleQuery------------");
        t1 = System.currentTimeMillis();
        testQuerySingleVersion(singleGraphFilters,qOper);
        t2 = System.currentTimeMillis();
        System.out.println(String.format("cost time: %d ms QPS : %f",t2-t1,singleGraphFilters.length*1000.0/(t2-t1)));


        System.out.println("-----------SameQuery------------");
        t1 = System.currentTimeMillis();
        testQuerySameVersions(sameGraphFilters,qOper);
        t2 = System.currentTimeMillis();
        System.out.println(String.format("cost time: %d ms QPS : %f",t2-t1,sameGraphFilters.size()*1000.0/(t2-t1)));


    }
}
