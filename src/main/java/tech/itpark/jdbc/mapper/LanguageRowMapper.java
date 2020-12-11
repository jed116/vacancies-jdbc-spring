package tech.itpark.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import tech.itpark.jdbc.model.Language;
import tech.itpark.jdbc.model.LanguageLevel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguageRowMapper implements RowMapper<Language> {
    @Override
    public Language mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Language(resultSet.getInt("language_id"), resultSet.getString("language_name"),
                new LanguageLevel(resultSet.getInt("level_id"), resultSet.getString("level_name")));
    }
}
