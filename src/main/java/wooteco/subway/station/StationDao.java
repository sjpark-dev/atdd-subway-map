package wooteco.subway.station;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class StationDao {

    private static final RowMapper<Station> STATION_ROW_MAPPER = (resultSet, rowNum) -> new Station(resultSet.getLong("id"), resultSet.getString("name"));
    private static final GeneratedKeyHolder KEY_HOLDER = new GeneratedKeyHolder();

    private final JdbcTemplate jdbcTemplate;

    public StationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Station findById(Long id) {
        String sql = "SELECT * FROM station WHERE id = (?)";

        return jdbcTemplate.queryForObject(sql, STATION_ROW_MAPPER, id);
    }

    public Station save(Station station) {
        String sql = "INSERT INTO station (name) values (?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, station.getName());
            return ps;
        }, KEY_HOLDER);

        return new Station(Objects.requireNonNull(KEY_HOLDER.getKey()).longValue(), station.getName());
    }

    public List<Station> findAll() {
        String sql = "SELECT * FROM station";

        return jdbcTemplate.query(sql, STATION_ROW_MAPPER);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM station WHERE id = (?)";
        int updatedRowCount = jdbcTemplate.update(sql, id);

        if (updatedRowCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 id 입니다.");
        }
    }
}
