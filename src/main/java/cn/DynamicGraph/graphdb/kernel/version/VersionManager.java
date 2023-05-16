package cn.DynamicGraph.graphdb.kernel.version;

import java.util.ArrayList;
import java.util.Iterator;


public class VersionManager<Version> {
    private ArrayList<Version> versions;
    private VersionGenerator<Version> generator;
    public VersionManager(VersionGenerator generator){
        this.versions = new ArrayList<>();
        this.generator = generator;
    }

    public Version getNextVersion(){
        Version v = this.generator.getNextVersion();
        this.versions.add(v);
        return v;
    }
    public Iterator<Version> listAllVersions(){
        return this.versions.iterator();
    }
}
