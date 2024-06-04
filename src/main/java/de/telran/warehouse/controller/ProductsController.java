package de.telran.warehouse.controller;

import de.telran.warehouse.dto.ProductsDto;
import de.telran.warehouse.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductsController {
    private final ProductsService productsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductsDto> getProducts() {
        return productsService.getProducts();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductsDto getProductsById(@PathVariable Long id) {
        return productsService.getProductById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductsById(@PathVariable Long id) {
        productsService.deleteProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductsDto insertProducts(@RequestBody ProductsDto productsDto) {
        return productsService.insertProduct(productsDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductsDto updateProducts(@RequestBody ProductsDto productsDto) {
        return productsService.updateProduct(productsDto);
    }
}
