package com.sayonara.onStore.service;

import com.sayonara.onStore.entity.Client;
import com.sayonara.onStore.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;


    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    public Client findClientByEmail(String email) {
        return clientRepository.findClientByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by email: " + email));
    }

    public Client findClientByPhone(String phone) {
        return clientRepository.findClientByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by phone number: " + phone));
    }

    @Transactional
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Transactional
    public void deleteClientByEmail(String email) {
        clientRepository.delete(findClientByEmail(email));
    }
}