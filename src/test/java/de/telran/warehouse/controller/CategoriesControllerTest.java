package de.telran.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.telran.warehouse.dto.CategoriesDto;
import de.telran.warehouse.service.CategoriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriesController.class)
class CategoriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoriesService categoriesServiceMock;

    private CategoriesDto categoriesExpected1;
    private CategoriesDto categoriesExpected2;

    @BeforeEach
    void setUp() {
        categoriesExpected1 = CategoriesDto.builder()
                .categoryId(1L)
                .name("juice")
                .build();

        categoriesExpected2 = CategoriesDto.builder()
                .categoryId(2L)
                .name("water")
                .build();
    }

    @Test
    void getCategoriesTest() throws Exception {
        when(categoriesServiceMock.getCategories()).thenReturn(List.of(categoriesExpected1, categoriesExpected2));
        this.mockMvc.perform(get("/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..categoryId").exists())
                .andExpect(jsonPath("$..name").exists());
    }

    @Test
    void getCategoriesByIdTest() throws Exception {
        when(categoriesServiceMock.getCategoriesById(anyLong())).thenReturn(CategoriesDto.builder().categoryId(1L).build());
        this.mockMvc.perform(get("/categories/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").exists())
                .andExpect(jsonPath("$.categoryId").value(1L));
    }

    @Test
    void deleteCategoriesByIdTest() throws Exception {
        doNothing().when(categoriesServiceMock).deleteCategoriesById(anyLong());

        this.mockMvc.perform(delete("/categories/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void insertCategoriesTest() throws Exception {
        CategoriesDto newCategory = CategoriesDto.builder()
                .categoryId(3L)
                .name("soda")
                .build();

        when(categoriesServiceMock.insertCategories(any(CategoriesDto.class))).thenReturn(newCategory);

        this.mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategory)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId").exists())
                .andExpect(jsonPath("$.categoryId").value(3L))
                .andExpect(jsonPath("$.name").value("soda"));
    }

    @Test
    void updateCategoriesTest() throws Exception {
        CategoriesDto expectedCategories = CategoriesDto.builder()
                .categoryId(1L)
                .name("juice")
                .build();

        when(categoriesServiceMock.updateCategories(any(CategoriesDto.class))).thenReturn(expectedCategories);
        this.mockMvc.perform(put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "categoryId": 1
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(1L));
    }
}