package de.telran.warehouse.repository;

import de.telran.warehouse.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepositories extends JpaRepository<Categories, Long> {

}
