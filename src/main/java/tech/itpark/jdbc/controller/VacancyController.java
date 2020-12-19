package tech.itpark.jdbc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import tech.itpark.jdbc.manager.VacancyManager;
import tech.itpark.jdbc.model.VacancyFoundSelection;
import tech.itpark.jdbc.model.Vacancy;
import tech.itpark.jdbc.model.VacancyPostRequestBody;

import java.sql.Date;
import java.util.*;

@RestController
@RequestMapping("/vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyManager vacancyManager;
//-----------------------------------------------------------------------------------------------------------------> GET
    @GetMapping
    public List<Vacancy> getAll() {
        return vacancyManager.get();
    }

    @GetMapping("/ids")
    public List<Vacancy> getByIds(@RequestParam int[] id) {
        return vacancyManager.get(id);
    }

    @GetMapping("/{id}")
    public Vacancy getById(@PathVariable int id) {
        return vacancyManager.get(id);
    }
//----------------------------------------------------------------------------------------------------------------- POST
    @PostMapping
    public Vacancy save(@RequestBody VacancyPostRequestBody vacancyPostRequestBody) {
        return vacancyManager.save(vacancyPostRequestBody);
    }
//----------------------------------------------------------------------------------------------------------------> FIND
    @GetMapping("/find")
    public VacancyFoundSelection find(@RequestParam Optional<Integer> page,
                                      @RequestParam Optional<Integer> rows,
                                      @RequestParam Optional<Integer> sort,
                                      @RequestParam Optional<Integer> sal,
                                      @RequestParam Optional<Date> date,
                                      @RequestParam Optional<List<String>>  names,
                                      @RequestParam Optional<List<Integer>> comps,
                                      @RequestParam Optional<List<Integer>> locs,
                                      @RequestParam Optional<List<Integer>> rates,
                                      @RequestParam Optional<List<Integer>> profs,
                                      @RequestParam Optional<List<Integer>> empls,
                                      @RequestParam Optional<List<Integer>> langs,
                                      @RequestParam Optional<List<Integer>> ctgrs,
                                      @RequestParam Optional<List<Integer>> types,
                                      @RequestParam Optional<Integer> lglvl,
                                      @RequestParam Optional<Integer> car) {
        Map<String, Object> findMap = new HashMap<>();

        if (!names.isEmpty()) {
            findMap.put("names", names.get());
        }

        if (!sal.isEmpty()) {
            findMap.put("salary", sal.get());
        }
        if (!date.isEmpty()) {
            findMap.put("date", date.get());
        }
        if (!rates.isEmpty()) {
            findMap.put("rates", rates.get());
        }
        if (!comps.isEmpty()) {
            findMap.put("companies", comps.get());
        }
        if (!locs.isEmpty()) {
            findMap.put("locations", vacancyManager.getSublocationsIdsByParentsIds(locs.get()));
        }
        if (!profs.isEmpty()) {
            findMap.put("professions", vacancyManager.getSubprofessionsIdsByParentsIds(profs.get()));
        }
        if (!empls.isEmpty()) {
            findMap.put("employments", empls.get());
        }
        if (!langs.isEmpty()) {
            findMap.put("languages", langs.get());
        }
        if (!lglvl.isEmpty()) {
            findMap.put("language_level", lglvl.get());
        }
        if (!ctgrs.isEmpty()) {
            findMap.put("categories", ctgrs.get());
        }
        if (!types.isEmpty()) {
            findMap.put("types", types.get());
        }
        if (!car.isEmpty()){
            findMap.put("car", car.get());
        }

        return vacancyManager.find( !page.isEmpty() ? page.get() : 1,
                                    !rows.isEmpty() ? rows.get() : 5,
                                    !sort.isEmpty() ? sort.get() : 0,
                                    findMap);
    }
//--------------------------------------------------------------------------------------------------------------- REMOVE
    @DeleteMapping("/{id}/remove")
    public Vacancy removeById(@PathVariable int id) {
        return vacancyManager.removeById(id);
    }
//----------------------------------------------------------------------------------------------------------------------
}

