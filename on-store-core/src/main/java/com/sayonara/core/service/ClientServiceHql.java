package com.sayonara.core.service;

import com.sayonara.core.dto.ClientDto;
import com.sayonara.core.entity.Client;
import com.sayonara.core.repository.ClientRepositoryHql;
import com.sayonara.core.util.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceHql {

    private ClientRepositoryHql clientRepositoryHql;

    public List<ClientDto> findAllClients() {
        return clientRepositoryHql.findAllClients().stream().map(ClientMapper::toClientDTO).collect(Collectors.toList());
    }

    public ClientDto findClientByEmail(String email) {
        Client client = clientRepositoryHql.findClientByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by email: " + email));

        return ClientMapper.toClientDTO(client);
    }

    public ClientDto findClientByPhone(String phone) {
        Client client = clientRepositoryHql.findClientByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by phone number: " + phone));

        return ClientMapper.toClientDTO(client);
    }

    @Transactional
    public ClientDto saveClient(ClientDto clientDTO) {
        Client client = ClientMapper.toClient(clientDTO);
        return ClientMapper.toClientDTO(clientRepositoryHql.saveClient(client));
    }

    @Transactional
    public void deleteClient(ClientDto clientDTO) {
        clientRepositoryHql.deleteClient(ClientMapper.toClient(clientDTO));
    }
}
