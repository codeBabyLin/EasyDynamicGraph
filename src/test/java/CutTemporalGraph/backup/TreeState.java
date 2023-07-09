package CutTemporalGraph.backup;

public class TreeState {
    private long start;
    private long end;

    public TreeState getPrevious() {
        return previous;
    }

    public TreeState getNext() {
        return next;
    }

    public void setPrevious(TreeState previous) {
        this.previous = previous;
    }

    public void setNext(TreeState next) {
        this.next = next;
    }

    private TreeState previous;
    private TreeState next;

    public TreeState(long start,long end){

    }
    public long getStart(){
        return start;
    }
    public long getEnd(){
        return end;
    }
    public TreeState cut(long index){
        TreeState newState = this;
       if(start<=index && index+1<=end){
           TreeState leftState = new TreeState(start,index);
           TreeState rightState = new TreeState(index+1,end);
           leftState.setNext(rightState);
           rightState.setPrevious(leftState);
           if(this.previous!=null) this.previous.setNext(leftState);
           if(this.next !=null) this.next.setPrevious(rightState);
           newState = leftState;
       }
       return newState;
    }


}
