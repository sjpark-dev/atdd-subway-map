package wooteco.subway.section;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SectionDao {

    private static final RowMapper<SectionEntity> SECTION_ROW_MAPPER = (resultSet, rowNum) ->
        new SectionEntity(resultSet.getLong("id"),
            resultSet.getLong("line_id"),
            resultSet.getLong("up_station_id"),
            resultSet.getLong("down_station_id"),
            resultSet.getInt("distance"));

    private final JdbcTemplate jdbcTemplate;

    public SectionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Sections sections, Long lineId) {
        String sql = "INSERT INTO section (up_station_id, down_station_id, distance, line_id) VALUES(?, ?, ?, ?)";

        List<Object[]> batch = new ArrayList<>();
        for (Section section : sections.values()) {
            Object[] params = new Object[]{section.upStation().getId(),
                section.downStation().getId(), section.distance().intValue(), lineId};
            batch.add(params);
        }

        jdbcTemplate.batchUpdate(sql, batch);
    }

    public List<SectionEntity> filterByLineId(Long lineId) {
        String sql = "SELECT id, line_id, up_station_id, down_station_id, distance FROM section WHERE line_id = (?)";

        return jdbcTemplate.query(sql, SECTION_ROW_MAPPER, lineId);
    }
}
