package com.sayonara.onStore.repository;

import com.sayonara.onStore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryJpa extends JpaRepository<Product, UUID> {

    Optional<Product> findProductByName(String name);
}
