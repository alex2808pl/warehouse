package de.telran.warehouse.controller;

import de.telran.warehouse.dto.CategoriesDto;
import de.telran.warehouse.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/categories")
public class CategoriesController {
    private final CategoriesService categoriesService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoriesDto> getCategories() {
        return categoriesService.getCategories();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoriesDto getCategoriesById(@PathVariable Long id) {
        return categoriesService.getCategoriesById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoriesById(@PathVariable Long id) {
        categoriesService.deleteCategoriesById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriesDto insertCategories(@RequestBody CategoriesDto categoriesDto) {
        return categoriesService.insertCategories(categoriesDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoriesDto updateFavorites(@RequestBody CategoriesDto categoriesDto) {
        return categoriesService.updateCategories(categoriesDto);
    }
}
