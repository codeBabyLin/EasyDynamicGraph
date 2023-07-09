package dataFactory.filters.basicFilter;

public class IntergerFilter implements BasicFilter {
    private int value;
    private String filterOperation = "=";

    public IntergerFilter(int value, String op){
        this.value = value;
        this.filterOperation = op;
    }

    @Override
    public boolean isFit(Object value) {
        int res = (int)value;
        switch (this.filterOperation){
            case "=": return res == this.value;
            case ">": return res > this.value;
            case ">=": return res >= this.value;
            case "<": return res < this.value;
            case "<=": return res <= this.value;
            default: return false;
        }
    }

}
