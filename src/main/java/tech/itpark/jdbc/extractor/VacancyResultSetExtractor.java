package tech.itpark.jdbc.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import tech.itpark.jdbc.model.Company;
import tech.itpark.jdbc.model.Location;
import tech.itpark.jdbc.model.Rate;
import tech.itpark.jdbc.model.Vacancy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class VacancyResultSetExtractor implements ResultSetExtractor<Map<Integer, Vacancy>> {
    @Override
    public Map<Integer, Vacancy> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Vacancy> mapResult = new HashMap<>();
        while (resultSet.next()) {
            Location location = null;
            if (resultSet.getInt("location_id") != 0){
                location = new Location(resultSet.getInt("location_id"), resultSet.getString("location_name"));
            }
            Rate rate = null;
            if (resultSet.getInt("rate_id") != 0){
                rate = new Rate(resultSet.getInt("rate_id"), resultSet.getString("rate_name"));
            }
            Company company = null;
            if (resultSet.getInt("company_id") != 0){
                company = new Company(resultSet.getInt("company_id"), resultSet.getString("company_name"));
            }
            int car = resultSet.getInt("car");

            Vacancy vacancy = new Vacancy(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDate("date"),
                    resultSet.getInt("salaryMin"),
                    resultSet.getInt("salaryMax"),
                    rate,
                    location,
                    company,
                    car);

            mapResult.put(vacancy.getId(), vacancy);
        }
        return mapResult;
    }
}
