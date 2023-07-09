package dataFactory.filters;

public class AtVersionFilter implements OuterFilter {

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    private int version;
    public AtVersionFilter(int version){
        this.version = version;
    }

}
