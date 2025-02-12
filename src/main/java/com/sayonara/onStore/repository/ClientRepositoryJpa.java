package com.sayonara.onStore.repository;

import com.sayonara.onStore.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepositoryJpa extends JpaRepository<Client, Integer> {

    Optional<Client> findClientByEmail(String email);

    Optional<Client> findClientByPhone(String phone);
}
