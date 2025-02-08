package com.sayonara.onStore.controller;

import com.sayonara.onStore.entity.Client;
import com.sayonara.onStore.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public List<Client> findAllClients() {
        return clientService.findAllClients();
    }

    @GetMapping("/find_by_email/{email}")
    public Client findClientByEmail(@PathVariable String email) {
        return clientService.findClientByEmail(email);
    }

    @GetMapping("/find_by_phone/{phone}")
    public Client findClientByPhone(@PathVariable String phone) {
        return clientService.findClientByPhone(phone);
    }

    @PostMapping("/create_client")
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @PostMapping("/update_client")
    public Client updateClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @DeleteMapping("/delete_client/{email}")
    public void deleteClient(@PathVariable String email) {
        clientService.deleteClientByEmail(email);
    }
}
