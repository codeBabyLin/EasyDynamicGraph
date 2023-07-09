package dataFactory.sampleGraph;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.HashSet;

public class Nodes {

    Labels nodeLabels;
    Properties nodeProperties;
    HashSet<Integer> nodes ;
    HashMap<Integer,HashSet<Integer>> nodeRelNodes;//node ->>>>node1 node 2 node3
    HashMap<Pair<Integer,Integer>,Integer> nodeRels;
    HashMap<Integer,Pair<Integer,Integer>> relNodes;

    public Nodes(){
        nodeLabels = new Labels();
        nodeProperties = new Properties();
        nodes = new HashSet<>();
        nodeRelNodes = new HashMap<>();
        nodeRels = new HashMap<>();
        relNodes = new HashMap<>();
    }
    public void addNode(Integer e){
        this.nodes.add(e);
    }
    public void delNode(Integer e){
        this.nodes.remove(e);
    }
    public void addNodes(HashSet<Integer> addNodes){
        this.nodes.addAll(addNodes);
    }
    public void delNodes(HashSet<Integer> delNodes){
        this.nodes.removeAll(delNodes);
    }

}
