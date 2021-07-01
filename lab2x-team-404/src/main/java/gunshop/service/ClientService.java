package gunshop.service;

import gunshop.domain.Client;
import gunshop.domain.validators.GunShopException;
import gunshop.domain.validators.ValidatorException;
import gunshop.repository.RepositoryInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientService
{
    private final RepositoryInterface<Long, Client> repository;

    public ClientService(RepositoryInterface<Long, Client> repository)
    {
        this.repository = repository;
    }

    /**
     * Adds the given client to the repository.
     * @param client - given client
     * @throws ValidatorException if client is not valid
     */
    public void addClient(Client client) throws ValidatorException
    {
        repository.save(client);
    }

    /**
     * Removes the given Client from the repository.
     * @param id - given id
     * @throws GunShopException if the client is not present in the repository
     */
    public void removeClient(Long id) throws ValidatorException
    {
        repository.delete(id).
                orElseThrow(() -> new GunShopException("Client does not exist"));
    }

    /**
     * Updates the given gunType in the repository.
     * @param client - given client
     * @throws GunShopException if the client is not present in the repository
     */
    public void updateClient(Client client) throws ValidatorException
    {
        repository.update(client)
                .orElseThrow(() -> new GunShopException("No such client"));
    }

    /**
     * Gets all the clients currently in the repository.
     * @return HashSet containing all clients in the repository.
     */
    public Set<Client> getAllClients()
    {
        return (Set<Client>) repository.findAll();
    }

    /**
     * Gets a client based on a given id.
     * @return a Client object or null if there is no such Client is in the repository.
     */
    public Client getClientById(Long id) {
        var optional = repository.findOne(id);
        return optional.orElse(null);
    }

    //TODO
    //filters
    public Set<Client> getAllClientsBornBefore(LocalDate date){
        Set<Client> clients = (Set<Client>) repository.findAll();
        return clients.stream()
                .filter((client)->client.getDateOfBirth().isBefore(date))
                .collect(Collectors.toSet());
    }

    public void addListOfClients(List<Client> clients)
    {
        clients.forEach(this::addClient);
    }



}
