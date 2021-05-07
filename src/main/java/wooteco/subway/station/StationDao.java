package wooteco.subway.station;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class StationDao {

    private static final RowMapper<Station> STATION_ROW_MAPPER =
        (resultSet, rowNum) -> new Station(resultSet.getLong("id"), resultSet.getString("name"));
    private static final GeneratedKeyHolder KEY_HOLDER = new GeneratedKeyHolder();

    private final JdbcTemplate jdbcTemplate;

    public StationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Station findById(Long id) {
        String sql = "SELECT * FROM station WHERE id = (?)";

        return jdbcTemplate.queryForObject(sql, STATION_ROW_MAPPER, id);
    }

    public boolean hasId(Long id) {
        String sql = "SELECT COUNT(*) FROM station WHERE id = (?)";

        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }

    public boolean hasName(String name) {
        String sql = "SELECT COUNT(*) FROM station WHERE `name` = (?)";

        return jdbcTemplate.queryForObject(sql, Integer.class, name) > 0;
    }

    public Station save(Station station) {
        String sql = "INSERT INTO station (name) values (?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, station.getName());
            return ps;
        }, KEY_HOLDER);

        return new Station(Objects.requireNonNull(KEY_HOLDER.getKey()).longValue(),
            station.getName());
    }

    public List<Station> findAll() {
        String sql = "SELECT * FROM station";

        return jdbcTemplate.query(sql, STATION_ROW_MAPPER);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM station WHERE id = (?)";
        jdbcTemplate.update(sql, id);
    }
}
