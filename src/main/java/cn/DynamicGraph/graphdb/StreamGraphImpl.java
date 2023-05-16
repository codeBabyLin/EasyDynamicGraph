package cn.DynamicGraph.graphdb;

import cn.DynamicGraph.graphdb.Interface.StreamGraphService;
import cn.DynamicGraph.graphdb.kernel.ContinuousGraph;
import org.neo4j.graphdb.*;
import org.neo4j.helpers.collection.PrefetchingResourceIterator;

import java.util.Collection;
import java.util.Iterator;

public class StreamGraphImpl implements StreamGraphService {

    private GraphDatabaseService pGraph;
    private ContinuousGraph<Long,Long> vGraph;
    private long currentVersion;
    private Transaction tx;
    public StreamGraphImpl(GraphDatabaseService graph){
        this.pGraph = graph;
        this.vGraph = new ContinuousGraph<>();
    }



    @Override
    public long beginNextVersion() {
        this.currentVersion = vGraph.getNextVersion();
        if(this.tx == null) this.tx = this.pGraph.beginTx();
        return currentVersion;
    }

    @Override
    public void commit() {
        this.tx.success();
        this.tx.close();
    }

    @Override
    public void beginRead() {
        this.pGraph.beginTx();
    }

    @Override
    public Node createNode() {
        Node node = this.pGraph.createNode();
        this.vGraph.addNode(node.getId(),this.currentVersion);
        return node;
    }

    @Override
    public Relationship createRelationship(Node srartNode, Node otherNode, RelationshipType type) {
        Relationship r= srartNode.createRelationshipTo(otherNode,type);
        this.vGraph.addRelation(r.getId(),currentVersion);
        return r;
    }

    @Override
    public void deleteNode(Node node) {
        this.vGraph.deleteNode(node.getId(),this.currentVersion);
    }

    @Override
    public void deleteRelation(Relationship relationship) {
        this.vGraph.deleteRelation(relationship.getId(),this.currentVersion);
    }

    @Override
    public long[] listAllVersions() {
        return this.vGraph.listAllVersions();
    }

    @Override
    public ResourceIterable<Node> getAllNodes(long version) {
        return () -> {
            Iterator<Long> iterator = StreamGraphImpl.this.vGraph.AllNodesByVersion(version);
            return new PrefetchingResourceIterator<Node>() {
                @Override
                public void close() {
                }
                @Override
                protected Node fetchNextOrNull() {
                    if(iterator.hasNext()){
                        return StreamGraphImpl.this.pGraph.getNodeById(iterator.next());
                    }
                    else{
                        this.close();
                        return null;
                    }
                }
            };
        };
    }

    @Override
    public ResourceIterable<Relationship> getAllRelationships(long version) {
        return () -> {
            Iterator<Long> iterator = StreamGraphImpl.this.vGraph.AllRelationsByVersion(version);
            return new PrefetchingResourceIterator<Relationship>() {
                @Override
                public void close() {
                }
                @Override
                protected Relationship fetchNextOrNull() {
                    if(iterator.hasNext()){
                        return StreamGraphImpl.this.pGraph.getRelationshipById(iterator.next());
                    }
                    else{
                        this.close();
                        return null;
                    }
                }
            };
        };
    }
}
