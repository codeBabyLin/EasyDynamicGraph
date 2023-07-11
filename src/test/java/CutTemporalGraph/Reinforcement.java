package CutTemporalGraph;

import CutTemporalGraph.backup.TreeState;
import CutTemporalGraph.backup.TreeStateManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Reinforcement {

    private ArrayList<SecGraphState> currentState;
    private EvaluateFunction evaluateFunction;
    //private TreeStateManage tsm;
    private SecGraphStateManage stateManage;
    public Reinforcement(ArrayList<SecGraphState> state,EvaluateFunction evaluateFunction){
        this.currentState = state;
        this.evaluateFunction = evaluateFunction;
        this.stateManage = new SecGraphStateManage();
    }


    private void getIndex(ArrayList<Long> indexs, long start,long end){
        for(long i = start;i<end;i++){
            indexs.add(i);
        }
    }
    public Long[] getAvailabelAction(){
        ArrayList<Long> indexsArray = new ArrayList<>();
        for(SecGraphState state: this.currentState){
            getIndex(indexsArray,state.getStart(),state.getEnd());
        }
        Long [] indexs = new Long[indexsArray.size()];
        indexsArray.toArray(indexs);
        return indexs;
    }

    private long getScore(ArrayList<SecGraphState> state){
        return this.evaluateFunction.evaluate(state);
    }

    private long getIndexOfMinValue(HashMap<Long,Long> indexScore){
        long flagIndex = -1;
        long MinValue = Long.MAX_VALUE;
        for(Map.Entry<Long,Long> pair: indexScore.entrySet()){
            long key = pair.getKey();
            long value = pair.getValue();
            if(value<=MinValue){
                flagIndex = key;
                MinValue = value;
            }
        }
        return flagIndex;
    }
    public void stepForward(){
        Long [] indexs = getAvailabelAction();

        System.out.println("-----> getAvailabelAction finished");

        HashMap<Long,Long> indexScore = new HashMap<>();
        for(long index: indexs){
           ArrayList<SecGraphState> temp = stateManage.copy(this.currentState);
           stateManage.cutGraph(index,temp);
           long score = getScore(temp);
           indexScore.put(index,score);
            System.out.printf("--> Evaluation %d finished\n",index);
        }

        System.out.println("-----> Evaluation all finished");
        long index = getIndexOfMinValue(indexScore);
        stateManage.cutGraph(index,this.currentState);
        System.out.printf(" step forward : %d\n",index);
    }
    public Iterator<SecGraphState> traverseState(){
        return this.currentState.iterator();
    }






}
