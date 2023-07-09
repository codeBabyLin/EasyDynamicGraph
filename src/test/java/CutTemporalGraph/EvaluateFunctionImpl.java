package CutTemporalGraph;

import CutTemporalGraph.backup.TreeState;
import cn.DynamicGraph.graphdb.welding.BigContinuousGraph;

public class EvaluateFunctionImpl implements EvaluateFunction {

    private int[] queries;
    private BigContinuousGraph graph;
    public EvaluateFunctionImpl(int[]queries, BigContinuousGraph graph){
        this.queries = queries;
        this.graph = graph;
    }



    @Override
    public int evaluate(TreeState state) {
        return 0;
    }
}
