package gunshop.repository;

import gunshop.domain.Client;
import gunshop.domain.validators.ClientValidator;
import gunshop.domain.validators.GunShopException;
import gunshop.domain.validators.Validator;
import gunshop.domain.validators.ValidatorException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryRepositoryTest {

    private static Validator<Client> testValidator;
    private static RepositoryInterface<Long, Client> clientRepository; // Repository is an interface

    @BeforeEach
    public void setUp() {
        testValidator = new ClientValidator();
        clientRepository = new InMemoryRepository<>(testValidator);
        clientRepository.save(new Client(1L,"name01", LocalDate.of(2001, 10, 10)));
        clientRepository.save(new Client(2L,"name02", LocalDate.of(2000, 10, 10)));
    }

    @AfterAll
    public static void tearDown() {
        testValidator=null;
        clientRepository=null;
    }

    @Test
    public void FindById() {
        if(clientRepository.findOne(1L).isEmpty())
            fail();
        assertEquals(clientRepository.findOne(1L).get(), new Client(1L,"name01", LocalDate.of(2001, 10, 10)));
    }

    @Test
    public void GetAll() {
        Set<Client> allClients = (HashSet<Client>) clientRepository.findAll();
        assertEquals(allClients.size(),2);
        assertTrue(allClients.contains(new Client(2L,"name02", LocalDate.of(2000, 10, 10))));
    }

    @Test
    public void AddWorks() {
        clientRepository.save(new Client(3L,"name03", LocalDate.of(2000, 11, 11)));
        Set<Client> allClients = (HashSet<Client>) clientRepository.findAll();
        assertEquals(allClients.size(),3);
        assertTrue(allClients.contains(new Client(3L,"name03", LocalDate.of(2000, 11, 11))));
    }

    @Test
    public void AddDuplicateIdException() {
        assertNotEquals(Optional.empty(),
                        clientRepository.save(new Client(1L,"name01", LocalDate.of(2001, 10, 10))));
    }

    @Test
    public void AddException() {
        assertThrows(ValidatorException.class, () ->  clientRepository.save(new Client(10L,"name10", LocalDate.of(2012, 10, 10))));
    }

    @Test
    public void Delete() {
        Set<Client> allClients = (HashSet<Client>) clientRepository.findAll();
        assertEquals(allClients.size(),2);
        clientRepository.delete(1L);
        allClients = (HashSet<Client>) clientRepository.findAll();
        assertEquals(allClients.size(),1);
        assertFalse(allClients.contains(new Client(1L,"name01", LocalDate.of(2001, 10, 10))));
    }

    @Test
    public void Update() {
        clientRepository.update(new Client(1L,"name05", LocalDate.of(2001, 10, 10)));
        if(clientRepository.findOne(1L).isEmpty())
            fail();
        assertEquals(clientRepository.findOne(1L).get().getName(),"name05");
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
