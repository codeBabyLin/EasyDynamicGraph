package CutTemporalGraph;

import java.util.ArrayList;

public class SecGraphStateManage {

    public void cutGraph(long version, ArrayList<SecGraphState> state){
        for(SecGraphState graphState: state){
            if(graphState.getStart()<= version && (version+1)<= graphState.getEnd()){
                int index = state.indexOf(graphState);
                SecGraphState left = new SecGraphState(graphState.getStart(), version);
                SecGraphState right = new SecGraphState(version+1, graphState.getEnd());
                state.remove(index);
                state.add(index,right);
                state.add(index,left);
                break;
            }
        }
    }

    public ArrayList<SecGraphState> copy(ArrayList<SecGraphState> state){
        ArrayList<SecGraphState> target =  new ArrayList<>();
        for(SecGraphState secGraphState: state){
            SecGraphState tempState = new SecGraphState(secGraphState.getStart(), secGraphState.getEnd());
            target.add(tempState);
        }
        return  target;
    }
}
