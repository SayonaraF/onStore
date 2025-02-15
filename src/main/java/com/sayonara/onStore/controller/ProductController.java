package com.sayonara.onStore.controller;

import com.sayonara.onStore.dto.ProductDTO;
import com.sayonara.onStore.service.ProductServiceJpa;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductServiceJpa productService;

    @GetMapping
    public List<ProductDTO> findAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/find_by_name/{name}")
    public ProductDTO findProductByName(@PathVariable("name") String name) {
        return productService.findProductByName(name);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO product) {
        productService.saveProduct(product);

        return ResponseEntity.ok("Successfully created product");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO product) {
        productService.updateProduct(product);

        return ResponseEntity.ok("Successfully updated product");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok("Successfully deleted product");
    }
}
