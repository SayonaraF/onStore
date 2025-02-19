package com.sayonara.onStore.util.validator;

import com.sayonara.onStore.dto.ClientDTO;
import com.sayonara.onStore.entity.Client;
import com.sayonara.onStore.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ClientValidator {

    private ClientRepository clientRepository;

    public void validateClientDTO(ClientDTO clientDTO) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String phoneRegex = "^\\\\+7\\d{10}$";

        if (clientDTO.getName() == null || clientDTO.getSurname() == null || clientDTO.getName().isEmpty() ||
                clientDTO.getSurname().isEmpty()) {
            throw new IllegalArgumentException("Client Name or Surname are required");
        }
        if (clientDTO.getName().length() > 30 || clientDTO.getSurname().length() > 30 ||
        clientDTO.getPatronymic().length() > 30) {
            throw new IllegalArgumentException("Client Name, Surname and Patronymic cannot be longer than 30 characters");
        }
        if (clientDTO.getGender() != 'М' && clientDTO.getGender() != 'Ж') {
            System.out.println(clientDTO.getGender());
            throw new IllegalArgumentException("Wrong gender");
        }
        if (clientDTO.getDateOfBirth() == null) {
            throw new IllegalArgumentException("Date of Birth is required");
        }
        if (clientDTO.getDateOfBirth().isAfter(LocalDate.now()) || clientDTO.getDateOfBirth()
                .isBefore(LocalDate.of(1900, 1, 1))) {
            throw new IllegalArgumentException("Incorrect date of birth");
        }
        if (clientDTO.getEmail() == null || clientDTO.getPhone() == null || clientDTO.getEmail().isEmpty() ||
                clientDTO.getPhone().isEmpty()) {
            throw new IllegalArgumentException("Email and phone are required");
        }
        if (!clientDTO.getEmail().matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (clientDTO.getPhone().matches(phoneRegex)) {
            throw new IllegalArgumentException("Wrong phone number");
        }
        if (clientDTO.getWalletBalance() == null) {
            throw new IllegalArgumentException("Wallet balance is required");
        }
        if (clientDTO.getWalletBalance().doubleValue() < 0 || clientDTO.getWalletBalance().doubleValue() > 99999999.99) {
            throw new IllegalArgumentException("Wallet balance should be between 0 and 99999999");
        }
    }

    public void validateSaveClientDTO(ClientDTO clientDTO) {
        validateClientDTO(clientDTO);

        if (clientRepository.findClientByEmail(clientDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException(String.format("Client with email \"%s\" already exists", clientDTO.getEmail()));
        }
        if (clientRepository.findClientByPhone(clientDTO.getPhone()).isPresent()) {
            throw new IllegalArgumentException(String.format("Client with phone number \"%s\" already exists",
                    clientDTO.getPhone()));
        }
    }

    public void validateUpdateClientDTO(ClientDTO clientDTO) {
        validateClientDTO(clientDTO);

        Optional<Client> clientByEmail = clientRepository.findClientByEmail(clientDTO.getEmail());
        Optional<Client> clientByPhone = clientRepository.findClientByPhone(clientDTO.getPhone());

        clientRepository.findById(clientDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Client not found with this id: "
                + clientDTO.getId()));

        if (clientByEmail.isPresent() && !clientByEmail.get().getId().equals(clientDTO.getId())) {
            throw new IllegalArgumentException(String.format("Client with email \"%s\" already exists", clientDTO.getEmail()));
        }
        if (clientByPhone.isPresent() && clientByPhone.get().getId() != clientDTO.getId()) {
            throw new IllegalArgumentException(String.format("Client with phone number \"%s\" already exists",
                    clientDTO.getPhone()));
        }
    }

    public void validateMoneyFormat(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Значение не должно быть null");
        }

        value = value.setScale(2, RoundingMode.HALF_EVEN);
        if ((value.precision() - value.scale()) > 8) {
            throw new IllegalArgumentException(String.format("Wrong format of value: %s\nExample: 99999999.99", value));
        }
    }
}
