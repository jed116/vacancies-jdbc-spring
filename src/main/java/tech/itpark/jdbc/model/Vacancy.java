package tech.itpark.jdbc.model;

import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class Vacancy {
    private int id;
    private String name;
    private Date date;
    private int salaryMin;
    private int salaryMax;
    private Rate rate;
    private Location location;
    private Company company;
    private String description;
    private int car;
    private List<Type> types = new ArrayList<>();
    private List<Profession> professions = new ArrayList<>();
    private List<Employment> employments = new ArrayList<>();
    private List<Language> languages = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public Vacancy(final int id,
                   final String name,
                   final String description,
                   final Date date,
                   final int salaryMin,
                   final int salaryMax,
                   final Rate rate,
                   final Location location,
                   final Company company,
                   final int car
                   ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.rate = rate;
        this.location = location;
        this.company = company;
        this.car = car;
    }

}
