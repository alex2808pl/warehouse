package de.telran.warehouse.service;


import de.telran.warehouse.dto.CategoriesDto;
import de.telran.warehouse.entity.Categories;
import de.telran.warehouse.mapper.Mappers;
import de.telran.warehouse.repository.CategoriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriesServiceTest {

    @Mock
    private CategoriesRepository categoriesRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @InjectMocks
    private CategoriesService categoriesServiceTest;

    private CategoriesDto categoriesDtoExpected1;
    private CategoriesDto categoriesDtoExpected2;

    private Categories categoriesEntityExpected1;
    private Categories categoriesEntityExpected2;

    @BeforeEach
    void setUp() {
        categoriesEntityExpected1 = new Categories(1L, "juice", null);
        categoriesEntityExpected2 = new Categories(2L, "water", null);

        categoriesDtoExpected1 = CategoriesDto.builder()
                .categoryId(1L)
                .name("juice")
                .build();

        categoriesDtoExpected2 = CategoriesDto.builder()
                .categoryId(2L)
                .name("water")
                .build();
    }

    @Test
    void getCategoriesTest() {
        when(categoriesRepositoryMock.findAll()).thenReturn(List.of(categoriesEntityExpected1, categoriesEntityExpected2));

        when(mappersMock.convertToCategoriesDto(categoriesEntityExpected1)).thenReturn(categoriesDtoExpected1);
        when(mappersMock.convertToCategoriesDto(categoriesEntityExpected2)).thenReturn(categoriesDtoExpected2);

        List<CategoriesDto> actualCategoriesDtoList = categoriesServiceTest.getCategories();

        assertTrue(actualCategoriesDtoList.size() > 0);
        verify(mappersMock, times(2)).convertToCategoriesDto(any(Categories.class));
        assertEquals(categoriesEntityExpected1.getCategoryId(), actualCategoriesDtoList.get(0).getCategoryId());

    }

    @Test
    void getCategoriesByIdTest() {
        when(categoriesRepositoryMock.findById(anyLong())).thenReturn(Optional.of(categoriesEntityExpected1));
        when(mappersMock.convertToCategoriesDto(any(Categories.class))).thenReturn(categoriesDtoExpected1);

        CategoriesDto actualCategoriesDto = categoriesServiceTest.getCategoriesById(1L);
        assertEquals(categoriesDtoExpected1, actualCategoriesDto);

        verify(categoriesRepositoryMock).findById(anyLong());
        verify(mappersMock).convertToCategoriesDto(any(Categories.class));
    }

    @Test
    void deleteCategoriesByIdTest() {
        when(categoriesRepositoryMock.findById(anyLong())).thenReturn(Optional.of(categoriesEntityExpected1));
        doNothing().when(categoriesRepositoryMock).deleteById(anyLong());

        categoriesServiceTest.deleteCategoriesById(1L);

        verify(categoriesRepositoryMock).findById(1L);
        verify(categoriesRepositoryMock).deleteById(1L);
    }

    @Test
    void insertCategoriesTest() {
        when(categoriesRepositoryMock.save(any(Categories.class))).thenReturn(categoriesEntityExpected1);
        when(mappersMock.convertToCategories(any(CategoriesDto.class))).thenReturn(categoriesEntityExpected1);
        when(mappersMock.convertToCategoriesDto(any(Categories.class))).thenReturn(categoriesDtoExpected1);

        CategoriesDto categoriesDtoActual = categoriesServiceTest.insertCategories(categoriesDtoExpected1);

        assertNotNull(categoriesDtoActual.getCategoryId());
        assertEquals(categoriesDtoExpected1.getName(), categoriesDtoActual.getName());

        verify(categoriesRepositoryMock).save(any(Categories.class));
        verify(mappersMock).convertToCategories(any(CategoriesDto.class));
        verify(mappersMock).convertToCategoriesDto(any(Categories.class));
    }

    @Test
    void updateCategoriesTest() {
        CategoriesDto updatedCategoryDto = CategoriesDto.builder()
                .categoryId(1L)
                .name("updatedCategory")
                .build();
        Categories existingCategoryEntity = new Categories(1L, "juice", null);
        Categories updatedCategoryEntity = new Categories(updatedCategoryDto.getCategoryId(), updatedCategoryDto.getName(), null);

        when(categoriesRepositoryMock.findById(updatedCategoryDto.getCategoryId())).thenReturn(Optional.of(existingCategoryEntity));
        when(categoriesRepositoryMock.save(any(Categories.class))).thenReturn(updatedCategoryEntity);
        when(mappersMock.convertToCategoriesDto(any(Categories.class))).thenReturn(updatedCategoryDto);

        CategoriesDto actualUpdatedCategoryDto = categoriesServiceTest.updateCategories(updatedCategoryDto);

        assertEquals(updatedCategoryDto.getCategoryId(), actualUpdatedCategoryDto.getCategoryId());
        assertEquals(updatedCategoryDto.getName(), actualUpdatedCategoryDto.getName());

        verify(categoriesRepositoryMock).findById(updatedCategoryDto.getCategoryId());
        verify(categoriesRepositoryMock).save(any(Categories.class));
        verify(mappersMock).convertToCategoriesDto(any(Categories.class));
    }
}