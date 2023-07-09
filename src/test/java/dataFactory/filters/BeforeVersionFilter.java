package dataFactory.filters;

public class BeforeVersionFilter implements OuterFilter {
    private int version;
    public BeforeVersionFilter(int version){
        this.version = version;
    }
}
