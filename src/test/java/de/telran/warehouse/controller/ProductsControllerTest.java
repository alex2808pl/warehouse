package de.telran.warehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.telran.warehouse.dto.ProductsDto;
import de.telran.warehouse.service.ProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductsController.class)
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductsService productsServiceMock;

    private ProductsDto productsExpected1;
    private ProductsDto productsExpected2;

    @BeforeEach
    void setUp() {
        productsExpected1 = ProductsDto.builder()
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

        productsExpected2 = ProductsDto.builder()
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
    void getProductsTest() throws Exception {
        when(productsServiceMock.getProducts()).thenReturn(List.of(productsExpected1, productsExpected2));
        this.mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..productId").exists())
                .andExpect(jsonPath("$..name").exists());
    }

    @Test
    void getProductsByIdTest() throws Exception {
        when(productsServiceMock.getProductById(anyLong())).thenReturn(ProductsDto.builder().productId(1L).build());
        this.mockMvc.perform(get("/products/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").exists())
                .andExpect(jsonPath("$.productId").value(1L));
    }

    @Test
    void deleteProductsByIdTest() throws Exception {
        doNothing().when(productsServiceMock).deleteProductById(anyLong());

        this.mockMvc.perform(delete("/products/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void insertProductsTest() throws Exception {
        ProductsDto newProduct = ProductsDto.builder()
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

        when(productsServiceMock.insertProduct(any(ProductsDto.class))).thenReturn(newProduct);

        this.mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").exists())
                .andExpect(jsonPath("$.productId").value(2L))
                .andExpect(jsonPath("$.name").value("Apple juice"))
                .andExpect(jsonPath("$.description").value("Juice from USA"))
                .andExpect(jsonPath("$.price").value(new BigDecimal("150.0")))
                .andExpect(jsonPath("$.imageURL").value("http://example.com/image2.jpg"))
                .andExpect(jsonPath("$.discountPrice").value(new BigDecimal("120.0")))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists())
                .andExpect(jsonPath("$.quantity").value(20));
    }

    @Test
    void updateProductsTest() throws Exception {
        ProductsDto expectedProduct = ProductsDto.builder()
                .productId(1L)
                .name("Apple juice")
                .description("Juice from USA")
                .price(new BigDecimal("150.0"))
                .imageURL("http://example.com/image2.jpg")
                .discountPrice(new BigDecimal("120.0"))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .quantity(20)
                .build();

        when(productsServiceMock.updateProduct(any(ProductsDto.class))).thenReturn(expectedProduct);
        this.mockMvc.perform(put("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "productId": 1
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1L));
    }
}