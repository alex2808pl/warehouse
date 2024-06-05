package de.telran.warehouse.service;

import de.telran.warehouse.config.MapperUtil;
import de.telran.warehouse.dto.CategoriesDto;
import de.telran.warehouse.entity.Categories;
import de.telran.warehouse.mapper.Mappers;
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
    private final Mappers mappers;

    public List<CategoriesDto> getCategories() {
        List<Categories> categoriesList = categoriesRepository.findAll();

        List<CategoriesDto> categoriesDtoList = MapperUtil.convertList(categoriesList, mappers::convertToCategoriesDto);

        return categoriesDtoList;
    }

    public CategoriesDto getCategoriesById(Long id) {
        Optional<Categories> categoriesOptional = categoriesRepository.findById(id);
        CategoriesDto categoriseDto = null;
        if (categoriesOptional.isPresent()) {
            categoriseDto = categoriesOptional.map(mappers::convertToCategoriesDto).orElse(null);
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

        Categories newCategory = mappers.convertToCategories(categoriesDto);
        newCategory.setCategoryId(0);
        Categories savedCategory = categoriesRepository.save(newCategory);
        return mappers.convertToCategoriesDto(savedCategory);
    }

    public CategoriesDto updateCategories(CategoriesDto categoriesDto) {
        if (categoriesDto.getCategoryId() <= 0) {
            return null;
        }

        Optional<Categories> categoriesOptional = categoriesRepository.findById(categoriesDto.getCategoryId());
        if (!categoriesOptional.isPresent()) {
            return null;
        }

        Categories categories = categoriesOptional.get();
        categories.setName(categoriesDto.getName());
        Categories savedCategory = categoriesRepository.save(categories);

        return mappers.convertToCategoriesDto(savedCategory);
    }
}
