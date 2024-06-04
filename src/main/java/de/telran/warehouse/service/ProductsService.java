package de.telran.warehouse.service;

import de.telran.warehouse.dto.CategoriesDto;
import de.telran.warehouse.dto.ProductsDto;
import de.telran.warehouse.entity.Categories;
import de.telran.warehouse.entity.Products;
import de.telran.warehouse.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    public ProductsDto toDTO(Products products) {
        ProductsDto productsDto = new ProductsDto();
        productsDto.setProductId(products.getProductId());
        productsDto.setName(products.getName());
        productsDto.setDescription(products.getDescription());
        productsDto.setPrice(products.getPrice());
        productsDto.setImageURL(products.getImageURL());
        productsDto.setDiscountPrice(products.getDiscountPrice());
        productsDto.setCreatedAt(products.getCreatedAt());
        productsDto.setUpdatedAt(products.getUpdatedAt());
        productsDto.setQuantity(products.getQuantity());
        productsDto.setCategory(new CategoriesDto(products.getCategory().getCategoryId(),
                products.getCategory().getName()));
        return productsDto;
    }

    public Products toEntity(ProductsDto productsDto) {
        Products products = new Products();
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

    public List<ProductsDto> getProducts() {
        List<Products> productsList = productsRepository.findAll();

        return productsList.stream()
                .map(f -> toDTO(f))
                .collect(Collectors.toList());
    }

    public ProductsDto getProductById(Long id) {
        Optional<Products> productsOptional = productsRepository.findById(id);

        ProductsDto productsDto = null;
        if (productsOptional.isPresent()) {
            Products products = productsOptional.get();
            productsDto = toDTO(products);
        }
        return productsDto;
    }

    public void deleteProductById(Long id) {
        Optional<Products> products = productsRepository.findById(id);
        if (products.isPresent()) {
            productsRepository.delete(products.get());
        }
    }

    public ProductsDto insertProduct(ProductsDto productsDto) {
        Products newProducts = toEntity(productsDto);
        productsRepository.save(newProducts);
        return productsDto;
    }

    public ProductsDto updateProduct(ProductsDto productsDto) {
        if (productsDto.getProductId() <= 0) {
            return null;
        }
        Optional<Products> productsOptional = productsRepository.findById(productsDto.getProductId());
        if (!productsOptional.isPresent()) {
            return null;
        }
        Products products = toEntity(productsDto);
        Products savedProducts = productsRepository.save(products);

        return toDTO(savedProducts);
    }
}
