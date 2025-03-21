package com.sayonara.core.repository;

import com.sayonara.core.entity.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepositoryHql {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Client> findAllClients() {
        return entityManager.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }

    public Optional<Client> findClientByEmail(String email) {
        TypedQuery<Client> query = entityManager.createQuery("SELECT c FROM Client c WHERE c.email = :email", Client.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst();
    }

    public Optional<Client> findClientByPhone(String phone) {
        TypedQuery<Client> query = entityManager.createQuery("SELECT c FROM Client c WHERE c.phone = :phone", Client.class);
        query.setParameter("phone", phone);
        return query.getResultStream().findFirst();
    }

    public Client saveClient(Client client) {
        boolean exist = entityManager.createQuery("SELECT COUNT(c) FROM Client c WHERE c.id = :id", Long.class)
                .setParameter("id", client.getId())
                .getSingleResult() > 0;

        if (!exist) {
            entityManager.createQuery("INSERT INTO Client (name, surname, patronymic, gender, dateOfBirth, email, phone)" +
                            "VALUES (:name, :surname, :patronymic, :gender, :dateOfBirth, :email, :phone)")
                    .setParameter("name", client.getName())
                    .setParameter("surname", client.getSurname())
                    .setParameter("patronymic", client.getPatronymic())
                    .setParameter("gender", client.getGender())
                    .setParameter("dateOfBirth", client.getDateOfBirth())
                    .setParameter("email", client.getEmail())
                    .setParameter("phone", client.getPhone())
                    .executeUpdate();
        } else {
            entityManager.createQuery("UPDATE Client c SET c.name = :name, c.surname = :surname, c.patronymic = :patronymic, " +
                            "c.gender = :gender, c.dateOfBirth = :dateOfBirth, c.email = :email, c.phone = :phone WHERE c.id = :id")
                    .setParameter("id", client.getId())
                    .setParameter("name", client.getName())
                    .setParameter("surname", client.getSurname())
                    .setParameter("patronymic", client.getPatronymic())
                    .setParameter("gender", client.getGender())
                    .setParameter("dateOfBirth", client.getDateOfBirth())
                    .setParameter("email", client.getEmail())
                    .setParameter("phone", client.getPhone())
                    .executeUpdate();
        }

        return entityManager.createQuery("SELECT c FROM Client c WHERE c.email = :email", Client.class)
                .setParameter("email", client.getEmail())
                .getSingleResult();
    }

    public void deleteClient(Client client) {
        entityManager.createQuery("DELETE FROM Client c WHERE c.id = :id")
                .setParameter("id", client.getId())
                .executeUpdate();
    }
}
