package cn.DynamicGraph.graphdb.kernel.version;

import java.util.concurrent.atomic.AtomicLong;

public class LongVersionGenerator implements VersionGenerator<Long> {

    private AtomicLong versionGen;
    public LongVersionGenerator(){
        this.versionGen = new AtomicLong(0);
    }

    public LongVersionGenerator(Long inite){
        this.versionGen = new AtomicLong(inite);
    }

    @Override
    public Long getNextVersion() {
        return this.versionGen.incrementAndGet();
    }
}
