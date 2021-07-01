package gunshop.ui;

import gunshop.domain.*;
import gunshop.domain.validators.ValidatorException;
import gunshop.service.ClientService;
import gunshop.service.GunProviderService;
import gunshop.service.GunTypeService;
import gunshop.service.RentalService;
import utils.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.IntStream;


public class Console {
    private final ClientService clientService;
    private final GunProviderService gunProviderService;
    private final GunTypeService gunTypeService;
    private final RentalService rentalService;

    public Console(ClientService clientService, GunProviderService gunProviderService, GunTypeService gunTypeService, RentalService rentalService) {
        this.clientService = clientService;
        this.gunProviderService = gunProviderService;
        this.gunTypeService = gunTypeService;
        this.rentalService = rentalService;
    }

    /**
     * Method to print all rentals.
     */
    private void printAllRental(){
        Set<Rental> rentals = rentalService.getAllRentals();
        rentals.forEach(System.out::println);
    }

    /**
     * Method to print all gunTypes.
     */
    private void printAllGunTypes(){
        Set<GunType> gunTypes = gunTypeService.getAllGunTypes();
        gunTypes.forEach(System.out::println);
    }

    /**
     * Method to print all clients.
     */
    private void printAllClients(){
        Set<Client> clients = clientService.getAllClients();
        clients.forEach(System.out::println);
    }

    /**
     * Method to handle adding rentals.
     */
    private void addRental(){
        Rental newRental = readRental();
        if (newRental == null || newRental.getId().getFirst() < 0 || newRental.getId().getSecond() < 0){
            return;
        }
        try {
            rentalService.addRental(newRental);
            System.out.println("Rental added");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Method to handle adding gunTypes.
     */
    private void addGunType() {
        GunType newGunType = readGunType();
        //

        //
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

    /**
     * Method to handle adding clients.
     */
    private void addClient(){
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
     * Method to handle removing clients.
     */
    private void removeClient(){
        Long givenId = readClientOrGunTypeId();
        if (givenId < 0){
            return;
        }
        try {
            clientService.removeClient(givenId);
            System.out.println("Client removed");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Method to handle removing gunTypes.
     */
    private void removeGunType() {
        Long givenId = readClientOrGunTypeId();
        if (givenId < 0){
            return;
        }
        try {
            gunTypeService.removeGunType(givenId);
            System.out.println("Gun removed");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Method to handle removing rentals.
     */
    private void removeRental() {
        Pair<Long,Long> givenId = readRentalId();
        if (givenId.getFirst() < 0 || givenId.getSecond() < 0){
            return;
        }
        try {
            rentalService.removeRental(givenId);
            System.out.println("Rental removed");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Method to handle updating clients.
     */
    private void updateClient(){
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

    /**
     * Method to handle adding rentals.
     */
    private void updateRental(){
        Rental newRental = readRental();
        if (newRental == null || newRental.getId().getFirst() < 0 || newRental.getId().getSecond() < 0){
            return;
        }
        try {
            rentalService.updateRental(newRental);
            System.out.println("Rental updated");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
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
                        case 18 -> addListOfClients();
                        case 19 -> filterCategory();
                        case 20 -> getMostRentedGunType();
                    }
                            }
                );
        }catch (RuntimeException ignored){
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private void getMostRentedGunType() {
        var gunOptional = rentalService.getMostRentedGunType();
        if (gunOptional.isEmpty())
            System.out.println("No gun types rented");
        else
            System.out.println(gunOptional.get());
    }

    private void addListOfClients()
    {
        List<Client> clients = new ArrayList<>();

        System.out.println("Give the number of clients to be added: \n");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int numberOfClients = Integer.parseInt(bufferedReader.readLine());

            for(int i=0;i<numberOfClients;i++)
            {
                Client newClient = readClient();
                clients.add(newClient);
            }

            this.clientService.addListOfClients(clients);

        } catch (IOException e) {
            e.printStackTrace();
        }


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

    private void removeGunProvider() {
        Long givenId = readClientOrGunTypeId();
        if (givenId < 0){
            return;
        }
        try {
            gunProviderService.removeGunProvider(givenId);
            System.out.println("Gun provider removed");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private void printAllGunProviders() {
        Set<GunProvider> gunProviders  = gunProviderService.getAllGunProviders();
        gunProviders.forEach(System.out::println);
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
        } catch(ValidatorException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    private void filterBornBefore() {
        clientService.getAllClientsBornBefore(readDate())
            .forEach(System.out::println);
    }

    private void filterCategory(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Give category:");

        try {
            Category category = Category.valueOf(bufferedReader.readLine());
            gunTypeService.filterGunTypesByCategory(category).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Helper method to handle reading a rental from keyboard.
     * @return read rental
     */
    private Rental readRental() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Read rental \nClient Id: ");
            long clientId = Long.parseLong(bufferedReader.readLine());
            System.out.println("GunType Id: ");
            long gunTypeId = Long.parseLong(bufferedReader.readLine());
            System.out.println("Price: ");
            int price = Integer.parseInt(bufferedReader.readLine());

            return new Rental(new Pair<>(clientId,gunTypeId),price);
        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        } catch(ValidatorException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    /**
     * Helper method to handle reading a gunType from keyboard.
     * @return read gunType
     */
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
        } catch(ValidatorException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    /**
     * Helper method to handle reading a client from keyboard.
     * @return read client
     */
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

            return new Client(id,name,localDate);
        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        } catch(ValidatorException exception) {
            System.out.println(exception.getMessage());
        } catch (DateTimeParseException exception){
            System.out.println("The date should have the format d/M/yyyy, where \n " +
                    "d is from 1-31 and M is from 1-12");
        }
        return null;
    }


    private LocalDate readDate(){
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
        } catch(ValidatorException exception) {
            System.out.println(exception.getMessage());
        } catch (DateTimeParseException exception){
            System.out.println("The date should have the format d/M/yyyy, where \n " +
                    "d is from 1-31 and M is from 1-12");
        }
        return null;
    }

    /**
     * Helper method to handle reading a client or gunType id from keyboard.
     * @return read client or gunType id
     */
    private Long readClientOrGunTypeId() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Read \nId: ");
            return Long.parseLong(bufferedReader.readLine());

        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        } catch(ValidatorException exception) {
            System.out.println(exception.getMessage());
        } catch (DateTimeParseException exception){
            System.out.println("The date should have the format d/M/yyyy, where \n " +
                    "d is from 1-31 and M is from 1-12");
        }
        return null;
    }

    /**
     * Helper method to handle reading a rental id from keyboard.
     * @return read rental id.
     */
    private Pair<Long,Long> readRentalId() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Read \nClient Id: ");
            long clientId =  Long.parseLong(bufferedReader.readLine());
            System.out.println("Read \nGunType Id: ");
            long gunTypeId =  Long.parseLong(bufferedReader.readLine());

            return new Pair<>(clientId, gunTypeId);

        } catch (NumberFormatException exception) {
            System.out.println("Id is not a number!");
        } catch (IOException exception) {
            System.out.println("Input error!");
        } catch(ValidatorException exception) {
            System.out.println(exception.getMessage());
        } catch (DateTimeParseException exception){
            System.out.println("The date should have the format d/M/yyyy, where \n " +
                    "d is from 1-31 and M is from 1-12");
        }
        return null;
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
            18.Add list of clients
            19.Filter gun types by category
            20.Get most rented gun type
            0.Exit""";

    private static final Set<Integer> choiceRange = new HashSet<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));

    /**
     * Helper method to handle reading a choice from keyboard.
     * @return read choice
     */
    private int getChoice(){
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
