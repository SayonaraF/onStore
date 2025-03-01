package com.sayonara.api.controller;

import com.sayonara.core.dto.ProductDTO;
import com.sayonara.core.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> findAllProducts() {
        log.info("Поступил GET-запрос: /products на поиск всех продуктов");
        return productService.findAllProducts();
    }

    @GetMapping("/find_by_name/{name}")
    public ProductDTO findProductByName(@PathVariable("name") String name) {
        log.info("Получен GET-запрос: /find_by_name/{name} на поиск продукта по названию: {}", name);
        return productService.findProductByName(name);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO product) {
        log.info("Поступил POST-запрос: /create на создание продукта с именем: {}", product.getName());
        productService.saveProduct(product);

        return ResponseEntity.ok("Successfully created product");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO product) {
        log.info("Поступил POST-запрос: /update на обновление продукта с id: {}", product.getId());
        productService.updateProduct(product);

        return ResponseEntity.ok("Successfully updated product");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID id) {
        log.info("Поступил DELETE-запрос: /delete/{id} на удаление продукта с id: {}", id);
        productService.deleteProduct(id);

        return ResponseEntity.ok("Successfully deleted product");
    }
}
