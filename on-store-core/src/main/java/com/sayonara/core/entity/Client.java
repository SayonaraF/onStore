package com.sayonara.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "client")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Column(name = "surname", nullable = false, length = 30)
    private String surname;
    @Column(name = "patronymic", length = 30)
    private String patronymic;
    @Column(name = "gender", nullable = false, length = 1)
    private char gender;
    @Column(name = "birth_date", nullable = false)
    private LocalDate dateOfBirth;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "phone", nullable = false, unique = true, length = 12)
    private String phone;
    @Column(name = "wallet_balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal walletBalance;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "client_item",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> cart;

}