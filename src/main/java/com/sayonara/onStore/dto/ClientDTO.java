package com.sayonara.onStore.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
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

}
