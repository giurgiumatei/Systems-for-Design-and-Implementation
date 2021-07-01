package gunshop.domain;

import gunshop.domain.validators.GunTypeValidator;
import gunshop.domain.validators.ValidatorException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class GunTest {

    private static final Long ID = 1L;
    private static final Long NEW_ID = 2L;
    private static final String NAME = "name01";
    private static final String NEW_NAME = "name02";
    private static final Category CATEGORY = Category.PISTOL;
    private static final Category NEW_CATEGORY = Category.RIFLE;

    private static GunType gunType;

    @BeforeEach
    public void setUp(){
        gunType = new GunType(NAME,CATEGORY);
        gunType.setId(ID);
    }

    @AfterAll
    public static void tearDown(){
        gunType = null;
    }

    //testing constructors
    @Test
    public void testDefaultConstructor()
    {
        assertNotNull(new GunType());
    }

    @Test
    public void testConstructorWithLong()
    {
        assertNotNull(new GunType(ID,NAME,CATEGORY));
    }


    // testing Id
    @Test
    public void testGetId(){
        assertEquals(ID,gunType.getId());
    }

    @Test
    public void testSetId(){
        gunType.setId(NEW_ID);
        assertEquals(NEW_ID,gunType.getId());
    }

    // testing Name
    @Test
    public void testGetName(){
        assertEquals(NAME,gunType.getName());
    }

    @Test
    public void testSetName(){
        gunType.setName(NEW_NAME);
        assertEquals(NEW_NAME,gunType.getName());
    }

    // testing Category
    @Test
    public void testGetCategory(){
        assertEquals(CATEGORY,gunType.getCategory());
    }

    @Test
    public void testSetCategory(){
        gunType.setCategory(NEW_CATEGORY);
        assertEquals(NEW_CATEGORY,gunType.getCategory());
    }

    //testing equals()
    @Test
    public void testEqualsTrueDifferentObjects()
    {
        var gunTypeMock = new GunType(NAME,CATEGORY);
        gunTypeMock.setId(ID);

        assertTrue(gunType.equals(gunTypeMock));

    }

    @Test
    public void testEqualsTrueSameObject()
    {
        assertTrue(gunType.equals(gunType));
    }

    @Test
    public void testEqualsFalseDifferentTypes()
    {
        assertFalse(gunType.equals(new Client()));
    }

    @Test
    public void testEqualsFalseNotNull()
    {
        assertFalse(gunType.equals(null));
    }

    @Test
    public void testEqualsFalseDifferentObjects()
    {
        var gunTypeMock = new GunType(NEW_NAME,CATEGORY);
        gunTypeMock.setId(ID);

        assertFalse(gunType.equals(gunTypeMock));

    }

    // testing hashCode
    @Test
    public void testHashCodeTrue()
    {
        var gunTypeMock = new GunType(NAME,CATEGORY);
        gunTypeMock.setId(ID);
        assertEquals(gunTypeMock.hashCode(), gunType.hashCode());
    }

    @Test
    public void TestHashCodeFalse()
    {

        var gunTypeMock = new GunType(NEW_NAME,NEW_CATEGORY);
        gunTypeMock.setId(NEW_ID);
        assertNotEquals(gunTypeMock.hashCode(), gunType.hashCode());
    }


    @Test
    public void validateEmptyName() {
        GunTypeValidator gunTypeValidator = new GunTypeValidator();
        assertThrows(ValidatorException.class, () ->
            gunTypeValidator.validate(new GunType("", null)));
    }


}