package cn.DynamicGraph.graphdb.kernel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;


public class RelationStore<Relation> {
    private HashSet<Relation> relaions;
    public RelationStore(){
        this.relaions = new HashSet<>();
    }
    public void addNode(Relation node){
        this.relaions.add(node);
    }
    public void deleteNode(Relation node){
        this.relaions.remove(node);
    }
    public Iterator<Relation> all(){
        return this.relaions.iterator();
    }


    public boolean existRelation(Relation r){
        return this.relaions.contains(r);
    }

    public RelationStore<Relation> copy(){
        RelationStore<Relation> relationStoreStore = new RelationStore<Relation>();
       this.relaions.forEach(new Consumer<Relation>() {
           @Override
           public void accept(Relation relation) {
               relationStoreStore.addNode(relation);
           }
       });
        return relationStoreStore;
    }
}
