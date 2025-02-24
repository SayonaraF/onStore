package com.sayonara.onStore.service;

import com.sayonara.onStore.dto.ProductDTO;
import com.sayonara.onStore.entity.Product;
import com.sayonara.onStore.repository.ProductRepository;
import com.sayonara.onStore.util.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findProductByName_ShouldReturnProduct_WhenProductExists() {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("TestName");
        when(productRepository.findProductByName("TestName")).thenReturn(Optional.of(product));

        ProductDTO productDTO = ProductMapper.toProductDTO(product);

        productService.findProductByName(productDTO.getName());

        assertNotNull(productDTO);
        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        verify(productRepository, times(1)).findProductByName("TestName");
    }
}