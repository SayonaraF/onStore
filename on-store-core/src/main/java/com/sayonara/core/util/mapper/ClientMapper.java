package com.sayonara.core.util.mapper;

import com.sayonara.core.dto.ClientDto;
import com.sayonara.core.entity.Client;

import java.time.LocalDate;
import java.time.Period;

public class ClientMapper {

    public static Client toClient(ClientDto clientDTO) {
        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setName(clientDTO.getName());
        client.setSurname(clientDTO.getSurname());
        client.setPatronymic(clientDTO.getPatronymic());
        client.setGender(clientDTO.getGender());
        client.setDateOfBirth(clientDTO.getDateOfBirth());
        client.setEmail(clientDTO.getEmail());
        client.setPhone(clientDTO.getPhone());
        client.setWalletBalance(clientDTO.getWalletBalance());

        return client;
    }

    public static ClientDto toClientDTO(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .patronymic(client.getPatronymic())
                .gender(client.getGender())
                .dateOfBirth(client.getDateOfBirth())
                .age(Period.between(client.getDateOfBirth(), LocalDate.now()).getYears())
                .email(client.getEmail())
                .phone(client.getPhone())
                .walletBalance(client.getWalletBalance())
                .build();
    }
}
