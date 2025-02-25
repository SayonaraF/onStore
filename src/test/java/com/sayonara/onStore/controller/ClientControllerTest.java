package com.sayonara.onStore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayonara.onStore.dto.ClientDTO;
import com.sayonara.onStore.repository.ClientRepository;
import com.sayonara.onStore.service.ClientServiceJpa;
import com.sayonara.onStore.util.mapper.ClientMapper;
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
    private ObjectMapper mapper;

    private UUID clientId;

    @BeforeEach
    void setUp() {
        ClientDTO clientDTO = ClientDTO.builder()
                .name("Test Client")
                .surname("Test Client")
                .gender('М')
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("test@test.com")
                .phone("+72345678990")
                .walletBalance(BigDecimal.valueOf(200.00))
                .build();
        clientRepository.deleteAll();
        clientRepository.save(ClientMapper.toClient(clientDTO));
        clientId = clientService.findClientByEmail("test@test.com").getId();
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
        mockMvc.perform(post("/clients/{id}/add_product", clientId)
                        .param("name", "Test Product"))
                .andExpect(status().isOk());
    }

    @Test
    void removeProductFromCart_shouldRemoveProduct_thenStatus200() throws Exception {
        // todo: а как оно работает то
        clientService.addProductToCart(clientId, "Test Product");
        mockMvc.perform(post("/clients/{id}/remove_product", clientId)
                        .param("name", "Test Product"))
                .andExpect(status().isOk());
    }

    @Test
    void payForCart_shouldPayCart_thenStatus200() throws Exception {
        clientService.addProductToCart(clientId, "Test Product");
        mockMvc.perform(post("/clients/{id}/pay_cart", clientId))
                .andExpect(status().isOk());
    }

    @Test
    void createClient_shouldSaveClient_thenStatus200() throws Exception {
        mockMvc.perform(post("/clients/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(addClient())))
                .andExpect(status().isOk());
    }

    @Test
    void updateClient_shouldUpdateClient_thenStatus200() throws Exception {
        mockMvc.perform(post("/clients/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateClient())))
                .andExpect(status().isOk());
    }

    @Test
    void deleteClient_shouldDeleteClient_thenStatus200() throws Exception {
        mockMvc.perform(delete("/clients/delete")
                        .param("id", clientId.toString()))
                .andExpect(status().isOk());
    }

    private ClientDTO addClient() {
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

    private ClientDTO updateClient() {
        return ClientDTO.builder()
                .id(clientId)
                .name("Test Client")
                .surname("Test Client")
                .gender('М')
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .email("test2@test.com")
                .phone("+72345678999")
                .walletBalance(BigDecimal.valueOf(200.00))
                .build();
    }
}