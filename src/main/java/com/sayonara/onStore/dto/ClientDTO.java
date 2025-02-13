package com.sayonara.onStore.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Data
public class ClientDTO {
    private UUID id;
    private String name;
    private String surname;
    private String patronymic;
    private char gender;
    private LocalDate dateOfBirth;
    private int age;
    private String email;
    private String phone;

    public void setAge(LocalDate dateOfBirth) {
        age = Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public String getPhone() {
        return ("+7" + phone);
    }

    public void setPhone(String phone) {
        this.phone = phone.substring(2);
    }
}
