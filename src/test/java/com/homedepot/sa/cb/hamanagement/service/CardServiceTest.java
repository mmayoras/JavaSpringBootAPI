package com.homedepot.sa.cb.hamanagement.service;

import com.homedepot.sa.cb.hamanagement.TestData;
import com.homedepot.sa.cb.hamanagement.exception.*;
import com.homedepot.sa.cb.hamanagement.model.api.*;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardAccount;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardRequest;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardCreateRequest;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.util.Date;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by mxm8528 on 1/12/17.
 */
public class CardServiceTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private DBService dbService;

    @Mock
    private TandemService tandemService;

    @Mock
    private MapperService mapperService;

    private TandemCardResponse tandemCardResponse;

    private TestData testData;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        this.testData = new TestData();

        //Mocking up tandem response
        this.tandemCardResponse = new TandemCardResponse();
        this.tandemCardResponse.setAuthBuyerId("AB12345");
        this.tandemCardResponse.setCrossReferenceNumber("1234567890123456");
        this.tandemCardResponse.setExpirationDate("1223");
        this.tandemCardResponse.setStatusCode("0");
        this.tandemCardResponse.setServiceAccountNumber("1234567890");
        this.tandemCardResponse.setSysId("ISS");

    }


    @Test
    public void testCreateNoDuplicate() throws ApplicationException
    {

        when(dbService.findByBuyerIdAndServiceAccountNumberAndSourceSystem(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(null);
        when(tandemService.submitTandemRequest(Mockito.any(TandemCardCreateRequest.class))).thenReturn(tandemCardResponse);
        doNothing().when(dbService).saveCardRequest(Mockito.any(HouseAccountCreditCardRequest.class));

        Card card = cardService.create(testData.createCardRequest());

        assertTrue(card.getCrossReferenceNumber().equals("1234567890123456"));
    }

    @Test
    public void testCreateNoDuplicateJpaObjectRetrievalFailureException() throws ApplicationException
    {

        when(dbService.findByBuyerIdAndServiceAccountNumberAndSourceSystem(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenThrow(JpaObjectRetrievalFailureException.class);
        when(tandemService.submitTandemRequest(Mockito.any(TandemCardCreateRequest.class))).thenReturn(tandemCardResponse);
        doNothing().when(dbService).saveCardRequest(Mockito.any(HouseAccountCreditCardRequest.class));

        Card card = cardService.create(testData.createCardRequest());

        assertTrue(card.getCrossReferenceNumber().equals("1234567890123456"));
    }

    @Test
    public void testCreateDuplicate() throws ApplicationException
    {
        when(dbService.findByBuyerIdAndServiceAccountNumberAndSourceSystem(Mockito.anyString(),Mockito.anyLong(),Mockito.anyString())).thenReturn(new HouseAccountCreditCardAccount());
        when(tandemService.submitTandemRequest(Mockito.any(TandemCardCreateRequest.class))).thenReturn(tandemCardResponse);
        doNothing().when(dbService).saveCardRequest(Mockito.any(HouseAccountCreditCardRequest.class));
        Card card = cardService.create(testData.createCardRequest());
        assertTrue(card.getCrossReferenceNumber().equals("1234567890123456"));

    }

    @Test(expected = NotFoundException.class)
    public void testRetrieveHouseAccountInvalidCardId() throws NotFoundException{
        cardService.retrieveHouseAccount("123456");
    }

    @Test(expected = NotFoundException.class)
    public void testRetrieveHouseAccountNullCardId() throws NotFoundException{
        cardService.retrieveHouseAccount(null);
    }

    @Test(expected = NotFoundException.class)
    public void testRetrieveHouseAccountCardNotFoundInDB() throws NotFoundException{
        when(dbService.findByCrossReferenceNumber(Mockito.anyString())).thenReturn(null);
        cardService.retrieveHouseAccount("123456");
    }

    @Test
    public void testRetrieveHouseAccount() throws NotFoundException{
        when(dbService.findByCrossReferenceNumber(Mockito.anyString())).thenReturn(testData.createHouseAccountCreditAccount());
        HouseAccountCreditCardAccount account = cardService.retrieveHouseAccount("1234567890123456");
        assertNotNull(testData.createHouseAccountCreditAccount());
        assertTrue(account.getCardHolderXrefNumber().equals("1234567890123456"));
    }

    @Test(expected = PreconditionFailedException.class)
    public void testUpdateCardPreconditionFailed() throws ApplicationException{
        when(dbService.findByCrossReferenceNumber(Mockito.anyString())).thenReturn(testData.createHouseAccountCreditAccount());

        cardService.updateCard(testData.createCard(),"2015-03-06 20:22:16.645");
    }

    //Update card method returns void. There is nothing to assert but this tests the if condition when card status is INACTIVE
    @Test
    public void testUpdateCardStatusInactive() throws ApplicationException{
        when(dbService.findByCrossReferenceNumber(Mockito.anyString())).thenReturn(testData.createHouseAccountCreditAccount());
        when(tandemService.submitTandemRequest(Mockito.any(TandemCardCreateRequest.class))).thenReturn(tandemCardResponse);
        doNothing().when(dbService).saveCardRequest(Mockito.any(HouseAccountCreditCardRequest.class));

        Card card = testData.createCard();
        card.setCardStatus(CardStatusType.INACTIVE);
        cardService.updateCard(card,new Date().toString());

    }

    //Update card method returns void. There is nothing to assert but this tests the if condition when card status is ACTIVE
    @Test
    public void testUpdateCardStatusActive() throws ApplicationException{
        when(dbService.findByCrossReferenceNumber(Mockito.anyString())).thenReturn(testData.createHouseAccountCreditAccount());
        when(tandemService.submitTandemRequest(Mockito.any(TandemCardCreateRequest.class))).thenReturn(tandemCardResponse);
        doNothing().when(dbService).saveCardRequest(Mockito.any(HouseAccountCreditCardRequest.class));

        cardService.updateCard(testData.createCard(),new Date().toString());

    }

    //Update card method returns void. There is nothing to assert but this tests the if condition when card status is NULL
    @Test
    public void testUpdateCardStatusNull() throws ApplicationException{
        when(dbService.findByCrossReferenceNumber(Mockito.anyString())).thenReturn(testData.createHouseAccountCreditAccount());
        when(tandemService.submitTandemRequest(Mockito.any(TandemCardCreateRequest.class))).thenReturn(tandemCardResponse);
        doNothing().when(dbService).saveCardRequest(Mockito.any(HouseAccountCreditCardRequest.class));

        Card card = testData.createCard();
        card.setCardStatus(null);
        cardService.updateCard(card,new Date().toString());

    }

    @Test
    public void testGetCard() throws ApplicationException{
        when(mapperService.convertDBHouseAccountToCard(Mockito.any(HouseAccountCreditCardAccount.class))).thenReturn(testData.createCard());

        Card card = cardService.getCard(testData.createHouseAccountCreditAccount());

        assertNotNull(card);
    }


    @Test(expected = InternalServerException.class)
    public void testUpdateCardRequestException() throws ApplicationException{
        when(dbService.findByCrossReferenceNumber(Mockito.anyString())).thenReturn(testData.createHouseAccountCreditAccount());
        doThrow(Exception.class).when(dbService).updateCardRequest(Mockito.anyInt(),Mockito.anyString(),Mockito.any(Date.class),Mockito.anyInt());

        cardService.updateCardRequest(testData.createCardRequest());
    }

    @Test
    public void testUpdateCardRequest() throws ApplicationException{
        when(dbService.findByCrossReferenceNumber(Mockito.anyString())).thenReturn(testData.createHouseAccountCreditAccount());
        doNothing().when(dbService).updateCardRequest(Mockito.anyInt(),Mockito.anyString(),Mockito.any(Date.class),Mockito.anyInt());

        cardService.updateCardRequest(testData.createCardRequest());
    }


}
