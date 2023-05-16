package cn.DynamicGraph.graphdb.kernel;

import cn.DynamicGraph.graphdb.kernel.BasicGraph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class UserGraph<Node,Relation,GraphName> {
    private HashMap<GraphName, BasicGraph<Node,Relation>> graphPool;
    public UserGraph(){
        this.graphPool = new HashMap<>();
    }
    public BasicGraph<Node,Relation> addGraph(GraphName name){
        BasicGraph<Node,Relation> graph = new BasicGraph<>();
        this.graphPool.put(name,graph);
        return graph;
    }

    private BasicGraph<Node,Relation> getGraph(GraphName name){
        return this.graphPool.getOrDefault(name,new BasicGraph<>());
    }
    public void deleteGraph(GraphName name){
        this.graphPool.remove(name);
    }

    public Iterator<Node> allnodesOfGraph(GraphName name){
        return getGraph(name).AllNodes();
    }

    public Iterator<Relation> allRelationsOfGraph(GraphName name){
        return getGraph(name).AllRelations();
    }

    public void addNodeToGraph(GraphName name, Node node){
        getGraph(name).addNode(node);
    }
    public void addNodesToGraph(GraphName name, Set<Node> nodes){
        for(Node n: nodes){
            addNodeToGraph(name,n);
        }
    }

    public void addRelationToGraph(GraphName name, Relation relation){
        getGraph(name).addRelation(relation);
    }

    public void addRelationsToGraph(GraphName name, Set<Relation> relations){
        for(Relation r: relations){
            addRelationToGraph(name,r);
        }
    }


}
