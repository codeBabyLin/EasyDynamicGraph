import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class DynamicShortestPathProcTest extends BaseTest{

    final String createGraph =
            "CREATEV (nG:Node{name:'LUZON STRAIT', latitude:20.5325, longitude:121.845})          at 1\n" +
                    "CREATEV (nH:Node{name:'WAYPOINT 87', latitude:29.32611111, longitude:131.2988889})   at 1\n" +
                    "CREATEV (nI:Node{name:'KARIMATA STRAIT', latitude:-2.0428, longitude:108.6225})      at 1\n" +
                    "CREATEV (nJ:Node{name:'LOMBOK STRAIT', latitude:-8.3256, longitude:115.8872})        at 1\n" +
                    "CREATEV (nK:Node{name:'SUMBAWA STRAIT', latitude:-8.5945, longitude:116.6867})       at 1\n" +
                    "CREATEV (nL:Node{name:'KOLANA AREA', latitude:-8.2211, longitude:125.2411})          at 1\n" +
                    "CREATEV (nM:Node{name:'EAST MANGOLE', latitude:-1.8558, longitude:126.5572})         at 1\n" +
                    "CREATEV (nN:Node{name:'WAYPOINT 88', latitude:3.96861111, longitude:128.3052778})    at 1\n" +
                    "CREATEV (nO:Node{name:'WAYPOINT 89', latitude:12.76305556, longitude:131.2980556})   at 1\n" +
                    "CREATEV (nP:Node{name:'WAYPOINT 90', latitude:22.32027778, longitude:134.700000})    at 1\n" +
                    "CREATEV (nX:Node{name:'CHIBA', latitude:35.562222, longitude:140.059187})            at 1\n" +
                    "CREATEV(nA)-[r1:TYPE {cost:29.0}]->(nB)    at 1\n" +
                    "CREATEV(nB)-[r2:TYPE {cost:694.0}]->(nC)   at 1\n" +
                    "CREATEV(nC)-[r3:TYPE {cost:172.0}]->(nD)   at 1\n" +
                    "CREATEV(nD)-[r4:TYPE {cost:101.0}]->(nE)   at 1\n" +
                    "CREATEV(nE)-[r5:TYPE {cost:357.0}]->(nF)   at 1\n" +
                    "CREATEV(nF)-[r6:TYPE {cost:299.0}]->(nG)   at 1\n" +
                    "CREATEV(nG)-[r7:TYPE {cost:740.0}]->(nH)   at 1\n" +
                    "CREATEV(nH)-[r8:TYPE {cost:587.0}]->(nX)   at 1\n" +
                    "CREATEV(nB)-[r9:TYPE {cost:389.0}]->(nI)   at 1\n" +
                    "CREATEV(nI)-[r10:TYPE {cost:584.0}]->(nJ)  at 1\n" +
                    "CREATEV(nJ)-[r11:TYPE {cost:82.0}]->(nK)   at 1\n" +
                    "CREATEV(nK)-[r12:TYPE {cost:528.0}]->(nL)  at 1\n" +
                    "CREATEV(nL)-[r13:TYPE {cost:391.0}]->(nM)  at 1\n" +
                    "CREATEV(nM)-[r14:TYPE {cost:364.0}]->(nN)  at 1\n" +
                    "CREATEV(nN)-[r15:TYPE {cost:554.0}]->(nO)  at 1\n" +
                    "CREATEV(nO)-[r16:TYPE {cost:603.0}]->(nP)  at 1\n" +
                    "CREATEV(nP)-[r17:TYPE {cost:847.0}]->(nX)  at 1\n" +
                    "CREATEV(nA)-[r18:TYPE {cost:314.1}]->(nX)    at 1 and 6";

    @Test
    public void testAstar(){
        File dataBaseDir = new File(path,"data");
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dataBaseDir);
        registerShutdownHook(graphDb);

        graphDb.execute(createGraph);

        final List<String> expectedNode = Arrays.asList("SINGAPORE", "SINGAPORE STRAIT", "WAYPOINT 68",
                "WAYPOINT 70", "WAYPOINT 74", "SOUTH CHINA SEA", "LUZON STRAIT", "WAYPOINT 87", "CHIBA");
        final List<Double> expectedDistance = Arrays.asList(0.0, 29.0, 723.0, 895.0, 996.0, 1353.0,
                1652.0, 2392.0, 2979.0);
        final List<String> actualNode = new ArrayList<String>();
        final List<Double> actualDistance = new ArrayList<Double>();

        long t1 = System.currentTimeMillis();
        graphDb.execute(
                        "MATCH (start:Node{name:'SINGAPORE'}), (end:Node{name:'CHIBA'}) " +
                                "CALL codebaby.shortestPath.astar.stream(start, end, 'cost',7) " +
                                "YIELD nodeId, cost RETURN nodeId, cost ")
                .accept(row -> {
                    long nodeId = row.getNumber("nodeId").longValue();
                    Node node = graphDb.getNodeById(nodeId);
                    String nodeName = (String) node.getProperty("name");
                    double distance = row.getNumber("cost").doubleValue();
                    actualNode.add(nodeName);
                    actualDistance.add(distance);
                    return true;
                });
        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);
       // assertArrayEquals(expectedNode.toArray(), actualNode.toArray());
       // assertArrayEquals(expectedDistance.toArray(), actualDistance.toArray());


    }
}
