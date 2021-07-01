package ro.ubb.catalog.core.repository;

import ro.ubb.catalog.core.model.Client;

import java.time.LocalDate;
import java.util.List;

public interface ClientRepository extends Repository<Client, Long> {

    List<Client> findClientsByDateOfBirthBefore(LocalDate date);
}
