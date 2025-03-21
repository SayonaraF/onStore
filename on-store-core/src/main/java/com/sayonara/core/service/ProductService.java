package com.sayonara.core.service;

import com.sayonara.core.dto.ProductDto;
import com.sayonara.core.entity.Product;
import com.sayonara.core.repository.ProductRepository;
import com.sayonara.core.util.mapper.ProductMapper;
import com.sayonara.core.util.validator.ProductValidator;
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
public class ProductService {

    private ProductRepository productRepository;
    private ProductValidator productValidator;

    public List<ProductDto> findAllProducts() {
        log.info("Запрос на получение всех продуктов");
        return productRepository.findAll().stream().map(ProductMapper::toProductDTO).collect(Collectors.toList());
    }

    public ProductDto findProductByName(String name) {
        log.info("Запрос на получение клиента по названию: {}", name);
        Product product = productRepository.findProductByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Product not found by name: " + name));

        return ProductMapper.toProductDTO(product);
    }

    @Transactional
    public void saveProduct(ProductDto productDTO) {
        log.info("Запрос на сохранение продукта с id: {}", productDTO.getId());
        productValidator.validateCreateProductDTO(productDTO);

        productRepository.save(ProductMapper.toProduct(productDTO));
        log.info("Успешно сохранен продукт с id: {}", productDTO.getId());
    }

    @Transactional
    public void updateProduct(ProductDto productDTO) {
        log.info("Запрос на изменение продукта с id: {}", productDTO.getId());
        productValidator.validateUpdateProductDTO(productDTO);
        isProductExists(productDTO.getId());

        productRepository.save(ProductMapper.toProduct(productDTO));
        log.info("Успешно изменен продукт с id: {}", productDTO.getId());
    }

    @Transactional
    public void deleteProduct(UUID id) {
        log.info("Запрос на удалене продукта с id: {}", id);
        isProductExists(id);

        productRepository.deleteById(id);
        log.info("Успешно удален продукт с id: {}", id);
    }

    private void isProductExists(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found by id: " + id);
        }
    }
}
