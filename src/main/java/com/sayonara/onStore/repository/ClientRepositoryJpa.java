package com.sayonara.onStore.repository;

import com.sayonara.onStore.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepositoryJpa extends JpaRepository<Client, UUID> {

    @Query("SELECT c FROM Client c WHERE c.email = :email")
    Optional<Client> findClientByEmail(@Param("email") String email);

    @Query("SELECT c FROM Client c WHERE c.phone = :phone")
    Optional<Client> findClientByPhone(@Param("phone") String phone);

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.walletBalance = c.walletBalance + :amount WHERE c.id = :id" +
            " AND c.walletBalance <= 99999999.99 - :amount")
    int increaseWalletById(UUID id, BigDecimal amount);

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.walletBalance = c.walletBalance - :amount WHERE c.id = :id AND c.walletBalance >= :amount")
    int decreaseWalletById(UUID id, BigDecimal amount);
}
