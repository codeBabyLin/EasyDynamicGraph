import cn.DynamicGraph.graphdb.BatchGraphImpl;
import cn.DynamicGraph.graphdb.Interface.BatchGraphService;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;

public class SubGraphTest extends BaseTest{

    @Test
    public void testSubGraph(){
        File dataBaseDir = new File(path,"data");
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dataBaseDir);
        registerShutdownHook(graphDb);

        BatchGraphService bgs = new BatchGraphImpl(graphDb,true);

        //graphDb.beginTx();
        long v1 = bgs.beginNextVersion();

        Node node = bgs.createNode(v1);
        node.setProperty("name","haha");

        long v2 = bgs.beginNextVersion();

        Node node2 = bgs.createNode(v2);
        node2.setProperty("age",1);

        long size = bgs.getAllNodes(v1).stream().count();
        System.out.println(size);
    }
}
