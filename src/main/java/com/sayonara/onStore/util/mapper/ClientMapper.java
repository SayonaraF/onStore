package com.sayonara.onStore.util.mapper;

import com.sayonara.onStore.dto.ClientDTO;
import com.sayonara.onStore.entity.Client;

import java.time.LocalDate;
import java.time.Period;

public class ClientMapper {

    public static Client toClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setName(clientDTO.getName());
        client.setSurname(clientDTO.getSurname());
        client.setPatronymic(clientDTO.getPatronymic());
        client.setGender(clientDTO.getGender());
        client.setDateOfBirth(clientDTO.getDateOfBirth());
        client.setEmail(clientDTO.getEmail());
        client.setPhone(clientDTO.getPhone());

        return client;
    }

    public static ClientDTO toClientDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .patronymic(client.getPatronymic())
                .gender(client.getGender())
                .dateOfBirth(client.getDateOfBirth())
                .age(Period.between(client.getDateOfBirth(), LocalDate.now()).getYears())
                .email(client.getEmail())
                .phone(client.getPhone())
                .build();
    }
}
