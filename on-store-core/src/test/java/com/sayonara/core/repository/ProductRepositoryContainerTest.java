package com.sayonara.core.repository;

import com.sayonara.core.entity.Product;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@ActiveProfiles("testcontainers")
public class ProductRepositoryContainerTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15");

    private final EasyRandom easyRandom = new EasyRandom();

    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void configurePostgreSQLContainer(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
    }

    @Test
    public void testSaveProduct() {
        Product product = easyRandom.nextObject(Product.class);
        product.setId(null);
        Product savedProduct = productRepository.save(product);

        assertNotNull(product.getId());
        assertEquals(savedProduct.getId().toString(), product.getId().toString());
        assertEquals(savedProduct.getName(), product.getName());
    }

    @Test
    public void testFindByName_shouldReturnProduct() {
        Product product = easyRandom.nextObject(Product.class);
        product.setId(null);

        Product savedProduct = productRepository.save(product);
        Optional<Product> productFromDb = productRepository.findProductByName(product.getName());

        assertTrue(productFromDb.isPresent());
        assertEquals(savedProduct.getId(), productFromDb.get().getId());
        assertEquals(savedProduct.getName(), productFromDb.get().getName());
    }
}