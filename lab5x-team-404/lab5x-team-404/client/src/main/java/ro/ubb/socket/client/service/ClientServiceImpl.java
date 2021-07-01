package ro.ubb.socket.client.service;

import domain.Client;
import domain.validators.SocketException;
import domain.validators.ValidatorException;
import message.Message;
import ro.ubb.socket.client.tcp.TcpClient;
import service.ClientService;
import utils.Factory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {
    ExecutorService executorService;
    TcpClient tcpClient;

    public ClientServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public CompletableFuture<Boolean> addClient(Client client) throws ValidatorException {
        // we send a Message with our task and expect an answer Message from server
        Message message = new Message("add client", Factory.clientToLine(client));
        Message response = tcpClient.sendAndReceive(message);
        // we check for errors
        if (response.getHeader().equals("error")){
            throw new RuntimeException();
        }
        return CompletableFuture.supplyAsync(
                ()->true,
                executorService
        );
    }

    @Override
    public CompletableFuture<Boolean> removeClient(Long id) throws ValidatorException {
        // we send a Message with our task and expect an answer Message from server
        Message response = tcpClient.sendAndReceive(
                new Message("remove client", Long.toString(id))
        );
        // we check for errors
        if (response.getHeader().equals("error")){
            throw new RuntimeException();
        }

        return CompletableFuture.supplyAsync(
                ()->true,
                executorService
        );
    }

    @Override
    public CompletableFuture<Boolean> updateClient(Client client) throws ValidatorException {
        // we send a Message with our task and expect an answer Message from server
        Message response = tcpClient.sendAndReceive(
                new Message("update client", Factory.clientToLine(client))
        );
        // we check for errors
        if (response.getHeader().equals("error")){
            throw new RuntimeException();
        }

        return CompletableFuture.supplyAsync(
                ()->true,
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<Client>> getAllClients() {
        Message response;
        try {
            response = tcpClient.sendAndReceive(
                    new Message("get all clients", "")
            );
        } catch (SocketException socketException){
            throw new RuntimeException();
        }

        String[] tokens = response.getBody().split(System.lineSeparator());

        // get the clients
        Set<Client> clients = Arrays.stream(tokens)
                .map(Factory::messageToClient)
                .collect(Collectors.toSet());

        // return a CompletableFuture
        return CompletableFuture.supplyAsync(
                ()->clients,
                executorService
        );
    }

    @Override
    public CompletableFuture<Client> getClientById(Long id) {
        Message response;
        try {
            response = tcpClient.sendAndReceive(
                    new Message("get client by id", "")
            );
        } catch (SocketException socketException){
            throw new RuntimeException();
        }

        String[] tokens = response.getBody().split(System.lineSeparator());

        // get the clients
        Set<Client> clients = Arrays.stream(tokens)
                .map(Factory::messageToClient)
                .collect(Collectors.toSet());

        Optional<Client> clientWithId = clients.stream()
                .filter(client -> client.getId().equals(id))
                .findAny();

        // return a CompletableFuture
        return CompletableFuture.supplyAsync(
                clientWithId::get,
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<Client>> getAllClientsBornBefore(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String formattedString = date.format(formatter);
        Message response;
        try {
            response = tcpClient.sendAndReceive(
                    new Message("get all clients born before", formattedString)
            );
        } catch (SocketException socketException){
            throw new RuntimeException();
        }

        String[] tokens = response.getBody().split(System.lineSeparator());

        // get the clients
        Set<Client> clients = Arrays.stream(tokens)
                .map(Factory::messageToClient)
                .filter(client -> !(client.getName() == null))
                .filter(client -> client.getDateOfBirth().isBefore(date))
                .collect(Collectors.toSet());

        // return a CompletableFuture
        return CompletableFuture.supplyAsync(
                ()->clients,
                executorService
        );
    }

}
