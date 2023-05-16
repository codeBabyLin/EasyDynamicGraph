package cn.DynamicGraph.graphdb;

import cn.DynamicGraph.graphdb.Interface.BatchGraphService;
import cn.DynamicGraph.graphdb.kernel.ContinuousGraph;
import cn.DynamicGraph.graphdb.kernel.DiscreteGraph;
import org.neo4j.graphdb.*;
import org.neo4j.helpers.collection.PrefetchingResourceIterator;

import java.util.Iterator;

public class BatchGraphImpl implements BatchGraphService {

    private GraphDatabaseService pGraph;
    private DiscreteGraph<Long,Long> vGraph;
    private long currentVersion;


    public BatchGraphImpl(GraphDatabaseService pGraph, boolean isCopy){
        this.pGraph = pGraph;
        this.vGraph = new DiscreteGraph<Long,Long>(isCopy);
    }

    @Override
    public long beginNextVersion() {
        return this.vGraph.nextGraph();
    }

    @Override
    public Node createNode(long version) {
        Node node  = this.pGraph.createNode();
        this.vGraph.addNode(node.getId(),version);
        return node;
    }

    @Override
    public Relationship createRelationship(Node srartNode, Node otherNode, RelationshipType type, long version) {
        Relationship r = srartNode.createRelationshipTo(otherNode,type);
        this.vGraph.addRelation(r.getId(),version);
        return r;
    }

    @Override
    public long[] listAllVersions() {

        return this.vGraph.listAllVersions();
    }

    @Override
    public ResourceIterable<Node> getAllNodes(long version) {
        return ()->{
            Iterator<Long> iterator = BatchGraphImpl.this.vGraph.AllNodes(version);
            return new PrefetchingResourceIterator<Node>() {
                @Override
                public void close() {
                }

                @Override
                protected Node fetchNextOrNull() {
                    if(iterator.hasNext()){
                        return BatchGraphImpl.this.pGraph.getNodeById(iterator.next());
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
            Iterator<Long> iterator = BatchGraphImpl.this.vGraph.AllRelations(version);
            return new PrefetchingResourceIterator<Relationship>() {
                @Override
                public void close() {
                }
                @Override
                protected Relationship fetchNextOrNull() {
                    if(iterator.hasNext()){
                        return BatchGraphImpl.this.pGraph.getRelationshipById(iterator.next());
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
