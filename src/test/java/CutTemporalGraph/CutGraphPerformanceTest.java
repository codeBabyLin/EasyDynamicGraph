package CutTemporalGraph;

import CutTemporalGraph.backup.TreeState;
import cn.DynamicGraph.graphdb.welding.BigContinuousGraph;
import cn.DynamicGraph.graphdb.welding.cache.*;
import dataFactory.coautor.CoauthorDataStore;
import dataFactory.coautor.CoauthorDataTestConfig;
import dataFactory.dataConfig.DataStore;
import dataFactory.dataConfig.QueryGenerator;
import dataFactory.dataConfig.QueryGeneratorConfig;
import dataFactory.operation.VersionGraphStore;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.rocksdb.Cache;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void testCutGraphByVersions1(){
        VersionGraphStoreImpl vgs = new VersionGraphStoreImpl();
        vgs.loadVGraph();
        BigContinuousGraph gf = vgs.getGraph();
        //BigContinuousGraph newg = gf.subGraph(2012,2012);

        //average
        for(int i = 1986;i<=2012;i++){
            long start = i;
            long end = i;
            String dir = String.format("./average1/%d_%d",start,end);
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

        for(int i = 1;i<=8;i++){
            System.out.printf("-----------第%d轮学习开始--------------\n",i);
            rel.stepForward();
            System.out.printf("-----------第%d轮学习结束--------------\n",i);
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

    private void prepareGraph(HashMap<String,BigContinuousGraph> graphPool,HashMap<Integer,String> graphIndex,String dir){
        //String dir = "./average";
        File dirFile = new File(dir);
        File[] files = dirFile.listFiles();
        //HashMap<String,BigContinuousGraph> graphPool = new HashMap<>();
        //HashMap<Integer,String> graphIndex = new HashMap<>();
        for(File file: files){
            String path = file.getAbsolutePath();
            BigContinuousGraph graph = new BigContinuousGraph(path);
            String name = file.getName();
            graphPool.put(name,graph);
            String[] strVersions = name.split("_");
            int start = new Integer(strVersions[0]);
            int end = new Integer(strVersions[1]);
            for(int i = start;i<=end;i++){
                graphIndex.put(i,name);
            }
        }
    }


    private void testQuery(HashMap<String,BigContinuousGraph> graphPool,HashMap<Integer,String> graphIndex){
        QueryGeneratorConfig qgc = new CoauthorDataTestConfig();
        QueryGenerator qg = new QueryGenerator(qgc);
        int [] singleVersions = new int[qgc.getSingleVersionQuerySize()];
        HashSet<Pair<Integer,Integer>> deltaVersions = new HashSet<>();
        HashSet<int[]> multiVersions = new HashSet<>();
        HashSet<Pair<Integer,Integer>> diffVersions = new HashSet<>();
        HashSet<int[]> sameVersions = new HashSet<>();
        String queryFileName = qgc.getQueryFileName();
        qg.readFromFile(singleVersions,deltaVersions,multiVersions,diffVersions,sameVersions,queryFileName);

        long end;
        long start = System.currentTimeMillis();
        for(int i=0;i<singleVersions.length;i++){
            int version = singleVersions[i];
            BigContinuousGraph graph = graphPool.get(graphIndex.get(version));
            Iterator<Long> iter = graph.AllNodesByVersion((long) version);
            long size = 0;
            while(iter.hasNext()){
                size = size + 1;
                iter.next();
            }
            end = System.currentTimeMillis();
            //System.out.println(String.format("finish %d query, cost time : %d ms   res: %d",i,(end-start),size));
            System.out.println(String.format("%d,%d,%d",version,(end-start),size));
        }
        end = System.currentTimeMillis();
        System.out.println(String.format("finish all query, cost time : %d ms",(end-start)));

    }


    @Test
    public void testAverageTime(){
        String dir = "./average";
        HashMap<String,BigContinuousGraph> graphPool = new HashMap<>();
        HashMap<Integer,String> graphIndex = new HashMap<>();

        prepareGraph(graphPool,graphIndex,dir);

        testQuery(graphPool,graphIndex);
    }
    @Test
    public void testAverageTime1(){
        String dir = "./average1";
        HashMap<String,BigContinuousGraph> graphPool = new HashMap<>();
        HashMap<Integer,String> graphIndex = new HashMap<>();

        prepareGraph(graphPool,graphIndex,dir);


        long end;
        long start = System.currentTimeMillis();
        for(int i=1986;i<=2012;i++){
            int version = i;
            BigContinuousGraph graph = graphPool.get(graphIndex.get(version));
            long middle = System.currentTimeMillis();
            Iterator<Long> iter = graph.AllNodesByVersion((long) version);
            long size = 0;
            while(iter.hasNext()){
                size = size + 1;
                iter.next();
            }
            end = System.currentTimeMillis();
            //System.out.println(String.format("finish %d query, cost time : %d ms   res: %d",i,(end-start),size));
            System.out.println(String.format("%d,%d,%d",i,(end-middle),size));
        }
        end = System.currentTimeMillis();
        System.out.println(String.format("finish all query, cost time : %d ms",(end-start)));

        //testQuery(graphPool,graphIndex);
    }

    @Test
    public void testSpaceTime(){
        String dir = "./space";
        HashMap<String,BigContinuousGraph> graphPool = new HashMap<>();
        HashMap<Integer,String> graphIndex = new HashMap<>();

        prepareGraph(graphPool,graphIndex,dir);

        testQuery(graphPool,graphIndex);
    }

    @Test
    public void testLearningTime(){
        String dir = "./learning";
        HashMap<String,BigContinuousGraph> graphPool = new HashMap<>();
        HashMap<Integer,String> graphIndex = new HashMap<>();

        prepareGraph(graphPool,graphIndex,dir);

        testQuery(graphPool,graphIndex);
    }

    @Test
    public void testNative(){
        VersionGraphStoreImpl vgs = new VersionGraphStoreImpl();
        vgs.loadVGraph();
        BigContinuousGraph gf = vgs.getGraph();

        HashMap<String,BigContinuousGraph> graphPool = new HashMap<>();
        String name = "native";
        HashMap<Integer,String> graphIndex = new HashMap<>();
        for(int i = 1986;i<=2012;i++){
            graphIndex.put(i,name);
        }
        graphPool.put(name,gf);

        testQuery(graphPool,graphIndex);

    }
    @Test
    public void testCache(){
        String path = "F:\\EasyDynamicGraphStore";
        CacheInterface cache1 = new CLOCKCache(5);
        CacheInterface cache2 = new LRUCache(5);
        CacheInterface cache3 = new FIFOCache(5);
        CacheInterface cache4 = new CLOCKCachePlus(5);
        BigContinuousGraph vGraph = new BigContinuousGraph(new File(path,"version").getAbsolutePath(),cache4);

        HashMap<String,BigContinuousGraph> graphPool = new HashMap<>();
        String name = "native";
        HashMap<Integer,String> graphIndex = new HashMap<>();
        for(int i = 1986;i<=2012;i++){
            graphIndex.put(i,name);
        }
        graphPool.put(name,vGraph);

        testQuery(graphPool,graphIndex);
    }


}
