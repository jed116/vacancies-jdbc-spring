package tech.itpark.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.jdbc.model.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRowMapper implements RowMapper<Location> {
    public Location mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Location(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
