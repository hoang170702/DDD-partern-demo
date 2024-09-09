package com.example.demo_ddd_pattern.domain.service;

import com.example.demo_ddd_pattern.domain.object.Product;
import com.example.demo_ddd_pattern.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllProduct();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findProductById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.create(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.update(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProductById(id);
    }
}
