package ro.ubb.socket.client.service;

import domain.Category;
import domain.GunType;
import domain.validators.SocketException;
import domain.validators.ValidatorException;
import message.Message;
import ro.ubb.socket.client.tcp.TcpClient;
import service.GunTypeService;
import utils.Factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class GunTypeServiceImpl implements GunTypeService {
    ExecutorService executorService;
    TcpClient tcpClient;

    public GunTypeServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public CompletableFuture<Boolean> addGunType(GunType gunType) throws ValidatorException {
        // we send a Message with our task and expect an answer Message from server
        Message response = tcpClient.sendAndReceive(
                new Message("add gunType", Factory.gunTypeToLine(gunType))
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
    public CompletableFuture<Boolean> removeGunType(Long id) throws ValidatorException {
        // we send a Message with our task and expect an answer Message from server
        Message response = tcpClient.sendAndReceive(
                new Message("remove gunType", Long.toString(id))
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
    public CompletableFuture<Boolean> updateGunType(GunType gunType) throws ValidatorException {
        // we send a Message with our task and expect an answer Message from server
        Message response = tcpClient.sendAndReceive(
                new Message("update gunType", Factory.gunTypeToLine(gunType))
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
    public CompletableFuture<Set<GunType>> getAllGunTypes() {
        Message response;
        try {
            response = tcpClient.sendAndReceive(
                    new Message("get all gunTypes", "")
            );
        } catch (SocketException socketException){
            throw new RuntimeException();
        }

        String[] tokens = response.getBody().split(System.lineSeparator());

        // get the clients
        Set<GunType> gunTypes = Arrays.stream(tokens)
                .map(Factory::messageToGunType)
                .collect(Collectors.toSet());

        // return a CompletableFuture
        return CompletableFuture.supplyAsync(
                ()->gunTypes,
                executorService
        );
    }

    @Override
    public CompletableFuture<List<GunType>> filterGunTypesByCategory(Category category) {
        Message response;
        try {
            response = tcpClient.sendAndReceive(
                    new Message("filter gunTypes by category", category.toString())
            );
        } catch (SocketException socketException){
            throw new RuntimeException();
        }

        String[] tokens = response.getBody().split(System.lineSeparator());

        // get the clients
        List<GunType> gunTypes = Arrays.stream(tokens)
                .map(Factory::messageToGunType)
                .filter(gunType -> gunType.getCategory().equals(category))
                .collect(Collectors.toList());

        // return a CompletableFuture
        return CompletableFuture.supplyAsync(
                ()->gunTypes,
                executorService
        );
    }

    @Override
    public CompletableFuture<GunType> getGunTypeById(Long id) {
        Message response;
        try {
            response = tcpClient.sendAndReceive(
                    new Message("get gunType by id", "")
            );
        } catch (SocketException socketException){
            throw new RuntimeException();
        }

        String[] tokens = response.getBody().split(System.lineSeparator());

        // get the clients
        Set<GunType> gunTypes = Arrays.stream(tokens)
                .map(Factory::messageToGunType)
                .collect(Collectors.toSet());

        Optional<GunType> gunTypeWithId = gunTypes.stream()
                .filter(gunType -> gunType.getId().equals(id))
                .findAny();

        // return a CompletableFuture
        return CompletableFuture.supplyAsync(
                gunTypeWithId::get,
                executorService
        );
    }
}
