package CutTemporalGraph;

import CutTemporalGraph.backup.TreeState;
import CutTemporalGraph.backup.TreeStateManage;

import java.util.ArrayList;
import java.util.Iterator;

public class Reinforcement {

    private ArrayList<SecGraphState> currentState;
    private EvaluateFunction evaluateFunction;
    //private TreeStateManage tsm;

    public Reinforcement(ArrayList<SecGraphState> state,EvaluateFunction evaluateFunction){
        this.currentState = state;
        this.evaluateFunction = evaluateFunction;
        //this.tsm = new TreeStateManage();
    }

    public void stepForward(){

    }
    public Iterator<SecGraphState> traverseState(){
        return this.currentState.iterator();
    }






}
