package tech.itpark.jdbc.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import tech.itpark.jdbc.model.Employment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmploymentsResultSetExtractor implements ResultSetExtractor<Map<Integer, List<Employment>>> {
    @Override
    public Map<Integer, List<Employment>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, List<Employment>> mapResult = new HashMap<>();
        while (resultSet.next()){
            int v_id = resultSet.getInt("v_id");
            Employment employment = new Employment(resultSet.getInt("id"), resultSet.getString("name"));
            List<Employment> employments = mapResult.get(v_id);
            if (employments == null){
                employments = new ArrayList<>();
                mapResult.put(v_id, employments);
            }
            employments.add(employment);
        }
        return mapResult;
    }
}
