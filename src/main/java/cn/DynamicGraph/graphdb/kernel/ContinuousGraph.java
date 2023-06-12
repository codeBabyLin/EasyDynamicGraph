package cn.DynamicGraph.graphdb.kernel;

import cn.DynamicGraph.Version.FilterVersion;

import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

public class ContinuousGraph<Node,Relation> {

    private NodeVersionStore<Node> nodeVersionStore;
    private RelationVersionStore<Relation> relationVersionStore;
    private BasicGraph<Node,Relation> basicGraph;
    private long currentVersion;
    private AtomicLong version;
    private HashSet<Long> versionSet;

    private UserGraph<Long,Long,String> userGraph;
    private boolean isReadByVersion = false;
    private FilterVersion filter;

    private boolean isReadByName = false;
    private String graphName;
    public ContinuousGraph(){
        this.basicGraph = new BasicGraph<>();
        this.nodeVersionStore = new NodeVersionStore<>();
        this.relationVersionStore = new RelationVersionStore<>();
        this.version = new AtomicLong(0);
        this.versionSet = new HashSet<>();
        this.userGraph = new UserGraph<Long,Long,String>();
    }

    public boolean reSet(){
        this.basicGraph = new BasicGraph<>();
        this.nodeVersionStore = new NodeVersionStore<>();
        this.relationVersionStore = new RelationVersionStore<>();
        this.versionSet = new HashSet<>();
        return true;
    }

    public void setFilterVersion(FilterVersion filter){
        this.filter = filter;
        this.isReadByVersion = true;
    }
    public boolean isOkNode(Node node){
        long s = this.nodeVersionStore.getNodeCreateVersion(node);
        long e = this.nodeVersionStore.getNodeDeleteVersion(node);
        return this.filter.isOk(s,e);
    }
    public boolean isOkRelation(Relation rel){
        long s = this.relationVersionStore.getRelationCreateVersion(rel);
        long e = this.relationVersionStore.getRelationDeleteVersion(rel);
        return this.filter.isOk(s,e);
    }
    public boolean isReadByVersion(){
        return isReadByVersion;
    }

    public void setReadName(String name){
        this.graphName = name;
        this.isReadByName = true;
    }
    public boolean isReadByName(){
        return this.isReadByName;
    }

    public UserGraph<Long, Long, String> getUserGraph() {
        return userGraph;
    }

    public long getNextVersion(){
        long v = this.version.incrementAndGet();
        this.versionSet.add(v);
        return v;
    }
    public long[] listAllVersions(){
        long []versions = new long[this.versionSet.size()];
        int i = 0;
        for(long v: this.versionSet){
            versions[i++]= v;
        }
        return versions;
    }


    public void addNode(Node node,Long version){
        versionSet.add(version);
        this.basicGraph.addNode(node);
        this.nodeVersionStore.setNodeCreateVersion(node,version);
        this.nodeVersionStore.setNodeDeleteVersion(node,Long.MAX_VALUE);
    }
    public void deleteNode(Node node,Long version){
        //this.basicGraph.deleteNode(node);
        this.nodeVersionStore.setNodeDeleteVersion(node,version);
    }
    public void addRelation(Relation relation,Long version){
        versionSet.add(version);
        this.basicGraph.addRelation(relation);
        this.relationVersionStore.setRelationCreateVersion(relation,version);
        this.relationVersionStore.setRelationDeleteVersion(relation,Long.MAX_VALUE);
    }
    public void deleteRelation(Relation relation,Long version){
        //this.basicGraph.deleteRelation(relation);
        this.relationVersionStore.setRelationDeleteVersion(relation,version);
    }


