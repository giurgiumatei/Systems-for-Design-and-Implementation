package gunshop;

import gunshop.domain.GunProvider;
import gunshop.domain.validators.*;
import gunshop.repository.*;
import gunshop.domain.Client;
import gunshop.domain.GunType;
import gunshop.domain.Rental;
import gunshop.service.ClientService;
import gunshop.service.GunProviderService;
import gunshop.service.GunTypeService;
import gunshop.service.RentalService;
import gunshop.ui.Console;
import utils.FilesCreationStartup;
import utils.Pair;

//only for attendance Giurgiu Mate-Alexandru

// TODO
// !!! -Dusername=yourusername -Dpassword=youruserpass as VM options for run config

public class Main {
    private static final String databaseName = "catalogGunShop";
    private static final String url = "jdbc:postgresql://localhost:5432/catalogGunShop";
    // !!don't forget to add run configurations with your password and username
    private static final String user = System.getProperty("username");
    private static final String password = System.getProperty("password");

    public static void main(String[] args) {
        FilesCreationStartup.createDirectories();
        FilesCreationStartup.createTextFile();
        FilesCreationStartup.createXMLFile();

        /*RepositoryInterface<Long,Client> clientInMemoryRepository = new InMemoryRepository<>(clientValidator);
        RepositoryInterface<Long,GunType> gunTypeInMemoryRepository = new InMemoryRepository<>(gunTypeValidator);
        RepositoryInterface<Pair<Long,Long>,Rental> rentalInMemoryRepository = new InMemoryRepository<>(rentalValidator);*/

        /*RepositoryInterface<Long,Client> clientsRepository = new FileRepository<>(clientValidator, "Files/text/clients.txt", Client.class);
        RepositoryInterface<Long,GunType> gunTypeRepository = new FileRepository<>(gunTypeValidator, "Files/text/guntypes.txt", GunType.class);
        RepositoryInterface<Pair<Long,Long>,Rental> rentalRepository = new FileRepository<>(rentalValidator, "Files/text/rentals.txt", Rental.class);
        */

        Validator<Client> clientValidator = new ClientValidator();
        Validator<GunType> gunTypeValidator = new GunTypeValidator();
        Validator<Rental> rentalValidator = new RentalValidator();
        Validator<GunProvider> gunProviderValidator = new GunProviderValidator();


//        RepositoryInterface<Long,Client> clientInMemoryRepository = new InMemoryRepository<>(clientValidator);
//        RepositoryInterface<Long,GunType> gunTypeInMemoryRepository = new InMemoryRepository<>(gunTypeValidator);
        RepositoryInterface<Pair<Long,Long>,Rental> rentalDatabaseRepository = new DatabaseRentalRepository(rentalValidator,databaseName,url,user,password);

//        RepositoryInterface<Long,Client> clientXMLRepository = new XMLRepository<>(clientValidator,"Files/xml/clients.xml", Client.class);
//        RepositoryInterface<Long,GunType> gunTypeXMLRepository = new XMLRepository<>(gunTypeValidator,"Files/xml/guntypes.xml", GunType.class);
//        RepositoryInterface<Pair<Long,Long>,Rental> rentalsXMLRepository = new XMLRepository<>(rentalValidator,"Files/xml/rentals.xml", Rental.class);

        RepositoryInterface<Long,Client> clientDatabaseRepository = new DatabaseRepository<>(clientValidator,databaseName,Client.class,Long.class,url,user,password);
        RepositoryInterface<Long, GunProvider> gunProvidersDatabaseRepository = new DatabaseRepository<>(gunProviderValidator,databaseName,GunProvider.class,Long.class,url,user,password);

        RepositoryInterface<Long,GunType> gunTypeDatabaseRepository = new DatabaseRepository<>(gunTypeValidator,databaseName,GunType.class,Long.class,url,user,password);
//        RepositoryInterface<Pair<Long,Long>,Rental> rentalRepositoryInterface = new DatabaseRepository<>(rentalValidator,databaseName,Rental.class,new Pair<Long,Long>(1L,1L).getClass(),url,user,password);

        ClientService clientService = new ClientService(clientDatabaseRepository);
        GunProviderService gunProviderService = new GunProviderService(gunProvidersDatabaseRepository);
        GunTypeService gunTypeService = new GunTypeService(gunTypeDatabaseRepository);
        RentalService rentalService = new RentalService(rentalDatabaseRepository, clientDatabaseRepository, gunTypeDatabaseRepository);

        Console console = new Console(clientService, gunProviderService, gunTypeService, rentalService);

        try {
            console.runConsole();
        }catch (Exception exception){
            System.out.println("exception");

        }



    }
}
