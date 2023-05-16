package cn.DynamicGraph.graphalgo.codebaby;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

import java.util.stream.Stream;

public class CodeBabyProc {


    @Procedure("codebaby.hello")
    @Description("CALL codebaby.hello(msg: String)")
    public Stream<StringMsg> sayHello(@Name("msg") String msg){
        String res = "codebaby say hello to " + msg;
        return Stream.of(new StringMsg(res));
    }

    public static class StringMsg {
        public final String value;

        StringMsg(String value) {
            this.value = value;
        }
    }
}
