package de.telran.warehouse.controller;

import de.telran.warehouse.dto.PricesDto;
import de.telran.warehouse.service.PricesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/prices")
public class PricesController {
    private final PricesService pricesService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PricesDto> getPrices() {
        return pricesService.getPrices();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PricesDto getPricesById(@PathVariable Long id) {
        return pricesService.getPricesById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePricesById(@PathVariable Long id) {
        pricesService.deletePriceById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PricesDto insertPrises(@RequestBody PricesDto pricesDto) {
        return pricesService.insertPrice(pricesDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PricesDto updatePrices(@RequestBody PricesDto pricesDto) {
        return pricesService.updatePrice(pricesDto);
    }
}
