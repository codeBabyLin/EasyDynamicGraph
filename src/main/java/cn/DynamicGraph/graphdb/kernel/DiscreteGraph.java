package cn.DynamicGraph.graphdb.kernel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

public class DiscreteGraph<Node,Relation>{

    private HashMap<Long, BasicGraph<Node,Relation>> graphPool;

    private BasicGraph<Node,Relation> currentGraph;

    private Long currentVersion;
    private Long latestVersion;
    private AtomicLong version;
    private boolean isCopy = false;

    public DiscreteGraph(){
        this.graphPool = new HashMap<>();
        this.version = new AtomicLong(0);
    }
    public DiscreteGraph(boolean isCopy){
        this.graphPool = new HashMap<>();
        this.version = new AtomicLong(0);
        this.isCopy = isCopy;
    }

    private long getNextVersion(){
        return this.version.incrementAndGet();
    }

    public long nextGraph(){
        long version = getNextVersion();
        nextGraph(version);
        //this.latestVersion = version;
        return version;
    }

    public long  nextGraph(long version){
        //long version = getNextVersion();
        this.latestVersion = version;
        if(this.currentGraph == null){
            this.currentGraph = new BasicGraph<Node,Relation>();
            this.currentVersion = version;
        }
        else{
            if(isCopy) {
                this.currentGraph = this.currentGraph.copy();
            }
            else{
                this.currentGraph = new BasicGraph<Node,Relation>();
            }
            this.currentVersion = version;
        }
        this.graphPool.put(this.currentVersion,this.currentGraph);
        return this.currentVersion;
    }
    private BasicGraph<Node,Relation> getGraphByVersion(long version){
        return this.graphPool.get(version);
    }
    public void seekGraphByVersion(long version){
        this.currentVersion = version;
        this.currentGraph = this.graphPool.getOrDefault(this.currentVersion,new BasicGraph<Node,Relation>());
        this.graphPool.put(this.currentVersion,this.currentGraph);
        //if(this.graphPool.)
        //this.currentGraph = this.graphPool.get(this.currentVersion);

    }
    public void latest(){
        this.currentVersion = this.latestVersion;
        this.currentGraph = this.graphPool.get(this.currentVersion);
    }
    public long[] listAllVersions(){
        long []versions = new long[this.graphPool.size()];
        int i = 0;
        for(long v: this.graphPool.keySet()){
            versions[i++]= v;
        }
        return versions;
    }

    private long getCurrentVersion(){
        return this.currentVersion;
    }

    public void addNode(Node node){
        this.currentGraph.addNode(node);
    }
    public void deleteNode(Node node){
        this.currentGraph.deleteNode(node);
    }
    public void addRelation(Relation relation){
        this.currentGraph.addRelation(relation);
    }

    public void deleteRelation(Relation relation){
        this.currentGraph.deleteRelation(relation);
    }

    public void addNode(Node node, Long version){
        if(this.currentVersion!= version) this.seekGraphByVersion(version);
        this.currentGraph.addNode(node);
    }
    public void deleteNode(Node node,Long version){
        if(this.currentVersion!= version) this.seekGraphByVersion(version);
        this.currentGraph.deleteNode(node);
    }
    public void addRelation(Relation relation, Long version){
        if(this.currentVersion!= version) this.seekGraphByVersion(version);
        this.currentGraph.addRelation(relation);
    }

    public void deleteRelation(Relation relation, Long version){
        if(this.currentVersion!= version) this.seekGraphByVersion(version);
        this.currentGraph.deleteRelation(relation);
    }

    public Iterator<Node> AllNodes(Long version){
        this.seekGraphByVersion(version);
        return this.currentGraph.AllNodes();
    }
    public Iterator<Relation> AllRelations(Long version){
        this.seekGraphByVersion(version);
        return this.currentGraph.AllRelations();
    }

}
