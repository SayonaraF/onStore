package com.sayonara.api.controller;

import com.sayonara.core.dto.ClientDTO;
import com.sayonara.core.service.ClientServiceJpa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Tag(name = "client_methods")
@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientServiceJpa clientServiceJpa;
    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Operation(
            summary = "Summary for findAllClients()",
            description = "Description for findAllClients()"
    )
    @GetMapping
    public List<ClientDTO> findAllClients() {
        logger.info("Получен GET-запрос: /clients на поиск всех клиентов");
        return clientServiceJpa.findAllClients();
    }

    @GetMapping("/find_by_email/{email}")
    public ClientDTO findClientByEmail(@PathVariable String email) {
        logger.info("Получен GET-запрос на поиск клиента по email: {}", email);
        return clientServiceJpa.findClientByEmail(email);
    }

    @GetMapping("/find_by_phone/{phone}")
    public ClientDTO findClientByPhone(@PathVariable String phone) {
        logger.info("Получен GET-запрос на поиск клиента по телефону: {}", phone);
        return clientServiceJpa.findClientByPhone(phone);
    }

    @PostMapping("/{id}/increase_wallet")
    public ResponseEntity<?> increaseWalletBalance(@PathVariable UUID id, @RequestParam BigDecimal value) {
        logger.info("Получен POST-запрос на пополнение кошелька на сумму {} у клиента с id: {}", value, id);
        clientServiceJpa.increaseWalletBalance(id, value);

        return ResponseEntity.ok("Wallet successfully increased");
    }

    @PostMapping("/{id}/decrease_wallet")
    public ResponseEntity<?> decreaseWalletBalance(@PathVariable UUID id, @RequestParam BigDecimal value) {
        logger.info("Получен POST-запрос на снятие с кошелька суммы {} у клиента с id: {}", value, id);
        clientServiceJpa.decreaseWalletBalance(id, value);

        return ResponseEntity.ok("Wallet successfully decreased");
    }

    @PostMapping("/{id}/add_product")
    public ResponseEntity<?> addProductToCart(@PathVariable UUID id, @RequestParam String name) {
        logger.info("Получен POST-запрос на добавление в корзину продукта \"{}\" у клиента с id: {}", name, id);
        clientServiceJpa.addProductToCart(id, name);

        return ResponseEntity.ok("Product successfully added to cart");
    }

    @PostMapping("/{id}/remove_product")
    public ResponseEntity<?> removeProductFromCart(@PathVariable UUID id, @RequestParam String name) {
        logger.info("Получен POST-запрос на удаление из корзины продукта \"{}\" у клиента с id: {}", name, id);
        clientServiceJpa.removeProductFromCart(id, name);

        return ResponseEntity.ok("Product successfully removed from cart");
    }

    @PostMapping("/{id}/pay_cart")
    public ResponseEntity<?> payForCart(@PathVariable UUID id) {
        logger.info("Получен POST-запрос на оплату корзины у клиента с id: {}", id);
        clientServiceJpa.payCart(id);

        return ResponseEntity.ok("Cart successfully payed");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestBody ClientDTO clientDTO) {
        logger.info("Поступил POST-запрос на создание клиента с email: {}", clientDTO.getEmail());
        clientServiceJpa.saveClient(clientDTO);

        return ResponseEntity.ok("Successfully created client");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateClient(@RequestBody ClientDTO clientDTO) {
        logger.info("Поступил POST-запрос на изменение клиента с id: {}", clientDTO.getId());
        clientServiceJpa.updateClient(clientDTO);

        return ResponseEntity.ok("Successfully updated client");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteClient(@RequestParam UUID id) {
        logger.info("Поступил DELETE-запрос на удаление клиента с id: {}", id);
        clientServiceJpa.deleteClient(id);

        return ResponseEntity.ok("Successfully deleted client");
    }
}
