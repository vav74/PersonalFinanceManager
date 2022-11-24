public class MaxCategory {
    private final String category;
    private final int sum;

    public MaxCategory(String category, int sum) {
        this.category = category;
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MaxCategory that = (MaxCategory) o;

        if (getSum() != that.getSum()) return false;
        return getCategory().equals(that.getCategory());
    }

    @Override
    public int hashCode() {
        int result = getCategory().hashCode();
        result = 31 * result + getSum();
        return result;
    }
}