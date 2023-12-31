package com.commercesquare.api.repository;

import com.commercesquare.api.entity.Category;
import com.commercesquare.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);

    List<Product> findByCategoriesContains(Category category);
}
