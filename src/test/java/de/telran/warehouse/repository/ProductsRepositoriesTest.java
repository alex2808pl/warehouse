package de.telran.warehouse.repository;

import de.telran.warehouse.entity.Products;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ProductsRepositoriesTest {

    private Products testProducts;
    @Autowired
    private ProductsRepositories productsRepositories;

    @BeforeEach
    public void setUp(){
        testProducts = new Products();
        testProducts.setProductId(2);
        productsRepositories.save(testProducts);
    }
    @AfterEach
    public void tearDown(){
        productsRepositories.delete(testProducts);
    }

    @Test
    void createProduct (){
        Long testProductId = 2L;
        Products products = new Products();
        products.setProductId(testProductId);
        products.setName("Glenmorangie");
        products = productsRepositories.save(testProducts);

        assertNotNull(products);
        assertTrue(products.getProductId()>0);
        assertEquals(products.getProductId(),testProductId);
    }
    @Test
    void getProduct (){
        Products productTest = productsRepositories.findById(2l).orElse(null);

        assertNotNull(productTest);
        assertEquals(2l, productTest.getProductId());
    }
    @Test
    void updateProduct (){
        Products productsTest = productsRepositories.findById(2L).get();
        productsTest.setName("Blue Label");
        Products updatedProduct = productsRepositories.save(productsTest);

        assertEquals(updatedProduct.getName(),"Blue Label");
    }
    @Test
    void deletedProduct (){
        productsRepositories.deleteById(2L);
        assertNull(productsRepositories.findById(2L).orElse(null));
    }
}