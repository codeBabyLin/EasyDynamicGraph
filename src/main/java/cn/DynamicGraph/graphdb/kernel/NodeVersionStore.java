package cn.DynamicGraph.graphdb.kernel;

import java.util.HashMap;

public class NodeVersionStore<Node> {
    private HashMap<Node,Long> nodesCreateVersion;
    private HashMap<Node,Long> nodesDeleteVersion;

    public NodeVersionStore(){
        this.nodesCreateVersion = new HashMap<>();
        this.nodesDeleteVersion = new HashMap<>();
    }

    public void setNodeCreateVersion(Node node,Long version){
        this.nodesCreateVersion.put(node,version);
    }
    public void setNodeDeleteVersion(Node node,Long version){
        this.nodesDeleteVersion.put(node,version);
    }

    public long getNodeCreateVersion(Node node){
        return this.nodesCreateVersion.get(node);
    }
    public long getNodeDeleteVersion(Node node){
        return this.nodesDeleteVersion.get(node);
    }



}
