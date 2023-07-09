package dataFactory.filters;

public class BetweenVersionFilter implements OuterFilter {

    private int startVersion;
    private int endVersion;
    public BetweenVersionFilter(int startVersion,int endVersion){
        this.startVersion = startVersion;
        this.endVersion = endVersion;
    }
}
