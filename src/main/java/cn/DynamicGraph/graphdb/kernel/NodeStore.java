package cn.DynamicGraph.graphdb.kernel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;

public class NodeStore<Node> {
    private HashSet<Node> nodes;
    public NodeStore(){
        this.nodes = new HashSet<>();
    }
    public void addNode(Node node){
        this.nodes.add(node);
    }
    public void deleteNode(Node node){
        this.nodes.remove(node);
    }

    public Iterator<Node> all(){
        return this.nodes.iterator();
    }

    public NodeStore<Node> copy(){
        NodeStore<Node> nodeStore = new NodeStore<Node>();
        this.nodes.forEach(new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                nodeStore.addNode(node);
            }
        });
        return nodeStore;
    }
}
