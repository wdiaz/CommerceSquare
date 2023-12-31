package com.commercesquare.api.repository;

import com.commercesquare.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c JOIN c.products p WHERE p.sku = :sku")
    List<Category> getCategoriesBySku(@Param("sku") String sku);
}