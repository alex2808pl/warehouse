package de.telran.warehouse.mapper;

import de.telran.warehouse.dto.CategoriesDto;
import de.telran.warehouse.dto.PricesDto;
import de.telran.warehouse.dto.ProductsDto;
import de.telran.warehouse.entity.Categories;
import de.telran.warehouse.entity.Prices;
import de.telran.warehouse.entity.Products;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mappers {
    @Autowired
    private ModelMapper modelMapper;

    public CategoriesDto convertToCategoriesDto(Categories categories) {
        CategoriesDto categoriesDto = modelMapper.map(categories, CategoriesDto.class);
        return categoriesDto;
    }

    public Categories convertToCategories(CategoriesDto categoriesDto) {
        Categories categories = modelMapper.map(categoriesDto, Categories.class);
        return categories;
    }

    public ProductsDto convertToProductsDto(Products products) {
        ProductsDto productsDto = modelMapper.map(products, ProductsDto.class);
        return productsDto;
    }

    public Products convertToProducts(ProductsDto productsDto) {
        Products products = modelMapper.map(productsDto, Products.class);
        return products;
    }
    public PricesDto convertToPricesDto(Prices prices){
        PricesDto pricesDto = modelMapper.map(prices, PricesDto.class);
        return pricesDto;
    }

    public Prices converToPrices (PricesDto pricesDto){
        Prices prices = modelMapper.map(pricesDto, Prices.class);
        return prices;
    }
}
