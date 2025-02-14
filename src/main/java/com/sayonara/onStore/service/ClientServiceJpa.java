package com.sayonara.onStore.service;

import com.sayonara.onStore.dto.ClientDTO;
import com.sayonara.onStore.entity.Client;
import com.sayonara.onStore.repository.ClientRepositoryJpa;
import com.sayonara.onStore.util.ClientMapper;
import com.sayonara.onStore.util.ClientValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceJpa {

    private final ClientRepositoryJpa clientRepositoryJpa;
    private final ClientValidator clientValidator;

    public List<ClientDTO> findAllClients() {
        return clientRepositoryJpa.findAll().stream().map(ClientMapper::toClientDTO).collect(Collectors.toList());
    }

    public ClientDTO findClientByEmail(String email) {
        Client client = clientRepositoryJpa.findClientByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by email: " + email));

        return ClientMapper.toClientDTO(client);
    }

    public ClientDTO findClientByPhone(String phone) {
        Client client = clientRepositoryJpa.findClientByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by phone number: " + phone));

        return ClientMapper.toClientDTO(client);
    }

    @Transactional
    public void saveClient(ClientDTO clientDTO) {
        clientValidator.validateCreateClientDTO(clientDTO);

        Client client = ClientMapper.toClient(clientDTO);
        clientRepositoryJpa.save(client);
    }

    @Transactional
    public void updateClient(ClientDTO clientDTO) {
        clientValidator.validateUpdateClientDTO(clientDTO);

        Client client = ClientMapper.toClient(clientDTO);
        clientRepositoryJpa.save(client);
    }

    @Transactional
    public void deleteClient(ClientDTO clientDTO) {
        clientValidator.validateDeleteClientDTO(clientDTO);

        clientRepositoryJpa.delete(ClientMapper.toClient(clientDTO));
    }
}