package com.sayonara.onStore.service;

import com.sayonara.onStore.dto.ClientDTO;
import com.sayonara.onStore.entity.Client;
import com.sayonara.onStore.repository.ClientRepositoryJpa;
import com.sayonara.onStore.util.mapper.ClientMapper;
import com.sayonara.onStore.util.validator.ClientValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
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

    // TODO: не хочу загружать полностью entity, нужно реализовать валидацию value
    @Transactional
    public void increaseWalletBalance(UUID id, BigDecimal value) {
        if (clientRepositoryJpa.existsById(id) && clientRepositoryJpa.increaseWalletById(id, value) != 1) {
            throw new IllegalArgumentException("Too big value, increase wallet by " + value + " exceeds the limit");
        }
    }

    @Transactional
    public void decreaseWalletBalance(UUID id, BigDecimal value) {
        if (clientRepositoryJpa.decreaseWalletById(id, value) != 1) {
            throw new IllegalArgumentException("Not enough funds for this amount: " + value);
        }
    }

    @Transactional
    public void saveClient(ClientDTO clientDTO) {
        clientValidator.validateSaveClientDTO(clientDTO);

        clientRepositoryJpa.save(ClientMapper.toClient(clientDTO));
    }

    @Transactional
    public void updateClient(ClientDTO clientDTO) {
        clientValidator.validateUpdateClientDTO(clientDTO);

        clientRepositoryJpa.save(ClientMapper.toClient(clientDTO));
    }

    @Transactional
    public void deleteClient(UUID id) {
        clientRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by id: " + id));

        clientRepositoryJpa.deleteById(id);
    }
}