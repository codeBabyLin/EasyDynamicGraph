package cn.DynamicGraph.graphdb.storePolicy;

import org.neo4j.graphdb.GraphDatabaseService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;


public class TimeStampGraph{
    private GraphDatabaseService graph;
    private HashMap<Long,Long> nodesCreateVersion;
    private HashMap<Long,Long> nodesDeleteVersion;
    private HashMap<Long,Long> relsCreateVersion;
    private HashMap<Long,Long> relsDeleteVersion;
    private long currentVersion;
    private AtomicLong version;
    private HashSet<Long> versionSet;

    public TimeStampGraph(GraphDatabaseService graph){
        this.graph = graph;
        this.nodesCreateVersion = new HashMap<>();
        this.nodesDeleteVersion = new HashMap<>();
        this.relsCreateVersion = new HashMap<>();
        this.nodesDeleteVersion = new HashMap<>();
        this.version = new AtomicLong(0);
        this.versionSet = new HashSet<>();
    }


}

