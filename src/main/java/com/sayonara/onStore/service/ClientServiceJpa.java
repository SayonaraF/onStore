package com.sayonara.onStore.service;

import com.sayonara.onStore.entity.Client;
import com.sayonara.onStore.repository.ClientRepositoryJpa;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceJpa {

    private final ClientRepositoryJpa clientRepositoryJpa;

    public List<Client> findAllClients() {
        return clientRepositoryJpa.findAll();
    }

    public Client findClientByEmail(String email) {
        return clientRepositoryJpa.findClientByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by email: " + email));
    }

    public Client findClientByPhone(String phone) {
        return clientRepositoryJpa.findClientByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by phone number: " + phone));
    }

    @Transactional
    public Client saveClient(Client client) {
        return clientRepositoryJpa.save(client);
    }

    @Transactional
    public void deleteClient(Client client) {
        clientRepositoryJpa.delete(client);
    }
}