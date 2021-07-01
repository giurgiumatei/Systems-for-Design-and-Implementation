package ro.ubb.remoting.client.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.remoting.client.service.ClientsServiceClient;
import ro.ubb.remoting.client.service.GunProviderServiceClient;
import ro.ubb.remoting.client.service.GunTypeServiceClient;
import ro.ubb.remoting.client.service.RentalServiceClient;
import ro.ubb.remoting.common.domain.Client;
import ro.ubb.remoting.common.domain.GunProvider;

import ro.ubb.remoting.common.domain.Category;
import ro.ubb.remoting.common.domain.GunType;
import ro.ubb.remoting.common.domain.Rental;

import ro.ubb.remoting.common.service.ClientService;
import ro.ubb.remoting.common.service.GunProviderService;
import ro.ubb.remoting.common.service.GunTypeService;
import ro.ubb.remoting.common.service.RentalService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

@Component
public class ClientConsole {

    /*@Autowired
    private ClientService clientService;*/
    @Autowired
    private ClientsServiceClient clientService;
    @Autowired
    private GunProviderServiceClient gunProviderService;
    @Autowired
    private GunTypeServiceClient gunTypeService;
    @Autowired
    private RentalServiceClient rentalService;

    private void printAllRental() {
        rentalService.getAllRentals()
                .forEach(System.out::println);
    }

    private void printAllClients(){
        clientService.getAllClients()
                .forEach(System.out::println);
    }

    private void printAllGunProviders() {
        gunProviderService.getAllGunProviders()
                .forEach(System.out::println);
    }

    private void printAllGunTypes(){
        gunTypeService.getAllGunTypes().
                forEach(System.out::println);
    }

    /**
     * Method that runs when app starts. The choice should be in the choice range
     * and it's value will determine which method is called.
     */
    @SuppressWarnings("EndlessStream")
    public void runConsole(){
        // instead of using a while(true) loop, we generate an infinite sequence of 0s
        try{
            IntStream.generate(() -> 0)
                    .forEach( i -> {
                                switch (getChoice()) {
                                    case 0 -> throw new RuntimeException("end this pls");
                                    case 1 -> addClient();
                                    case 2 -> addGunProvider();
                                    case 3 -> addGunType();
                                    case 4 -> addRental();
                                    case 5 -> printAllClients();
                                    case 6 -> printAllGunProviders();
                                    case 7 -> printAllGunTypes();
                                    case 8 -> printAllRental();
                                    case 9 -> removeClient();
                                    case 10 -> removeGunProvider();
                                    case 11 -> removeGunType();
                                    case 12 -> removeRental();
                                    case 13 -> updateClient();
                                    case 14 -> updateGunProvider();
                                    case 15 -> updateGunType();
                                    case 16 -> updateRental();
                                    case 17 -> filterBornBefore();
                                    case 18 -> filterCategory();
                                    case 19 -> getMostRentedGunType();
                                }
                            }
                    );
        } catch (Exception ignored){
            System.out.println(ignored.getMessage());
        }
    }

    private void filterBornBefore() {
        clientService.getAllClientsBornBefore(readDate())
                .forEach(System.out::println
                );
    }

