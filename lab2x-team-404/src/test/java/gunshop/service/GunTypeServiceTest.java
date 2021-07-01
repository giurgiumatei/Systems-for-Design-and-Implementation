package gunshop.service;

import gunshop.domain.Category;
import gunshop.domain.GunType;
import gunshop.domain.validators.GunShopException;
import gunshop.domain.validators.GunTypeValidator;
import gunshop.domain.validators.ValidatorException;
import gunshop.repository.InMemoryRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GunTypeServiceTest {

    private static GunTypeService gunTypeService;

    @BeforeEach
    public void setUp() {
        gunTypeService = new GunTypeService(new InMemoryRepository<>(new GunTypeValidator()));
        gunTypeService.addGunType(new GunType(1L,"Beretta A400", Category.SHOTGUN));
        gunTypeService.addGunType(new GunType(2L,"Winchester SXP", Category.SHOTGUN));
        gunTypeService.addGunType(new GunType(3L,"GLOCK 42", Category.PISTOL));
    }

    @AfterAll
    public static void tearDown() {
        gunTypeService = null;
    }

    @Test
    public void getAll() {
        assertEquals(gunTypeService.getAllGunTypes().size(), 3);
    }

    @Test
    public void addGunsWorks() {
        gunTypeService.addGunType(new GunType(4L,"Beretta 92X", Category.PISTOL));
        gunTypeService.addGunType(new GunType(5L,"GLOCK 21", Category.PISTOL));
        assertEquals(gunTypeService.getAllGunTypes().size(), 5);
    }

    @Test
    public void addGunsValidatorException() {
        assertThrows(Exception.class,
                () -> gunTypeService.addGunType(new GunType(4L,"GLOCK", Category.valueOf(""))));
    }

    @Test
    public void removeGunTypeWorks()
    {
        gunTypeService.removeGunType(1L);
        assertFalse(gunTypeService.getAllGunTypes().contains(new GunType(1L, "Beretta A400",Category.PISTOL)));

    }
    @Test
    public void removeGunTypeNotWorks()
    {
        assertThrows(GunShopException.class,
                () -> gunTypeService.removeGunType(4L));
    }
    @Test
    public void updateGunTypeWorks()
    {
        gunTypeService.updateGunType(new GunType(1L,"GunNew",Category.RIFLE));
        assertTrue(gunTypeService.getAllGunTypes().contains(new GunType(1L,"GunNew",Category.RIFLE)));
        assertFalse(gunTypeService.getAllGunTypes().contains(new GunType(1L,"Beretta A400",Category.PISTOL)));
    }

    @Test
    public void updateGunTypeNotWorks()
    {
        assertThrows(GunShopException.class,
                () -> gunTypeService.updateGunType(new GunType(4L,"Gun4",Category.PISTOL)));
    }

    @Test
    public void getGunTypeById()
    {
        assertEquals(gunTypeService.getGunTypeById(1L),new GunType(1L,"Beretta A400", Category.SHOTGUN));
        assertNotEquals(gunTypeService.getGunTypeById(1L),new GunType(2L,"Winchester SXP", Category.SHOTGUN));
    }

}
