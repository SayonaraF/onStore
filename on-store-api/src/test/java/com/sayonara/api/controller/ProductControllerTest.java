package com.sayonara.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayonara.core.dto.ProductDTO;
import com.sayonara.core.repository.ProductRepository;
import com.sayonara.core.service.ProductService;
import com.sayonara.core.util.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper mapper;

    private UUID productId;

    @BeforeEach
    void setUp() {
        ProductDTO productDTO = ProductDTO.builder()
                .name("Test Product")
                .description("Test")
                .costPrice(BigDecimal.valueOf(100.00))
                .price(BigDecimal.valueOf(200.00))
                .build();
        productRepository.deleteAll();
        productRepository.save(ProductMapper.toProduct(productDTO));
        productId = productService.findProductByName("Test Product").getId();
    }

    @Test
    void findAllProducts_shouldReturnAllProducts_thenStatus200() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    @Test
    void findProductByName_shouldReturnProductByName_thenStatus200() throws Exception {
        String name = "Test Product";
        mockMvc.perform(get("/products/find_by_name/" + name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void createProduct_shouldSaveProduct_thenStatus200() throws Exception {
        mockMvc.perform(post("/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newProduct())))
                .andExpect(status().isOk());
    }

    @Test
    void updateProduct_shouldUpdateProduct_thenStatus200() throws Exception {
        mockMvc.perform(post("/products/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateProduct())))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct_shouldDeleteProduct_thenStatus200() throws Exception {
        mockMvc.perform(delete("/products/delete/{id}", productId))
                .andExpect(status().isOk());
    }

    private ProductDTO newProduct() {
        return ProductDTO.builder()
                .name("New Product")
                .description("New Product")
                .costPrice(BigDecimal.valueOf(100.00))
                .price(BigDecimal.valueOf(200.00))
                .build();
    }

    private ProductDTO updateProduct() {
        return ProductDTO.builder()
                .id(productId)
                .name("Updated Product")
                .description("Updated Product")
                .costPrice(BigDecimal.valueOf(100.00))
                .price(BigDecimal.valueOf(200.00))
                .build();
    }
}