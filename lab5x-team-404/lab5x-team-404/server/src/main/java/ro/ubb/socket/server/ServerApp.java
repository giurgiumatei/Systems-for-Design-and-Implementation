package ro.ubb.socket.server;

import domain.Client;
import domain.GunProvider;
import domain.GunType;
import domain.Rental;
import domain.validators.ClientValidator;
import domain.validators.GunProviderValidator;
import domain.validators.GunTypeValidator;
import domain.validators.RentalValidator;
import repository.RepositoryInterface;
import ro.ubb.socket.server.database.ClientDbRepository;
import ro.ubb.socket.server.database.GunProviderDbRepository;
import ro.ubb.socket.server.database.GunTypeDbRepository;
import ro.ubb.socket.server.database.RentalDbRepository;
import ro.ubb.socket.server.service.ClientServiceImpl;
import ro.ubb.socket.server.service.GunProviderServiceImpl;
import ro.ubb.socket.server.service.GunTypeServiceImpl;
import ro.ubb.socket.server.service.RentalServiceImpl;
import ro.ubb.socket.server.tcp.MessageHandler;
import ro.ubb.socket.server.tcp.TcpServer;
import service.ClientService;
import service.GunProviderService;
import service.GunTypeService;
import service.RentalService;
import utils.Pair;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {

    private static final String databaseName = "catalogGunShop";
    private static final String url = "jdbc:postgresql://localhost:5432/catalogGunShop";
    // !!don't forget to add run configurations with your password and username
    private static final String user = System.getProperty("username");
    private static final String password = System.getProperty("password");

    // !!! -Dusername=yourusername -Dpassword=youruserpass as VM options for run config for database
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        RepositoryInterface<Long, Client> clientRepository = new ClientDbRepository(url, user, password, new ClientValidator());
        RepositoryInterface<Long, GunProvider> gunProviderRepository = new GunProviderDbRepository(url, user, password, new GunProviderValidator());
        RepositoryInterface<Long, GunType> gunTypeRepository = new GunTypeDbRepository(url, user, password, new GunTypeValidator());
        RepositoryInterface<Pair<Long, Long>, Rental> rentalRepository = new RentalDbRepository(url, user, password, new RentalValidator());

        ClientService clientService = new ClientServiceImpl(clientRepository);
        GunProviderService gunProviderService = new GunProviderServiceImpl(gunProviderRepository);
        GunTypeService gunTypeService = new GunTypeServiceImpl(gunTypeRepository);
        RentalService rentalService = new RentalServiceImpl(rentalRepository, clientRepository, gunTypeRepository);

        MessageHandler messageHandler = new MessageHandler(clientService, gunProviderService, gunTypeService, rentalService);
        TcpServer tcpServer = new TcpServer(executorService, 1234);

        tcpServer.addHandler("add client", messageHandler::addClient);
        tcpServer.addHandler("remove client", messageHandler::removeClient);
        tcpServer.addHandler("update client", messageHandler::updateClient);
        tcpServer.addHandler("get all clients", messageHandler::getAllClients);

        tcpServer.addHandler("add gunProvider", messageHandler::addGunProvider);
        tcpServer.addHandler("remove gunProvider", messageHandler::removeGunProvider);
        tcpServer.addHandler("update gunProvider", messageHandler::updateGunProvider);
        tcpServer.addHandler("get all gunProviders", messageHandler::getAllGunProviders);

        tcpServer.addHandler("add gunType", messageHandler::addGunType);
        tcpServer.addHandler("remove gunType", messageHandler::removeGunType);
        tcpServer.addHandler("update gunType", messageHandler::updateGunType);
        tcpServer.addHandler("get all gunTypes", messageHandler::getAllGunTypes);

        tcpServer.addHandler("add rental", messageHandler::addRental);
        tcpServer.addHandler("remove rental", messageHandler::removeRental);
        tcpServer.addHandler("update rental", messageHandler::updateRental);
        tcpServer.addHandler("get all rentals", messageHandler::getAllRentals);

        tcpServer.addHandler("get all clients born before", messageHandler::getAllClientsBornBefore);
        tcpServer.addHandler("filter gunTypes by category", messageHandler::getGunsFilteredByCategory);
        tcpServer.addHandler("get most rented gunType", messageHandler::getMostRentedGunType);


        System.out.println("Server started");
        tcpServer.startServer();
        executorService.shutdown();
        System.out.println("bye server!!!");
    }
}
