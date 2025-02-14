package com.sayonara.onStore.controller;

import com.sayonara.onStore.dto.ClientDTO;
import com.sayonara.onStore.service.ClientServiceHql;
import com.sayonara.onStore.service.ClientServiceJpa;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientServiceJpa clientServiceJpa;
    private final ClientServiceHql clientServiceHql;

    @GetMapping
    public List<ClientDTO> findAllClients() {
        return clientServiceJpa.findAllClients();
//        return clientServiceHql.findAllClients();
    }

    @GetMapping("/find_by_email/{email}")
    public ClientDTO findClientByEmail(@PathVariable String email) {
        return clientServiceJpa.findClientByEmail(email);
//        return clientServiceHql.findClientByEmail(email);
    }

    @GetMapping("/find_by_phone/{phone}")
    public ClientDTO findClientByPhone(@PathVariable String phone) {
        return clientServiceJpa.findClientByPhone(phone);
//        return clientServiceHql.findClientByPhone(phone);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestBody ClientDTO clientDTO) {
        clientServiceJpa.saveClient(clientDTO);
//        return clientServiceHql.saveClient(clientDTO);

        return ResponseEntity.ok("Successfully created client");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateClient(@RequestBody ClientDTO clientDTO) {
        clientServiceJpa.updateClient(clientDTO);
//        clientServiceHql.saveClient(clientDTO);

        return ResponseEntity.ok("Successfully updated client");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteClient(@RequestBody ClientDTO clientDTO) {
        try {
            clientServiceJpa.deleteClient(clientDTO);
//        clientServiceHql.deleteClient(clientDTO);

            return ResponseEntity.ok("Successfully deleted client");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
