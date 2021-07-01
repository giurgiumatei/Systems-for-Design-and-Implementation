package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Rental;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {
    List<Client> getAllClients();

    Client addClient(Client client);

    void deleteClient(Long id);

    Client updateClient(Client client);

    Client getClientById(Long id);

    List<Client> getAllClientsBornBefore(LocalDate date);

    Rental addRental(Rental rental);
}
