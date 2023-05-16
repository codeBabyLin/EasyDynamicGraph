package cn.DynamicGraph.graphdb.storePolicy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class InternalGraph {
    private HashSet<Long> nodes;
    private HashSet<Long> relations;

    public InternalGraph(){
        this.nodes = new HashSet<>();
        this.relations = new HashSet<>();
    }
    
    public void  addNode(Long node){
        this.nodes.add(node);
    }
    public void  delNode(Long node){
        this.nodes.add(node);
    }
    public void  addNodes(Long [] nodes){
        this.nodes.addAll(Arrays.asList(nodes));

    }
    public void  deldNodes(Long [] nodes){
        for(Long node: nodes) {
            this.nodes.remove(node);
        }
    }

    public void  addRelation(Long node){
        this.relations.add(node);
    }
    public void  delRelation(Long node){
        this.relations.add(node);
    }
    public void  addRelations(Long [] nodes){
        this.relations.addAll(Arrays.asList(nodes));

    }
    public void  deldRelations(Long [] nodes){
        for(Long node: nodes) {
            this.relations.remove(node);
        }
    }

    public Iterator<Long> AllNodes(){
        return this.nodes.iterator();
    }

    public Iterator<Long> AllRelations(){
        return this.relations.iterator();
    }
    private HashSet<Long> Nodes(){
        return this.nodes;
    }

    private HashSet<Long> Relations(){
        return this.relations;
    }

    private void addNodes(HashSet<Long> nodes){
        this.nodes.addAll(nodes);
    }
    private void addRelations(HashSet<Long> nodes){
        this.relations.addAll(nodes);
    }
    

    public InternalGraph copy() {
        InternalGraph graph = new InternalGraph();
        graph.addNodes(this.nodes);
        graph.addRelations(this.relations);
        return graph;
    }
}
