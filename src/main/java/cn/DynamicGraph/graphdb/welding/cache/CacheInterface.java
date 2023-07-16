package cn.DynamicGraph.graphdb.welding.cache;

import cn.DynamicGraph.graphdb.kernel.BasicGraph;

public interface CacheInterface {

    void cacheGraph(BasicGraph<Long,Long> graph, int version);
    boolean exist(int version);
    BasicGraph<Long,Long> hitGraph(int version);
}
