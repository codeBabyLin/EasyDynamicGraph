package common;

import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class ShortestPathProcTest extends BaseTest{

    final String createGraph =
            "CREATE (nA:Node{name:'SINGAPORE', latitude:1.304444,longitude:103.717373})\n" +
                    "CREATE (nB:Node{name:'SINGAPORE STRAIT', latitude:1.1892, longitude:103.4689})\n" +
                    "CREATE (nC:Node{name:'WAYPOINT 68', latitude:8.83055556, longitude:111.8725})\n" +
                    "CREATE (nD:Node{name:'WAYPOINT 70', latitude:10.82916667, longitude:113.9722222})\n" +
                    "CREATE (nE:Node{name:'WAYPOINT 74', latitude:11.9675, longitude:115.2366667})\n" +
                    "CREATE (nF:Node{name:'SOUTH CHINA SEA', latitude:16.0728, longitude:119.6128})\n" +
                    "CREATE (nG:Node{name:'LUZON STRAIT', latitude:20.5325, longitude:121.845})\n" +
                    "CREATE (nH:Node{name:'WAYPOINT 87', latitude:29.32611111, longitude:131.2988889})\n" +
                    "CREATE (nI:Node{name:'KARIMATA STRAIT', latitude:-2.0428, longitude:108.6225})\n" +
                    "CREATE (nJ:Node{name:'LOMBOK STRAIT', latitude:-8.3256, longitude:115.8872})\n" +
                    "CREATE (nK:Node{name:'SUMBAWA STRAIT', latitude:-8.5945, longitude:116.6867})\n" +
                    "CREATE (nL:Node{name:'KOLANA AREA', latitude:-8.2211, longitude:125.2411})\n" +
                    "CREATE (nM:Node{name:'EAST MANGOLE', latitude:-1.8558, longitude:126.5572})\n" +
                    "CREATE (nN:Node{name:'WAYPOINT 88', latitude:3.96861111, longitude:128.3052778})\n" +
                    "CREATE (nO:Node{name:'WAYPOINT 89', latitude:12.76305556, longitude:131.2980556})\n" +
                    "CREATE (nP:Node{name:'WAYPOINT 90', latitude:22.32027778, longitude:134.700000})\n" +
                    "CREATE (nX:Node{name:'CHIBA', latitude:35.562222, longitude:140.059187})\n" +
                    "CREATE\n" +
                    "  (nA)-[:TYPE {cost:29.0}]->(nB),\n" +
                    "  (nB)-[:TYPE {cost:694.0}]->(nC),\n" +
                    "  (nC)-[:TYPE {cost:172.0}]->(nD),\n" +
                    "  (nD)-[:TYPE {cost:101.0}]->(nE),\n" +
                    "  (nE)-[:TYPE {cost:357.0}]->(nF),\n" +
                    "  (nF)-[:TYPE {cost:299.0}]->(nG),\n" +
                    "  (nG)-[:TYPE {cost:740.0}]->(nH),\n" +
                    "  (nH)-[:TYPE {cost:587.0}]->(nX),\n" +
                    "  (nB)-[:TYPE {cost:389.0}]->(nI),\n" +
                    "  (nI)-[:TYPE {cost:584.0}]->(nJ),\n" +
                    "  (nJ)-[:TYPE {cost:82.0}]->(nK),\n" +
                    "  (nK)-[:TYPE {cost:528.0}]->(nL),\n" +
                    "  (nL)-[:TYPE {cost:391.0}]->(nM),\n" +
                    "  (nM)-[:TYPE {cost:364.0}]->(nN),\n" +
                    "  (nN)-[:TYPE {cost:554.0}]->(nO),\n" +
                    "  (nO)-[:TYPE {cost:603.0}]->(nP),\n" +
                    "  (nP)-[:TYPE {cost:847.0}]->(nX)";

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
        for(int i = 0;i<5;i++) {
            graphDb.execute(
                            "MATCH (start:Node{name:'SINGAPORE'}), (end:Node{name:'CHIBA'}) " +
                                    "CALL algo.shortestPath.astar.stream(start, end, 'cost') " +
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
        }
        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);
        //assertArrayEquals(expectedNode.toArray(), actualNode.toArray());
       // assertArrayEquals(expectedDistance.toArray(), actualDistance.toArray());


    }

}
