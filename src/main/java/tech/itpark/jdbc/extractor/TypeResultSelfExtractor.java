package tech.itpark.jdbc.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import tech.itpark.jdbc.model.Type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeResultSelfExtractor implements ResultSetExtractor<Map<Integer, List<Type>>> {
    @Override
    public Map<Integer, List<Type>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, List<Type>> mapResult = new HashMap<>();
        while (resultSet.next()) {
            int v_id = resultSet.getInt("v_id");
            Type type = new Type(resultSet.getInt("id"), resultSet.getString("name"));
            List<Type> types = mapResult.get(v_id);
            if (types == null) {
                types = new ArrayList<>();
                mapResult.put(v_id, types);
            }
            types.add(type);
        }
        return mapResult;
    }
}
