package service;

import domain.Client;
import domain.validators.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface ClientService {


    /**
     * Adds the given client to the repository.
     * @param client - given client
     * @throws ValidatorException if client is not valid
     */
    CompletableFuture<Boolean> addClient(Client client) throws ValidatorException;
    /**
     * Removes the given Client from the repository.
     * @param id - given id
     * @throws GunShopException if the client is not present in the repository
     */
    CompletableFuture<Boolean> removeClient(Long id) throws ValidatorException;

    /**
     * Updates the given gunType in the repository.
     * @param client - given client
     * @throws GunShopException if the client is not present in the repository
     */
    CompletableFuture<Boolean> updateClient(Client client) throws ValidatorException;

    /**
     * Gets all the clients currently in the repository.
     * @return HashSet containing all clients in the repository.
     */
    CompletableFuture<Set<Client>> getAllClients();

    /**
     * Gets a client based on a given id.
     * @return a Client object or null if there is no such Client is in the repository.
     */
    CompletableFuture<Client> getClientById(Long id) ;

    //TODO
    //filters
    CompletableFuture<Set<Client>> getAllClientsBornBefore(LocalDate date);


}
