package dataFactory.filters.basicFilter;

public class FloatFilter implements BasicFilter {
    private float value;
    private String filterOperation = "=";

    public FloatFilter(float value, String op){
        this.value = value;
        this.filterOperation = op;
    }

    @Override
    public boolean isFit(Object value) {
        float res = (float)value;
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
