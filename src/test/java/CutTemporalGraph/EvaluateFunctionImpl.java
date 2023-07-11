package CutTemporalGraph;

import CutTemporalGraph.backup.TreeState;
import cn.DynamicGraph.graphdb.welding.BigContinuousGraph;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class EvaluateFunctionImpl implements EvaluateFunction {

    private int[] queries;
    private BigContinuousGraph graph;
    private HashMap<Pair<Long,Long>,Long> sizeCache;
    public EvaluateFunctionImpl(int[]queries, BigContinuousGraph graph){
        this.queries = queries;
        this.graph = graph;
        this.sizeCache = new HashMap<>();
    }


    private long sumOfNodeSize(long start,long end,BigContinuousGraph graph){
        HashSet<Long> tempV = new HashSet<>();
        for(long v = start;v<=end;v++){
            tempV.add(v);
        }
        Long[]versions = new Long[tempV.size()];
        tempV.toArray(versions);
        Iterator<Long> iter = graph.AllNodesByMultiVersion(versions);
        long nodesSum = 0;
        while(iter.hasNext()){
            iter.next();
            nodesSum = nodesSum +1;
        }
        return nodesSum;
    }


    @Override
    public long evaluate(ArrayList<SecGraphState> state) {
        long score = 0;
        for(int version: queries){
            for(SecGraphState secGraphState: state){
                if(secGraphState.getStart()<=version && version<=secGraphState.getEnd()){
                    Pair<Long,Long> pair = Pair.of(secGraphState.getStart(),secGraphState.getEnd());
                    long nodeSize = this.sizeCache.getOrDefault(pair,-1L);
                    if(nodeSize<0){
                        //score = score + nodeSize;
                        nodeSize = sumOfNodeSize(secGraphState.getStart(),secGraphState.getEnd(),this.graph);
                        this.sizeCache.put(pair,nodeSize);
                    }
                    //long nodeSize = sumOfNodeSize(secGraphState.getStart(),secGraphState.getEnd(),this.graph);
                    score = score + nodeSize;
                    break;
                }
            }
        }
        return score;
    }
}
