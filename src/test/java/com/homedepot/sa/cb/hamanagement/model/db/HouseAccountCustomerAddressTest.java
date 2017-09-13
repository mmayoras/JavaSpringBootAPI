package com.homedepot.sa.cb.hamanagement.model.db;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by mxm8528 on 1/13/17.
 */
public class HouseAccountCustomerAddressTest {

    @Test
    public void testValidHouseAccountCustomerAddress()
    {
        HouseAccountCustomerAddress houseAccountCustomerAddress = createHouseAccountCustomerAddress();
        assertNotNull(houseAccountCustomerAddress.getHouseAccountCreditCardAccount());
        assertNotNull(houseAccountCustomerAddress.getCompanyName());
        assertNotNull(houseAccountCustomerAddress.getAddressLine1());
        assertNotNull(houseAccountCustomerAddress.getAddressLine2());
        assertNotNull(houseAccountCustomerAddress.getCity());
        assertNotNull(houseAccountCustomerAddress.getState());
        assertNotNull(houseAccountCustomerAddress.getPostalCode());
        assertNotNull(houseAccountCustomerAddress.getCountryCode());
        assertNotNull(houseAccountCustomerAddress.getShippingCode());
    }

    public HouseAccountCustomerAddress createHouseAccountCustomerAddress()
    {
        HouseAccountCustomerAddress houseAccountCustomerAddress = new HouseAccountCustomerAddress();
        houseAccountCustomerAddress.setHouseAccountCreditCardAccount(new HouseAccountCreditCardAccount());
        houseAccountCustomerAddress.setCompanyName("TestCompany");
        houseAccountCustomerAddress.setAddressLine1("12345 Test Street");
        houseAccountCustomerAddress.setAddressLine2("Apt 123");
        houseAccountCustomerAddress.setCity("Atlanta");
        houseAccountCustomerAddress.setState("GA");
        houseAccountCustomerAddress.setPostalCode("12345");
        houseAccountCustomerAddress.setCountryCode("US");
        houseAccountCustomerAddress.setShippingCode(1);

        return houseAccountCustomerAddress;
    }
}
