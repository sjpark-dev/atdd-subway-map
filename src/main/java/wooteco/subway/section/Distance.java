package wooteco.subway.section;

public class Distance {

    private static final Distance NULL_DISTANCE = new Distance();

    private int value;

    private Distance() {
    }

    private Distance(int value) {
        this.value = value;
    }

    public static Distance of(int value) {
        return new Distance(value);
    }

    public static Distance nullDistance() {
        return NULL_DISTANCE;
    }

    public int intValue() {
        return value;
    }
}
