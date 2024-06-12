package de.telran.warehouse.service;

import de.telran.warehouse.dto.PricesDto;
import de.telran.warehouse.entity.Prices;
import de.telran.warehouse.mapper.Mappers;
import de.telran.warehouse.repository.PricesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PricesServiceTest {

    @Mock
    private PricesRepository pricesRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @InjectMocks
    private PricesService pricesServiceTest;

    private PricesDto pricesDtoExpected1;
    private PricesDto pricesDtoExpected2;

    private Prices pricesEntityExpected1;
    private Prices pricesEntityExpected2;

    @BeforeEach
    void setUp() {
        pricesEntityExpected1 = new Prices(1L, null, BigDecimal.valueOf(2000), null, null);
        pricesEntityExpected2 = new Prices(2L, null, BigDecimal.valueOf(5000), null, null);

        pricesDtoExpected1 = PricesDto.builder()
                .priceId(1L)
                .price(BigDecimal.valueOf(2000))
                .build();

        pricesDtoExpected2 = PricesDto.builder()
                .priceId(2L)
                .price(BigDecimal.valueOf(5000))
                .build();
    }

    @Test
    void getPricesTest() {
        when(pricesRepositoryMock.findAll()).thenReturn(List.of(pricesEntityExpected1, pricesEntityExpected2));

        when(mappersMock.convertToPricesDto(pricesEntityExpected1)).thenReturn(pricesDtoExpected1);
        when(mappersMock.convertToPricesDto(pricesEntityExpected2)).thenReturn(pricesDtoExpected2);

        List<PricesDto> actualPricesDtoList = pricesServiceTest.getPrices();

        assertTrue(actualPricesDtoList.size() > 0);
        verify(mappersMock, times(2)).convertToPricesDto(any(Prices.class));
        assertEquals(pricesEntityExpected1.getPriceId(), actualPricesDtoList.get(0).getPriceId());

    }

    @Test
    void getPricesByIdTest() {
        when(pricesRepositoryMock.findById(anyLong())).thenReturn(Optional.of(pricesEntityExpected1));
        when(mappersMock.convertToPricesDto(any(Prices.class))).thenReturn(pricesDtoExpected1);

        PricesDto actualPricesDto = pricesServiceTest.getPricesById(1L);
        assertEquals(pricesDtoExpected1, actualPricesDto);

        verify(pricesRepositoryMock).findById(anyLong());
        verify(mappersMock).convertToPricesDto(any(Prices.class));
    }

    @Test
    void deletePricesByIdTest() {
        when(pricesRepositoryMock.findById(anyLong())).thenReturn(Optional.of(pricesEntityExpected1));
        doNothing().when(pricesRepositoryMock).deleteById(anyLong());

        pricesServiceTest.deletePriceById(1L);

        verify(pricesRepositoryMock).findById(1L);
        verify(pricesRepositoryMock).deleteById(1L);
    }

    @Test
    void insertPricesTest() {
        when(pricesRepositoryMock.save(any(Prices.class))).thenReturn(pricesEntityExpected1);
        when(mappersMock.converToPrices(any(PricesDto.class))).thenReturn(pricesEntityExpected1);
        when(mappersMock.convertToPricesDto(any(Prices.class))).thenReturn(pricesDtoExpected1);

        PricesDto pricesDtoActual = pricesServiceTest.insertPrice(pricesDtoExpected1);

        assertNotNull(pricesDtoActual.getPriceId());
        assertEquals(pricesDtoExpected1.getPrice(), pricesDtoActual.getPrice());

        verify(pricesRepositoryMock).save(any(Prices.class));
        verify(mappersMock).converToPrices((any(PricesDto.class)));
        verify(mappersMock).convertToPricesDto(any(Prices.class));
    }

    @Test
    void updatePricesTest() {
        PricesDto updatedPriceDto = PricesDto.builder()
                .priceId(1L)
                .price(BigDecimal.valueOf(2000))
                .build();
        //Categories existingCategoryEntity = new Categories(1L, "juice", null);
        Prices existingPricesEntity = new Prices(1L, null, BigDecimal.valueOf(2000), null, null);
        Prices updatedPricesEntity = new Prices(updatedPriceDto.getPriceId(), null, updatedPriceDto.getPrice(), null, null);

        when(pricesRepositoryMock.findById(updatedPriceDto.getPriceId())).thenReturn(Optional.of(existingPricesEntity));
        when(pricesRepositoryMock.save(any(Prices.class))).thenReturn(updatedPricesEntity);
        when(mappersMock.convertToPricesDto(any(Prices.class))).thenReturn(updatedPriceDto);

        PricesDto actualUpdatedPriceDto = pricesServiceTest.updatePrice(updatedPriceDto);

        assertEquals(updatedPriceDto.getPriceId(), actualUpdatedPriceDto.getPriceId());
        assertEquals(updatedPriceDto.getPrice(), actualUpdatedPriceDto.getPrice());

        verify(pricesRepositoryMock).findById(updatedPriceDto.getPriceId());
        verify(pricesRepositoryMock).save(any(Prices.class));
        verify(mappersMock).convertToPricesDto(any(Prices.class));
    }
}