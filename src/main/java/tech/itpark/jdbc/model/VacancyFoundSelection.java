package tech.itpark.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Value
public class VacancyFoundSelection {
    private int page;
    private int pages;
    private int rows;
    private int size;
    private int sort;
    private List<Vacancy> items;
}
