package ro.ubb.socket.client.service;

import domain.GunProvider;
import domain.validators.SocketException;
import domain.validators.ValidatorException;
import message.Message;
import ro.ubb.socket.client.tcp.TcpClient;
import service.GunProviderService;
import utils.Factory;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class GunProviderServiceImpl implements GunProviderService {
    ExecutorService executorService;
    TcpClient tcpClient;

    public GunProviderServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public CompletableFuture<Boolean> addGunProvider(GunProvider gunProvider) throws ValidatorException {
        // we send a Message with our task and expect an answer Message from server
        Message response = tcpClient.sendAndReceive(
                new Message("add gunProvider", Factory.gunProviderToLine(gunProvider))
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
    public CompletableFuture<Boolean> removeGunProvider(Long id) throws ValidatorException {
        // we send a Message with our task and expect an answer Message from server
        Message response = tcpClient.sendAndReceive(
                new Message("remove gunProvider", Long.toString(id))
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
    public CompletableFuture<Boolean> updateGunProvider(GunProvider gunProvider) throws ValidatorException {
        // we send a Message with our task and expect an answer Message from server
        Message response = tcpClient.sendAndReceive(
                new Message("update gunProvider", Factory.gunProviderToLine(gunProvider))
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
    public CompletableFuture<Set<GunProvider>> getAllGunProviders() {
        Message response;
        try {
            response = tcpClient.sendAndReceive(
                    new Message("get all gunProviders", "")
            );
        } catch (SocketException socketException){
            throw new RuntimeException();
        }

        String[] tokens = response.getBody().split(System.lineSeparator());

        // get the clients
        Set<GunProvider> gunProviders = Arrays.stream(tokens)
                .map(Factory::messageToGunProvider)
                .collect(Collectors.toSet());

        // return a CompletableFuture
        return CompletableFuture.supplyAsync(
                ()->gunProviders,
                executorService
        );
    }

    @Override
    public CompletableFuture<GunProvider> getGunProviderById(Long id) {
        Message response;
        try {
            response = tcpClient.sendAndReceive(
                    new Message("get gunProviders by id", "")
            );
        } catch (SocketException socketException){
            throw new RuntimeException();
        }

        String[] tokens = response.getBody().split(System.lineSeparator());

        // get the clients
        Set<GunProvider> gunProviders = Arrays.stream(tokens)
                .map(Factory::messageToGunProvider)
                .collect(Collectors.toSet());

        Optional<GunProvider> gunProviderWithId = gunProviders.stream()
                .filter(gunProvider -> gunProvider.getId().equals(id))
                .findAny();

        // return a CompletableFuture
        return CompletableFuture.supplyAsync(
                gunProviderWithId::get,
                executorService
        );
    }
}
