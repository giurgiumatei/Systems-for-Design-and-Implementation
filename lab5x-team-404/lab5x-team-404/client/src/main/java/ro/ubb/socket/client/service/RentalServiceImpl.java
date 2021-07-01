package ro.ubb.socket.client.service;

import domain.GunType;
import domain.Rental;
import domain.validators.SocketException;
import domain.validators.ValidatorException;
import message.Message;
import ro.ubb.socket.client.tcp.TcpClient;
import service.RentalService;
import utils.Factory;
import utils.Pair;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class RentalServiceImpl implements RentalService {
    ExecutorService executorService;
    TcpClient tcpClient;

    public RentalServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }



    @Override
    public CompletableFuture<Boolean> addRental(Rental rental) throws ValidatorException {
        // we send a Message with our task and expect an answer Message from server
        Message response = tcpClient.sendAndReceive(
                new Message("add rental", Factory.rentalToLine(rental))
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
    public CompletableFuture<Boolean> removeRental(Pair<Long, Long> id) throws ValidatorException {
        // we send a Message with our task and expect an answer Message from server
        Message response = tcpClient.sendAndReceive(
                new Message("remove rental", id.toString())
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
    public CompletableFuture<Boolean> updateRental(Rental rental) throws ValidatorException {
        // we send a Message with our task and expect an answer Message from server
        Message response = tcpClient.sendAndReceive(
                new Message("update rental", Factory.rentalToLine(rental))
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
    public CompletableFuture<Set<Rental>> getAllRentals() {
        Message response;
        try {
            response = tcpClient.sendAndReceive(
                    new Message("get all rentals", "")
            );
        } catch (SocketException socketException){
            throw new RuntimeException();
        }

        String[] tokens = response.getBody().split(System.lineSeparator());

        // get the clients
        Set<Rental> rentals = Arrays.stream(tokens)
                .map(Factory::messageToRental)
                .collect(Collectors.toSet());

        // return a CompletableFuture
        return CompletableFuture.supplyAsync(
                ()->rentals,
                executorService
        );
    }

    @Override
    public CompletableFuture<GunType> getMostRentedGunType() {
        Message response;
        try {
            response = tcpClient.sendAndReceive(
                    new Message("get most rented gunType", "")
            );
        } catch (SocketException socketException){
            throw new RuntimeException();
        }

        // we check for errors
        if (response.getHeader().equals("error")){
            throw new RuntimeException();
        }

        return CompletableFuture.supplyAsync(
                () -> Factory.messageToGunType(response.getBody()),
                executorService
        );
    }
}
