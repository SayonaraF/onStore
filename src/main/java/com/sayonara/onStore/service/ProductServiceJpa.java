package com.sayonara.onStore.service;

import com.sayonara.onStore.dto.ProductDTO;
import com.sayonara.onStore.entity.Product;
import com.sayonara.onStore.repository.ProductRepository;
import com.sayonara.onStore.util.mapper.ProductMapper;
import com.sayonara.onStore.util.validator.ProductValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceJpa {

    private ProductRepository productRepository;
    private ProductValidator productValidator;

    public List<ProductDTO> findAllProducts() {
        log.info("Запрос на получение всех продуктов");
        return productRepository.findAll().stream().map(ProductMapper::toProductDTO).collect(Collectors.toList());
    }

    public ProductDTO findProductByName(String name) {
        log.info("Запрос на получение клиента по названию: {}", name);
        Product product = productRepository.findProductByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Product not found by name: " + name));

        return ProductMapper.toProductDTO(product);
    }

    @Transactional
    public void saveProduct(ProductDTO productDTO) {
        log.info("Запрос на сохранение продукта");
        productValidator.validateCreateProductDTO(productDTO);

        productRepository.save(ProductMapper.toProduct(productDTO));
        log.info("Продукт успешно сохранен");
    }

    @Transactional
    public void updateProduct(ProductDTO productDTO) {
        log.info("Запрос на изменение продукта");
        productValidator.validateUpdateProductDTO(productDTO);

        productRepository.save(ProductMapper.toProduct(productDTO));
        log.info("Продукт успешно изменен");
    }

    @Transactional
    public void deleteProduct(UUID id) {
        log.info("Запрос на удалене продукта");
        isProductExists(id);

        productRepository.deleteById(id);
        log.info("Продукт успешно удален");
    }

    private void isProductExists(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found by id: " + id);
        }
    }
}
