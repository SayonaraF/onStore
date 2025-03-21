package com.sayonara.core.service;

import com.sayonara.core.dto.ClientDto;
import com.sayonara.core.entity.Client;
import com.sayonara.core.entity.Product;
import com.sayonara.core.repository.ClientRepository;
import com.sayonara.core.repository.ProductRepository;
import com.sayonara.core.util.mapper.ClientMapper;
import com.sayonara.core.util.validator.ClientValidator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceJpaTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientValidator clientValidator;

    @InjectMocks
    private ClientServiceJpa clientServiceJpa;

    private Client client;
    private UUID clientId;

    @BeforeEach
    void setUp() {
        clientId = UUID.randomUUID();
        client = new Client();
        client.setId(clientId);
        client.setWalletBalance(BigDecimal.valueOf(100.00));
        client.setDateOfBirth(LocalDate.of(2000, 1, 1));
    }

    @Test
    void findClientByEmail_shouldReturnClientDTO_whenClientExists() {
        String email = "test@test.com";
        when(clientRepository.findClientByEmail(email)).thenReturn(Optional.of(client));
        ClientDto expectedDto = ClientMapper.toClientDTO(client);

        ClientDto result = clientServiceJpa.findClientByEmail(email);

        assertNotNull(result);
        assertEquals(expectedDto.getEmail(), result.getEmail());
        assertEquals(expectedDto.getId(), result.getId());
        verify(clientRepository, times(1)).findClientByEmail(email);
    }

    @Test
    void findClientByEmail_shouldThrowException_whenClientDoesNotExist() {
        when(clientRepository.findClientByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientServiceJpa.findClientByEmail("notfound@example.com"));
    }

    @Test
    void findClientByPhone_shouldReturnClientDTO_whenClientExists() {
        String phone = "+79213984656";
        when(clientRepository.findClientByPhone(phone)).thenReturn(Optional.of(client));
        ClientDto expectedDto = ClientMapper.toClientDTO(client);

        ClientDto result = clientServiceJpa.findClientByPhone(phone);

        assertNotNull(result);
        assertEquals(expectedDto.getPhone(), result.getPhone());
        assertEquals(expectedDto.getId(), result.getId());
        verify(clientRepository, times(1)).findClientByPhone(phone);
    }

    @Test
    void findClientByPhone_shouldThrowException_whenClientDoesNotExist() {
        when(clientRepository.findClientByPhone(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientServiceJpa.findClientByPhone("+77777777777"));
    }

    @Test
    void increaseWalletBalance_shouldIncreaseBalance_whenClientExists() {
        BigDecimal amount = BigDecimal.valueOf(100.00);

        when(clientRepository.existsById(clientId)).thenReturn(true);
        when(clientRepository.increaseWalletById(clientId, amount)).thenReturn(1);

        clientServiceJpa.increaseWalletBalance(clientId, amount);

        verify(clientRepository, times(1)).increaseWalletById(clientId, amount);
    }

    @Test
    void increaseWalletBalance_shouldThrowException_whenClientDoesNotExist() {
        when(clientRepository.existsById(clientId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> clientServiceJpa.increaseWalletBalance(clientId, BigDecimal.valueOf(100.00)));
    }

    @Test
    void increaseWalletBalance_shouldThrowException_whenIncreaseFails() {
        BigDecimal amount = BigDecimal.valueOf(99999999999.00);
        when(clientRepository.existsById(clientId)).thenReturn(true);
        when(clientRepository.increaseWalletById(clientId, amount)).thenReturn(0);

        assertThrows(IllegalArgumentException.class, () -> clientServiceJpa.increaseWalletBalance(clientId, amount));
    }

    @Test
    void decreaseWalletBalance_shouldDecreaseBalance_whenClientExists() {
        BigDecimal amount = BigDecimal.valueOf(100.00);
        when(clientRepository.existsById(clientId)).thenReturn(true);
        when(clientRepository.decreaseWalletById(clientId, amount)).thenReturn(1);

        clientServiceJpa.decreaseWalletBalance(clientId, amount);

        verify(clientRepository, times(1)).decreaseWalletById(clientId, amount);
    }

    @Test
    void decreaseWalletBalance_shouldThrowException_whenClientDoesNotExist() {
        when(clientRepository.existsById(clientId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> clientServiceJpa.decreaseWalletBalance(clientId, BigDecimal.valueOf(100.00)));
    }

    @Test
    void decreaseWalletBalance_shouldThrowException_whenDecreaseFails() {
        BigDecimal amount = BigDecimal.valueOf(99999999999.00);
        when(clientRepository.existsById(clientId)).thenReturn(true);
        when(clientRepository.decreaseWalletById(clientId, amount)).thenReturn(0);

        assertThrows(IllegalArgumentException.class, () -> clientServiceJpa.decreaseWalletBalance(clientId, amount));
    }

    @Test
    void addProductToCart_shouldAddProductToCart_whenClientAndProductExists() {
        String productName = "TestProduct";
        Product product = new Product();
        client.setCart(new ArrayList<>());

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(productRepository.findProductByName(productName)).thenReturn(Optional.of(product));

        clientServiceJpa.addProductToCart(clientId, productName);

        assertTrue(client.getCart().contains(product));
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void addProductToCart_shouldThrowException_whenClientDoesNotExist() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientServiceJpa.addProductToCart(clientId, "TestProduct"));
    }

    @Test
    void addProductToCart_shouldThrowException_whenProductDoesNotExist() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(productRepository.findProductByName(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientServiceJpa.addProductToCart(clientId, "TestProduct"));
    }

    @Test
    void removeProductFromCart_shouldRemoveProductFromCart_whenClientAndProductExists() {
        String productName = "TestProduct";
        Product product = new Product();
        client.setCart(new ArrayList<>(){{add(product);}});

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(productRepository.findProductByName(productName)).thenReturn(Optional.of(product));

        clientServiceJpa.removeProductFromCart(clientId, productName);

        assertFalse(client.getCart().contains(product));
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void removeProductToCart_shouldThrowException_whenClientDoesNotExist() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientServiceJpa.removeProductFromCart(clientId, "TestProduct"));
    }

    @Test
    void removeProductToCart_shouldThrowException_whenProductDoesNotExist() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(productRepository.findProductByName(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientServiceJpa.removeProductFromCart(clientId, "TestProduct"));
    }

    @Test
    void removeProductFromCart_shouldThrowException_whenCartDoesNotHaveProduct() {
        String productName = "TestProduct";
        Product product = new Product();
        client.setCart(new ArrayList<>());

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(productRepository.findProductByName(productName)).thenReturn(Optional.of(product));

        assertThrows(EntityNotFoundException.class, () -> clientServiceJpa.removeProductFromCart(clientId, productName));
    }

    @Test
    void payCart_shouldPayCart_whenClientAndCartExists() {
        Product product = new Product();
        client.setCart(new ArrayList<>(){{add(product);}});

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(clientRepository.getCartTotalPrice(clientId)).thenReturn(BigDecimal.valueOf(25.00));

        clientServiceJpa.payCart(clientId);

        assertTrue(client.getCart().isEmpty());
        assertEquals(client.getWalletBalance(), BigDecimal.valueOf(75.00));
        verify(clientRepository, times(1)).save(client);
    }
}