package wooteco.subway.line;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import wooteco.subway.section.Distance;
import wooteco.subway.section.Section;
import wooteco.subway.station.Station;

public class Line {

    private Long id;
    private String name;
    private String color;
    private Set<Section> sections = new HashSet<>();

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
        sections.add(section);
        sections
            .add(new Section(Station.emptyStation(), section.upStation(), Distance.nullDistance()));
        sections.add(
            new Section(section.downStation(), Station.emptyStation(), Distance.nullDistance()));
    }

    public Line(String name, String color, Section section) {
        this(null, name, color, section);
    }

    public Line(Long id, String name, String color,
        Set<Section> sections) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.sections = new HashSet<>(sections);
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

    public Set<Section> sections() {
        return new HashSet<>(sections);
    }

    public List<Station> path() {
        List<Station> result = new ArrayList<>();
        Station now = sectionWithUpStation(Station.emptyStation()).downStation();

        while (!now.isEmptyStation()) {
            result.add(now);
            now = sectionWithUpStation(now).downStation();
        }

        return result;
    }

    private Section sectionWithUpStation(Station station) {
        return sections.stream()
            .filter(section -> section.upStation().equals(station))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("상행 종점역이 존재하지 않습니다."));
    }
}
