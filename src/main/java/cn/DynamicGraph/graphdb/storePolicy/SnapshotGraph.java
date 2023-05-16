package cn.DynamicGraph.graphdb.storePolicy;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class SnapshotGraph {
    private HashMap<Long, InternalGraph> graphPool;

    private InternalGraph currentGraph;

    private long currentVersion;
    private AtomicLong version;

    public SnapshotGraph(){
        this.graphPool = new HashMap<>();
        this.version = new AtomicLong(0);
    }

    private long getNextVersion(){
        return this.version.incrementAndGet();
    }
    public InternalGraph nextGraph(){
        long version = getNextVersion();
        if(this.currentGraph == null){
            this.currentGraph = new InternalGraph();
            this.currentVersion = version;
        }
        else{
            this.currentGraph = this.currentGraph.copy();
            this.currentVersion = version;
        }
        this.graphPool.put(this.currentVersion,this.currentGraph);
        return this.currentGraph;
    }
    public InternalGraph getGraphByVersion(long version){
        return this.graphPool.get(version);
    }
    public long[] listAllVersions(){
        long []versions = new long[this.graphPool.size()];
        int i = 0;
        for(long v: this.graphPool.keySet()){
            versions[i++]= v;
        }
        return versions;
    }

    public long getCurrentVersion(){
        return this.currentVersion;
    }
}
