package CutTemporalGraph;

import dataFactory.coautor.CoauthorDataStore;
import dataFactory.coautor.CoauthorDataTestConfig;
import dataFactory.dataConfig.DataStore;
import dataFactory.dataConfig.QueryGenerator;
import dataFactory.dataConfig.QueryGeneratorConfig;
import dataFactory.operation.VersionGraphStore;
import cn.DynamicGraph.graphdb.welding.BigContinuousGraph;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;

public class CutGraphTest{



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
    public void testCutGraph(){
        VersionGraphStoreImpl vgs = new VersionGraphStoreImpl();
        vgs.loadVGraph();
        BigContinuousGraph gf = vgs.getGraph();
        BigContinuousGraph newg = gf.subGraph(2012,2012);

        long version = 2012;
        long startT = System.currentTimeMillis();
        Iterator<Long> iter = gf.AllNodesByVersion(version);
        int cnt = 0;
        while(iter.hasNext()){
            cnt = cnt +1;
            iter.next();
        }
        long endT = System.currentTimeMillis();
        System.out.println(String.format("all %d---size:%d   cost:%d",version,cnt,(endT-startT)));

        startT = System.currentTimeMillis();
        Iterator<Long> iter2 = newg.AllNodesByVersion(version);
        cnt = 0;
        while(iter2.hasNext()){
            cnt = cnt +1;
            iter2.next();
        }
        endT = System.currentTimeMillis();
        System.out.println(String.format("sub  %d---size:%d   cost:%d",version,cnt,(endT-startT)));

    }

    @Test
    public void testloadData(){
        VersionGraphStoreImpl vgs = new VersionGraphStoreImpl();
        vgs.loadVGraph();
        BigContinuousGraph gf = vgs.getGraph();

        int nodesNum = 0;
        int relationsNum = 0;
        long start = 1986;
        long end = 2012;

        long startT = System.currentTimeMillis();
        Iterator<Long> iter = gf.AllNodes();

        while(iter.hasNext()){
            nodesNum = nodesNum +1;
            iter.next();
        }
        long endT = System.currentTimeMillis();
        System.out.printf("all nodeSize:%d   cost:%d%n",nodesNum,(endT-startT));

        startT = System.currentTimeMillis();
        Iterator<Long> iter1 = gf.AllRelations();
        //int cnt = 0;
        while(iter1.hasNext()){
            relationsNum = relationsNum +1;
            iter1.next();
        }
        endT = System.currentTimeMillis();
        System.out.printf("all relationSize:%d   cost:%d%n",relationsNum,(endT-startT));

    }
    @Test
    public void testCutGraphAll(){
        VersionGraphStoreImpl vgs = new VersionGraphStoreImpl();
        vgs.loadVGraph();
        BigContinuousGraph gf = vgs.getGraph();
        //BigContinuousGraph newg = gf.subGraph(2012,2012);

        //average
        for(int i = 1986;i+3<=2012;i++){
            long start = i;
            long end = Math.min((i + 3), 2012);
            String dir = String.format("./average/%d_%d",start,end);
            gf.subGraph(start,end,dir);
        }



    }

    @Test
    public void Reinforcement_learning_cutGraph(){
        VersionGraphStoreImpl vgs = new VersionGraphStoreImpl();
        vgs.loadVGraph();
        BigContinuousGraph gf = vgs.getGraph();



    }


    @Test
    public void nodesAndRelationsCount(){
        VersionGraphStoreImpl vgs = new VersionGraphStoreImpl();
        vgs.loadVGraph();
        BigContinuousGraph gf = vgs.getGraph();




        long version = 2012;
        long start = 1986;
        long end = 2012;

        for(version = start;version<=end;version++){


            int nodesNum = 0;
            long nodesScanTime = 0;

            int relationsNum = 0;
            long relationsScanTime = 0;

            long startT = System.currentTimeMillis();
            Iterator<Long> iter = gf.AllNodesByVersion(version);

            while(iter.hasNext()){
                nodesNum = nodesNum +1;
                iter.next();
            }
            long endT = System.currentTimeMillis();
            nodesScanTime = endT-startT;

            startT = System.currentTimeMillis();
            Iterator<Long> iter2 = gf.AllRelationsByVersion(version);

            while(iter2.hasNext()){
                relationsNum = relationsNum +1;
                iter2.next();
            }
            endT = System.currentTimeMillis();
            relationsScanTime = endT-startT;

            System.out.println(String.format("-----------------------------------------"));
            System.out.println(String.format("------------version: %d------------------",version));
            System.out.println(String.format("nodeSize: %d   scanTime:%d",nodesNum,nodesScanTime));
            System.out.println(String.format("relsSize: %d   scanTime:%d",relationsNum,relationsScanTime));


        }


    }

    @Test
    public void testNodeSize(){
        VersionGraphStoreImpl vgs = new VersionGraphStoreImpl();
        vgs.loadVGraph();
        BigContinuousGraph gf = vgs.getGraph();




        long version = 2012;
        long start = 1986;
        long end = 2012;

        for(version = end;version<=end;version++){


            int nodesNum = 0;

            HashSet<Long> tempV = new HashSet<>();
            for(long v = start;v<=version;v++){
                tempV.add(v);
            }
            Long[]versions = new Long[tempV.size()];
            tempV.toArray(versions);
            Iterator<Long> iter = gf.AllNodesByMultiVersion(versions);

            //Iterator<Long> iter = gf.AllNodesByVersion(start,version);

            while(iter.hasNext()){
                nodesNum = nodesNum +1;
                iter.next();
            }


            System.out.println(String.format("version: %d-----%d    nodeSize: %d",start,version,nodesNum));



        }
    }



}
