package com.sayonara.onStore.repository;

import com.sayonara.onStore.entity.Client;
import com.sayonara.onStore.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setName("Test");
        client.setSurname("Test");
        client.setGender('М');
        client.setDateOfBirth(LocalDate.of(2000,1,1));
        client.setEmail("test@test.com");
        client.setPhone("+79998887766");
        client.setWalletBalance(BigDecimal.valueOf(100.00));

        clientRepository.save(client);
    }

    @Test
    void findClientByEmailTest() {
        Optional<Client> foundClient = clientRepository.findClientByEmail(client.getEmail());

        assertTrue(foundClient.isPresent());
        assertEquals(client.getEmail(), foundClient.get().getEmail());
        assertEquals(client.getPhone(), foundClient.get().getPhone());
    }

    @Test
    void findClientByPhoneTest() {
        Optional<Client> foundClient = clientRepository.findClientByPhone(client.getPhone());

        assertTrue(foundClient.isPresent());
        assertEquals(client.getEmail(), foundClient.get().getEmail());
        assertEquals(client.getPhone(), foundClient.get().getPhone());
    }

    @Test
    void increaseWalletByIdTest() {
        int rows = clientRepository.increaseWalletById(client.getId(), BigDecimal.valueOf(100.00));

        entityManager.refresh(client);

        assertEquals(1, rows);
        assertEquals(0, client.getWalletBalance().compareTo(BigDecimal.valueOf(200)));
    }

    @Test
    void decreaseWalletByIdTest() {
        int rows = clientRepository.decreaseWalletById(client.getId(), BigDecimal.valueOf(20.00));

        entityManager.refresh(client);

        assertEquals(1, rows);
        assertEquals(0, client.getWalletBalance().compareTo(BigDecimal.valueOf(80)));
    }

    @Test
    void increaseWalletById_insufficientFunds() {
        int rows = clientRepository.increaseWalletById(client.getId(), BigDecimal.valueOf(100000000.00));

        assertEquals(0, rows);
    }

    @Test
    void decreaseWalletById_notEnoughFunds() {
        int rows = clientRepository.decreaseWalletById(client.getId(), BigDecimal.valueOf(200.00));

        assertEquals(0, rows);
    }

    @Test
    void getCartTotalPriceTest() {
        client.setCart(addProducts());

        BigDecimal total = clientRepository.getCartTotalPrice(client.getId());

        assertEquals(0, total.compareTo(BigDecimal.valueOf(90)));
    }

    private List<Product> addProducts() {
        Product product = new Product();
        product.setName("Test");
        product.setDescription("Test");
        product.setPrice(BigDecimal.valueOf(80.00));
        product.setCostPrice(BigDecimal.valueOf(50.00));

        Product product2 = new Product();
        product2.setName("Test2");
        product2.setDescription("Test2");
        product2.setPrice(BigDecimal.valueOf(10.00));
        product2.setCostPrice(BigDecimal.valueOf(5.00));

        productRepository.save(product);

        return List.of(product, product2);
    }
}