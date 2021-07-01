package gunshop.domain;

import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class ClientTest {
    private static final Long ID = 1L;
    private static final Long NEW_ID = 2L;
    private static final String NAME = "name01";
    private static final String NEW_NAME = "name02";
    private static final LocalDate DATE = LocalDate.of(2020, 10, 10);
    private static final LocalDate NEW_DATE = LocalDate.of(2020, 10, 11);

    private static Client client;

    @BeforeEach
    public void setUp(){
        client = new Client(NAME,DATE);
        client.setId(ID);
    }

    @AfterAll
    public static void tearDown(){
        client = null;
    }

    //testing default constructor
    @Test
    public void testDefaultConstructor()
    {
        assertNotNull(new Client());
    }

    // testing Id
    @Test
    public void testGetId(){
            assertEquals(ID, client.getId());
    }

    @Test
    public void testSetId(){
        client.setId(NEW_ID);
        assertEquals(NEW_ID,client.getId());
    }

    // testing Name
    @Test
    public void testGetName(){
        assertEquals(NAME,client.getName());
    }

    @Test
    public void testSetName(){
        client.setName(NEW_NAME);
        assertEquals(NEW_NAME,client.getName());
    }

    // testing DateOfBirth
    @Test
    public void testGetDateOfBirth(){
        assertEquals(DATE,client.getDateOfBirth());
    }

    @Test
    public void testSetDateOfBirth()
    {
        client.setDateOfBirth(NEW_DATE);
        assertEquals(NEW_DATE,client.getDateOfBirth());
    }

    //testing equals()
    @Test
    public void testEqualsTrueDifferentObjects()
    {

        var clientMock = new Client(NAME,DATE);
        clientMock.setId(ID);
        assertTrue(client.equals(clientMock));

    }



    @Test
    public void testEqualsTrueSameObject()
    {

        assertTrue(client.equals(client));

    }

    @Test
    public void testEqualsFalseDifferentTypes()
    {
        assertFalse(client.equals(new Rental()));

    }

    @Test
    public void testEqualsFalseNotNull()
    {
        assertFalse(client.equals(null));

    }

    @Test
    public void testEqualsFalseDifferentObjects()
    {

        var clientMock = new Client(NEW_NAME,NEW_DATE);
        clientMock.setId(ID);
        assertFalse(client.equals(clientMock));

    }

    @Test
    public void testToStringBaseEntity()
    {
        var baseEntity=new BaseEntity<Long>(1L);
        assertEquals(baseEntity.toString(),"BaseEntity{" +
                "id=" + 1 +
                '}');
    }
}
