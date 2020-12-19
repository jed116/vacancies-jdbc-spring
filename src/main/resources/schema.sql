CREATE TABLE companies_list
(
    id   INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT NOT NULL
);

    CREATE TABLE locations_list
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT NOT NULL,
    is_group BOOLEAN DEFAULT FALSE,
    parent_id INTEGER REFERENCES locations_list
);

CREATE TABLE employments_list
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE professions_list
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT NOT NULL,
    is_group BOOLEAN DEFAULT FALSE,
    parent_id  INTEGER REFERENCES professions_list
);

CREATE TABLE rates_list
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE types_list
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE languages_list
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE language_levels_list
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE categories_list
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT NOT NULL
);

---------------------------------------------------------------------

CREATE TABLE vacancies
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    date DATE NOT NULL,
    salaryMin INTEGER,
    salaryMax INTEGER,
    rate_id INTEGER REFERENCES rates_list,
    location_id INTEGER REFERENCES locations_list,
    company_id INTEGER REFERENCES companies_list,
    car INTEGER DEFAULT 0
);

---------------------------------------------------------------------

CREATE TABLE vacancies_professions
(
   vacancy_id INTEGER REFERENCES vacancies,
   profession_id INTEGER REFERENCES professions_list,
   UNIQUE (vacancy_id, profession_id)
);

CREATE TABLE vacancies_employments
(
    vacancy_id INTEGER REFERENCES vacancies,
    employment_id INTEGER REFERENCES employments_list,
    UNIQUE (vacancy_id, employment_id)
);

CREATE TABLE vacancies_languages
(
    vacancy_id INTEGER REFERENCES vacancies,
    language_id INTEGER REFERENCES languages_list,
    level   INTEGER REFERENCES language_levels_list,
    UNIQUE (vacancy_id, language_id)
);

CREATE TABLE vacancies_types
(
    vacancy_id INTEGER REFERENCES vacancies,
    type_id INTEGER REFERENCES types_list,
    UNIQUE (vacancy_id, type_id)
);

CREATE TABLE vacancies_categories
(
    vacancy_id INTEGER REFERENCES vacancies,
    category_id INTEGER REFERENCES categories_list,
    UNIQUE (vacancy_id, category_id)
);
