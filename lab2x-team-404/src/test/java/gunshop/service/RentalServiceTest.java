package gunshop.service;

import gunshop.domain.Category;
import gunshop.repository.InMemoryRepository;
import gunshop.domain.Client;
import gunshop.domain.GunType;
import gunshop.domain.Rental;
import gunshop.domain.validators.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Pair;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class RentalServiceTest {

    private static ClientService clientService;
    private static RentalService rentalService;
    private static GunTypeService gunTypeService;

    @BeforeEach
    public void setUp() {
        InMemoryRepository<Long, Client> clientRepository = new InMemoryRepository<>(new ClientValidator());
        clientService = new ClientService(clientRepository);
        clientService.addClient(new Client(1L, "andrei", LocalDate.of(2000, 5, 21)));
        clientService.addClient(new Client(2L, "matei", LocalDate.of(2000, 8, 8)));

        InMemoryRepository<Long, GunType> gunRepository = new InMemoryRepository<>(new GunTypeValidator());
        gunTypeService = new GunTypeService(gunRepository);
        gunTypeService.addGunType(new GunType(1L,"Beretta A400", Category.SHOTGUN));
        gunTypeService.addGunType(new GunType(2L,"Winchester SXP", Category.SHOTGUN));

        rentalService = new RentalService(new InMemoryRepository<>(new RentalValidator()), clientRepository, gunRepository);
        rentalService.addRental(new Rental(new Pair<>(1L, 1L), 100));
        rentalService.addRental(new Rental(new Pair<>(1L, 2L), 100));
    }

    @AfterAll
    public static void tearDown() {
        rentalService = null;
    }

    @Test
    public void getAll() {
        assertEquals(rentalService.getAllRentals().size(), 2);
    }

    @Test
    public void addRentalWorks() {
        rentalService.addRental(new Rental(new Pair<>(2L, 1L), 150));
        assertEquals(rentalService.getAllRentals().size(), 3);
    }

    @Test
    public void addRentalValidatorException() {
        assertThrows(ValidatorException.class,
                () -> rentalService.addRental(new Rental(new Pair<>(2L, 1L), 0)));
    }

    @Test
    public void addRentalClientDoesNotExistException() {
        assertThrows(GunShopException.class,
                () -> rentalService.addRental(new Rental(new Pair<>(5L, 2L), 200)));
    }

    @Test
    public void addRentalGunDoesNotExistException() {
        RentalService rs = new RentalService(new InMemoryRepository<>(new RentalValidator()));
        InMemoryRepository<Long, Client> clientRepository = new InMemoryRepository<>(new ClientValidator());
        ClientService cs = new ClientService(clientRepository);
        rs.setClientRepository(clientRepository);
        InMemoryRepository<Long, GunType> gunRepository = new InMemoryRepository<>(new GunTypeValidator());
        GunTypeService gunTypeService = new GunTypeService(gunRepository);
        rs.setGunRepository(gunRepository);
        cs.addClient(new Client(1L, "test", LocalDate.of(2000, Month.JANUARY, 2)));
        assertThrows(GunShopException.class,
                () -> rentalService.addRental(new Rental(new Pair<>(1L, 5L), 200)));
    }

    @Test
    public void removeRentalWorks()
    {
        rentalService.removeRental(new Pair<Long,Long>(1L,1L));
        assertFalse(rentalService.getAllRentals().contains(new Rental(new Pair<>(1L, 1L), 100)));
    }
    @Test
    public void removeRentalNotWorks()
    {
        assertThrows(GunShopException.class,
                () ->  rentalService.removeRental(new Pair<Long,Long>(2L,2L)));
    }
    @Test
    public void updateRentalWorks()
    {
        rentalService.updateRental(new Rental(new Pair<>(1L, 1L), 300));
        assertTrue(rentalService.getAllRentals().contains(new Rental(new Pair<>(1L, 1L), 300)));
        assertFalse(rentalService.getAllRentals().contains(new Rental(new Pair<>(1L, 1L), 100)));


    }

    @Test
    public void updateRentalNotWorks()
    {
        assertThrows(GunShopException.class,
                () ->  rentalService.updateRental(new Rental(new Pair<>(2L, 2L), 100)));
    }
}
