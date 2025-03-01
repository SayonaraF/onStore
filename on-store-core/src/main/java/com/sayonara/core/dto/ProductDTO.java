package com.sayonara.core.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class ProductDTO {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal costPrice;

}
