package tech.itpark.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.jdbc.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<Category> {
    public Category mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Category(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
