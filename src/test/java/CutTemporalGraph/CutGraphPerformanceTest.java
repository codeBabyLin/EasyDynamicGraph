package CutTemporalGraph;

import CutTemporalGraph.backup.TreeState;
import cn.DynamicGraph.graphdb.welding.BigContinuousGraph;
import dataFactory.coautor.CoauthorDataStore;
import dataFactory.coautor.CoauthorDataTestConfig;
import dataFactory.dataConfig.DataStore;
import dataFactory.dataConfig.QueryGenerator;
import dataFactory.dataConfig.QueryGeneratorConfig;
import dataFactory.operation.VersionGraphStore;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class CutGraphPerformanceTest {

    @Test
    public void generatequery(){
        QueryGeneratorConfig qgc = new CoauthorDataTestConfig();
        QueryGenerator qg = new QueryGenerator(qgc);
        qg.generate();
    }

    @Test
    public void ReadData(){
        DataStore ds = new CoauthorDataStore();
        VersionGraphStore vgs = new VersionGraphStoreImpl();
        ds.storeVersionGraph(vgs);
    }

    @Test
    public void testCutGraphByVersions(){
        VersionGraphStoreImpl vgs = new VersionGraphStoreImpl();
        vgs.loadVGraph();
        BigContinuousGraph gf = vgs.getGraph();
        //BigContinuousGraph newg = gf.subGraph(2012,2012);

        //average
        for(int i = 1986;i<=2012;i=i+3){
            long start = i;
            long end = Math.min((i + 2), 2012);
            String dir = String.format("./average/%d_%d",start,end);
            gf.subGraph(start,end,dir);
        }

    }


    @Test
    public void testCutGraphBySize(){
        VersionGraphStoreImpl vgs = new VersionGraphStoreImpl();
        vgs.loadVGraph();
        BigContinuousGraph gf = vgs.getGraph();
        long totalNodeSize = 855165;
        long totalRelationSize = 3358827;
        int numOfcut = 9;
        long averageNodeSize = totalNodeSize/numOfcut;
        long start;
        long end=0;
        for(int i = 1;i<=9;i++){

            boolean flag = false;

            end = start = (i<=1)?1986:end+1;

            if(i==9){
                //flag = true;
                end = 2012;
            }

            while(!flag && end!=2012){
                HashSet<Long> tempV = new HashSet<>();
                for(long v = start;v<=end;v++){
                     tempV.add(v);
                }
                Long[]versions = new Long[tempV.size()];
                tempV.toArray(versions);
                Iterator<Long> iter = gf.AllNodesByMultiVersion(versions);
                long nodesSum = 0;
                while(iter.hasNext()){
                    iter.next();
                    nodesSum = nodesSum +1;
                }
                if(nodesSum>=averageNodeSize){
                    flag = true;
                }
                else{
                    end = Math.min(end+1,2012);
                }
            }
            String dir = String.format("./space/%d_%d",start,end);
            gf.subGraph(start,end,dir);
        }

    }


    @Test
    public void testCutGraphByLearning() {
        QueryGeneratorConfig qgc = new CoauthorDataTestConfig();
        QueryGenerator qg = new QueryGenerator(qgc);
        int [] singleVersions = new int[qgc.getSingleVersionQuerySize()];
        HashSet<Pair<Integer,Integer>> deltaVersions = new HashSet<>();
        HashSet<int[]> multiVersions = new HashSet<>();
        HashSet<Pair<Integer,Integer>> diffVersions = new HashSet<>();
        HashSet<int[]> sameVersions = new HashSet<>();
        String queryFileName = qgc.getQueryFileName();
        qg.readFromFile(singleVersions,deltaVersions,multiVersions,diffVersions,sameVersions,queryFileName);



        VersionGraphStoreImpl vgs = new VersionGraphStoreImpl();
        vgs.loadVGraph();
        BigContinuousGraph gf = vgs.getGraph();

        ArrayList<SecGraphState> stateList = new ArrayList<>();
        SecGraphState secGraph = new SecGraphState(1986,2012);
        stateList.add(secGraph);

        EvaluateFunction eval = new EvaluateFunctionImpl(singleVersions,gf);

        Reinforcement rel = new Reinforcement(stateList,eval);

        for(int i = 1;i<=9;i++){
            rel.stepForward();
        }
        Iterator<SecGraphState> iter = rel.traverseState();
        while(iter.hasNext()){
            SecGraphState state = iter.next();
            long start = state.getStart();
            long end = state.getEnd();
            String dir = String.format("./learning/%d_%d",start,end);
            gf.subGraph(start,end,dir);
        }

    }


}
