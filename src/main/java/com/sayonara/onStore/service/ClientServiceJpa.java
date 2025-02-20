package com.sayonara.onStore.service;

import com.sayonara.onStore.dto.ClientDTO;
import com.sayonara.onStore.entity.Client;
import com.sayonara.onStore.entity.Product;
import com.sayonara.onStore.repository.ClientRepository;
import com.sayonara.onStore.repository.ProductRepository;
import com.sayonara.onStore.util.mapper.ClientMapper;
import com.sayonara.onStore.util.validator.ClientValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ClientServiceJpa {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final ClientValidator clientValidator;

    public List<ClientDTO> findAllClients() {
        log.info("Запрос на получение всех клиентов");
        return clientRepository.findAll().stream().map(ClientMapper::toClientDTO).collect(Collectors.toList());
    }

    public ClientDTO findClientByEmail(String email) {
        log.info("Запрос на получение клиента по email: {}", email);
        Client client = clientRepository.findClientByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by email: " + email));

        return ClientMapper.toClientDTO(client);
    }

    public ClientDTO findClientByPhone(String phone) {
        log.info("Запрос на получение клиента по телефону: {}", phone);
        Client client = clientRepository.findClientByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by phone number: " + phone));

        return ClientMapper.toClientDTO(client);
    }

    @Transactional
    public void increaseWalletBalance(UUID id, BigDecimal value) {
        log.info("Запрос на увеличение клиента кошелька");
        isClientExists(id);
        clientValidator.validateMoneyFormat(value);

        int resultOfIncrease = clientRepository.increaseWalletById(id, value);

        if (resultOfIncrease != 1) {
            throw new IllegalArgumentException("Too big value, increase wallet by " + value + " exceeds the limit");
        }
        log.info("Кошелек успешно пополнен");
    }

    @Transactional
    public void decreaseWalletBalance(UUID id, BigDecimal value) {
        log.info("Запрос на снятие с кошелька средств");
        isClientExists(id);
        clientValidator.validateMoneyFormat(value);

        if (clientRepository.decreaseWalletById(id, value) != 1) {
            throw new IllegalArgumentException("Not enough funds for this decrease: " + value);
        }
        log.info("Средства успешно сняты");
    }

    @Transactional
    public void addProductToCart(UUID id, String productName) {
        log.info("Запрос на добавление продукта \"{}\" в корзину", productName);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by id: " + id));
        Product product = productRepository.findProductByName(productName)
                .orElseThrow(() -> new EntityNotFoundException("Product not found by name: " + productName));

        client.getCart().add(product);
        clientRepository.save(client);
        log.info("Продукт \"{}\" успешно добавлен в корзину", productName);
    }

    @Transactional
    public void removeProductFromCart(UUID id, String productName) {
        log.info("Запрос на удаление продукта \"{}\" из корзины", productName);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by id: " + id));
        Product product = productRepository.findProductByName(productName)
                .orElseThrow(() -> new EntityNotFoundException("Product not found by name: " + productName));

        if (client.getCart().remove(product)) {
            clientRepository.save(client);
        } else {
            throw new EntityNotFoundException("Cart doesn't contain product: " + productName);
        }
        log.info("Продукт \"{}\" успешно удален из корзины", productName);
    }

    @Transactional
    public void payCart(UUID id) {
        log.info("Запрос на оплату корзины");
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found by id: " + id));

        if (client.getCart().isEmpty()) {
            throw new EntityNotFoundException("Cart is empty");
        }

        BigDecimal cartTotalPrice = clientRepository.getCartTotalPrice(id);

        if (client.getWalletBalance().compareTo(cartTotalPrice) >= 0) {
            client.setWalletBalance(client.getWalletBalance().subtract(cartTotalPrice));
            client.getCart().clear();
            clientRepository.save(client);
        } else {
            throw new IllegalArgumentException("Not enough funds to pay this cart");
        }
        log.info("Корзина успешно оплачена");
    }

    @Transactional
    public void saveClient(ClientDTO clientDTO) {
        log.info("Запрос на сохранение клиента");
        clientValidator.validateSaveClientDTO(clientDTO);

        clientRepository.save(ClientMapper.toClient(clientDTO));
        log.info("Клиент успешно сохранен");
    }

    @Transactional
    public void updateClient(ClientDTO clientDTO) {
        log.info("Запрос на изменение клиента");
        clientValidator.validateUpdateClientDTO(clientDTO);

        clientRepository.save(ClientMapper.toClient(clientDTO));
        log.info("Клиент успешно изменен");
    }

    @Transactional
    public void deleteClient(UUID id) {
        log.info("Запрос на удалене клиента");
        isClientExists(id);

        clientRepository.deleteById(id);
        log.info("Клиент успешно удален");
    }

    private void isClientExists(UUID id) {
        if(!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client not found by id: " + id);
        }
    }
}
