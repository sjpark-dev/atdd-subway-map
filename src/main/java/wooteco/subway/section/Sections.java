package wooteco.subway.section;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import wooteco.subway.station.Station;

public class Sections {

    private final Set<Section> sections;

    public Sections(Section section) {
        sections = new HashSet<>();
        sections.add(section);
        sections
            .add(new Section(Station.emptyStation(), section.upStation(), Distance.nullDistance()));
        sections.add(
            new Section(section.downStation(), Station.emptyStation(), Distance.nullDistance()));
    }

    public Sections(Set<Section> sections) {
        this.sections = new HashSet<>(sections);
    }

    public List<Station> path() {
        List<Station> result = new ArrayList<>();

        for (Section now = firstSection(); !now.isLastSection(); now = nextSection(now)) {
            result.add(now.downStation());
        }
        return result;
    }

    private Section firstSection() {
        return sections.stream()
            .filter(Section::isFirstSection)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("상행 종점역이 존재하지 않습니다."));
    }

    private Section nextSection(Section section) {
        Station station = section.downStation();

        return sections.stream()
            .filter(section1 -> section1.upStation().equals(station))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("다음역이 존재하지 않습니다."));
    }

    public List<Section> values() {
        return new ArrayList<>(sections);
    }
}
