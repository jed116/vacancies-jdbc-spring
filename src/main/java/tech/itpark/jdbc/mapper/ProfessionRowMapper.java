package tech.itpark.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.jdbc.model.Profession;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessionRowMapper implements RowMapper<Profession> {
    public Profession mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Profession(rs.getInt("id"), rs.getString("name"));
    }
}
