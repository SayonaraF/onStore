package com.sayonara.core.repository;

import com.sayonara.core.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest()
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveProduct_shouldSaveProduct() {
        Product product = createProduct();
        productRepository.save(product);

        Optional<Product> productOptional = productRepository.findById(product.getId());

        assertTrue(productOptional.isPresent());
        assertEquals("Test", productOptional.get().getName());
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {
        Product product = createProduct();
        productRepository.save(product);
        List<Product> products = productRepository.findAll();

        assertFalse(products.isEmpty());
        assertEquals(product.getId(), products.get(0).getId());
        assertEquals("Test", products.get(0).getName());
    }

    @Test
    void getProductByName_shouldReturnProduct() {
        productRepository.save(createProduct());
        Optional<Product> found = productRepository.findProductByName("Test");

        assertTrue(found.isPresent());
        assertEquals("Test", found.get().getName());
    }

    @Test
    void updateProduct_shouldUpdateProduct() {
        Product product = createProduct();
        productRepository.save(product);

        product.setName("Updated Test");
        product.setDescription("Updated Test");
        product.setPrice(BigDecimal.valueOf(400.00));
        product.setCostPrice(BigDecimal.valueOf(200.00));
        Product updatedProduct = productRepository.save(product);

        Optional<Product> found = productRepository.findById(updatedProduct.getId());

        assertTrue(found.isPresent());
        assertEquals("Updated Test", found.get().getName());
        assertEquals("Updated Test", found.get().getDescription());
        assertEquals(BigDecimal.valueOf(400.00), found.get().getPrice());
        assertEquals(BigDecimal.valueOf(200.00), found.get().getCostPrice());
    }

    @Test
    void deleteProduct_shouldDeleteProduct() {
        Product product = createProduct();
        productRepository.save(product);
        productRepository.delete(product);

        Optional<Product> found = productRepository.findById(product.getId());

        assertFalse(found.isPresent());
    }

    private Product createProduct() {
        Product product = new Product();
        product.setName("Test");
        product.setDescription("Test");
        product.setPrice(BigDecimal.valueOf(200.00));
        product.setCostPrice(BigDecimal.valueOf(100.00));

        return product;
    }
}