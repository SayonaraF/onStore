package com.sayonara.core.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CustomerDto {

    private UUID id;
    private String name;
    private String address;
    private int age;
    private boolean active;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private BigDecimal salary;
    private GiftCardDto card;

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", active=" + active +
                ", birthday=" + birthday +
                ", createdAt=" + createdAt +
                ", salary=" + salary +
                ", card={id=" + card.getId() +
                ", discount=" + card.getDiscount() +
                ", expiryDate=" + card.getExpiryDate() +
                "}}";
    }
}
