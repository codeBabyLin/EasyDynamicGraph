package cn.DynamicGraph.graphdb.kernel;

import java.util.HashMap;

public class RelationVersionStore<Relation> {
    private HashMap<Relation,Long> relsCreateVersion;
    private HashMap<Relation,Long> relsDeleteVersion;
    public RelationVersionStore(){
        this.relsCreateVersion = new HashMap<>();
        this.relsDeleteVersion = new HashMap<>();
    }

    public void setRelationCreateVersion(Relation relation, Long version){
        this.relsCreateVersion.put(relation,version);
    }
    public void setRelationDeleteVersion(Relation relation, Long version){
        this.relsDeleteVersion.put(relation,version);
    }

    public long getRelationCreateVersion(Relation relation){
        return this.relsCreateVersion.get(relation);
    }
    public long getRelationDeleteVersion(Relation relation){
        return this.relsDeleteVersion.get(relation);
    }

}
