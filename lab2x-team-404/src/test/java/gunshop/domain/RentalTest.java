package gunshop.domain;

import org.junit.jupiter.api.*;
import utils.Pair;

import static org.junit.jupiter.api.Assertions.*;

public class RentalTest {
    private static final Long ClientID = 1L;
    private static final Long GunID = 1L;
    private static final Pair<Long, Long> ID = new Pair<>(ClientID, GunID);

    private static final Long NEW_ClientID = 2L;
    private static final Long NEW_GunID = 2L;
    private static final Pair<Long, Long> NEW_ID = new Pair<>(NEW_ClientID, NEW_GunID);

    private static final int PRICE = 1;
    private static final int NEW_PRICE = 2;

    private static Rental rental;

    @BeforeEach
    public void setUp(){
        rental = new Rental(PRICE);
        rental.setId(ID);
    }

    @AfterAll
    public static void tearDown(){
        rental = null;
    }

    //testing  constructors
    @Test
    public void testDefaultConstructor()
    {
        assertNotNull(new Rental());
    }

    @Test
    public void testConstructorWithPair()
    {
        assertNotNull(new Rental(ID,PRICE));
    }


    // testing Id
    @Test
    public void testGetId(){
        assertEquals(ID,rental.getId());
    }

    @Test
    public void testSetId(){
        rental.setId(NEW_ID);
        assertEquals(NEW_ID,rental.getId());
    }

    // testing Price
    @Test
    public void testGetPrice(){
        assertEquals(PRICE,rental.getPrice());
    }

    @Test
    public void testSetPrice(){
        rental.setPrice(NEW_PRICE);
        assertEquals(NEW_PRICE, rental.getPrice());
    }

    @Test
    public void testSetName(){
        rental.setPrice(NEW_PRICE);
        assertEquals(NEW_PRICE,rental.getPrice());
    }

    //testing equals()
    @Test
    public void testEqualsTrueDifferentObjects()
    {
        var rentalMock = new Rental(PRICE);
        rentalMock.setId(ID);

        assertTrue(rental.equals(rentalMock));

    }

    @Test
    public void testEqualsTrueSameObject()
    {

        assertTrue(rental.equals(rental));

    }
    @Test
    public void testEqualsFalseDifferentTypes()
    {
        assertFalse(rental.equals(new Client()));

    }

    @Test
    public void testEqualsFalseNotNull()
    {
        assertFalse(rental.equals(null));

    }
    @Test
    public void testEqualsFalseDifferentObjects()
    {
        assertFalse(rental.equals(new Rental(NEW_PRICE)));

    }
    @Test
    public void testEqualsFalseDifferentPrices()
    {
        var rentalMock = new Rental(ID,NEW_PRICE);
        rentalMock.setId(ID);

        assertFalse(rental.equals(rentalMock));

    }

    // testing hashCode
    @Test
    public void testHashCodeTrue()
    {
        var rentalMock = new Rental(PRICE);
        rentalMock.setId(ID);
        assertEquals(rentalMock.hashCode(), rental.hashCode());
    }

    @Test
    public void TestHashCodeFalse()
    {

        var rentalMock = new Rental(NEW_PRICE);
        rentalMock.setId(NEW_ID);
        assertNotEquals(rentalMock.hashCode(), rental.hashCode());
    }

    // testing toString()
    @Test
    public void testToString()
    {
        assertEquals(rental.toString(),"Rental{" +
                "Client ID ='" + ClientID + '\'' +
                ", Gun ID ='" + GunID + '\'' +
                ", price =" + PRICE +
                "} ");
    }



}
