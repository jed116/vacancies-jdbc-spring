package tech.itpark.jdbc.model;

import lombok.Value;

@Value
public class Language {
    private int id;
    private String name;
    private LanguageLevel level;
}
