package gunshop.service;

import gunshop.repository.InMemoryRepository;
import gunshop.domain.Client;
import gunshop.domain.validators.ClientValidator;
import gunshop.domain.validators.GunShopException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {

    private static ClientService clientService;

    @BeforeEach
    public void setUp() {
        clientService = new ClientService(new InMemoryRepository<>(new ClientValidator()));
        clientService.addClient(new Client(1, "andrei", LocalDate.of(2000, 5, 21)));
        clientService.addClient(new Client(2, "matei", LocalDate.of(2000, 8, 8)));
    }

    @AfterAll
    public static void tearDown() {
        clientService = null;
    }

    @Test
    public void getAll() {
        assertEquals(clientService.getAllClients().size(), 2);
    }

    @Test
    public void addClientWorks() {
        clientService.addClient(new Client(3, "tudor", LocalDate.of(2000, 7, 15)));
        clientService.addClient(new Client(4, "razvan", LocalDate.of(2001, 10, 10)));
        assertEquals(clientService.getAllClients().size(), 4);
    }

    @Test
    public void addClientValidatorException() {
        assertThrows(GunShopException.class,
                () -> clientService.addClient(new Client(3, "tudor", LocalDate.of(2015, 7, 18))));
    }

    @Test
    public void removeClientWorks()
    {
        clientService.removeClient(1L);
        assertFalse(clientService.getAllClients().contains(new Client(1, "Matei", LocalDate.of(2000, 9, 30))));

    }
    @Test
    public void removeClientNotWorks()
    {
        assertThrows(GunShopException.class,
                () -> clientService.removeClient(3L));
    }
    @Test
    public void updateClientWorks()
    {
        clientService.updateClient(new Client(1,"Matei",LocalDate.of(2000,10,30)));
        assertTrue(clientService.getAllClients().contains(new Client(1,"Matei",LocalDate.of(2000,10,30))));
        assertFalse(clientService.getAllClients().contains(new Client(1,"Matei",LocalDate.of(2000,9,30))));


    }

    @Test
    public void updateClientNotWorks()
    {
        assertThrows(GunShopException.class,
                () -> clientService.updateClient(new Client(3,"Marian",LocalDate.of(2000,10,30))));
    }

    @Test
    public void getClientById()
    {
        assertEquals(clientService.getClientById(1L),new Client(1, "andrei", LocalDate.of(2000, 5, 21)));
        assertNotEquals(clientService.getClientById(1L),new Client(2, "matei", LocalDate.of(2000, 8, 8)));
    }


}
