package com.commercesquare.api.controller;

import com.commercesquare.api.entity.Product;
import com.commercesquare.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HomeController {

    @Autowired
    ProductRepository productRepository;
    @GetMapping("/")
    public String homepage()
    {
        List<Product> products;
        products = productRepository.findAll();

        return "hit";
    }

    @GetMapping("api/products")
    public List<EntityModel<Product>>  getProducts() {
        List<Product> products = productRepository.findAll();
        List<EntityModel<Product>> productModels = products.stream()
                .map(product -> EntityModel.of(product,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HomeController.class)
                                        .getProductById(product.getId()))
                                .withSelfRel(),
                        WebMvcLinkBuilder.linkTo(HomeController.class).slash("api/products").withRel("allProducts")))
                .collect(Collectors.toList());

        return productModels;
    }

    @GetMapping("api/{id}")
    public EntityModel<Product> getProductById(@PathVariable("id") Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product != null) {
            return EntityModel.of(product,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HomeController.class)
                                    .getProductById(id))
                            .withSelfRel(),
                    WebMvcLinkBuilder.linkTo(HomeController.class).slash("api/products").withRel("allProducts"));
        } else {
            return EntityModel.of(null); // or throw a specific exception
        }
    }

}
