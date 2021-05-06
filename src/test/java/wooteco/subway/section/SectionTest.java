package wooteco.subway.section;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.station.Station;

@DisplayName("지하철 구간 도메인")
public class SectionTest {

    @DisplayName("지하철 구간을 생성한다.")
    @Test
    void createSection() {
        assertThatCode(() -> {
            new Section(new Station(1L, "강남역"), new Station(2L, "잠실역"), Distance.of(10));
        }).doesNotThrowAnyException();
    }
}
