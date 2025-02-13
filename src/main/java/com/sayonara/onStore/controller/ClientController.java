package com.sayonara.onStore.controller;

import com.sayonara.onStore.dto.ClientDTO;
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
    public List<ClientDTO> findAllClients() {
//        return clientServiceJpa.findAllClients();
        return clientServiceHql.findAllClients();
    }

    @GetMapping("/find_by_email/{email}")
    public ClientDTO findClientByEmail(@PathVariable String email) {
//        return clientServiceJpa.findClientByEmail(email);
        return clientServiceHql.findClientByEmail(email);
    }

    @GetMapping("/find_by_phone/{phone}")
    public ClientDTO findClientByPhone(@PathVariable String phone) {
//        return clientServiceJpa.findClientByPhone(phone);
        return clientServiceHql.findClientByPhone(phone);
    }

    @PostMapping("/create")
    public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
//        return clientServiceJpa.saveClient(clientDTO);
        return clientServiceHql.saveClient(clientDTO);
    }

    @PostMapping("/update")
    public ClientDTO updateClient(@RequestBody ClientDTO clientDTO) {
//        return clientServiceJpa.saveClient(clientDTO);
        return clientServiceHql.saveClient(clientDTO);
    }

    @DeleteMapping("/delete")
    public void deleteClient(@RequestBody ClientDTO clientDTO) {
//        clientServiceJpa.deleteClient(clientDTO);
        clientServiceHql.deleteClient(clientDTO);
    }
}
