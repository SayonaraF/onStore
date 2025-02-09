package com.sayonara.onStore.repository;

import com.sayonara.onStore.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepositoryJpa extends JpaRepository<Client, Integer> {

    Optional<Client> findClientByEmail(String email);

    Optional<Client> findClientByPhone(String phone);
}
