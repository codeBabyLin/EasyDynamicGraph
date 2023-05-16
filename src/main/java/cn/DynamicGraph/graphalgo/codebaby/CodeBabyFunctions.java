package cn.DynamicGraph.graphalgo.codebaby;

import cn.DynamicGraph.Common.DGVersion;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.UserFunction;

public class CodeBabyFunctions {

    public CodeBabyFunctions() {
    }

    @Description("Output code baby name.")
    @UserFunction(name = "getBabyName")
    public String getName() {
        String name = "handsome code baby!!!";
        return name;
    }

    @Description("Output important date.")
    @UserFunction(name = "getBabyDate")
    public String getDate() {
        String date = "2023-5-9";
        return date;
    }

    @Description("Output important date.")
    @UserFunction(name = "getVersion")
    public long getVersion(@Name("startVersion") long s, @Name("endVersion") long e) {
        return DGVersion.setStartEndVersion(s,e);
    }


}
