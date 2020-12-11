package tech.itpark.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IdsRowMapper implements RowMapper<Integer> {
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt("id");
    }
}
