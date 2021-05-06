package wooteco.subway.line;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.section.Distance;
import wooteco.subway.section.Section;
import wooteco.subway.station.Station;

@DisplayName("지하철 노선 도메인")
public class LineTest {

    @DisplayName("지하철 노선을 생성한다.")
    @Test
    void createLine() {
        assertThatCode(() -> {
            Section section = new Section(new Station(1L, "강남역"), new Station(2L, "잠실역"), Distance.of(10));
            new Line(1L, "강남역", "bg-red-600", section);
        }).doesNotThrowAnyException();
    }

}
