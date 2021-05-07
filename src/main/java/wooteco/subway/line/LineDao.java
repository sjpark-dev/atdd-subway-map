package wooteco.subway.line;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class LineDao {

    private static final RowMapper<Line> LINE_ROW_MAPPER = (resultSet, rowNum) -> new Line(
        resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("color"));
    private static final GeneratedKeyHolder KEY_HOLDER = new GeneratedKeyHolder();

    private final JdbcTemplate jdbcTemplate;

    public LineDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Line save(Line line) {
        String sql = "INSERT INTO line (name, color) values (?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, line.getName());
            ps.setString(2, line.getColor());
            return ps;
        }, KEY_HOLDER);

        return new Line(Objects.requireNonNull(KEY_HOLDER.getKey()).longValue(), line.getName(),
            line.getColor(), line.sections());
    }

    public boolean hasId(Long id) {
        String sql = "SELECT COUNT(*) FROM line WHERE id = (?)";

        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }

    public boolean hasNameAndDifferentId(Long id, String name) {
        String sql = "SELECT COUNT(*) FROM line WHERE `name` = (?) AND id <> (?)";

        return jdbcTemplate.queryForObject(sql, Integer.class, name, id) > 0;
    }

    public boolean hasColorAndDifferentId(Long id, String color) {
        String sql = "SELECT COUNT(*) FROM line WHERE color = (?) AND id <> (?)";

        return jdbcTemplate.queryForObject(sql, Integer.class, color, id) > 0;
    }

    public List<Line> findAll() {
        String sql = "SELECT * FROM line";

        return jdbcTemplate.query(sql, LINE_ROW_MAPPER);
    }

    public Line findById(Long id) {
        String sql = "SELECT * FROM line WHERE id = (?)";
        return jdbcTemplate.queryForObject(sql, LINE_ROW_MAPPER, id);
    }

    public void updateById(Long id, Line line) {
        String sql = "UPDATE line SET name = (?), color = (?) WHERE id = (?) ";
        jdbcTemplate.update(sql, line.getName(), line.getColor(), id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM line WHERE id = (?)";
        jdbcTemplate.update(sql, id);
    }

    public boolean hasName(String name) {
        String sql = "SELECT COUNT(*) FROM line WHERE `name` = (?)";

        return jdbcTemplate.queryForObject(sql, Integer.class, name) > 0;
    }

    public boolean hasColor(String color) {
        String sql = "SELECT COUNT(*) FROM line WHERE color = (?)";

        return jdbcTemplate.queryForObject(sql, Integer.class, color) > 0;
    }
}
