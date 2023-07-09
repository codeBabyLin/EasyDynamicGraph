package dataFactory.filters.basicFilter;

public class BooleanFilter implements BasicFilter{
    private boolean value;
    private String filterOperation = "=";

    public BooleanFilter(boolean value, String op){
        this.value = value;
        this.filterOperation = op;
    }

    @Override
    public boolean isFit(Object value) {
        boolean res = (boolean)value;
        return this.value & res;
    }
}
