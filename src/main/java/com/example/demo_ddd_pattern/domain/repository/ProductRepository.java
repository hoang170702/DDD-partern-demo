package com.example.demo_ddd_pattern.domain.repository;

import com.example.demo_ddd_pattern.domain.object.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ProductRepository {
    List<Product> findAllProduct();

    Optional<Product> findProductById(Long id);

    Product create(Product product);

    void deleteProductById(Long id);

    Product update(Product product);
}
