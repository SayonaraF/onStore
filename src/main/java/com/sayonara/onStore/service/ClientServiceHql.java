package com.sayonara.onStore.service;

import com.sayonara.onStore.entity.Client;
import com.sayonara.onStore.repository.ClientRepositoryHql;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceHql {

    private ClientRepositoryHql clientRepositoryHql;

    public List<Client> findAllClients() {
        return clientRepositoryHql.findAllClients();
    }

    public Client findClientByEmail(String email) {
        return clientRepositoryHql.findClientByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by email: " + email));
    }

    public Client findClientByPhone(String phone) {
        return clientRepositoryHql.findClientByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by phone number: " + phone));
    }

    @Transactional
    public Client saveClient(Client client) {
        return clientRepositoryHql.saveClient(client);
    }

    @Transactional
    public void deleteClient(Client client) {
        clientRepositoryHql.deleteClient(client);
    }
}