    private LocalDate readDate() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Date (d/m/yyyy): ");
            String date = bufferedReader.readLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            return LocalDate.parse(date, formatter);


        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        /*} catch(ValidatorException exception) {
            System.out.println(exception.getMessage());*/
        } catch (DateTimeParseException exception){
            System.out.println("The date should have the format d/M/yyyy, where \n " +
                    "d is from 1-31 and M is from 1-12");
        }
        return null;
    }

    private void updateGunProvider() {
        GunProvider newGunProvider = readGunProvider();
        if (newGunProvider == null || newGunProvider.getId() < 0){
            return;
        }
        try {
            gunProviderService.updateGunProvider(newGunProvider);
            System.out.println("Gun provider updated");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private void removeGunType() {
        GunType gunType = readGunType();
        if (gunType == null || gunType.getId() < 0){
            return;
        }
        try {
            gunTypeService.deleteGunType(gunType);
            System.out.println("Gun removed");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private void addRental() {
        Rental newRental = readRental();
        if (newRental == null || newRental.getId() < 0 ){
            return;
        }
        try {
            rentalService.addRental(newRental);
            System.out.println("Rental added");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private void removeGunProvider() {
        GunProvider newGunProvider = readGunProvider();
        if (newGunProvider == null || newGunProvider.getId() < 0){
            return;
        }
        try {
            gunProviderService.deleteGunProvider(newGunProvider);
            System.out.println("Gun provider removed");
        }catch (Exception exception){
//            System.out.println(exception.getMessage());
        }
    }

    private void addGunProvider() {
        GunProvider newGunProvider = readGunProvider();
        if (newGunProvider == null || newGunProvider.getId() < 0){
            return;
        }
        try {
            gunProviderService.addGunProvider(newGunProvider);
            System.out.println("Provider added");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Helper method to handle reading a rental from keyboard.
     * @return read rental
     */
    private Rental readRental() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Read rental \nId: ");
            long id = Long.parseLong(bufferedReader.readLine());
            System.out.println("Client Id: ");
            long clientId = Long.parseLong(bufferedReader.readLine());
            System.out.println("GunType Id: ");
            long gunTypeId = Long.parseLong(bufferedReader.readLine());
            System.out.println("Price: ");
            int price = Integer.parseInt(bufferedReader.readLine());

            return new Rental(id,clientId,gunTypeId,price);
        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        }
        return null;
    }

    private void addGunType() {
        GunType newGunType = readGunType();
        if (newGunType == null || newGunType.getId() < 0){
            return;
        }
        try {
            gunTypeService.addGunType(newGunType);
            System.out.println("Gun added");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private GunProvider readGunProvider() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Read gun provider \nId: ");
            Long id = Long.valueOf(bufferedReader.readLine());
            System.out.println("Name: ");
            String name = bufferedReader.readLine();
            System.out.println("Speciality: ");
            String speciality = bufferedReader.readLine();
            System.out.println("Reputation: ");
            int reputation = Integer.parseInt(bufferedReader.readLine());

            return new GunProvider(id, name, speciality, reputation);
        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        /*} catch(ValidatorException exception) {
            System.out.println(exception.getMessage());*/
        }
        return null;
    }

    private GunType readGunType() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Read gun type \nId: ");
            Long id = Long.valueOf(bufferedReader.readLine());
            System.out.println("Name: ");
            String name = bufferedReader.readLine();
            System.out.println("Category: ");
            String category = bufferedReader.readLine();
            System.out.println("Provider id: ");
            int provider = Integer.parseInt(bufferedReader.readLine());


            return new GunType(id,name, Category.valueOf(category),provider);

        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        /*} catch(ValidatorException exception) {
            System.out.println(exception.getMessage());*/
        }
        return null;
    }

    private void updateClient() {
        Client newClient = readClient();
        if (newClient == null || newClient.getId() < 0){
            return;
        }
        try {
            clientService.updateClient(newClient);
            System.out.println("Client updated");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    /**
     * Method to handle removing rentals.
     */
    private void removeRental() {
        Rental newRental = readRental();
        if (newRental == null || newRental.getId() < 0){
            return;
        }
        try {
            rentalService.deleteRental(newRental);
            System.out.println("Rental removed");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private void removeClient() {
        Client newClient = readClient();
        if (newClient == null || newClient.getId() < 0){
            return;
        }
        try {
            clientService.deleteClient(newClient);
            System.out.println("Client removed");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }


    private void addClient() {
        Client newClient = readClient();
        if (newClient == null || newClient.getId() < 0){
            return;
        }
        try {
            clientService.addClient(newClient);
            System.out.println("Client added");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Method to handle updating gunTypes.
     */
    private void updateGunType() {
        GunType newGunType = readGunType();
        if (newGunType == null || newGunType.getId() < 0){
            return;
        }
        try {
            gunTypeService.updateGunType(newGunType);
            System.out.println("Gun updated");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }


    private Client readClient() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Read client \nId: ");
            long id = Long.parseLong(bufferedReader.readLine());
            System.out.println("Name: ");
            String name = bufferedReader.readLine();
            System.out.println("Date (d/m/yyyy): ");
            String date = bufferedReader.readLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate localDate = LocalDate.parse(date, formatter);

            return new Client(id, name, localDate);
        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        /*} catch (ValidatorException exception) {
            System.out.println(exception.getMessage());*/
        } catch (DateTimeParseException exception) {
            System.out.println("The date should have the format d/M/yyyy, where \n " +
                    "d is from 1-31 and M is from 1-12");
        }
        return null;
    }
    /**
     * Method to handle adding rentals.
     */
    private void updateRental(){
        Rental newRental = readRentalForUpdate();
        if (newRental == null || newRental.getId() < 0){
            return;
        }
        try {
            rentalService.updateRental(newRental);
            System.out.println("Rental updated");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private Rental readRentalForUpdate() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Read rental \nId: ");
            long id = Long.parseLong(bufferedReader.readLine());
            System.out.println("Price: ");
            int price = Integer.parseInt(bufferedReader.readLine());

            return new Rental(id,price);
        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        }
        return null;
    }

    private void filterCategory(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Give category:");

        Category category = null;
        try {
            category = Category.valueOf(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        gunTypeService.filterGunTypesByCategory(category)
                .forEach(System.out::println);
    }

    private void getMostRentedGunType() {
        GunType gunType = rentalService.getMostRentedGunType();
        if (gunType == null)
            System.out.println("No gun types rented");
        else
            System.out.println(gunType);
    }

    private static final String menu = """
            1.Add client
            2.Add gun provider
            3.Add gun type
            4.Add rental
            5.Print all clients
            6.Print all providers
            7.Print all gun types
            8.Print all rentals
            9.Delete client
            10.Delete gun provider
            11.Delete gun type
            12.Delete rental
            13.Update client
            14.Update gun provider
            15.Update gun type
            16.Update rental
            17.Filter born before
            18.Filter gun types by category
            19.Get most rented gun type
            0.Exit""";

    private static final Set<Integer> choiceRange = new HashSet<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19));

    /**
     * Helper method to handle reading a choice from keyboard.
     * @return read choice
     */
    private int getChoice(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(menu);
        System.out.print(">>");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int choice = Integer.parseInt(bufferedReader.readLine());

            if (!choiceRange.contains(choice)){
                throw new RuntimeException("choice not in range!\n");
            }

            return choice;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

}