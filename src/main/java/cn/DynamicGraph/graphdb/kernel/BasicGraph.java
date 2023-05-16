package cn.DynamicGraph.graphdb.kernel;

import java.util.Iterator;

public class BasicGraph<Node,Relation> {
    private NodeStore<Node> nodeStore;
    private RelationStore<Relation> relationStore;

    public BasicGraph(){
        this.nodeStore = new NodeStore<Node>();
        this.relationStore = new RelationStore<Relation>();
    }

    public void setNodeStore(NodeStore<Node> nodeStore) {
        this.nodeStore = nodeStore;
    }

    public void setRelationStore(RelationStore<Relation> relationStore) {
        this.relationStore = relationStore;
    }

    public void addNode(Node node){
        this.nodeStore.addNode(node);
    }
    public void deleteNode(Node node){
        this.nodeStore.deleteNode(node);
    }

    public void addRelation(Relation relation){
        this.relationStore.addNode(relation);
    }
    public void deleteRelation(Relation relation){
        this.relationStore.deleteNode(relation);
    }

    public Iterator<Node> AllNodes(){
        return this.nodeStore.all();
    }
    public Iterator<Relation> AllRelations(){
        return this.relationStore.all();
    }

    public BasicGraph<Node,Relation> copy(){
        NodeStore<Node> ns = this.nodeStore.copy();
        RelationStore<Relation> rs = this.relationStore.copy();
        BasicGraph<Node,Relation> g = new BasicGraph<Node,Relation>();
        g.setNodeStore(ns);
        g.setRelationStore(rs);
        return g;
    }

    //graph operations


}
