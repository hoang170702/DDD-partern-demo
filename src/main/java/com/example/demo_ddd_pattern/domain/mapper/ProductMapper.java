package com.example.demo_ddd_pattern.domain.mapper;

import com.example.demo_ddd_pattern.domain.object.Product;
import com.example.demo_ddd_pattern.infrastructure.entity.ProductEntity;

public class ProductMapper {

    public static Product toDomain(ProductEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getCategory(), entity.getPrice());
    }

    public static ProductEntity toEntity(Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getCategory(), product.getPrice());
    }

}
