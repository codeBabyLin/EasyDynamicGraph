package cn.DynamicGraph.graphdb.storePolicy;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterable;

public interface DynamicGraphDatabaseService {
    long [] listAllVersions();

    ResourceIterable<Node> getAllNodesInSingleVersion(long version);
    ResourceIterable<Relationship> getAllRelationshipsInSingleVersion(long version);

    ResourceIterable<Node> getAllNodesInVersionDelta(long startVersion, long endVersion);
    ResourceIterable<Relationship> getAllRelationshipsInVersionDelta(long startVersion, long endVersion);

    ResourceIterable<Node> getAllNodesInVersions(long startVersion, long endVersion);
    ResourceIterable<Relationship> getAllRelationshipsInVersions(long startVersion, long endVersion);

    //Node createNodeWithVersion(long version);

}
