package de.telran.warehouse.repository;

import de.telran.warehouse.entity.Prices;
import de.telran.warehouse.entity.Products;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

@DataJpaTest
class PricesRepositoriesTest {

    @Autowired
    PricesRepository pricesRepositoriesTest;

    @Autowired
    ProductsRepository productsRepositoriesTest;

    @Test
    void testGet() {
        Products product = new Products();
        product.setProductId(1L);

        Prices pricesExpected = new Prices();
        pricesExpected.setChangeAt(new Timestamp(System.currentTimeMillis()));
        pricesExpected.setPrice(BigDecimal.valueOf(19.99));
        pricesExpected.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        pricesExpected.setProduct(product);

        Prices savedPrices = pricesRepositoriesTest.save(pricesExpected);

        Optional<Prices> pricesActual = pricesRepositoriesTest.findById(savedPrices.getPriceId());
        assertTrue(pricesActual.isPresent());

        assertEquals(pricesExpected.getPriceId(), pricesActual.get().getPriceId());
        assertEquals(pricesExpected.getPrice(), pricesActual.get().getPrice());
        assertEquals(pricesExpected.getChangeAt(), pricesActual.get().getChangeAt());
        assertEquals(pricesExpected.getCreatedAt(), pricesActual.get().getCreatedAt());
        assertEquals(pricesExpected.getProduct().getProductId(), pricesActual.get().getProduct().getProductId());
    }

    @Test
    void testInsert() {
        Prices newPrices = new Prices();
        newPrices.setPriceId(6L);
        newPrices.setChangeAt(new Timestamp(System.currentTimeMillis()));
        newPrices.setPrice(BigDecimal.valueOf(19.99));
        newPrices.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        newPrices.setProduct(new Products());

        Prices savedPrices = pricesRepositoriesTest.save(newPrices);

        assertNotNull(savedPrices);
        assertTrue(savedPrices.getPriceId() > 0);

        Optional<Prices> findPrices = pricesRepositoriesTest.findById(savedPrices.getPriceId());
        assertTrue(findPrices.isPresent());
        assertEquals(BigDecimal.valueOf(19.99), findPrices.get().getPrice());
    }

    @Test
    void testEdit() {
        Prices pricesToUpdate = new Prices();
        pricesToUpdate.setChangeAt(new Timestamp(System.currentTimeMillis()));
        pricesToUpdate.setPrice(BigDecimal.valueOf(59.99));
        pricesToUpdate.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        pricesToUpdate.setProduct(new Products());

        Prices savedPrices = pricesRepositoriesTest.save(pricesToUpdate);

        Optional<Prices> pricesDB = pricesRepositoriesTest.findById(savedPrices.getPriceId());
        assertTrue(pricesDB.isPresent());

        Prices pricesToUpdateFetched = pricesDB.get();
        pricesToUpdateFetched.setPrice(BigDecimal.valueOf(39.99));

        Prices updatedPrices = pricesRepositoriesTest.save(pricesToUpdateFetched);
        assertNotNull(updatedPrices);
        assertEquals(BigDecimal.valueOf(39.99), updatedPrices.getPrice());

        Optional<Prices> findUpdatedPrices = pricesRepositoriesTest.findById(savedPrices.getPriceId());
        assertTrue(findUpdatedPrices.isPresent());
        assertEquals(BigDecimal.valueOf(39.99), findUpdatedPrices.get().getPrice());
    }

    @Test
    void testDelete() {

        Prices pricesToDelete = new Prices();
        pricesToDelete.setChangeAt(new Timestamp(System.currentTimeMillis()));
        pricesToDelete.setPrice(BigDecimal.valueOf(19.99));
        pricesToDelete.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        pricesToDelete.setProduct(new Products());

        Prices savedPrices = pricesRepositoriesTest.save(pricesToDelete);
        assertNotNull(savedPrices);
        assertTrue(savedPrices.getPriceId() > 0);

        Long pricesId = savedPrices.getPriceId();

        pricesRepositoriesTest.delete(savedPrices);

        Optional<Prices> findPricesAfterDelete = pricesRepositoriesTest.findById(pricesId);
        assertFalse(findPricesAfterDelete.isPresent());
    }
}