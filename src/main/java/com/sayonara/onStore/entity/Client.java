package com.sayonara.onStore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private char gender;
    @Column(name = "birth_date")
    private LocalDate dateOfBirth;
    @Transient
    private int age;
    private String email;
    private String phone;

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}