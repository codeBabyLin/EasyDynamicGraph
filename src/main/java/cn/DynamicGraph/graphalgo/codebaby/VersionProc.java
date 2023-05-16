package cn.DynamicGraph.graphalgo.codebaby;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.internal.GraphDatabaseAPI;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

import java.util.Arrays;
import java.util.stream.Stream;

public class VersionProc {

    @Context
    public GraphDatabaseAPI db;

    @Procedure("codebaby.listVersion")
    @Description("CALL codebaby.listVersion()")
    public Stream<LongResult> listVersion(){
        long[] versions = this.db.getDyGraph().listAllVersions();

        return Arrays.stream(versions).mapToObj(LongResult::new);
    }
    @Procedure("codebaby.nextVersion")
    @Description("CALL codebaby.nextVersion()")
    public Stream<LongResult> nextVersion(){
        long version = this.db.getDyGraph().getNextVersion();

        return Stream.of(new LongResult(version));
    }

    @Procedure("codebaby.clearVersion")
    @Description("CALL codebaby.clearVersion()")
    public Stream<BooleanResult> clearVersion(){
        boolean res = this.db.getDyGraph().reSet();

        return Stream.of(new BooleanResult(res));
    }

    public static class BooleanResult {
        public boolean value;

        BooleanResult(boolean value) {
            this.value = value;

        }
    }

    public static class LongResult {
        public Long value;

        LongResult(long value) {
            this.value = value;

        }
    }

}
