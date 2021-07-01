package ro.ubb.catalog.client.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import ro.ubb.catalog.core.model.Category;
import ro.ubb.catalog.core.model.GunType;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;
import ro.ubb.catalog.web.dto.*;

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

    private static final String clientsUrl = "http://localhost:8080/api/clients";
    private static final String gunTypesUrl = "http://localhost:8080/api/gun-types";
    private static final String gunProvidersUrl = "http://localhost:8080/api/gun-providers";
    private static final String rentalsUrl = "http://localhost:8080/api/rentals";
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
            20.Get gun providers sorted by name
            21. Get gun providers with two filters
            0.Exit""";
    private static final Set<Integer> choiceRange = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21));
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Method that runs when app starts. The choice should be in the choice range
     * and it's value will determine which method is called.
     */
    @SuppressWarnings("EndlessStream")
    public void runConsole() {
        // instead of using a while(true) loop, we generate an infinite sequence of 0s
        try {
            IntStream.generate(() -> 0)
                    .forEach(i -> {
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
                                    case 20 -> getGunProvidersSortedByName();
                                    case 21 -> filterBySpecialityAndReputationGreater();
                                }
                            }
                    );
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }
    }

    private int getChoice() {
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

            if (!choiceRange.contains(choice)) {
                throw new RuntimeException("choice not in range!\n");
            }

            return choice;

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return 0;
    }


    private long readId() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("ID: ");
        long id = -1;
        try {
            id = Long.parseLong(bufferedReader.readLine());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return id;
    }

    private LocalDate readDate() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Date (d-M-yyyy): ");
            String date = bufferedReader.readLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
            return LocalDate.parse(date, formatter);

        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        } catch (ValidatorException exception) {
            System.out.println(exception.getMessage());
        } catch (DateTimeParseException exception) {
            System.out.println("The date should have the format d-M-yyyy, where \n " +
                    "d is from 1-31 and M is from 1-12");
        }
        return null;
    }

    private ClientDto readClient() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Name: ");
            String name = bufferedReader.readLine();
            System.out.println("Date (d-M-yyyy): ");
            String date = bufferedReader.readLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
            LocalDate localDate = LocalDate.parse(date, formatter);
            ClientDto clientDto = new ClientDto(name, localDate);

            return clientDto;
        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        } catch (ValidatorException exception) {
            System.out.println(exception.getMessage());
        } catch (DateTimeParseException exception) {
            System.out.println("The date should have the format d-M-yyyy, where \n " +
                    "d is from 1-31 and M is from 1-12");
        }
        return null;
    }

    private GunTypeDto readGunType() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("--Read gun type--");
            System.out.println("Name: ");
            String name = bufferedReader.readLine();
            System.out.println("Category: ");
            String category = bufferedReader.readLine();
            System.out.println("Provider id: ");
            int provider = Integer.parseInt(bufferedReader.readLine());


            return new GunTypeDto(name, Category.valueOf(category), provider);

        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        } catch (ValidatorException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    private GunProviderDto readGunProvider() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Read gun provider: \n ");
            System.out.println("Name: ");
            String name = bufferedReader.readLine();
            System.out.println("Speciality: ");
            String speciality = bufferedReader.readLine();
            System.out.println("Reputation: ");
            int reputation = Integer.parseInt(bufferedReader.readLine());

            return new GunProviderDto(name, speciality, reputation);
        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        } catch (ValidatorException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    private RentalDto readRental() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Read rental: \n");
            System.out.println("Client Id: ");
            long clientId = Long.parseLong(bufferedReader.readLine());
            System.out.println("GunType Id: ");
            long gunTypeId = Long.parseLong(bufferedReader.readLine());
            System.out.println("Price: ");
            int price = Integer.parseInt(bufferedReader.readLine());

            return new RentalDto(price, clientId, gunTypeId);
        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        }
        return null;
    }

    private void addClient() {
        ClientDto client = readClient();
        try {
            restTemplate.postForObject(clientsUrl, client, GunTypeDto.class);
            System.out.println("Client added successfully!");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot add this client!");
        }
    }

    private void addGunType() {
        GunTypeDto gunType = readGunType();
        try {
            restTemplate.postForObject(gunTypesUrl, gunType, GunTypeDto.class);
            System.out.println("Gun Type added successfully!");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot add this gun type!");
        }
    }

    private void addGunProvider() {
        GunProviderDto gunProvider = readGunProvider();
        try {
            restTemplate.postForObject(gunProvidersUrl, gunProvider, GunProviderDto.class);
            System.out.println("Gun Provider added successfully!");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot add this gun provider!");
        }
    }

    private void addRental() {
        RentalDto rental = readRental();
        try {
            restTemplate.postForObject(rentalsUrl, rental, RentalsDto.class);
            System.out.println("Rental added successfully!");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot add this rental!");
        }
    }

    private void removeClient() {
        long id = readId();
        try {
            restTemplate.delete(clientsUrl + "/{id}", id);
            System.out.println("Client deleted successfully!");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot remove this client!");
        }
    }

    private void removeGunType() {
        long id = readId();
        try {
            restTemplate.delete(gunTypesUrl + "/{id}", id);
            System.out.println("Gun Type deleted successfully!");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot remove this gun type!");
        }
    }

    private void removeGunProvider() {
        long id = readId();
        try {
            restTemplate.delete(gunProvidersUrl + "/{id}", id);
            System.out.println("Gun Provider deleted successfully!");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot remove this gun provider!");
        }
    }

    private void removeRental() {
        long id = readId();
        try {
            restTemplate.delete(rentalsUrl + "/{id}", id);
            System.out.println("Rental deleted successfully!");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot remove this rental!");
        }
    }

    private void updateClient() {
        long id = readId();
        ClientDto client = readClient();
        if (client == null) {
            return;
        }
        try {
            restTemplate.put(clientsUrl + "/{id}", client, id);
            System.out.println("Client updated successfully!");

        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update this client!");
        }
    }

    private void updateGunProvider() {
        long id = readId();
        GunProviderDto gunProviderDto = readGunProvider();
        if (gunProviderDto == null) {
            return;
        }
        try {
            restTemplate.put(gunProvidersUrl + "/{id}", gunProviderDto, id);
            System.out.println("Gun provider updated successfully!");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update this gun provider!");
        }
    }

    private void updateGunType() {
        long id = readId();
        GunTypeDto gunTypeDto = readGunType();
        if (gunTypeDto == null) {
            return;
        }
        try {
            restTemplate.put(gunTypesUrl + "/{id}", gunTypeDto, id);
            System.out.println("Gun type updated successfully!");

        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update this gun type!");
        }
    }

    private void updateRental() {
        long id = readId();
        RentalDto rentalDto = readRental();
        if (rentalDto == null) {
            return;
        }
        try {
            restTemplate.put(rentalsUrl + "/{id}", rentalDto, id);
            System.out.println("Rental updated successfully!");

        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update this rental!");
        }
    }

    private void printAllClients() {
        ClientsDto clients = restTemplate.getForObject(clientsUrl, ClientsDto.class);
        assert clients != null;
        clients.getClients().forEach(System.out::println);
    }

    private void printAllGunTypes() {
        GunTypesDto gunTypes = restTemplate.getForObject(gunTypesUrl, GunTypesDto.class);
        assert gunTypes != null;
        gunTypes.getGunTypes().forEach(System.out::println);
    }

    private void printAllGunProviders() {
        GunProvidersDto gunProviders = restTemplate.getForObject(gunProvidersUrl, GunProvidersDto.class);
        assert gunProviders != null;
        gunProviders.getGunProviders().forEach(System.out::println);
    }

    private void printAllRental() {
        RentalsDto rentals = restTemplate.getForObject(rentalsUrl, RentalsDto.class);
        assert rentals != null;
        rentals.getRentals().forEach(System.out::println);
    }

    private void filterBornBefore() {
        ///clients/filter/{date}
        LocalDate date = readDate();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("d-M-yyyy");
        String string = date.format(pattern);
        System.out.println(string);

        ClientsDto clientsDto = restTemplate.getForObject(
                clientsUrl + "/filter/{string}",
                ClientsDto.class,
                string);
        if (clientsDto == null)
            System.out.println("No such clients!");
        else
            clientsDto.getClients().forEach(System.out::println);
    }

    private void filterCategory() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Give category:");

        Category category = null;
        try {
            category = Category.valueOf(bufferedReader.readLine());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        assert category != null;
        GunTypesDto gunTypes = restTemplate.getForObject(gunTypesUrl + "/filter/{category}", GunTypesDto.class, category);
        assert gunTypes != null;
        gunTypes.getGunTypes().forEach(System.out::println);

    }

    private void getMostRentedGunType() {
        GunTypeDto gunType = restTemplate.getForObject(rentalsUrl + "/most", GunTypeDto.class);
        if (gunType == null)
            System.out.println("No gun types rented");
        else
            System.out.println(gunType);
    }

    private void getGunProvidersSortedByName() {
        GunProvidersDto gunProvidersDto = restTemplate.getForObject(gunProvidersUrl + "/sort/name", GunProvidersDto.class);
        if (gunProvidersDto == null)
            System.out.println("No gun providers!");
        else
            gunProvidersDto.getGunProviders().forEach(System.out::println);
    }

    private void filterBySpecialityAndReputationGreater() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Give speciality:");
        String speciality = null;
        try {
            speciality = bufferedReader.readLine();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println("Give reputation:");
        int reputation = 0;
        try {
            reputation = Integer.parseInt(bufferedReader.readLine());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        GunProvidersDto gunProvidersDto = restTemplate.getForObject(gunProvidersUrl + "/filter?speciality={speciality}&reputation={reputation}", GunProvidersDto.class, speciality, reputation);
        if (gunProvidersDto == null)
            System.out.println("No gun providers!");
        else
            gunProvidersDto.getGunProviders().forEach(System.out::println);
    }


}
