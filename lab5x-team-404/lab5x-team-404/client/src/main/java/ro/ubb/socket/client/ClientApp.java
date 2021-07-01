package ro.ubb.socket.client;

import ro.ubb.socket.client.service.ClientServiceImpl;
import ro.ubb.socket.client.service.GunProviderServiceImpl;
import ro.ubb.socket.client.service.GunTypeServiceImpl;
import ro.ubb.socket.client.service.RentalServiceImpl;
import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.client.ui.ClientConsole;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// this comment is only for attendance

public class ClientApp {
    public static void main(String[] args) {
        System.out.println("ClientApp started the process");

        ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        TcpClient tcpClient = new TcpClient(executorService);
        ClientServiceImpl clientService = new ClientServiceImpl(executorService, tcpClient);
        GunTypeServiceImpl gunTypeService = new GunTypeServiceImpl(executorService, tcpClient);
        GunProviderServiceImpl gunProviderService = new GunProviderServiceImpl(executorService, tcpClient);
        RentalServiceImpl rentalService = new RentalServiceImpl(executorService, tcpClient);

        ExecutorService executorServiceUi = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        ClientConsole clientConsole = new ClientConsole(
                clientService,gunProviderService,gunTypeService,rentalService
        );
        clientConsole.runConsole();


        executorService.shutdown();
        executorServiceUi.shutdown();
        System.out.println("Client ended tasks.");
    }
}
