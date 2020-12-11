package tech.itpark.jdbc.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import tech.itpark.jdbc.extractor.*;
import tech.itpark.jdbc.mapper.*;
import tech.itpark.jdbc.model.*;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class VacancyManager {
    private final int MAX_LANGUAGE_LEVEL = 3;
    private final int MIN_NAMES_LENGTH = 2;
    private final int MAX_NAMES_LENGTH = 20;
    private final int MAX_NAMES_COUNT = 10;
    private final int DEFAULT_ROWS_COUNT = 5;
    private final int MAX_ROWS_COUNT = 50;

    private final NamedParameterJdbcTemplate template;

    private final ProfessionRowMapper   rowMapperProfession = new ProfessionRowMapper();
    private final EmploymentRowMapper   rowMapperEmployment = new EmploymentRowMapper();
    private final CategoryRowMapper     rowMapperCategory   = new CategoryRowMapper();
    private final LocationRowMapper     rowMapperLocation   = new LocationRowMapper();
    private final LanguageRowMapper     rowMapperLanguage   = new LanguageRowMapper();
    private final TypeRowMapper         rowMapperType       = new TypeRowMapper();
    private final IdsRowMapper          rowMapperIds        = new IdsRowMapper();

    private final VacancyResultSetExtractor     vacancyRSE      = new VacancyResultSetExtractor();

    private final TypeResultSelfExtractor    typeRSE= new TypeResultSelfExtractor();
    private final ProfessionsResulSetExtractor  professionRSE   = new ProfessionsResulSetExtractor();
    private final EmploymentsResultSetExtractor employmentsRSE  = new EmploymentsResultSetExtractor();
    private final LanguagesResultSetExtractor languagesRSE = new LanguagesResultSetExtractor();
    private final CategoriesResultSetExtractor categoriesRSE = new CategoriesResultSetExtractor();
//============================================================================================================>>> G E T
    public List<Vacancy> get(List<Integer> sortedInputIds){
        List<Vacancy> result = new ArrayList<>();

        Map<Integer, Vacancy> vacanciesMap = template.query(
                "SELECT DISTINCT v.id id, v.name name, v.date date, v.salaryMin, v.salaryMax, " +
            "r.id rate_id, r.name rate_name, l.id location_id, l.name location_name, c.id company_id, c.name company_name " +
                        "FROM vacancies v " +
                        "LEFT JOIN rates_list r     ON v.rate_id = r.id " +
                        "LEFT JOIN locations_list l ON v.location_id = l.id " +
                        "LEFT JOIN companies_list c ON v.company_id = c.id " +
                        "WHERE v.id IN (:ids) OR :ids_size = 0 " +
                        "ORDER BY v.id DESC " +
                        "LIMIT 100",
                new MapSqlParameterSource(Map.of("ids", sortedInputIds, "ids_size", sortedInputIds.size())), vacancyRSE);

        List<Integer> unsortedOutputIds = new ArrayList<>(vacanciesMap.keySet());

        for (Integer id : sortedInputIds) {
            if(vacanciesMap.containsKey(id)){
                result.add(vacanciesMap.get(id));
            }
        }

        if (result.size() == 0) {
            result = new ArrayList<>(vacanciesMap.values());
        }

        Map<Integer, List<Type>> vacancies_types = template.query(
                "SELECT v_t.vacancy_id v_id, t.id id, t.name name FROM vacancies_types v_t " +
                        "INNER JOIN types_list t on v_t.type_id = t.id WHERE v_t.vacancy_id IN (:ids)",
                new MapSqlParameterSource("ids", unsortedOutputIds), typeRSE);

        Map<Integer, List<Profession>> vacancies_professions = template.query(
                "SELECT v_p.vacancy_id v_id, p.id id, p.name name FROM vacancies_professions v_p " +
                        "INNER JOIN professions_list p on v_p.profession_id = p.id WHERE v_p.vacancy_id IN (:ids)",
                new MapSqlParameterSource("ids", unsortedOutputIds), professionRSE);

        Map<Integer, List<Employment>> vacancies_employments = template.query(
                "SELECT v_e.vacancy_id v_id, e.id id, e.name name FROM vacancies_employments v_e " +
                        "INNER JOIN employments_list e on v_e.employment_id = e.id WHERE v_e.vacancy_id IN (:ids)",
                new MapSqlParameterSource("ids", unsortedOutputIds), employmentsRSE);

        Map<Integer, List<Category>> vacancies_categories = template.query(
                "SELECT v_c.vacancy_id v_id, c.id id, c.name name FROM vacancies_categories v_c " +
                        "INNER JOIN categories_list c on v_c.category_id = c.id WHERE v_c.vacancy_id IN (:ids)",
                new MapSqlParameterSource("ids", unsortedOutputIds), categoriesRSE);

        Map<Integer, List<Language>> vacancies_languages = template.query(
                "SELECT v_l.vacancy_id v_id, l.id language_id, l.name language_name, lv.id level_id, lv.name level_name FROM vacancies_languages v_l " +
                        "INNER JOIN languages_list l ON v_l.language_id = l.id " +
                        "INNER JOIN language_levels_list lv ON v_l.level = lv.id " +
                    " WHERE v_l.vacancy_id IN (:ids) ",
                new MapSqlParameterSource("ids", unsortedOutputIds), languagesRSE);

        for (Map.Entry<Integer, Vacancy> vacanciesEntry : vacanciesMap.entrySet()) {
            Integer v_id = vacanciesEntry.getKey();
            Vacancy vacancy = vacanciesEntry.getValue();
            if (vacancies_professions.containsKey(v_id)){
                vacancy.setProfessions(vacancies_professions.get(v_id));
            }
            if (vacancies_employments.containsKey(v_id)){
                vacancy.setEmployments(vacancies_employments.get(v_id));
            }
            if (vacancies_languages.containsKey(v_id)){
                vacancy.setLanguages(vacancies_languages.get(v_id));
            }
            if (vacancies_types.containsKey(v_id)){
                vacancy.setTypes(vacancies_types.get(v_id));
            }
            if (vacancies_categories.containsKey(v_id)){
                vacancy.setCategories(vacancies_categories.get(v_id));
            }
        }

        return result;
    }
//----------------------------------------------------------------------------------------------------------------------
    public List<Vacancy> get(int []ids) {
        List<Integer> idsList = Arrays.stream(ids).filter(i -> i > 0).boxed().collect(Collectors.toList());
        return get(idsList);
    }
//----------------------------------------------------------------------------------------------------------------------
    public List<Vacancy> get()
    {
        return get(new int[] {});
    }
//----------------------------------------------------------------------------------------------------------------------
    public Vacancy get(int id){
        if (id <= 0){
            return null;
        }
        List<Vacancy> vacancies = get(new int[] {id});
        if (vacancies.size() > 0){
            return vacancies.get(0);
        }
        return null;
    }
//======================================================================================================> SAVE (ADD|UPD)
    public Vacancy save(VacancyPostRequestBody vacancyPostRequestBody){
        int company_id = vacancyPostRequestBody.getCompany_id();

        String company_update_param = company_id !=0 ? " :company_id" : " null";
        Map<String, ?> vacancyParams = Map.of(
                "id", vacancyPostRequestBody.getId(),
                "name", vacancyPostRequestBody.getName(),
                "date", new Date(System.currentTimeMillis()), //vacancyPostRequestBody.getDate(),
                "salaryMin", vacancyPostRequestBody.getSalaryMin(),
                "salaryMax", vacancyPostRequestBody.getSalaryMax(),
                "rate_id",  vacancyPostRequestBody.getRate_id(),
                "location_id", vacancyPostRequestBody.getLocation_id(),
                "company_id", company_id);

        if (vacancyPostRequestBody.getId() == 0){
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update("INSERT INTO vacancies(name, date, salaryMin, salaryMax, rate_id, location_id, company_id) " +
               "VALUES (:name, :date, :salaryMin, :salaryMax, :rate_id, :location_id, "+ company_update_param +" )",
                    new MapSqlParameterSource(vacancyParams), keyHolder);
            int vacancy_id = Objects.requireNonNull(keyHolder.getKey()).intValue();
            if (vacancy_id == 0){
                return null;
            }
            vacancyPostRequestBody.setId(vacancy_id);
        }
        else {
            template.update("UPDATE vacancies SET name = :name, date = :date, salaryMin = :salaryMin, salaryMax = :salaryMax, " +
                "rate_id = :rate_id, location_id = :location_id, company_id = " + company_update_param + " WHERE id = :id", vacancyParams);
        }

        updateVacancyTypes(vacancyPostRequestBody);
        updateVacancyProfessions(vacancyPostRequestBody);
        updateVacancyEmployments(vacancyPostRequestBody);
        updateVacancyCategories(vacancyPostRequestBody);
        updateVacancyLanguages(vacancyPostRequestBody);

        return get(vacancyPostRequestBody.getId());
    }
//----------------------------------------------------------------------------------------------------------------------
    private void updateVacancyTypes(VacancyPostRequestBody vacancyPostRequestBody){
        int vacancy_id = vacancyPostRequestBody.getId();
        template.update("DELETE FROM vacancies_types WHERE vacancy_id = :id", Map.of("id", vacancy_id));
        List<Type> types = getTypesByIds(vacancyPostRequestBody.getTypes_ids());
        if(types == null){
            return;
        }
        if(types.size() > 0) {
            List<Map<String, Integer>> params = new ArrayList<>();
            types.stream().forEach(x -> params.add(Map.of("vacancy_id", vacancy_id, "type_id", x.getId())));
            template.batchUpdate("INSERT INTO vacancies_types(vacancy_id, type_id) " +
                    "VALUES ( :vacancy_id, :type_id)", SqlParameterSourceUtils.createBatch(params.toArray()));
        }
    }
//----------------------------------------------------------------------------------------------------------------------
    private void updateVacancyProfessions(VacancyPostRequestBody vacancyPostRequestBody){
        int vacancy_id = vacancyPostRequestBody.getId();
        template.update("DELETE FROM vacancies_professions WHERE vacancy_id = :id", Map.of("id", vacancy_id));
        List<Profession> professions = getProfessionsByIds(vacancyPostRequestBody.getProfessions_ids());
        if(professions == null){
            return;
        }
        if(professions.size() > 0) {
            List<Map<String, Integer>> params = new ArrayList<>();
            professions.stream().forEach(x -> params.add(Map.of("vacancy_id", vacancy_id, "profession_id", x.getId())));
            template.batchUpdate("INSERT INTO vacancies_professions(vacancy_id, profession_id) " +
                    "VALUES ( :vacancy_id, :profession_id)", SqlParameterSourceUtils.createBatch(params.toArray()));
        }
    }
//----------------------------------------------------------------------------------------------------------------------
    private void updateVacancyEmployments(VacancyPostRequestBody vacancyPostRequestBody){
        int vacancy_id = vacancyPostRequestBody.getId();
        template.update("DELETE FROM vacancies_employments WHERE vacancy_id = :id", Map.of("id", vacancy_id));
        List<Employment> employments = getEmploymentsByIds(vacancyPostRequestBody.getEmployments_ids());
        if(employments == null){
            return;
        }
        if (employments.size() > 0) {
        List<Map<String, Integer>> params = new ArrayList<>();
            employments.stream().forEach(x -> params.add(Map.of("vacancy_id", vacancy_id, "employment_id", x.getId())));
            template.batchUpdate("INSERT INTO vacancies_employments(vacancy_id, employment_id) " +
                    "VALUES ( :vacancy_id, :employment_id)",SqlParameterSourceUtils.createBatch(params.toArray()));
        }
    }
//----------------------------------------------------------------------------------------------------------------------
    private void updateVacancyCategories(VacancyPostRequestBody vacancyPostRequestBody){
        int vacancy_id = vacancyPostRequestBody.getId();
        template.update("DELETE FROM vacancies_categories WHERE vacancy_id = :id", Map.of("id", vacancy_id));
        List<Category> categories = getCategoriesByIds(vacancyPostRequestBody.getCategories_ids());
        if(categories == null){
            return;
        }
        if (categories.size() > 0) {
            List<Map<String, Integer>> params = new ArrayList<>();
            categories.stream().forEach(x -> params.add(Map.of("vacancy_id", vacancy_id,"category_id", x.getId())));
            template.batchUpdate("INSERT INTO vacancies_categories(vacancy_id, category_id) " +
                    "VALUES ( :vacancy_id, :category_id)",SqlParameterSourceUtils.createBatch(params.toArray()));
        }
    }
//----------------------------------------------------------------------------------------------------------------------
    private void updateVacancyLanguages(VacancyPostRequestBody vacancyPostRequestBody){
        int vacancy_id = vacancyPostRequestBody.getId();
        template.update("DELETE FROM vacancies_languages WHERE vacancy_id = :id", Map.of("id", vacancy_id));
        List<Language> languages = getLanguagesByIds(vacancyPostRequestBody.getLanguages_ids_levels_ids());
        if (languages == null){
            return;
        }
        if(languages.size() > 0){
            List<Map<String, Integer>> params = new ArrayList<>();
            languages.stream().forEach( language -> params.add(Map.of("vacancy_id", vacancy_id,
            "language_id", language.getId(), "level", language.getLevel().getId() )) );
            template.batchUpdate("INSERT INTO vacancies_languages(vacancy_id, language_id, level) " +
                "VALUES ( :vacancy_id, :language_id, :level)",SqlParameterSourceUtils.createBatch(params.toArray()));

        }
    }
//==============================================================================================================> REMOVE
public Vacancy removeById(int id) {
    Vacancy result = get(id);

    template.update("DELETE FROM vacancies_types        WHERE vacancy_id = :id", Map.of("id", id));
    template.update("DELETE FROM vacancies_professions  WHERE vacancy_id = :id", Map.of("id", id));
    template.update("DELETE FROM vacancies_employments  WHERE vacancy_id = :id", Map.of("id", id));
    template.update("DELETE FROM vacancies_categories   WHERE vacancy_id = :id", Map.of("id", id));
    template.update("DELETE FROM vacancies_languages    WHERE vacancy_id = :id", Map.of("id", id));

    template.update("DELETE FROM vacancies WHERE id = :id", Map.of("id", id));

    return result;
}
//================================================================================================================> FIND
public VacancyFoundSelection find(int page, int rows, int sort, Map<String, ?> findMap) {
    int selection_size = 0;
    int selection_pages = 0;
    int selection_page = page > 0 ? page : 1;
    int selection_rows = rows>0 && rows<= MAX_ROWS_COUNT ? rows : DEFAULT_ROWS_COUNT;
    int selection_sort = sort <= 2 && sort >= -2 ? sort : 0;

    List<Integer> foundIds = getFoundIds(selection_page, selection_rows, selection_sort, findMap);

    selection_size = foundIds.get(0);
    foundIds.remove(0);

    if (selection_size > 0) {
        selection_pages = 1;
        if (selection_size > selection_rows){
            selection_pages = (selection_size / selection_rows) + (selection_size % selection_rows > 0 ? 1 : 0);
        }
    }

    List<Vacancy> findResult = null;
    if (foundIds.size() > 0){
        findResult = get(foundIds);
    }
    if (findResult == null){
        findResult = new ArrayList<Vacancy>(0);
    }

    return new VacancyFoundSelection(selection_page, selection_pages, selection_rows, selection_size ,selection_sort, findResult);
}
//----------------------------------------------------------------------------------------------------------------------
/*  get(0) (first element of list) contains length of full selection
    get(1)...get(rows) contains values of the specified page of the full selection */
private List<Integer> getFoundIds(int page, int rows, int sort, Map <String, ?> findMap){
    List<Integer> empty_ids = new ArrayList<Integer>(0) ;

    List<String> names = findMap.containsKey("names") ? (List<String>) findMap.get("names") : null;
    String regexpNames = "";
    if (names != null) {
        regexpNames = names.stream()
                            .map(x -> x.trim().toLowerCase())
                            .filter(x -> MIN_NAMES_LENGTH <= x.length()  && x.length() <= MAX_NAMES_LENGTH)
                            .limit(MAX_NAMES_COUNT).collect(Collectors.joining("|"));
    }
    String namesConditionExpression = " FALSE ";
    if (regexpNames.length() > 0) {
        namesConditionExpression = " LOWER(v.name) REGEXP '"+regexpNames+"' OR LOWER(c.name) REGEXP '"+regexpNames+"' ";
    }

    Date date = (Date) findMap.get("date");
    Integer salary = (Integer) findMap.get("salary");
    Integer language_level = (Integer) findMap.get("language_level");

    List<Integer> types_ids = (List<Integer>) findMap.get("types");
    List<Integer> rates_ids = (List<Integer>) findMap.get("rates");
    List<Integer> companies_ids = (List<Integer>) findMap.get("companies");
    List<Integer> locations_ids = (List<Integer>) findMap.get("locations");
    List<Integer> professions_ids =  (List<Integer>) findMap.get("professions");
    List<Integer> employments_ids = (List<Integer>) findMap.get("employments");
    List<Integer> languages_ids = (List<Integer>) findMap.get("languages");
    List<Integer> categories_ids = (List<Integer>) findMap.get("categories");

    Map<String, ?> paramsMap = Map.ofEntries(
        new AbstractMap.SimpleEntry<String, Integer>("rows_limit", rows),
        new AbstractMap.SimpleEntry<String, Integer>("rows_offset", (page-1)*rows),
        new AbstractMap.SimpleEntry<String, Date>("date", date != null ? date : Date.valueOf("0001-01-01")),
        new AbstractMap.SimpleEntry<String, Integer>("salary", salary != null ? salary.intValue() : 0),
        new AbstractMap.SimpleEntry<String, Boolean>("disable_names_filter", names == null),
        new AbstractMap.SimpleEntry<String, List<Integer>>("types_ids", types_ids != null ? types_ids : empty_ids),
        new AbstractMap.SimpleEntry<String, List<Integer>>("rates_ids", rates_ids != null ? rates_ids : empty_ids),
        new AbstractMap.SimpleEntry<String, List<Integer>>("companies_ids", companies_ids != null ? companies_ids : empty_ids),
        new AbstractMap.SimpleEntry<String, List<Integer>>("locations_ids", locations_ids != null ? locations_ids : empty_ids),
        new AbstractMap.SimpleEntry<String, List<Integer>>("professions_ids", professions_ids != null ? professions_ids : empty_ids),
        new AbstractMap.SimpleEntry<String, List<Integer>>("employments_ids", employments_ids != null ? employments_ids : empty_ids),
        new AbstractMap.SimpleEntry<String, List<Integer>>("categories_ids", categories_ids != null ? categories_ids : empty_ids),
        new AbstractMap.SimpleEntry<String, List<Integer>>("languages_ids", languages_ids != null ? languages_ids : empty_ids),
        new AbstractMap.SimpleEntry<String, Integer>("language_level", language_level != null ? language_level : MAX_LANGUAGE_LEVEL),
        new AbstractMap.SimpleEntry<String, Boolean>("disable_types_filter", types_ids == null),
        new AbstractMap.SimpleEntry<String, Boolean>("disable_rates_filter", rates_ids == null),
        new AbstractMap.SimpleEntry<String, Boolean>("disable_companies_filter", companies_ids == null),
        new AbstractMap.SimpleEntry<String, Boolean>("disable_locations_filter", locations_ids == null),
        new AbstractMap.SimpleEntry<String, Boolean>("disable_professions_filter", professions_ids == null),
        new AbstractMap.SimpleEntry<String, Boolean>("disable_employments_filter", employments_ids == null),
        new AbstractMap.SimpleEntry<String, Boolean>("disable_categories_filter",categories_ids == null),
        new AbstractMap.SimpleEntry<String, Boolean>("disable_languages_filter", languages_ids == null));

//    List<Integer> result = template.query(
//                "SELECT COUNT(cnt_tbl.id) id " +
//                    "FROM ( SELECT DISTINCT v.id id "+
//                            "FROM vacancies v " +
//                                "LEFT JOIN companies_list c ON v.company_id = c.id " +
//                                "LEFT JOIN rates_list r     ON v.rate_id = r.id " +
//                                "LEFT JOIN locations_list l ON v.location_id = l.id " +
//                                "LEFT JOIN vacancies_types          v_t ON v.id = v_t.vacancy_id " +
//                                "LEFT JOIN vacancies_professions    v_p ON v.id = v_p.vacancy_id " +
//                                "LEFT JOIN vacancies_employments    v_e ON v.id = v_e.vacancy_id " +
//                                "LEFT JOIN vacancies_categories     v_c ON v.id = v_c.vacancy_id " +
//                                "LEFT JOIN vacancies_languages      v_l ON v.id = v_l.vacancy_id " +
//                            "WHERE " +
//                                "( " + namesConditionExpression + " OR :disable_names_filter) " +
//                                " AND ( :date <= v.date) AND ( :salary <= v.salaryMin OR :salary <= v.salaryMax )" +
//                                " AND (c.id IN (:companies_ids) OR :disable_companies_filter) " +
//                                " AND (l.id IN (:locations_ids) OR :disable_locations_filter ) " +
//                                " AND (r.id IN (:rates_ids )    OR :disable_rates_filter) " +
//                                " AND (v_t.type_id       IN (:types_ids)        OR :disable_types_filter ) " +
//                                " AND (v_p.profession_id IN (:professions_ids)  OR :disable_professions_filter ) " +
//                                " AND (v_e.employment_id IN (:employments_ids)  OR :disable_employments_filter ) " +
//                                " AND (v_c.category_id   IN (:categories_ids)   OR :disable_categories_filter ) " +
//                                " AND ((v_l.language_id  IN (:languages_ids) AND " +
//                                       "v_l.level <= :language_level)           OR :disable_languages_filter ) " +
//                          ") cnt_tbl"
//                    , new MapSqlParameterSource(paramsMap), rowMapperIds);
//
//    if (result.get(0) > (page-1)*rows) {
//        String sortFiled = "";
//        String AscDesc = sort > 0 ? "DESC" : "ASC";
//        switch (Math.abs(sort)){
//            case 2:
//                sortFiled = " salaryAvg " + AscDesc + ", ";
//                break;
//            case 1:
//                sortFiled = " date  " + AscDesc + ", ";
//                break;
//            default:
//                sortFiled = " ";
//        }
//
//        List<Integer> ids = template.query(
//                "SELECT DISTINCT v.id id, v.date date, " +
//                        "CASE " +
//                        "   WHEN v.salaryMax > 0 AND v.salaryMin > 0 THEN (v.salaryMax + v.salaryMin)/2 " +
//                        "   WHEN v.salaryMax > 0 AND v.salaryMin = 0 THEN v.salaryMax " +
//                        "   WHEN v.salaryMin > 0 AND v.salaryMax = 0 THEN v.salaryMin" +
//                        "   ELSE 0 " +
//                        "END salaryAvg " +
//                        "FROM vacancies v " +
//                            "LEFT JOIN companies_list c ON v.company_id = c.id " +
//                            "LEFT JOIN rates_list r     ON v.rate_id = r.id " +
//                            "LEFT JOIN locations_list l ON v.location_id = l.id " +
//                            "LEFT JOIN vacancies_types          v_t ON v.id = v_t.vacancy_id " +
//                            "LEFT JOIN vacancies_professions    v_p ON v.id = v_p.vacancy_id " +
//                            "LEFT JOIN vacancies_employments    v_e ON v.id = v_e.vacancy_id " +
//                            "LEFT JOIN vacancies_categories     v_c ON v.id = v_c.vacancy_id " +
//                            "LEFT JOIN vacancies_languages      v_l ON v.id = v_l.vacancy_id " +
//                        "WHERE " +
//                        "( " + namesConditionExpression + " OR :disable_names_filter) " +
//                            " AND ( :date <= v.date) AND ( :salary <= v.salaryMin OR :salary <= v.salaryMax )" +
//                            " AND (c.id IN (:companies_ids) OR :disable_companies_filter) " +
//                            " AND (l.id IN (:locations_ids) OR :disable_locations_filter ) " +
//                            " AND (r.id IN (:rates_ids )    OR :disable_rates_filter) " +
//                            " AND (v_t.type_id       IN (:types_ids)        OR :disable_types_filter ) " +
//                            " AND (v_p.profession_id IN (:professions_ids)  OR :disable_professions_filter ) " +
//                            " AND (v_e.employment_id IN (:employments_ids)  OR :disable_employments_filter ) " +
//                            " AND (v_c.category_id   IN (:categories_ids)   OR :disable_categories_filter ) " +
//                            " AND ((v_l.language_id  IN (:languages_ids) AND " +
//                                   "v_l.level <= :language_level)           OR :disable_languages_filter ) " +
//                        " ORDER BY " + sortFiled + " id " +
//                            " LIMIT :rows_limit OFFSET :rows_offset"
//                , new MapSqlParameterSource(paramsMap), rowMapperIds);
//
//
//
//        result.addAll(ids);
//    }

    String sort_Filed = "";
    String AscDesc = sort > 0 ? "DESC" : "ASC";
    switch (Math.abs(sort)) {
        case 2:
            sort_Filed = " salaryAvg " + AscDesc + ", ";
            break;
        case 1:
            sort_Filed = " date  " + AscDesc + ", ";
            break;
        default:
            sort_Filed = " ";
    }

    List<Integer> result = template.query("WITH " +
        "cte_all AS (SELECT DISTINCT v.id id, v.date date, " +
                    "CASE " +
                    "   WHEN v.salaryMax > 0 AND v.salaryMin > 0 THEN (v.salaryMax + v.salaryMin)/2 " +
                    "   WHEN v.salaryMax > 0 AND v.salaryMin = 0 THEN v.salaryMax " +
                    "   WHEN v.salaryMin > 0 AND v.salaryMax = 0 THEN v.salaryMin" +
                    "   ELSE 0 " +
                    "END salaryAvg " +
                    "FROM vacancies v " +
                    "LEFT JOIN companies_list c ON v.company_id = c.id " +
                    "LEFT JOIN rates_list r     ON v.rate_id = r.id " +
                    "LEFT JOIN locations_list l ON v.location_id = l.id " +
                    "LEFT JOIN vacancies_types          v_t ON v.id = v_t.vacancy_id " +
                    "LEFT JOIN vacancies_professions    v_p ON v.id = v_p.vacancy_id " +
                    "LEFT JOIN vacancies_employments    v_e ON v.id = v_e.vacancy_id " +
                    "LEFT JOIN vacancies_categories     v_c ON v.id = v_c.vacancy_id " +
                    "LEFT JOIN vacancies_languages      v_l ON v.id = v_l.vacancy_id " +
                    "WHERE " +
                    "( " + namesConditionExpression + " OR :disable_names_filter) " +
                    " AND ( :date <= v.date) AND ( :salary <= v.salaryMin OR :salary <= v.salaryMax )" +
                    " AND (c.id IN (:companies_ids) OR :disable_companies_filter) " +
                    " AND (l.id IN (:locations_ids) OR :disable_locations_filter ) " +
                    " AND (r.id IN (:rates_ids )    OR :disable_rates_filter) " +
                    " AND (v_t.type_id       IN (:types_ids)        OR :disable_types_filter ) " +
                    " AND (v_p.profession_id IN (:professions_ids)  OR :disable_professions_filter ) " +
                    " AND (v_e.employment_id IN (:employments_ids)  OR :disable_employments_filter ) " +
                    " AND (v_c.category_id   IN (:categories_ids)   OR :disable_categories_filter ) " +
                    " AND ((v_l.language_id  IN (:languages_ids) AND " +
                    "v_l.level <= :language_level)           OR :disable_languages_filter ) " +
                    "ORDER BY " + sort_Filed + " id), " +
        "cte_page AS (SELECT cte_all.id id FROM cte_all " +
                    "  LIMIT :rows_limit OFFSET :rows_offset ) " +
        "SELECT COUNT(cte_all.id) id FROM cte_all " +
        "UNION ALL " +
        "SELECT cte_page.id FROM cte_page"
            , new MapSqlParameterSource(paramsMap), rowMapperIds);

    return  result;
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<Integer> getSublocationsIdsByParentsIds(List<Integer> parentLocations){
        List<Location> locations = template.query(
                "WITH RECURSIVE rec_tbl (id, name, parent_id, is_group) AS (" +
                        "  SELECT     id, name, parent_id, is_group  FROM locations_list" +
                        "  WHERE      id IN (:locations)" +
                        "  UNION ALL" +
                        "  SELECT     pl.id, pl.name, pl.parent_id, pl.is_group FROM locations_list pl" +
                        "  INNER JOIN rec_tbl ON pl.parent_id = rec_tbl.id) " +
                    "SELECT DISTINCT * FROM rec_tbl WHERE NOT is_group",
                new MapSqlParameterSource(Map.of("locations", parentLocations)), rowMapperLocation);
        return locations.stream().map(Location::getId).collect(Collectors.toList());

//        List<Integer> locations_ids =  null;
//        if (locations != null){
//            locations_ids = new ArrayList<>();
//            for (Location location : locations) {
//                locations_ids.add(location.getId());
//            }
//        }
//        return locations_ids;
    }
//=========================================================================================================> Professions
    public List<Profession> getProfessionsByIds(int[] professions_id){
        List<Integer> ids = Arrays.stream(professions_id).filter(i -> i > 0).boxed().collect(Collectors.toList());
        if (ids.size()>0){
             return template.query("SELECT DISTINCT id, name FROM professions_list WHERE id IN (:ids) AND NOT is_group",
                     new MapSqlParameterSource("ids", ids), rowMapperProfession);
        }
        return null;
    }
//----------------------------------------------------------------------------------------------------------------------
    public List<Integer> getSubprofessionsIdsByParentsIds(List<Integer> parentProfessions){
        List<Profession> professions = template.query(
                "WITH RECURSIVE rec_tbl (id, name, parent_id, is_group) AS (" +
                        "  SELECT     id, name, parent_id, is_group  FROM professions_list" +
                        "  WHERE      id IN (:professions)" +
                        "  UNION ALL" +
                        "  SELECT     p_t.id, p_t.name, p_t.parent_id, p_t.is_group FROM professions_list p_t" +
                        "  INNER JOIN rec_tbl ON p_t.parent_id = rec_tbl.id) " +
                        "SELECT DISTINCT * FROM rec_tbl WHERE NOT is_group",
                new MapSqlParameterSource(Map.of("professions", parentProfessions)), rowMapperProfession);
        return professions.stream().map(Profession::getId).collect(Collectors.toList());

//        List<Integer> professions_ids =  null;
//        if (professions != null){
//            professions_ids =  new ArrayList<>();
//            for (Profession profession : professions) {
//                professions_ids.add(profession.getId());
//            }
//        }
//        return professions_ids;
    }
//===============================================================================================================> Types
    public List<Type> getTypesByIds(List<Integer> ids){
        if (ids.size()>0){
            return template.query("SELECT DISTINCT id, name FROM types_list WHERE id IN (:ids)",
                    new MapSqlParameterSource("ids", ids), rowMapperType);
        }
        return null;
    }
//----------------------------------------------------------------------------------------------------------------------
    public List<Type> getTypesByIds(int[] types_ids){
        return getTypesByIds(Arrays.stream(types_ids).filter(i -> i > 0).boxed().collect(Collectors.toList()));
    }
//=========================================================================================================> Employments
    public List<Employment> getEmploymentsByIds(List<Integer> ids){
        if (ids.size()>0){
            return template.query("SELECT DISTINCT id, name FROM employments_list WHERE id IN (:ids)",
                    new MapSqlParameterSource("ids", ids), rowMapperEmployment);
        }
        return null;
    }
//----------------------------------------------------------------------------------------------------------------------
    public List<Employment> getEmploymentsByIds(int[] employments_ids){
        List<Integer> ids = Arrays.stream(employments_ids).filter(i -> i > 0).boxed().collect(Collectors.toList());
        return getEmploymentsByIds(ids);
    }
//==========================================================================================================> Categories
    public List<Category> getCategoriesByIds(List<Integer> ids){
        if (ids != null){
            return template.query("SELECT DISTINCT id, name FROM categories_list WHERE id IN (:ids)",
                    new MapSqlParameterSource("ids", ids), rowMapperCategory);
        }
        return null;
    }
//----------------------------------------------------------------------------------------------------------------------
    public List<Category> getCategoriesByIds(int[] categories_ids){
        List<Integer> ids = null;
        if (categories_ids != null) {
            ids = Arrays.stream(categories_ids).filter(i -> i > 0).boxed().collect(Collectors.toList());
        }
        return getCategoriesByIds(ids);
    }
//===========================================================================================================> Languages
    public List<Language> getLanguagesByIds(int [][] languages_ids_levels_ids){
        if (languages_ids_levels_ids == null){
            return null;
        }
        List<Language> result = null;
        Map<Integer, Integer> language_ids_levels_ids_Map = new HashMap<>();
        for (int[] languages_ids_levels_id : languages_ids_levels_ids) {
            int language_id = 0;
            int level_id = 0;
            if (languages_ids_levels_id.length == 2) {
                language_id = languages_ids_levels_id[0];
                level_id = languages_ids_levels_id[1];
            }
            if (language_id > 0 && level_id > 0) {
                language_ids_levels_ids_Map.put(language_id, level_id);
            }
        }
        if (language_ids_levels_ids_Map.size() > 0) {
            List<Integer> languages_ids = new ArrayList<>(language_ids_levels_ids_Map.keySet());
            List<Integer> levels_ids = new ArrayList<>(language_ids_levels_ids_Map.values());
            List<Language>  all_languages = template.query(
                    "SELECT ll.id language_id, ll.name language_name, lll.id level_id, lll.name level_name " +
                            "FROM languages_list ll, language_levels_list lll WHERE ll.id IN (:languages_ids) AND lll.id IN (:levels_ids)",
                    new MapSqlParameterSource(Map.of("languages_ids", languages_ids, "levels_ids", levels_ids)),
                    rowMapperLanguage);
            result = new ArrayList<>();
            for (Language language : all_languages) {
                if (language_ids_levels_ids_Map.get(language.getId()) == language.getLevel().getId()) {
                    result.add(language);
                }
            }
        }
        return result;
    }
}
//===============================================================================================================> Rates
//    public Rate getRateById(int rate_id){
//        if (rate_id <= 0){
//            return null;
//        }
//        return  template.queryForObject("SELECT id, name FROM rates_list WHERE id = :id",
//                                            Map.of("id", rate_id), rowMapperRate);
//    }

//===========================================================================================================> Locations
//    public Location getLocationById(int location_id){
//        if (location_id <= 0){
//            return null;
//        }
//        return  template.queryForObject("SELECT id, name FROM locations_list WHERE id = :id",
//                                            Map.of("id", location_id), rowMapperLocation);
//    }
//----------------------------------------------------------------------------------------------------------------------
