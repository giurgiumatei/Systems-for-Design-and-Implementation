package ro.ubb.remoting.common.service;

import ro.ubb.remoting.common.domain.Client;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public interface ClientService {

    void addClient(Client client);
    void deleteClient(Client client);
    void updateClient(Client client);
    Client getClientById(Long id);
    List<Client> getAllClients();
    Set<Client> getAllClientsBornBefore(LocalDate date);
}
