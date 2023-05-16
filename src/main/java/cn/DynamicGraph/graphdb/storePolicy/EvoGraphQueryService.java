package cn.DynamicGraph.graphdb.storePolicy;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterable;

public interface EvoGraphQueryService {

    long [] listAllVersions();
    ResourceIterable<Node> getAllNodes(long version);
    ResourceIterable<Relationship> getAllRelationships(long version);




}
