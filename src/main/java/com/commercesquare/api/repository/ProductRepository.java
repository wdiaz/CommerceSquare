package com.commercesquare.api.repository;

import com.commercesquare.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product, Long> {
}
