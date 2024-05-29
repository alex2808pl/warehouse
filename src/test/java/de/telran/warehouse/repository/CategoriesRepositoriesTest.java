package de.telran.warehouse.repository;

import de.telran.warehouse.entity.Categories;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Optional;

@DataJpaTest
class CategoriesRepositoriesTest {

    @Autowired
    CategoriesRepositories categoriesRepositoriesTest;

    @Autowired
    ProductsRepositories productsRepositoriesTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

        @Test
    void testGet(){
        Categories categoriesExpected = new Categories(1L, "Beer", new HashSet<>());
        Optional<Categories> categoriesActual = categoriesRepositoriesTest.findById(1L);

        assertTrue(categoriesActual.isPresent());
        assertEquals(categoriesExpected.getCategoryId(), categoriesActual.get().getCategoryId());
    }

    @Test
    void testInsert(){
        Categories newCategory = new Categories();
        newCategory.setName("Vodka");
        newCategory.setProducts(new HashSet<>());

        Categories savedCategory = categoriesRepositoriesTest.save(newCategory);

        assertNotNull(savedCategory);
        assertTrue(savedCategory.getCategoryId() > 0);

        Optional<Categories> findCategory = categoriesRepositoriesTest.findById(savedCategory.getCategoryId());
        assertTrue(findCategory.isPresent());
        assertEquals("Vodka", findCategory.get().getName());
    }

    @Test
    void testEdit(){
        Optional<Categories> categoriesDb = categoriesRepositoriesTest.findById(1L);
        assertTrue(categoriesDb.isPresent());

        Categories categoryToUpdate = categoriesDb.get();
        categoryToUpdate.setName("Craft Beer");

        Categories updatedCategory = categoriesRepositoriesTest.save(categoryToUpdate);
        assertNotNull(updatedCategory);
        assertEquals("Craft Beer", updatedCategory.getName());

        Optional<Categories> findUpdatedCategory = categoriesRepositoriesTest.findById(1L);
        assertTrue(findUpdatedCategory.isPresent());
        assertEquals("Craft Beer", findUpdatedCategory.get().getName());
    }

    @Test
    void testDelete() {
        Categories categoryToDelete = new Categories();
        categoryToDelete.setName("Defective");
        categoryToDelete.setProducts(new HashSet<>());

        Categories savedCategory = categoriesRepositoriesTest.save(categoryToDelete);
        assertNotNull(savedCategory);
        assertTrue(savedCategory.getCategoryId() > 0);

        Long categoryId = savedCategory.getCategoryId();
        categoriesRepositoriesTest.delete(savedCategory);

        Optional<Categories> findCategoryAfterDelete = categoriesRepositoriesTest.findById(categoryId);
        assertFalse(findCategoryAfterDelete.isPresent());
    }
}

