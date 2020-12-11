package tech.itpark.jdbc.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import tech.itpark.jdbc.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesResultSetExtractor implements ResultSetExtractor<Map<Integer, List<Category>>> {
    @Override
    public Map<Integer, List<Category>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, List<Category>> mapResult = new HashMap<>();
        while (resultSet.next()) {
            int v_id = resultSet.getInt("v_id");
            Category category = new Category(resultSet.getInt("id"), resultSet.getString("name"));
            List<Category> categories = mapResult.get(v_id);
            if (categories == null) {
                categories = new ArrayList<>();
                mapResult.put(v_id, categories);
            }
            categories.add(category);
        }
        return mapResult;
    }
}
