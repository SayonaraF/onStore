package com.sayonara.core.util.mapper;

import com.sayonara.core.dto.ProductDTO;
import com.sayonara.core.entity.Product;

public class ProductMapper {

    public static Product toProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCostPrice(productDTO.getCostPrice());

        return product;
    }

    public static ProductDTO toProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .costPrice(product.getCostPrice())
                .build();
    }
}
