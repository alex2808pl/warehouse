package de.telran.warehouse.service;

import de.telran.warehouse.dto.ProductsDto;
import de.telran.warehouse.entity.Products;
import de.telran.warehouse.mapper.Mappers;
import de.telran.warehouse.repository.ProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {
    @Mock
    private ProductsRepository productsRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @InjectMocks
    private ProductsService productsServiceTest;

    private ProductsDto productsDtoExpected1;
    private ProductsDto productsDtoExpected2;

    private Products productsEntityExpected1;
    private Products productsEntityExpected2;

    @BeforeEach
    void setUp() {
        productsEntityExpected1 = new Products(1L, "Orange juice", "Juice from China",
                new BigDecimal("123.45"), "http://example.com/image1.jpg", new BigDecimal("100.0"),
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), 10,
                null, null);

        productsEntityExpected2 = new Products(2L, "Apple juice", "Juice from America",
                new BigDecimal("150.0"), "http://example.com/image2.jpg", new BigDecimal("120.0"),
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), 20,
                null, null);


        productsDtoExpected1 = ProductsDto.builder()
                .productId(1L)
                .name("Orange juice")
                .description("Juice from China")
                .price(new BigDecimal("123.45"))
                .imageURL("http://example.com/image1.jpg")
                .discountPrice(new BigDecimal("100.00"))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .quantity(10)
                .build();

        productsDtoExpected2 = ProductsDto.builder()
                .productId(2L)
                .name("Apple juice")
                .description("Juice from USA")
                .price(new BigDecimal("150.0"))
                .imageURL("http://example.com/image2.jpg")
                .discountPrice(new BigDecimal("120.0"))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .quantity(20)
                .build();

    }

    @Test
    void getProductsTest() {
        when(productsRepositoryMock.findAll()).thenReturn(List.of(productsEntityExpected1, productsEntityExpected2));

        when(mappersMock.convertToProductsDto(productsEntityExpected1)).thenReturn(productsDtoExpected1);
        when(mappersMock.convertToProductsDto(productsEntityExpected2)).thenReturn(productsDtoExpected2);

        List<ProductsDto> actualProductsDtoList = productsServiceTest.getProducts();

        assertTrue(actualProductsDtoList.size() > 0);
        verify(mappersMock, times(2)).convertToProductsDto(any(Products.class));
        assertEquals(productsEntityExpected1.getProductId(), actualProductsDtoList.get(0).getProductId());


    }

    @Test
    void getProductByIdTest() {
        when(productsRepositoryMock.findById(anyLong())).thenReturn(Optional.of(productsEntityExpected1));
        when(mappersMock.convertToProductsDto(any(Products.class))).thenReturn(productsDtoExpected1);

        ProductsDto actualProductsDto = productsServiceTest.getProductById(1L);
        assertEquals(productsDtoExpected1, actualProductsDto);

        verify(productsRepositoryMock).findById(anyLong());
        verify(mappersMock).convertToProductsDto(any(Products.class));
    }

    @Test
    void deleteProductByIdTest() {
        when(productsRepositoryMock.findById(anyLong())).thenReturn(Optional.of(productsEntityExpected1));
        doNothing().when(productsRepositoryMock).deleteById(anyLong());

        productsServiceTest.deleteProductById(1L);

        verify(productsRepositoryMock).findById(1L);
        verify(productsRepositoryMock).deleteById(1L);
    }

    @Test
    void insertProductTest() {
        when(productsRepositoryMock.save(any(Products.class))).thenReturn(productsEntityExpected1);
        when(mappersMock.convertToProducts(any(ProductsDto.class))).thenReturn(productsEntityExpected1);
        when(mappersMock.convertToProductsDto(any(Products.class))).thenReturn(productsDtoExpected1);

        ProductsDto productsDtoActual = productsServiceTest.insertProduct(productsDtoExpected1);

        assertNotNull(productsDtoActual.getProductId());
        assertEquals(productsDtoExpected1.getName(), productsDtoActual.getName());

        verify(productsRepositoryMock).save(any(Products.class));
        verify(mappersMock).convertToProducts(any(ProductsDto.class));
        verify(mappersMock).convertToProductsDto(any(Products.class));
    }

    @Test
    void updateProductTest() {
        ProductsDto updatedProductDto = ProductsDto.builder()
                .productId(1L)
                .name("Updated Orange Juice")
                .description("Updated Juice from China")
                .price(new BigDecimal("130.0"))
                .imageURL("http://example.com/updated_image1.jpg")
                .discountPrice(new BigDecimal("110.0"))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .quantity(15)
                .build();

        Products existingProductEntity = productsEntityExpected1;
        Products updatedProductEntity = new Products(
                updatedProductDto.getProductId(),
                updatedProductDto.getName(),
                updatedProductDto.getDescription(),
                updatedProductDto.getPrice(),
                updatedProductDto.getImageURL(),
                updatedProductDto.getDiscountPrice(),
                updatedProductDto.getCreatedAt(),
                updatedProductDto.getUpdatedAt(),
                updatedProductDto.getQuantity(),
                null,
                null
        );

        when(productsRepositoryMock.findById(updatedProductDto.getProductId())).thenReturn(Optional.of(existingProductEntity));
        when(productsRepositoryMock.save(any(Products.class))).thenReturn(updatedProductEntity);
        when(mappersMock.convertToProductsDto(any(Products.class))).thenReturn(updatedProductDto);

        ProductsDto actualUpdatedProductDto = productsServiceTest.updateProduct(updatedProductDto);

        assertEquals(updatedProductDto.getProductId(), actualUpdatedProductDto.getProductId());
        assertEquals(updatedProductDto.getName(), actualUpdatedProductDto.getName());
        assertEquals(updatedProductDto.getDescription(), actualUpdatedProductDto.getDescription());
        assertEquals(updatedProductDto.getPrice(), actualUpdatedProductDto.getPrice());
        assertEquals(updatedProductDto.getImageURL(), actualUpdatedProductDto.getImageURL());
        assertEquals(updatedProductDto.getDiscountPrice(), actualUpdatedProductDto.getDiscountPrice());
        assertEquals(updatedProductDto.getCreatedAt(), actualUpdatedProductDto.getCreatedAt());
        assertEquals(updatedProductDto.getUpdatedAt(), actualUpdatedProductDto.getUpdatedAt());
        assertEquals(updatedProductDto.getQuantity(), actualUpdatedProductDto.getQuantity());

        verify(productsRepositoryMock).findById(updatedProductDto.getProductId());
        verify(productsRepositoryMock).save(any(Products.class));
        verify(mappersMock).convertToProductsDto(any(Products.class));
    }
}