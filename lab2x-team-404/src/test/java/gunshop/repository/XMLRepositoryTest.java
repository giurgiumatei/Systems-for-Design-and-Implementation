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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class XMLRepositoryTest {
    private static Validator<GunProvider> providerValidator;
    private static RepositoryInterface<Long, GunProvider> providerRepository;
    private static Validator<GunType> gunTypeValidator;
    private static RepositoryInterface<Long, GunType> gunTypeRepository;
    private static Validator<Client> clientValidator;
    private static XMLRepository<Long, Client> clientRepository;
    private static Validator<Rental> rentalValidator;
    private static RepositoryInterface<Pair<Long,Long>,Rental> rentalRepository;
    String clientFile = "src\\test\\java\\gunshop\\files\\test-clients.xml";
    String providerFile = "src\\test\\java\\gunshop\\files\\test-gunProviders.xml";
    String gunFile = "src\\test\\java\\gunshop\\files\\test-gunTypes.xml";
    String rentalFile = "src\\test\\java\\gunshop\\files\\test-rentals.xml";

    @BeforeEach
    public void setUp()
    {
        writeToFiles();
        clientValidator = new ClientValidator();
        clientRepository = new XMLRepository<>(clientValidator, clientFile, Client.class);
        providerValidator = new GunProviderValidator();
        providerRepository = new XMLRepository<>(providerValidator, providerFile, GunProvider.class);
        gunTypeValidator = new GunTypeValidator();
        gunTypeRepository = new XMLRepository<>(gunTypeValidator, gunFile, GunType.class);
        rentalValidator = new RentalValidator();
        rentalRepository = new XMLRepository<>(rentalValidator, rentalFile, Rental.class);
    }

    private void writeToFiles() {
        Path clientPath = Paths.get(clientFile);
        Path gunPath = Paths.get(gunFile);
        Path rentalPath = Paths.get(rentalFile);
        Path providerPath = Paths.get(providerFile);
        String clientXML = """
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <clients>
                    <client>
                        <id>1</id>
                        <name>Matei</name>
                        <dateOfBirth>30/9/2000</dateOfBirth>
                    </client>
                    <client>
                        <id>2</id>
                        <name>Andrei</name>
                        <dateOfBirth>21/5/2000</dateOfBirth>
                    </client>
                </clients>""";
        String providerXML = """
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <providers>
                    <provider>
                        <id>1</id>
                        <name>provider1</name>
                        <speciality>spec1</speciality>
                        <reputation>5</reputation>
                    </provider>
                    <provider>
                        <id>2</id>
                        <name>provider2</name>
                        <speciality>spec2</speciality>
                        <reputation>7</reputation>
                    </provider>
                </providers>""";
        String gunXML = """
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <guntypes>
                    <gunType>
                        <id>1</id>
                        <name>Gun1</name>
                        <category>PISTOL</category>
                        <providerid>1</providerid>
                    </gunType>
                    <gunType>
                        <id>2</id>
                        <name>Gun2</name>
                        <category>LONG_GUN</category>
                        <providerid>2</providerid>
                    </gunType>
                </guntypes>""";
        String rentalXML = """
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <rentals>
                    <rental>
                        <id>(1,1)</id>
                        <price>10</price>
                    </rental>
                    <rental>
                        <id>(2,2)</id>
                        <price>20</price>
                    </rental>
                </rentals>""";
        try {
            Files.write(clientPath, clientXML.getBytes());
            Files.write(gunPath, gunXML.getBytes());
            Files.write(rentalPath, rentalXML.getBytes());
            Files.write(providerPath, providerXML.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void tearDown()
    {
        gunTypeValidator =null;
        gunTypeRepository=null;
        clientValidator = null;
        clientRepository = null;
        rentalValidator = null;
        rentalRepository = null;
    }

    @Test
    public void testLoadData()
    {
        Set<GunType> gunTypes = (HashSet<GunType>) gunTypeRepository.findAll();
        assertTrue(gunTypes.contains(new GunType(1L,"Gun1", Category.PISTOL,1)));
        assertTrue(gunTypes.contains(new GunType(2L,"Gun2",Category.LONG_GUN,2)));
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
        clientRepository.loadDataFromXML();
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
