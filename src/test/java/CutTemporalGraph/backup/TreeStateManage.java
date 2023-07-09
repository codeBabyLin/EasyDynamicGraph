package CutTemporalGraph.backup;

import CutTemporalGraph.backup.TreeState;

public class TreeStateManage {

    public TreeStateManage(){
    }

    public TreeState seekToFirst(TreeState rootState){
        TreeState tempState = rootState;
        while(tempState.getPrevious()!=null){
            tempState = tempState.getPrevious();
        }
        return tempState;
    }
    public TreeState seekToTail(TreeState rootState){
        TreeState tempState = rootState;
        while(tempState.getNext()!=null){
            tempState = tempState.getNext();
        }
        return tempState;
    }
    public TreeState seekToIndex(long index,TreeState rootState){
        TreeState tempState = this.seekToFirst(rootState);
        while(tempState.getEnd()<index&&tempState.getNext()!=null){
            tempState = tempState.getNext();
        }
        return tempState;
    }

    public TreeState cut(long index,TreeState rootState){
        TreeState temp = seekToIndex(index,rootState);
        temp = temp.cut(index);
        return temp;
    }

    public TreeState copy(TreeState state){
        TreeState temp = seekToFirst(state);
        TreeState srcState = new TreeState(temp.getStart(), temp.getEnd());
        TreeState head = srcState;
        while(temp.getNext()!=null){
            temp = temp.getNext();
            TreeState newNextState = new TreeState(temp.getStart(),temp.getEnd());
            srcState.setNext(newNextState);
            newNextState.setPrevious(srcState);
            srcState = newNextState;
        }
        return head;
    }

}
