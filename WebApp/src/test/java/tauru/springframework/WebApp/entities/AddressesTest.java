package tauru.springframework.WebApp.entities;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tauru.springframework.WebApp.repositories.AddressRepository;

import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class AddressesTest {

    private static final String county = "CountyTest";
    private static final String street = "StreetTest";
    private static final String zipCode = "ZipCodeTest";
    private static final String streetNumber= "StreetNumberTest";
    private static final Logger LOGGER = Logger.getLogger(AddressesTest.class.getName());
    private Address address;

    @Autowired
    private AddressRepository addressRepository;

    @Before
    public void initAddressTests() {

        LOGGER.info("Before Initializing");
        LOGGER.info("Star initiating addresses tests");
        address = new Address();
    }

    @Test
    public void testSaveAddress() {

        address.setCounty(county);
        address.setStreet(street);
        address.setStreetNumber(streetNumber);
        address.setZipCode(zipCode);

        LOGGER.info("Saving creating address for test");
        addressRepository.save(address);
        Address savedAddress = addressRepository.findStreetByStreetName(street);

        assertEquals(county, savedAddress.getCounty());
        assertEquals(street, savedAddress.getStreet());
        assertEquals(zipCode, savedAddress.getZipCode());
        assertEquals(streetNumber, savedAddress.getStreetNumber());

    }

    @After
    public void deleteAddress() {
        LOGGER.info("Deleting test address");
        addressRepository.delete(address);
    }

}
