package dataFactory.sampleGraph;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.HashSet;

public class SampleGraph {
    public HashSet<Integer> getNodes() {
        return nodes;
    }

    public HashSet<Pair<Integer, Integer>> getRels() {
        return rels;
    }

    public void setNodes(HashSet<Integer> nodes) {
        this.nodes = nodes;
    }

    public void setRels(HashSet<Pair<Integer, Integer>> rels) {
        this.rels = rels;
    }

    HashSet<Integer> nodes ;
    HashSet<Pair<Integer,Integer>> rels;

    public HashMap<Integer, HashSet<Pair<Integer, Integer>>> getNodes_rels() {
        return nodes_rels;
    }

    public void setNodes_rels(HashMap<Integer, HashSet<Pair<Integer, Integer>>> nodes_rels) {
        this.nodes_rels = nodes_rels;
    }

    public HashMap<Integer, String> getNodeLabels() {
        return nodeLabels;
    }

    public void setNodeLabels(HashMap<Integer, String> nodeLabels) {
        this.nodeLabels = nodeLabels;
    }

    public HashMap<Pair<Integer, Integer>, String> getRelationTypes() {
        return relationTypes;
    }

    public void setRelationTypes(HashMap<Pair<Integer, Integer>, String> relationTypes) {
        this.relationTypes = relationTypes;
    }

    public HashMap<Integer, HashMap<String, Object>> getNodeProperties() {
        return nodeProperties;
    }

    public void setNodeProperties(HashMap<Integer, HashMap<String, Object>> nodeProperties) {
        this.nodeProperties = nodeProperties;
    }

    public HashMap<Pair<Integer, Integer>, HashMap<String, Object>> getRelationProperties() {
        return relationProperties;
    }

    public void setRelationProperties(HashMap<Pair<Integer, Integer>, HashMap<String, Object>> relationProperties) {
        this.relationProperties = relationProperties;
    }

    HashMap<Integer,HashSet<Pair<Integer,Integer>>> nodes_rels;
    HashMap<Integer, String> nodeLabels;
    HashMap<Pair<Integer,Integer>, String> relationTypes;
    HashMap<Integer,HashMap<String,Object>> nodeProperties;
    HashMap<Pair<Integer,Integer>,HashMap<String,Object>> relationProperties;

    public void setNodeLabel(int id,String label){
        this.nodeLabels.put(id,label);
    }
    public String getNodeLabel(int id){
        return this.nodeLabels.get(id);
    }
    public void setNodeProperty(int id,String key,Object value){
        this.nodeProperties.computeIfAbsent(id,k->new HashMap<>());
        this.nodeProperties.get(id).put(key,value);
    }
    public Object getNodeProperty(int id,String key){
        return this.nodeProperties.get(id).get(key);
    }

    public void setRelationType(Pair<Integer,Integer> rel, String type){
        this.relationTypes.put(rel,type);
    }
    public String getRelationType(Pair<Integer,Integer> rel){
        return this.relationTypes.get(rel);
    }
    public void setRelationProperty(Pair<Integer,Integer> rel,String key,Object value){
        this.relationProperties.computeIfAbsent(rel,k->new HashMap<>());
        this.relationProperties.get(rel).put(key,value);
    }
    public Object getRelationProperty(Pair<Integer,Integer> rel,String key){
        return this.relationProperties.get(rel).get(key);
    }

 /*   public HashSet<Integer> getNodes(){
        return this.nodes;
    }*/


    public SampleGraph(){
        nodes = new HashSet<>();
        rels = new HashSet<>();
        nodes_rels = new HashMap<>();
        nodeLabels = new HashMap<>();
        relationTypes = new HashMap<>();
        nodeProperties = new HashMap<>();
        relationProperties = new HashMap<>();
    }
    public void addNode(Integer e,String label,HashMap<String,Object> property){
        this.nodes.add(e);
        this.nodeLabels.put(e,label);
        this.nodeProperties.put(e,property);
    }
    public void delNode(Integer e){
        this.nodes.remove(e);
        this.nodeLabels.remove(e);
        this.nodeProperties.remove(e);
    }
    public void addNodes(HashSet<Integer> addNodes,HashMap<Integer, String> nodeLabels,HashMap<Integer,HashMap<String,Object>> nodeProperties){
        this.nodes.addAll(addNodes);
        this.nodeLabels.putAll(nodeLabels);
        this.nodeProperties.putAll(nodeProperties);

    }
    public void delNodes(HashSet<Integer> delNodes){
        this.nodes.removeAll(delNodes);
        delNodes.forEach(integer -> {
            this.nodeLabels.remove(integer);
            this.nodeProperties.remove(integer);
        });
    }

    public void addRel(Pair<Integer,Integer> rel,String type,HashMap<String,Object> property){

        this.rels.add(rel);

        int startNode = rel.getLeft();
        int endNode = rel.getRight();

        nodes_rels.computeIfAbsent(startNode,k->new HashSet<>());
        nodes_rels.computeIfAbsent(endNode,k->new HashSet<>());
        nodes_rels.get(startNode).add(rel);
        nodes_rels.get(endNode).add(rel);

        this.relationTypes.put(rel,type);
        this.relationProperties.put(rel,property);
    }
    public void delRel(Pair<Integer,Integer> rel){
        this.rels.remove(rel);

        int startNode = rel.getLeft();
        int endNode = rel.getRight();

        nodes_rels.computeIfAbsent(startNode,k->new HashSet<>());
        nodes_rels.computeIfAbsent(endNode,k->new HashSet<>());
        nodes_rels.get(startNode).remove(rel);
        nodes_rels.get(endNode).remove(rel);


        this.relationTypes.remove(rel);
        this.relationProperties.remove(rel);
    }
    public void addRels(HashSet<Pair<Integer,Integer>> relsAdd,HashMap<Pair<Integer,Integer>, String> relationTypes,HashMap<Pair<Integer,Integer>,HashMap<String,Object>> relationProperties){
        this.rels.addAll(relsAdd);
        this.relationTypes.putAll(relationTypes);
        this.relationProperties.putAll(relationProperties);

        relsAdd.forEach(rel -> {
            int startNode = rel.getLeft();
            int endNode = rel.getRight();
            nodes_rels.computeIfAbsent(startNode,k->new HashSet<>());
            nodes_rels.computeIfAbsent(endNode,k->new HashSet<>());
            nodes_rels.get(startNode).add(rel);
            nodes_rels.get(endNode).add(rel);
        });

    }
    public void delRels(HashSet<Pair<Integer,Integer>> relsDel){
        this.rels.removeAll(relsDel);
        relsDel.forEach(integerIntegerPair -> {
            this.relationTypes.remove(integerIntegerPair);
            this.relationProperties.remove(integerIntegerPair);
            int startNode = integerIntegerPair.getLeft();
            int endNode = integerIntegerPair.getRight();

            nodes_rels.computeIfAbsent(startNode,k->new HashSet<>());
            nodes_rels.computeIfAbsent(endNode,k->new HashSet<>());
            nodes_rels.get(startNode).remove(integerIntegerPair);
            nodes_rels.get(endNode).remove(integerIntegerPair);
        });
    }


}
