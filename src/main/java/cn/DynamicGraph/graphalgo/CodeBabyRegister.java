package cn.DynamicGraph.graphalgo;

import cn.DynamicGraph.graphalgo.codebaby.CodeBabyFunctions;
import cn.DynamicGraph.graphalgo.codebaby.CodeBabyProc;
import cn.DynamicGraph.graphalgo.codebaby.DynamicShortestPathProc;
import cn.DynamicGraph.graphalgo.codebaby.VersionProc;
import org.neo4j.graphalgo.ShortestPathProc;
import org.neo4j.kernel.impl.proc.Procedures;

public class CodeBabyRegister {
    private Procedures procedures;
    public CodeBabyRegister(Procedures procedures){
        this.procedures = procedures;
    }
    private void registerFuction(){
        try {
            this.procedures.registerBuiltInFunctions(CodeBabyFunctions.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void registerProc(){
        try {
            this.procedures.registerProcedure(CodeBabyProc.class);
            this.procedures.registerProcedure(VersionProc.class);
            this.procedures.registerProcedure(ShortestPathProc.class);
            this.procedures.registerProcedure(DynamicShortestPathProc.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void registerCodeBaby(){
       registerFuction();
       registerProc();
    }
}
