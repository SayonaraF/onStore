package com.sayonara.onStore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Entity
@Data
@Table(name = "client", schema = "public")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
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
    @Transient
    private int age;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public String getPhone() {
        return ("+7" + phone);
    }

    public void setPhone(String phone) {
        this.phone = phone.substring(2);
    }
}