package com.sayonara.onStore.repository;

import com.sayonara.onStore.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepositoryJpa extends JpaRepository<Client, UUID> {

    @Query("SELECT c FROM Client c WHERE c.email = :email")
    Optional<Client> findClientByEmail(@Param("email") String email);

    @Query("SELECT c FROM Client c WHERE c.phone = :phone")
    Optional<Client> findClientByPhone(@Param("phone") String phone);
}
