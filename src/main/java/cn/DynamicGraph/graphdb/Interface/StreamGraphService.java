package cn.DynamicGraph.graphdb.Interface;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterable;

public interface StreamGraphService {

    long beginNextVersion();
    void commit();

    void beginRead();


    Node createNode();
    Relationship createRelationship(Node srartNode, Node otherNode, RelationshipType type);
    void deleteNode(Node node);
    void deleteRelation(Relationship relationship);


    long [] listAllVersions();
    ResourceIterable<Node> getAllNodes(long version);
    ResourceIterable<Relationship> getAllRelationships(long version);
}
