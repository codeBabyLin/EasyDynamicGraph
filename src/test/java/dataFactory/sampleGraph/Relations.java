package dataFactory.sampleGraph;

import java.util.HashSet;

public class Relations {


    Types relTypes;
    Properties relProperties;
    HashSet<Integer> rels;



    public Relations(){
        rels = new HashSet<>();
        relTypes = new Types();
        relProperties = new Properties();
    }
    public void addRel(Integer rel){
        this.rels.add(rel);
    }
    public void delRel(Integer rel){
        this.rels.remove(rel);
    }

    public void addRels(HashSet<Integer> relsAdd){
        this.rels.addAll(relsAdd);
    }
    public void delRels(HashSet<Integer> relsDel){
        this.rels.removeAll(relsDel);
    }
}
