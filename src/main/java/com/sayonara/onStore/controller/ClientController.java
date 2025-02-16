package com.sayonara.onStore.controller;

import com.sayonara.onStore.dto.ClientDTO;
import com.sayonara.onStore.service.ClientServiceJpa;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

// TODO: переписать url в нормальный вид

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientServiceJpa clientServiceJpa;

    @GetMapping
    public List<ClientDTO> findAllClients() {
        return clientServiceJpa.findAllClients();
    }

    @GetMapping("/find_by_email/{email}")
    public ClientDTO findClientByEmail(@PathVariable String email) {
        return clientServiceJpa.findClientByEmail(email);
    }

    @GetMapping("/find_by_phone/{phone}")
    public ClientDTO findClientByPhone(@PathVariable String phone) {
        return clientServiceJpa.findClientByPhone(phone);
    }

    @PostMapping("/increase_wallet/{id}")
    public ResponseEntity<?> increaseWalletBalance(@PathVariable UUID id, @RequestParam BigDecimal value) {
        clientServiceJpa.increaseWalletBalance(id, value);

        return ResponseEntity.ok("Wallet successfully increased");
    }

    @PostMapping("/decrease_wallet/{id}")
    public ResponseEntity<?> decreaseWalletBalance(@PathVariable UUID id, @RequestParam BigDecimal value) {
        clientServiceJpa.decreaseWalletBalance(id, value);

        return ResponseEntity.ok("Wallet successfully decreased");
    }

    @PostMapping("/add_product/{client_id}")
    public ResponseEntity<?> addProductToCart(@PathVariable UUID client_id, @RequestParam String name) {
        clientServiceJpa.addProductToCart(client_id, name);

        return ResponseEntity.ok("Product successfully added to cart");
    }

    @PostMapping("/remove_product/{client_id}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable UUID client_id, @RequestParam String name) {
        clientServiceJpa.removeProductFromCart(client_id, name);

        return ResponseEntity.ok("Product successfully removed from cart");
    }

    @PostMapping("/pay_cart/{client_id}")
    public ResponseEntity<?> payForCart(@PathVariable UUID client_id) {
        clientServiceJpa.cartPayment(client_id);

        return ResponseEntity.ok("Cart successfully payed");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestBody ClientDTO clientDTO) {
        clientServiceJpa.saveClient(clientDTO);

        return ResponseEntity.ok("Successfully created client");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateClient(@RequestBody ClientDTO clientDTO) {
        clientServiceJpa.updateClient(clientDTO);

        return ResponseEntity.ok("Successfully updated client");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") UUID id) {
        clientServiceJpa.deleteClient(id);

        return ResponseEntity.ok("Successfully deleted client");
    }
}
