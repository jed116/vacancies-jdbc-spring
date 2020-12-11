package tech.itpark.jdbc.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import tech.itpark.jdbc.model.Language;
import tech.itpark.jdbc.model.LanguageLevel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguagesResultSetExtractor implements ResultSetExtractor<Map<Integer, List<Language>>> {
    @Override
    public Map<Integer, List<Language>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, List<Language>> mapResult = new HashMap<>();
        while (resultSet.next()){
            int v_id = resultSet.getInt("v_id");

            Language language = new Language(resultSet.getInt("language_id"),
                                            resultSet.getString("language_name"),
                                            new LanguageLevel(resultSet.getInt("level_id"),
                                                    resultSet.getString("level_name")));

            List<Language> languages = mapResult.get(v_id);
            if (languages == null){
                languages = new ArrayList<>();
                mapResult.put(v_id, languages);
            }
            languages.add(language);

//            Employment employment = new Employment(resultSet.getInt("id"), resultSet.getString("name"));
//            List<Employment> employments = mapResult.get(v_id);
//            if (employments == null){
//                employments = new ArrayList<>();
//                mapResult.put(v_id, employments);
//            }
//            employments.add(employment);
        }
        return mapResult;
    }
}
