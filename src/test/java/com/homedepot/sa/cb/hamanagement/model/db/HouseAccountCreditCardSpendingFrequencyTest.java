package com.homedepot.sa.cb.hamanagement.model.db;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by mxm8528 on 1/13/17.
 */
public class HouseAccountCreditCardSpendingFrequencyTest {

    @Test
    public void testValidHouseAccountCreditCardSpendingFrequency()
    {
        HouseAccountCreditCardSpendingFrequency houseAccountCreditCardSpendingFrequency = createHouseAccountCreditCardSpendingFrequency();
        assertNotNull(houseAccountCreditCardSpendingFrequency.getHouseAccountCreditCardAccount());
    }

    public HouseAccountCreditCardSpendingFrequency createHouseAccountCreditCardSpendingFrequency()
    {
        HouseAccountCreditCardSpendingFrequency houseAccountCreditCardSpendingFrequency = new HouseAccountCreditCardSpendingFrequency();
        houseAccountCreditCardSpendingFrequency.setHouseAccountCreditCardAccount(new HouseAccountCreditCardAccount());
        return houseAccountCreditCardSpendingFrequency;
    }
}
