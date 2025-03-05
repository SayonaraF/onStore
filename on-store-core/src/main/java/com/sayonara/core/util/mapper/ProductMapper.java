package com.sayonara.core.util.mapper;

import com.sayonara.core.dto.ProductDto;
import com.sayonara.core.entity.Product;

public class ProductMapper {

    public static Product toProduct(ProductDto productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCostPrice(productDTO.getCostPrice());

        return product;
    }

    public static ProductDto toProductDTO(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .costPrice(product.getCostPrice())
                .build();
    }
}
