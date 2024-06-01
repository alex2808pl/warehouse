package de.telran.warehouse.repository;

import de.telran.warehouse.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepositories extends JpaRepository<Products, Long> {


}
