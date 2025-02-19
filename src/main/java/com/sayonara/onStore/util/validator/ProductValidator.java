package com.sayonara.onStore.util.validator;

import com.sayonara.onStore.dto.ProductDTO;
import com.sayonara.onStore.entity.Product;
import com.sayonara.onStore.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;

    public void validateProductDRO(ProductDTO productDTO) {
        if (productDTO.getName() == null || productDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name are required");
        }
        if (productDTO.getName().length() > 50) {
            throw new IllegalArgumentException("Product name cannot be longer than 50 characters");
        }
        if (productDTO.getDescription() == null || productDTO.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Product description are required");
        }
        if (productDTO.getDescription().length() > 50) {
            throw new IllegalArgumentException("Product description cannot be longer than 50 characters");
        }
        if (productDTO.getPrice() == null) {
            throw new IllegalArgumentException("Product price are required");
        }
        if (productDTO.getPrice().doubleValue() < 0 || productDTO.getPrice().doubleValue() > 99999999.99) {
            throw new IllegalArgumentException("Product price must be between 0 and 99999999.99");
        }
        if (productDTO.getCostPrice() == null) {
            throw new IllegalArgumentException("Product cost price are required");
        }
        if (productDTO.getCostPrice().doubleValue() < 0 || productDTO.getCostPrice().doubleValue() > 99999999.99) {
            throw new IllegalArgumentException("Product cost price must be between 0 and 99999999.99");
        }
    }

    public void validateCreateProductDTO(ProductDTO productDTO) {
        validateProductDRO(productDTO);

        if (productRepository.findProductByName(productDTO.getName()).isPresent()) {
            throw new EntityNotFoundException(String.format("Product with name \"%s\" already exists", productDTO.getName()));
        }
    }

    public void validateUpdateProductDTO(ProductDTO productDTO) {
        validateProductDRO(productDTO);

        Optional<Product> product = productRepository.findProductByName(productDTO.getName());

        if (product.isPresent() && !product.get().getId().equals(productDTO.getId())) {
            throw new EntityNotFoundException(String.format("Product with name \"%s\" already exists", productDTO.getName()));
        }
    }
}
