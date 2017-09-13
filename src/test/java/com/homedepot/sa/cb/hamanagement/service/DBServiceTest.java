package com.homedepot.sa.cb.hamanagement.service;

import com.homedepot.sa.cb.hamanagement.TestData;
import com.homedepot.sa.cb.hamanagement.exception.ApplicationException;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardAccount;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardRequest;
import com.homedepot.sa.cb.hamanagement.repositories.CardRepository;
import com.homedepot.sa.cb.hamanagement.repositories.CardRequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * Created by n84qvs on 3/14/17.
 */
public class DBServiceTest {

    @InjectMocks
    private DBService dbService;

    @Mock
    private CardRequestRepository cardRequestRepository;

    @Mock
    private CardRepository cardRepository;

    private TestData testData;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.testData = new TestData();
    }

    @Test(expected = ApplicationException.class)
    public void testSaveCardRequestException() throws ApplicationException{
        when(cardRequestRepository.save(Mockito.any(HouseAccountCreditCardRequest.class))).thenThrow(Exception.class);

        dbService.saveCardRequest(testData.createHouseAccountCreditCardRequest());
    }


    @Test(expected = ApplicationException.class)
    public void testSaveCardException() throws ApplicationException{
        when(cardRepository.save(Mockito.any(HouseAccountCreditCardAccount.class))).thenThrow(Exception.class);

        dbService.saveCard(testData.createHouseAccountCreditAccount());
    }

}
