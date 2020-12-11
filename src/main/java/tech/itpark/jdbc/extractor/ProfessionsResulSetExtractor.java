package tech.itpark.jdbc.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import tech.itpark.jdbc.model.Profession;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfessionsResulSetExtractor implements ResultSetExtractor<Map<Integer, List<Profession>>> {
    @Override
    public Map<Integer, List<Profession>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, List<Profession>> mapResult = new HashMap<>();
        while (resultSet.next()) {
            int v_id = resultSet.getInt("v_id");
            Profession profession = new Profession(resultSet.getInt("id"), resultSet.getString("name"));
            List<Profession> professions = mapResult.get(v_id);
            if (professions == null) {
                professions = new ArrayList<>();
                mapResult.put(v_id, professions);
            }
            professions.add(profession);
        }
        return mapResult;
    }
}
