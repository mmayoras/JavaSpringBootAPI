package com.homedepot.sa.cb.hamanagement.model.db;

import com.homedepot.sa.cb.hamanagement.model.api.*;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by mxm8528 on 1/13/17.
 */
public class HouseAccountSpendingFrequencyIdTest {

    @Test
    public void testValidHouseAccountSpendingFrequencyId()
    {
        HouseAccountSpendingFrequencyId houseAccountSpendingFrequencyId = createHouseAccountSpendingFrequencyId();
        assertNotNull(houseAccountSpendingFrequencyId.getHouseAccountCreditCardAccount());
        assertNotNull(houseAccountSpendingFrequencyId.getFrequencyTypeCode());
    }

    @Test
    public void testHouseAccountSpendingFrequencyIdEqualsAndSymmetric()
    {
        HouseAccountSpendingFrequencyId houseAccountSpendingFrequencyId1 = new HouseAccountSpendingFrequencyId();
        HouseAccountSpendingFrequencyId houseAccountSpendingFrequencyId2 = new HouseAccountSpendingFrequencyId();
        HouseAccountCreditCardAccount houseAccountCreditCardAccount = new HouseAccountCreditCardAccount();

        houseAccountSpendingFrequencyId1.setHouseAccountCreditCardAccount(houseAccountCreditCardAccount);
        houseAccountSpendingFrequencyId2.setHouseAccountCreditCardAccount(houseAccountCreditCardAccount);

        assertTrue(houseAccountSpendingFrequencyId1.equals(houseAccountSpendingFrequencyId2));
        assertTrue(houseAccountSpendingFrequencyId1.hashCode() == houseAccountSpendingFrequencyId1.hashCode());

        houseAccountSpendingFrequencyId2.setFrequencyTypeCode(LimitFrequencyType.valueOf("WEEKLY").getCode());
        assertFalse(houseAccountSpendingFrequencyId1.equals(houseAccountSpendingFrequencyId2));

        houseAccountSpendingFrequencyId2.setHouseAccountCreditCardAccount(new HouseAccountCreditCardAccount());
        assertFalse(houseAccountSpendingFrequencyId1.equals(houseAccountSpendingFrequencyId2));

        assertFalse(houseAccountSpendingFrequencyId1.equals(new HouseAccountCreditCardAccount()));
    }

    public HouseAccountSpendingFrequencyId createHouseAccountSpendingFrequencyId()
    {
        HouseAccountSpendingFrequencyId houseAccountSpendingFrequencyId = new HouseAccountSpendingFrequencyId();
        HouseAccountCreditCardAccount houseAccountCreditCardAccount = new HouseAccountCreditCardAccount();

        houseAccountSpendingFrequencyId.setHouseAccountCreditCardAccount(houseAccountCreditCardAccount);
        houseAccountSpendingFrequencyId.setFrequencyTypeCode(LimitFrequencyType.valueOf("DAILY").getCode());

        return houseAccountSpendingFrequencyId;
    }
}
