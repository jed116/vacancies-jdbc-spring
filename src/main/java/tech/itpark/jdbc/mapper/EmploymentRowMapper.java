package tech.itpark.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.jdbc.model.Employment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmploymentRowMapper implements RowMapper<Employment> {
    public Employment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Employment(rs.getInt("id"), rs.getString("name"));
    }
}
