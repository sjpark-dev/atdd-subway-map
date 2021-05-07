package wooteco.subway.section;

public class Distance {

    private static final Distance NULL_DISTANCE = new Distance();

    private int value;

    private Distance() {
    }

    private Distance(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("거리는 자연수이어야 합니다.");
        }
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
