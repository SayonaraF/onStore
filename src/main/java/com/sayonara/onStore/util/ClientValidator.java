package com.sayonara.onStore.util;

import com.sayonara.onStore.dto.ClientDTO;
import com.sayonara.onStore.entity.Client;
import com.sayonara.onStore.repository.ClientRepositoryJpa;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ClientValidator {

    private ClientRepositoryJpa clientRepositoryJpa;

    public void validateClientDTO(ClientDTO clientDTO) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String phoneRegex = "^\\\\+7\\d{10}$";

        if (clientDTO.getName() == null || clientDTO.getSurname() == null || clientDTO.getName().isEmpty() ||
                clientDTO.getSurname().isEmpty()) {
            throw new IllegalArgumentException("Client Name or Surname are required");
        }
        if (clientDTO.getName().length() > 30 || clientDTO.getSurname().length() > 30 ||
        clientDTO.getPatronymic().length() > 30) {
            throw new IllegalArgumentException("Name, Surname and Patronymic can't be longer than 30 characters");
        }
        if (clientDTO.getGender() != 'М' && clientDTO.getGender() != 'Ж') {
            System.out.println(clientDTO.getGender());
            throw new IllegalArgumentException("Wrong gender");
        }
        if (clientDTO.getDateOfBirth().isAfter(LocalDate.now()) || clientDTO.getDateOfBirth()
                .isBefore(LocalDate.of(1900, 1, 1))) {
            throw new IllegalArgumentException("Incorrect date of birth");
        }
        if (clientDTO.getEmail() == null || clientDTO.getPhone() == null) {
            throw new IllegalArgumentException("Email and phone are required");
        }
        if (!clientDTO.getEmail().matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (clientDTO.getPhone().matches(phoneRegex)) {
            throw new IllegalArgumentException("Wrong phone number");
        }
    }

    public void validateCreateClientDTO(ClientDTO clientDTO) {
        validateClientDTO(clientDTO);

        if (clientRepositoryJpa.findClientByEmail(clientDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (clientRepositoryJpa.findClientByPhone(clientDTO.getPhone()).isPresent()) {
            throw new IllegalArgumentException("Phone already exists");
        }
    }

    public void validateUpdateClientDTO(ClientDTO clientDTO) {
        validateClientDTO(clientDTO);

        Optional<Client> clientFromDB = clientRepositoryJpa.findById(clientDTO.getId());
        Optional<Client> clientByEmail = clientRepositoryJpa.findClientByEmail(clientDTO.getEmail());
        Optional<Client> clientByPhone = clientRepositoryJpa.findClientByPhone(clientDTO.getPhone());

        clientFromDB.orElseThrow(() -> new EntityNotFoundException("Client not found"));

        if (clientByEmail.isPresent() && clientByEmail.get().getId() != clientDTO.getId()) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (clientByPhone.isPresent() && clientByPhone.get().getId() != clientDTO.getId()) {
            throw new IllegalArgumentException("Phone already exists");
        }
    }

    public void validateDeleteClientDTO(ClientDTO clientDTO) {
        if (clientRepositoryJpa.findById(clientDTO.getId()).isEmpty()) {
            throw new EntityNotFoundException("Client not found by id: " + clientDTO.getId());
        }
    }
}
