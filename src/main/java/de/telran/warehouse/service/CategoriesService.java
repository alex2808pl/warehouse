package de.telran.warehouse.service;

import de.telran.warehouse.dto.CategoriesDto;
import de.telran.warehouse.entity.Categories;
import de.telran.warehouse.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public List<CategoriesDto> getCategories() {
        List<Categories> categoriesList = categoriesRepository.findAll();

        return categoriesList.stream()
                .map(f -> CategoriesDto.builder()
                        .categoryId(f.getCategoryId())
                        .name(f.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public CategoriesDto getCategoriesById(Long id) {
        Optional<Categories> categories = categoriesRepository.findById(id);
        CategoriesDto categoriseDto = null;
        if (categories.isPresent()) {
            categoriseDto = new CategoriesDto(categories.get().getCategoryId(), categories.get().getName());
        }
        return categoriseDto;
    }

    public void deleteCategoriesById(Long id) {
        Optional<Categories> categories = categoriesRepository.findById(id);
        if (categories.isPresent()) {
            categoriesRepository.delete(categories.get());
        }
    }

    public CategoriesDto insertCategories(CategoriesDto categoriesDto) {

        Categories newCategory = new Categories();
        newCategory.setName(categoriesDto.getName());

        Categories savedCategory = categoriesRepository.save(newCategory);
        return new CategoriesDto(savedCategory.getCategoryId(), savedCategory.getName());
    }

    public CategoriesDto updateCategories(CategoriesDto categoriseDto) {
        if (categoriseDto.getCategoryId() <= 0) {
            return null;
        }
        Optional<Categories> categoriesOptional = categoriesRepository.findById(categoriseDto.getCategoryId());
        if (!categoriesOptional.isPresent()) {
            return null;
        }
        Categories categories = categoriesOptional.get();
        categories.setName(categoriseDto.getName());
        Categories savedCategory = categoriesRepository.save(categories);

        return new CategoriesDto(savedCategory.getCategoryId(), savedCategory.getName());
    }
}
