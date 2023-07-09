package dataFactory.filters;

public class LineFilter{
    public InnerFilter getPreFilter() {
        return preFilter;
    }

    public void setPreFilter(InnerFilter preFilter) {
        this.preFilter = preFilter;
    }

    public InnerFilter getLaterFilter() {
        return laterFilter;
    }

    public void setLaterFilter(InnerFilter laterFilter) {
        this.laterFilter = laterFilter;
    }

    public InnerFilter getCurrentFilter() {
        return currentFilter;
    }

    public void setCurrentFilter(InnerFilter currentFilter) {
        this.currentFilter = currentFilter;
    }

    private InnerFilter preFilter;
    private InnerFilter laterFilter;
    private InnerFilter currentFilter;

}
