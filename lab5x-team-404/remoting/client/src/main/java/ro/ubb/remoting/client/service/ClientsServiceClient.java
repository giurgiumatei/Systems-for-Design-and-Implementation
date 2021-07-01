package ro.ubb.remoting.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.remoting.common.domain.Client;
import ro.ubb.remoting.common.service.ClientService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class ClientsServiceClient implements ClientService {

    @Autowired
    ClientService clientService;

    @Override
    public void addClient(Client client) {
        clientService.addClient(client);

    }

    @Override
    public void deleteClient(Client client) {
        clientService.deleteClient(client);

    }

    @Override
    public void updateClient(Client client) {
        clientService.updateClient(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @Override
    public Client getClientById(Long id) {
        return clientService.getClientById(id);
    }

    @Override
    public Set<Client> getAllClientsBornBefore(LocalDate date) {
        return clientService.getAllClientsBornBefore(date);
    }
}
