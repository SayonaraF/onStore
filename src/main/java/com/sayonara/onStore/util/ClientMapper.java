package com.sayonara.onStore.util;

import com.sayonara.onStore.dto.ClientDTO;
import com.sayonara.onStore.entity.Client;

import java.time.LocalDate;
import java.time.Period;

public class ClientMapper {

    public static ClientDTO toClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setSurname(client.getSurname());
        clientDTO.setPatronymic(client.getPatronymic());
        clientDTO.setGender(client.getGender());
        clientDTO.setDateOfBirth(client.getDateOfBirth());
        clientDTO.setAge(Period.between(client.getDateOfBirth(), LocalDate.now()).getYears());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPhone(client.getPhone());

        return clientDTO;
    }

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
}
