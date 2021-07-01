package gunshop.repository;

import gunshop.domain.*;
import gunshop.domain.validators.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileRepositoryTest
{
    private static Validator<GunProvider> providerValidator;
    private static RepositoryInterface<Long, GunProvider> providerRepository;
    private static Validator<GunType> gunTypeValidator;
    private static RepositoryInterface<Long, GunType> gunTypeRepository;
    private static Validator<Client> clientValidator;
    private static FileRepository<Long, Client> clientRepository;
    private static Validator<Rental> rentalValidator;
    private static RepositoryInterface<Pair<Long,Long>,Rental> rentalRepository;

    @BeforeEach
    public void setUp()
    {
        Path clientsPath = Paths.get("src\\test\\java\\gunshop\\files\\test-clients.txt");
        try {
            Files.write(clientsPath,"1,Matei,30/09/2000".getBytes());
            Files.write(clientsPath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
            Files.write(clientsPath,"2,Andrei,21/05/2000".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path gunTypePath = Paths.get("src\\test\\java\\gunshop\\files\\test-gunTypes.txt");
        try {
            Files.write(gunTypePath,"1,Gun1,LONG_GUN,1".getBytes());
            Files.write(gunTypePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
            Files.write(gunTypePath,"2,Gun2,PISTOL,2".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path rentalsPath = Paths.get("src\\test\\java\\gunshop\\files\\test-rentals.txt");
        try {
            Files.write(rentalsPath,"1,1,10".getBytes());
            Files.write(rentalsPath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
            Files.write(rentalsPath,"2,2,20".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path providersPath = Paths.get("src\\test\\java\\gunshop\\files\\test-providers.txt");
        try {
            Files.write(providersPath,"1,test1,test2,5".getBytes());
            Files.write(providersPath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
            Files.write(providersPath,"2,test2,test2,7".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientValidator = new ClientValidator();
        clientRepository = new FileRepository<>(clientValidator,"src\\test\\java\\gunshop\\files\\test-clients.txt",Client.class);
        gunTypeValidator = new GunTypeValidator();
        gunTypeRepository = new FileRepository<>(gunTypeValidator,"src\\test\\java\\gunshop\\files\\test-gunTypes.txt",GunType.class);
        rentalValidator = new RentalValidator();
        rentalRepository = new FileRepository<>(rentalValidator,"src\\test\\java\\gunshop\\files\\test-rentals.txt",Rental.class);
        providerValidator = new GunProviderValidator();
        providerRepository = new FileRepository<>(providerValidator,"src\\test\\java\\gunshop\\files\\test-providers.txt", GunProvider.class);
    }

    @AfterAll
    public static void tearDown()
    {
        gunTypeValidator =null;
        gunTypeRepository = null;
        clientValidator = null;
        clientRepository = null;
        rentalValidator = null;
        rentalRepository = null;
    }

    @Test
    public void testLoadData()
    {
        Set<GunType> gunTypes = (HashSet<GunType>) gunTypeRepository.findAll();
        assertTrue(gunTypes.contains(new GunType(1L,"Gun1", Category.LONG_GUN,1)));
        assertTrue(gunTypes.contains(new GunType(2L,"Gun2",Category.PISTOL,2)));
        Set<Client> clients = (HashSet<Client>) clientRepository.findAll();
        assertTrue(clients.contains(new Client(1L,"Matei", LocalDate.of(2000, 9, 30))));
        assertTrue(clients.contains(new Client(2L,"Andrei", LocalDate.of(2000, 5, 21))));
        Set<Rental> rentals = (HashSet<Rental>) rentalRepository.findAll();
        assertTrue(rentals.contains(new Rental(new Pair<>(1L,1L),10)));
        assertTrue(rentals.contains(new Rental(new Pair<>(2L,2L),20)));
    }

    @Test
    public void FindById() {
        if(clientRepository.findOne(1L).isEmpty())
            fail();
        assertEquals(clientRepository.findOne(1L).get(), new Client(1L,"Matei", LocalDate.of(2000, 9, 30)));
    }

    @Test
    public void GetAll() {
        Set<Client> allClients = (HashSet<Client>) clientRepository.findAll();
        assertEquals(allClients.size(),2);
        assertTrue(allClients.contains(new Client(2L,"Andrei", LocalDate.of(2000, 5, 21))));
    }

    @Test
    public void AddWorks() {
        clientRepository.save(new Client(3L,"Tudor", LocalDate.of(2000, 4, 1)));
        Set<Client> allClients = (HashSet<Client>) clientRepository.findAll();
        assertEquals(allClients.size(),3);
        assertTrue(allClients.contains(new Client(3L,"Tudor", LocalDate.of(2000, 4, 1))));
    }
    @Test
    public void AddAndLoadWorks() {
        clientRepository.save(new Client(3L,"Tudor", LocalDate.of(2000, 4, 1)));
        clientRepository.loadData();
        Set<Client> allClients = (HashSet<Client>) clientRepository.findAll();
        assertEquals(allClients.size(),3);
    }

    @Test
    public void AddDuplicateIdException() {
               assertNotEquals(Optional.empty(),
                       clientRepository.save(new Client(1L,"Matei", LocalDate.of(2000, 9, 30))));
    }

    @Test
    public void AddException() {
        assertThrows(ValidatorException.class, () ->  clientRepository.save(new Client(10L,"Ionci", LocalDate.of(2012, 10, 10))));
    }

    @Test
    public void Delete() {
        Set<Client> allClients = (HashSet<Client>) clientRepository.findAll();
        assertEquals(allClients.size(),2);
        clientRepository.delete(1L);
        allClients = (HashSet<Client>) clientRepository.findAll();
        assertEquals(allClients.size(),1);
        assertFalse(allClients.contains(new Client(1L,"Matei", LocalDate.of(2000, 9, 30))));
    }

    @Test
    public void Update() {
        clientRepository.update(new Client(1L,"Gion", LocalDate.of(2000, 9, 30)));
        if(clientRepository.findOne(1L).isEmpty())
            fail();
        assertEquals(clientRepository.findOne(1L).get().getName(),"Gion");
    }

    @Test
    public void UpdateException() {
        assertThrows(ValidatorException.class, () -> clientRepository.update(new Client(5L,"",LocalDate.of(2020, 10, 10))));
    }

    @Test
    public void nullBranchInAllMethods() {
        assertThrows(IllegalArgumentException.class,
                () -> clientRepository.save(null));
        assertThrows(IllegalArgumentException.class,
                () -> clientRepository.delete(null));
        assertThrows(IllegalArgumentException.class,
                () -> clientRepository.update(null));
        assertThrows(IllegalArgumentException.class,
                () -> clientRepository.findOne(null));
    }
}