    public Iterator<Node> AllNodesByVersion(Long version){
        Iterator<Node> it = this.basicGraph.AllNodes();
        HashSet<Node> nodeHashSet = new HashSet<>();
        while (it.hasNext()){
            Node node =  it.next();
            long startVersion = this.nodeVersionStore.getNodeCreateVersion(node);
            long endVersion = this.nodeVersionStore.getNodeDeleteVersion(node);
            if(startVersion<= version && endVersion>= version){
                nodeHashSet.add(node);
            }
        }
        return nodeHashSet.iterator();
    }
    public Iterator<Relation> AllRelationsByVersion(Long version){
        Iterator<Relation> it = this.basicGraph.AllRelations();
        HashSet<Relation> relationHashSet = new HashSet<>();
        while (it.hasNext()){
            Relation relation = it.next();
            long startVersion = this.relationVersionStore.getRelationCreateVersion(relation);
            long endVersion = this.relationVersionStore.getRelationDeleteVersion(relation);
            if(startVersion<= version && endVersion>= version){
                relationHashSet.add(relation);
            }
        }
        return relationHashSet.iterator();
    }


    public Iterator<Node> AllNodesByFilterVersion(){
        Iterator<Node> it = this.basicGraph.AllNodes();
        HashSet<Node> nodeHashSet = new HashSet<>();
        while (it.hasNext()){
            Node node =  it.next();
            long startVersion = this.nodeVersionStore.getNodeCreateVersion(node);
            long endVersion = this.nodeVersionStore.getNodeDeleteVersion(node);
            if(this.filter.isOk(startVersion,endVersion)){
                nodeHashSet.add(node);
            }
        }
        this.isReadByVersion = false;
        return nodeHashSet.iterator();
    }

    public Iterator<Relation> AllRelationsByFilterVersion(){
        Iterator<Relation> it = this.basicGraph.AllRelations();
        HashSet<Relation> relationHashSet = new HashSet<>();
        while (it.hasNext()){
            Relation relation = it.next();
            long startVersion = this.relationVersionStore.getRelationCreateVersion(relation);
            long endVersion = this.relationVersionStore.getRelationDeleteVersion(relation);
            if(this.filter.isOk(startVersion,endVersion)){
                relationHashSet.add(relation);
            }
        }
        this.isReadByVersion = false;
        return relationHashSet.iterator();
    }


    public Iterator<Long> AllNodesByGraphName(){
        //Iterator<Long> it = this.userGraph.allnodesOfGraph(this.graphName);

        this.isReadByName = false;
        return this.userGraph.allnodesOfGraph(this.graphName);
    }

    public Iterator<Long> AllRelationsByGraphName(){
        this.isReadByName = false;
        return this.userGraph.allRelationsOfGraph(this.graphName);
    }


    //Iterator<Node> NodesUnion(Set<Version> versionSet);
    //Iterator<Relation> RelationsUnion(Set<Version> versionSet);


    //Iterator<Node> NodesIntersection(Set<Version> versionSet);
    //Iterator<Relation> RelationsIntersection(Set<Version> versionSet);


    //Iterator<Node> NodesExcept(Version first, Version second);
    //Iterator<Relation> RelationsExcept(Version first, Version second);

    //Iterator<Node> NodesDelta(Version v1, Version v2);
    //Iterator<Relation> RelationsDelta(Version v1, Version v2);



    public ContinuousGraph subGraph(long vStart, long vEnd){
        ContinuousGraph<Node,Relation> subGraph = new ContinuousGraph<>();
        Iterator<Node> nodesIter = this.basicGraph.AllNodes();
        while(nodesIter.hasNext()){
            Node node = nodesIter.next();
            long s = this.nodeVersionStore.getNodeCreateVersion(node);
            long e = this.nodeVersionStore.getNodeDeleteVersion(node);
            if(s<=vStart && vEnd<=e){
                subGraph.addNode(node,s);
                subGraph.deleteNode(node,e);
            }
        }
        Iterator<Relation> relIter = this.basicGraph.AllRelations();
        while(relIter.hasNext()){
            Relation relation = relIter.next();
            long s = this.relationVersionStore.getRelationCreateVersion(relation);
            long e = this.relationVersionStore.getRelationDeleteVersion(relation);
            if(s<=vStart && vEnd<=e){
                subGraph.addRelation(relation,s);
                subGraph.deleteRelation(relation,e);
            }
        }
        return subGraph;
    }


}
