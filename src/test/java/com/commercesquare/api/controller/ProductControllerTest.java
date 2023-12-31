package com.commercesquare.api.controller;

import com.commercesquare.api.entity.Product;
import com.commercesquare.api.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductController productController;

    @Test
    void getAllProducts() {
        Product product1 = new Product(1L, "SKU1", "Product 1", "Short Desc 1", "Long Desc 1", null, null, null);
        Product product2 = new Product(2L, "SKU2", "Product 2", "Short Desc 2", "Long Desc 2", null, null, null);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productController.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(product1, products.get(0));
        assertEquals(product2, products.get(1));

        // Verify that the repository method was called
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById() {
        Product product = new Product(1L, "SKU1", "Product 1", "Short Desc 1", "Long Desc 1", null, null, null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));


        ResponseEntity<Product> responseEntity = productController.getProductById(1L);


        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody());

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductBySku() {

        Product product = new Product(1L, "SKU1", "Product 1", "Short Desc 1", "Long Desc 1", null, null, null);
        when(productRepository.findBySku("SKU1")).thenReturn(Optional.of(product));

        ResponseEntity<Product> responseEntity = productController.getProductBySku("SKU1");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody());

        verify(productRepository, times(1)).findBySku("SKU1");
    }
}