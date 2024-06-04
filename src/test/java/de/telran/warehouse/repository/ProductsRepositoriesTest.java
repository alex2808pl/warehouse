package de.telran.warehouse.repository;

import de.telran.warehouse.entity.Products;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductsRepositoriesTest {
    private Products testProducts;
    @Autowired
    private ProductsRepository productsRepository;

    @Test
    void testGetProduct() {
        Optional<Products> products = productsRepository.findById(1L);
        assertTrue(products.isPresent());
        assertEquals(1L, products.get().getProductId());
    }

    @Test
    void testInsertProduct() {
        Products testProduct = new Products();
        testProduct.setName("testProduct");
        Products newProduct = productsRepository.save(testProduct);
        assertNotNull(newProduct);
        assertTrue(newProduct.getProductId() > 0);

        Optional<Products> findProduct = productsRepository.findById(newProduct.getProductId());
        assertTrue(findProduct.isPresent());
        assertEquals(testProduct.getName(), findProduct.get().getName());
    }

    @Test
    void testEditProduct() {
        Optional<Products> product = productsRepository.findById(1L);
        assertTrue(product.isPresent());

        Products getTestProduct = product.get();
        assertEquals(1L, getTestProduct.getProductId());
        getTestProduct.setName("Rom");
        Products newTestProduct = productsRepository.save(getTestProduct);
        assertNotNull(newTestProduct);
        assertEquals(1L, newTestProduct.getProductId());

        Optional<Products> findTestProduct = productsRepository.findById(1L);
        assertTrue(findTestProduct.isPresent());
        assertEquals("Rom", findTestProduct.get().getName());
    }

    @Test
    void testDeletedProduct() {
        Products testProduct = new Products();
        testProduct.setName("Rom");
        Products returnTestProduct = productsRepository.save(testProduct);
        assertNotNull(returnTestProduct);
        assertTrue(returnTestProduct.getProductId() > 0);

        Optional<Products> findTestProduct = productsRepository.findById(returnTestProduct.getProductId());
        assertTrue(findTestProduct.isPresent());
        assertEquals(testProduct.getName(), findTestProduct.get().getName());

        productsRepository.delete(findTestProduct.get());

        Optional<Products> productAfterDelete = productsRepository.findById(returnTestProduct.getProductId());
        assertFalse(productAfterDelete.isPresent());
    }
}