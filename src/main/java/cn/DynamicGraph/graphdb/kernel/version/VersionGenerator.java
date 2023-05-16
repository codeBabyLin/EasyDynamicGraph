package cn.DynamicGraph.graphdb.kernel.version;

public interface VersionGenerator<Version> {
    Version getNextVersion();
}
