package com.homedepot.sa.cb.hamanagement.model.db;

import com.homedepot.sa.cb.hamanagement.TestData;
import com.homedepot.sa.cb.hamanagement.model.api.BrandType;
import com.homedepot.sa.cb.hamanagement.model.api.CardStatusType;
import com.homedepot.sa.cb.hamanagement.model.api.SystemType;
import com.ibm.db2.jcc.DBTimestamp;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by mxm8528 on 1/13/17.
 */
public class HouseAccountCreditCardAccountTest {

    @Test
    public void testValidHouseAccountCreditCardAccount()
    {
        HouseAccountCreditCardAccount houseAccountCreditCardAccount = createHouseAccountCreditCardAccount();
        assertNotNull(houseAccountCreditCardAccount.getCardHolderMiddleInitial());
        assertNotNull(houseAccountCreditCardAccount.getCreatedTimeStamp());
        assertNotNull(houseAccountCreditCardAccount.getBrandName());
        assertNotNull(houseAccountCreditCardAccount.getStatusCode());
        assertNotNull(houseAccountCreditCardAccount.getSourceSystem());
        assertNotNull(houseAccountCreditCardAccount.getHouseAccountCustomerAddress());
    }

    public HouseAccountCreditCardAccount createHouseAccountCreditCardAccount()
    {
        HouseAccountCreditCardAccount houseAccountCreditCardAccount = new HouseAccountCreditCardAccount();
        houseAccountCreditCardAccount.setCardHolderMiddleInitial("T");
        houseAccountCreditCardAccount.setCreatedTimeStamp(new DBTimestamp(new Date().getTime()));
        houseAccountCreditCardAccount.setBrandName(BrandType.valueOf("BARNETT").getDescription());
        houseAccountCreditCardAccount.setStatusCode(CardStatusType.valueOf("ACTIVE").getCode());
        houseAccountCreditCardAccount.setSourceSystem(SystemType.ISS.toString());
        houseAccountCreditCardAccount.setHouseAccountCustomerAddress(new ArrayList<>());
        return houseAccountCreditCardAccount;
    }
}
