package wooteco.subway.station;

public class Station {

    private static final Station EMPTY_STATION = new Station();

    private Long id;
    private String name;

    private Station() {
    }

    public Station(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Station(String name) {
        this.name = name;
    }

    public static Station emptyStation() {
        return EMPTY_STATION;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isEmptyStation() {
        return this.equals(EMPTY_STATION);
    }
}

