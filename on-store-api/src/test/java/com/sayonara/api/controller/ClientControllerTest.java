package com.sayonara.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayonara.core.dto.ClientDTO;
import com.sayonara.core.entity.Client;
import com.sayonara.core.entity.Product;
import com.sayonara.core.repository.ClientRepository;
import com.sayonara.core.repository.ProductRepository;
import com.sayonara.core.service.ClientServiceJpa;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientServiceJpa clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper mapper;

    private UUID clientId;

    @BeforeEach
    void setUp() {
        Client client = new Client();
        client.setName("Test Client");
        client.setSurname("Test Surname");
        client.setGender('М');
        client.setDateOfBirth(LocalDate.of(2000,1,1));
        client.setEmail("test@test.com");
        client.setPhone("+72345678990");
        client.setWalletBalance(BigDecimal.valueOf(200.00));

        clientRepository.save(client);
        clientId = client.getId();
    }

    @AfterEach
    void tearDown() {
        clientRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void findAllClients_shouldReturnAllClients_thenStatus200() throws Exception {
        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Test Client"));
    }

    @Test
    void findClientByEmail_shouldReturnClientByEmail_thenStatus200() throws Exception {
        String email = "test@test.com";
        mockMvc.perform(get("/clients/find_by_email/{email}", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.id").value(clientId.toString()));
    }

    @Test
    void findClientByPhone_shouldReturnClientByPhone_thenStatus200() throws Exception {
        String phone = "+72345678990";
        mockMvc.perform(get("/clients/find_by_phone/{phone}", phone))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.id").value(clientId.toString()));
    }

    @Test
    void increaseWalletBalance_shouldIncreaseWalletBalance_thenStatus200() throws Exception {
        mockMvc.perform(post("/clients/{id}/increase_wallet", clientId)
                        .param("value", "100.00"))
                .andExpect(status().isOk());
    }

    @Test
    void decreaseWalletBalance_shouldDecreaseWalletBalance_thenStatus200() throws Exception {
        mockMvc.perform(post("/clients/{id}/decrease_wallet", clientId)
                .param("value", "100.00"))
                .andExpect(status().isOk());
    }

    @Test
    void addProductToCart_shouldAddProduct_thenStatus200() throws Exception {
        productRepository.save(createProduct());
        mockMvc.perform(post("/clients/{id}/add_product", clientId)
                        .param("name", "Test Product"))
                .andExpect(status().isOk());
    }

    @Test
    void removeProductFromCart_shouldRemoveProduct_thenStatus200() throws Exception {
        productRepository.save(createProduct());
        clientService.addProductToCart(clientId, "Test Product");
        mockMvc.perform(post("/clients/{id}/remove_product", clientId)
                        .param("name", "Test Product"))
                .andExpect(status().isOk());
    }

    @Test
    void payForCart_shouldPayCart_thenStatus200() throws Exception {
        productRepository.save(createProduct());
        clientService.addProductToCart(clientId, "Test Product");
        mockMvc.perform(post("/clients/{id}/pay_cart", clientId))
                .andExpect(status().isOk());
    }

    @Test
    void createClient_shouldSaveClient_thenStatus200() throws Exception {
        ClientDTO clientDTO = newClient();

        mockMvc.perform(post("/clients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void updateClient_shouldUpdateClient_thenStatus200() throws Exception {
        ClientDTO updatedClient = updatedClient();

        mockMvc.perform(post("/clients/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedClient)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteClient_shouldDeleteClient_thenStatus200() throws Exception {
        mockMvc.perform(delete("/clients/delete")
                        .param("id", clientId.toString()))
                .andExpect(status().isOk());
    }

    private ClientDTO newClient() {
        return ClientDTO.builder()
                .name("Test Client")
                .surname("Test Client")
                .gender('М')
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("test2@test.com")
                .phone("+72345678999")
                .walletBalance(BigDecimal.valueOf(200.00))
                .build();
    }

    private ClientDTO updatedClient() {
        return ClientDTO.builder()
                .id(clientId)
                .name("Update Client")
                .surname("Update Client")
                .gender('М')
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("test2@test.com")
                .phone("+72345678999")
                .walletBalance(BigDecimal.valueOf(300.00))
                .build();
    }

    private Product createProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(20.00));
        product.setCostPrice(BigDecimal.valueOf(10.00));

        return product;
    }
}