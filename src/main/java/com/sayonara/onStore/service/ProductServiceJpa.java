package com.sayonara.onStore.service;

import com.sayonara.onStore.dto.ProductDTO;
import com.sayonara.onStore.entity.Product;
import com.sayonara.onStore.repository.ProductRepository;
import com.sayonara.onStore.util.mapper.ProductMapper;
import com.sayonara.onStore.util.validator.ProductValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceJpa {

    private ProductRepository productRepository;
    private ProductValidator productValidator;

    public List<ProductDTO> findAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::toProductDTO).collect(Collectors.toList());
    }

    public ProductDTO findProductByName(String name) {
        Product product = productRepository.findProductByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Product not found by name: " + name));

        return ProductMapper.toProductDTO(product);
    }

    public void saveProduct(ProductDTO productDTO) {
        productValidator.validateCreateProductDTO(productDTO);

        productRepository.save(ProductMapper.toProduct(productDTO));
    }

    public void updateProduct(ProductDTO productDTO) {
        productValidator.validateUpdateProductDTO(productDTO);

        productRepository.save(ProductMapper.toProduct(productDTO));
    }

    public void deleteProduct(UUID id) {
        productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found by id: " + id));

        productRepository.deleteById(id);
    }
}
