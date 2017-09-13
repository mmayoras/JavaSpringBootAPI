package com.homedepot.sa.cb.hamanagement.model.tandem;

import com.homedepot.sa.cb.hamanagement.TestData;
import com.homedepot.sa.cb.hamanagement.model.api.Address;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by n84qvs on 3/14/17.
 */
public class TandemCardResponseTest {

    private TestData testData;

    @Before
    public void setUp(){
      this.testData = new TestData();
    }

    @Test
    public void testToStringMethod(){
        TandemCardResponse response = testData.createTandemResponse();
        String tandemResponseString = response.toString();
        assertNotNull(tandemResponseString);
        assertEquals(tandemResponseString,"TandemCardResponse{sysId='ISS', statusCode='0', serviceAccountNumber='1234567890', authBuyerId='AB12345', crossReferenceNumber='1234567890123456', statusMessage='Request Successful', expirationDate='mmyy'}");

    }
}
