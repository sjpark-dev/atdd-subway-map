package wooteco.subway.section;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SectionDao {

    private final JdbcTemplate jdbcTemplate;

    public SectionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Section section, Long lineId) {
        String sql = "INSERT INTO section (up_station_id, down_station_id, distance, line_id) VALUES(?, ?, ?, ?)";

        jdbcTemplate.update(sql, section.upStation().getId(), section.downStation().getId(),
            section.distance().intValue(), lineId);
    }
}
