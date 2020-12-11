package tech.itpark.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.jdbc.model.Type;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeRowMapper implements RowMapper {
    public Type mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Type(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
