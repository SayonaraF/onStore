package com.sayonara.onStore.controller;

import com.sayonara.onStore.entity.Client;
import com.sayonara.onStore.service.ClientServiceHql;
import com.sayonara.onStore.service.ClientServiceJpa;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientServiceJpa clientServiceJpa;
    private final ClientServiceHql clientServiceHql;

    @GetMapping
    public List<Client> findAllClients() {
        return clientServiceJpa.findAllClients();
//        return clientServiceHql.findAllClients();
    }

    @GetMapping("/find_by_email/{email}")
    public Client findClientByEmail(@PathVariable String email) {
        return clientServiceJpa.findClientByEmail(email);
//        return clientServiceHql.findClientByEmail(email);
    }

    @GetMapping("/find_by_phone/{phone}")
    public Client findClientByPhone(@PathVariable String phone) {
        return clientServiceJpa.findClientByPhone(phone);
//        return clientServiceHql.findClientByPhone(phone);
    }

    @PostMapping("/create")
    public Client createClient(@RequestBody Client client) {
        return clientServiceJpa.saveClient(client);
//        return clientServiceHql.saveClient(client);
    }

    @PostMapping("/update")
    public Client updateClient(@RequestBody Client client) {
        return clientServiceJpa.saveClient(client);
//        return clientServiceHql.saveClient(client);
    }

    @DeleteMapping("/delete")
    public void deleteClient(@RequestBody Client client) {
        clientServiceJpa.deleteClient(client);
//        clientServiceHql.deleteClient(client);
    }
}
