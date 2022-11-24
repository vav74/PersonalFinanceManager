public class MaxCat {
    private final MaxCategory maxCategory;

    public MaxCategory getMaxCategory() {
        return maxCategory;
    }
    public MaxCat(MaxCategory maxCategory) {
        this.maxCategory = maxCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MaxCat maxCat = (MaxCat) o;

        return getMaxCategory().equals(maxCat.getMaxCategory());
    }

    @Override
    public int hashCode() {
        return getMaxCategory().hashCode();
    }
}