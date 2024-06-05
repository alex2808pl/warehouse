package de.telran.warehouse.mapper;

import de.telran.warehouse.dto.CategoriesDto;
import de.telran.warehouse.dto.ProductsDto;
import de.telran.warehouse.entity.Categories;
import de.telran.warehouse.entity.Products;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mappers {
    @Autowired
    private ModelMapper modelMapper;

    public CategoriesDto convertToCategoriesDto(Categories categories) {
        modelMapper.typeMap(Categories.class, CategoriesDto.class);

        CategoriesDto categoriesDto = modelMapper.map(categories, CategoriesDto.class);
        return categoriesDto;
    }

    public Categories convertToCategories(CategoriesDto categoriesDto) {
        Categories categories = modelMapper.map(categoriesDto, Categories.class);
        categories.setCategoryId(categoriesDto.getCategoryId());
        categories.setName(categoriesDto.getName());
        return categories;
    }

    public ProductsDto convertToProductsDto(Products products) {
        modelMapper.typeMap(Products.class, ProductsDto.class);

        ProductsDto productsDto = modelMapper.map(products, ProductsDto.class);
        return productsDto;
    }

    public Products convertToProducts(ProductsDto productsDto) {
        Products products = modelMapper.map(productsDto, Products.class);
        products.setProductId(productsDto.getProductId());
        products.setName(productsDto.getName());
        products.setDescription(productsDto.getDescription());
        products.setPrice(productsDto.getPrice());
        products.setImageURL(productsDto.getImageURL());
        products.setDiscountPrice(productsDto.getDiscountPrice());
        products.setCreatedAt(productsDto.getCreatedAt());
        products.setUpdatedAt(productsDto.getUpdatedAt());
        products.setQuantity(productsDto.getQuantity());
        products.setCategory(new Categories(productsDto.getCategory().getCategoryId(),
                productsDto.getCategory().getName(), null));
        return products;
    }

}
