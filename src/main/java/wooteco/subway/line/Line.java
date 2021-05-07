package wooteco.subway.line;

import java.util.List;
import wooteco.subway.section.Section;
import wooteco.subway.section.Sections;
import wooteco.subway.station.Station;

public class Line {

    private Long id;
    private String name;
    private String color;
    private Sections sections;

    public Line(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Line(Long id, String name, String color, Section section) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.sections = new Sections(section);
    }

    public Line(String name, String color, Section section) {
        this(null, name, color, section);
    }

    public Line(Long id, String name, String color, Sections sections) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.sections = sections;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Sections sections() {
        return sections;
    }

    public List<Station> path() {
        return sections.path();
    }
}
