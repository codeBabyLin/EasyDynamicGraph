package dataFactory.filters.basicFilter;

public class StringFilter implements BasicFilter{
    private String value;
    private String filterOperation = "=";

    public StringFilter(String value, String op){
        this.value = value;
        this.filterOperation = op;
    }

    @Override
    public boolean isFit(Object value) {
        String res = (String)value;
        switch (this.filterOperation){
            case "equals": return res.equals(this.value);
            case "contains": return res.contains(this.value);
            case "startsWith": return res.startsWith(this.value);
            case "endsWith": return res.endsWith(this.value);
            case "equalsIgnoreCase": return res.equalsIgnoreCase(this.value);
            default: return false;
        }
    }
}
