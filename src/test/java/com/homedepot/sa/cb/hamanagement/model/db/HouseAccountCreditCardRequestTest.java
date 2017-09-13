package com.homedepot.sa.cb.hamanagement.model.db;

import com.homedepot.sa.cb.hamanagement.model.api.CardStatusType;
import com.ibm.db2.jcc.DBTimestamp;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by mxm8528 on 1/13/17.
 */
public class HouseAccountCreditCardRequestTest {

    @Test
    public void testValidHouseAccountCreditCardRequest()
    {
        HouseAccountCreditCardRequest houseAccountCreditCardRequest = createHouseAccountCreditCardRequest();
        assertNotNull(houseAccountCreditCardRequest.getRequestId());
        assertNotNull(houseAccountCreditCardRequest.getCreatedTimeStamp());
        assertNotNull(houseAccountCreditCardRequest.getRequestorUserId());
        assertNotNull(houseAccountCreditCardRequest.getStatusFlag());
        assertNotNull(houseAccountCreditCardRequest.getLastUpdatedSysUserId());
        assertNotNull(houseAccountCreditCardRequest.getHouseAccountCreditCardAccounts());
    }

    public HouseAccountCreditCardRequest createHouseAccountCreditCardRequest()
    {
        HouseAccountCreditCardRequest houseAccountCreditCardRequest = new HouseAccountCreditCardRequest();
        houseAccountCreditCardRequest.setRequestId(1);
        houseAccountCreditCardRequest.setCreatedTimeStamp(new DBTimestamp(new Date().getTime()));
        houseAccountCreditCardRequest.setRequestorUserId("testUser");
        houseAccountCreditCardRequest.setStatusFlag(CardStatusType.valueOf("ACTIVE").getCode());
        houseAccountCreditCardRequest.setLastUpdatedSysUserId("testUser");
        houseAccountCreditCardRequest.setHouseAccountCreditCardAccounts(new ArrayList<>());
        return houseAccountCreditCardRequest;
    }
}
