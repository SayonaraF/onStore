package com.sayonara.onStore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;
    @Column(name = "description", nullable = false, length = 200)
    private String description;
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column(name = "cost_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal costPrice;
}
