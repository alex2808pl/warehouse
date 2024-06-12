package de.telran.warehouse.controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.telran.warehouse.dto.PricesDto;
import de.telran.warehouse.service.PricesService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PricesController.class)
class PricesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PricesService pricesServiceMock;


    private PricesDto pricesExpected;
    private PricesDto pricesExpected2;


    @BeforeEach
    void setUp() {
        pricesExpected = PricesDto.builder()
                .priceId(1L)
                .price(BigDecimal.valueOf(2000))
                .build();

        pricesExpected2 = PricesDto.builder()
                .priceId(2L)
                .price(BigDecimal.valueOf(3000))
                .build();
    }

    @Test
    void getPricesTest() throws Exception {
        when(pricesServiceMock.getPrices()).thenReturn(List.of(pricesExpected, pricesExpected2));
        this.mockMvc.perform(get("/prices"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..priceId").exists())
                .andExpect(jsonPath("$..price").exists());
    }

    @Test
    void getPricesByIdTest() throws Exception {
        when(pricesServiceMock.getPricesById(anyLong())).thenReturn(PricesDto.builder().priceId(1L).build());
        this.mockMvc.perform(get("/prices/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceId").exists())
                .andExpect(jsonPath("$.priceId").value(1L));
    }

    @Test
    void deleteCategoriesByIdTest() throws Exception {
        doNothing().when(pricesServiceMock).deletePriceById(anyLong());

        this.mockMvc.perform(delete("/prices/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void insertPricesTest() throws Exception {
        PricesDto newPrice = PricesDto.builder()
                .priceId(3L)
                .price(BigDecimal.valueOf(4000))
                .build();

        when(pricesServiceMock.insertPrice(any(PricesDto.class))).thenReturn(newPrice);

        this.mockMvc.perform(post("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPrice)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.priceId").exists())
                .andExpect(jsonPath("$.priceId").value(3L))
                .andExpect(jsonPath("$.price").value(4000));
    }

    @Test
    void updatePricesTest() throws Exception {
        PricesDto expectedPrices = PricesDto.builder()
                .priceId(1L)
                .price(BigDecimal.valueOf(2000))
                .build();

        when(pricesServiceMock.updatePrice(any(PricesDto.class))).thenReturn(expectedPrices);
        this.mockMvc.perform(put("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "priceId": 1
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceId").value(1L));
    }
}