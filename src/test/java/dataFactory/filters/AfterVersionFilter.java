package dataFactory.filters;

public class AfterVersionFilter implements OuterFilter {

    private int version;
    public AfterVersionFilter(int version){
        this.version = version;
    }
}
