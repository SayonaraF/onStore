package com.sayonara.core.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class GiftCardDto {

    private UUID id;
    private int discount;
    private LocalDate expiryDate;

}
