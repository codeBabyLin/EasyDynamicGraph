package cn.DynamicGraph.graphdb.Interface;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterable;

public interface BatchGraphService {

    long beginNextVersion();
    Node createNode(long version);
    Relationship createRelationship(Node srartNode, Node otherNode, RelationshipType type,long version);



    long [] listAllVersions();
    ResourceIterable<Node> getAllNodes(long version);
    ResourceIterable<Relationship> getAllRelationships(long version);
}
