package ro.ubb.socket.server.service;

import domain.Client;
import domain.validators.GunShopException;
import domain.validators.ValidatorException;
import repository.RepositoryInterface;
import service.ClientService;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {

    private final RepositoryInterface<Long, Client> repository;

    public ClientServiceImpl(RepositoryInterface<Long, Client> repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Boolean> addClient(Client client) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.save(client).isEmpty();
            } catch (GunShopException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> removeClient(Long id) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.delete(id).isPresent();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> updateClient(Client client) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.update(client).isPresent();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Set<Client>> getAllClients() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return (Set<Client>) repository.findAll();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Client> getClientById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.findOne(id).orElse(null);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Set<Client>> getAllClientsBornBefore(LocalDate date) {
        return CompletableFuture.supplyAsync(() -> {
            Set<Client> clients = (Set<Client>) repository.findAll();
            return clients.stream()
                .filter((client)->client.getDateOfBirth().isBefore(date))
                .collect(Collectors.toSet());
            });
    }

}

