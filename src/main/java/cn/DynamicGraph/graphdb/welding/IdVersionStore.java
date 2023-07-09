package cn.DynamicGraph.graphdb.welding;

import cn.DynamicGraph.KVStore.RocksDBFactory;
import cn.DynamicGraph.KVStore.RocksDBStorage;

import java.util.Iterator;

public class IdVersionStore {

    private RocksDBStorage db;
    public IdVersionStore(String dataPath){
        this.db = RocksDBFactory.getDB(dataPath);
    }
    public void add(byte[] data, byte[] version){
        this.db.put(data,version);
    }
    public void remove(byte[] key){
        this.db.delete(key);
    }
    public byte[] get(byte[] key){
        return this.db.get(key);
    }

    public Iterator<byte[]> all(){
        return this.db.All();
    }

    public boolean exist(byte[]key){
        return this.db.exist(key);
    }
    public void flush(){
        this.db.flush();
    }
}
