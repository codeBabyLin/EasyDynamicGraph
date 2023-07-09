package CutTemporalGraph;

import dataFactory.operation.VersionGraphStore;
import dataFactory.sampleGraph.SampleGraph;
//import lombok.extern.slf4j.Slf4j;
import cn.DynamicGraph.graphdb.welding.BigContinuousGraph;
import cn.DynamicGraph.graphdb.welding.IdVersionStore;
import cn.DynamicGraph.graphdb.welding.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.util.HashMap;


@Slf4j
public class VersionGraphStoreImpl implements VersionGraphStore {

    String path = "F:\\EasyDynamicGraphStore";
    GraphDatabaseService graphDb;

    BigContinuousGraph vGraph;

    private IdVersionStore nodeIds;
    private IdVersionStore relationIds;
    private Transformer transformer;
    @Override
    public void begin() {
        File dataBaseDir = new File(path,"data");
        delFile(new File(path));
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dataBaseDir);
        registerShutdownHook(graphDb);
        this.vGraph = new BigContinuousGraph(new File(path,"version").getAbsolutePath());
        this.nodeIds = new IdVersionStore(new File(path,"tempNodes").getAbsolutePath());
        this.relationIds = new IdVersionStore(new File(path,"tempRelations").getAbsolutePath());
        this.transformer = new Transformer();
        //graphDb.beginTx();
        //graphDb.createNode();
    }

    @Override
    public void storeGraph(SampleGraph sampleGraph, int version) {
        Transaction tx = graphDb.beginTx();
        int count = 0;
        //Node temp = graphDb.createNode();
        //temp.addLabel(()->"label");
        for(Integer e: sampleGraph.getNodes()){

            long ne = e;
            byte[]key = transformer.LongToByte(ne);
            if(this.nodeIds.exist(key)){
                this.vGraph.deleteNode(this.nodeIds.get(key),version);
                //System.out.println(String.format("exist-->%d",ne));
                continue;
            }
            //System.out.println(String.format("new iiiiiiiidddddddd-->%d",ne));
            Node n = graphDb.createNode();
            count = count + 1;

            String label = sampleGraph.getNodeLabel(e);
            HashMap<String,Object> nodeProperty = sampleGraph.getNodeProperties().get(e);
            n.addLabel(()->label);
            nodeProperty.forEach(n::setProperty);
            n.setProperty("nodeId",e);
            long nodeId = n.getId();
            nodeIds.add(key,transformer.LongToByte(nodeId));
            this.vGraph.addNode(nodeId,version);
            if(count >= 100000){
                tx.success();
                tx.close();
                tx = graphDb.beginTx();
                count = 0;
            }
        }
        tx.success();
        tx.close();
        tx = graphDb.beginTx();
        count = 0;
        //tx = graphDb.beginTx();
        for(Pair<Integer,Integer> pais: sampleGraph.getRels()) {

            long left = pais.getLeft();
            long right = pais.getRight();
            byte[]key = transformer.VersionToByte(new long[]{left,right});
            if(this.relationIds.exist(key)){
                this.vGraph.deleteRelation(key,version);
                continue;
            }
            byte[] leftb = nodeIds.get(transformer.LongToByte(left));
            byte[] rightb = nodeIds.get(transformer.LongToByte(right));


            Node n1 = graphDb.getNodeById(transformer.ByteToLong(leftb));
            Node n2 = graphDb.getNodeById(transformer.ByteToLong(rightb));
            String type = sampleGraph.getRelationType(pais);
            Relationship r1 = n1.createRelationshipTo(n2, ()->type);
            this.vGraph.addRelation(r1.getId(),version);
            this.relationIds.add(key,transformer.LongToByte(r1.getId()));

            count = count + 1;
            if(count >= 100000){
                tx.success();
                tx.close();
                tx = graphDb.beginTx();
                count = 0;
            }
        }
        tx.success();
        tx.close();
        //log.info("finish store graph version : {}",version);
        System.out.println(String.format("finish store graph version : %d",version));
    }

    @Override
    public void finish() {
        //this.vGraph.flush();
    }


    public void loadVGraph(){
        //File dataBaseDir = new File(path,"data");
        //delFile(new File(path));
        //graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dataBaseDir);
        //registerShutdownHook(graphDb);
        this.vGraph = new BigContinuousGraph(new File(path,"version").getAbsolutePath());
        //this.nodeIds = new IdVersionStore(new File(path,"tempNodes").getAbsolutePath());
        //this.relationIds = new IdVersionStore(new File(path,"tempRelations").getAbsolutePath());
        //this.transformer = new Transformer();
    }

    public BigContinuousGraph getGraph(){
        return this.vGraph;
    }

    private void delFile(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File value : files) {
                delFile(value);
            }
        }
        file.delete();
    }

    private void registerShutdownHook(GraphDatabaseService graphDb){
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        }));
    }



}
