package de.telran.warehouse.service;

import de.telran.warehouse.config.MapperUtil;
import de.telran.warehouse.dto.CategoriesDto;
import de.telran.warehouse.dto.ProductsDto;
import de.telran.warehouse.entity.Categories;
import de.telran.warehouse.entity.Products;
import de.telran.warehouse.mapper.Mappers;
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
    private final Mappers mappers;

    public List<ProductsDto> getProducts() {
        List<Products> productsList = productsRepository.findAll();

        List<ProductsDto> productsDtoList = MapperUtil.convertList(productsList, mappers::convertToProductsDto);

        return productsDtoList;
    }

    public ProductsDto getProductById(Long id) {
        Optional<Products> productsOptional = productsRepository.findById(id);
        ProductsDto productsDto = null;
        if (productsOptional.isPresent()) {
            productsDto = productsOptional.map(mappers::convertToProductsDto).orElse(null);
        }
        return productsDto;
    }

    public void deleteProductById(Long id) {
        Optional<Products> products = productsRepository.findById(id);
        if (products.isPresent()) {
            productsRepository.deleteById(id);
        }
    }

    public ProductsDto insertProduct(ProductsDto productsDto) {
        Products newProducts = mappers.convertToProducts(productsDto);
        newProducts.setProductId(0);
        Products savedProducts = productsRepository.save(newProducts);
        return mappers.convertToProductsDto(savedProducts);
    }

    public ProductsDto updateProduct(ProductsDto productsDto) {
        if (productsDto.getProductId() <= 0) {
            return null;
        }
        Optional<Products> productsOptional = productsRepository.findById(productsDto.getProductId());
        if (!productsOptional.isPresent()) {
            return null;
        }
        Products products = productsOptional.get();
        products.setName(productsDto.getName());
        Products savedProducts = productsRepository.save(products);

        return mappers.convertToProductsDto(savedProducts);
    }
}
