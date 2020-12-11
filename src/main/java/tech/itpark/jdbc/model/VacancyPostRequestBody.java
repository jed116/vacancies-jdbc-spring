package tech.itpark.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@AllArgsConstructor
@Data
public class VacancyPostRequestBody {
    private int id;
    private String name;
    private Date date;
    private int salaryMin;
    private int salaryMax;
    private int rate_id;
    private int location_id;
    private int company_id;
    private int [] types_ids;
    private int [] professions_ids;
    private int [] employments_ids;
    private int [] categories_ids;
    private int [][] languages_ids_levels_ids;
}
