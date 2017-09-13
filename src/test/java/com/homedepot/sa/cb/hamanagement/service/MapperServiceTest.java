package com.homedepot.sa.cb.hamanagement.service;

import com.homedepot.sa.cb.hamanagement.TestData;
import com.homedepot.sa.cb.hamanagement.model.api.*;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardAccount;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardRequest;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardSpendingFrequency;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCustomerAddress;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardDisableRequest;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardRequest;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardUpdateRequest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by mxm8528 on 1/19/17.
 */


public class MapperServiceTest {

    @InjectMocks
    private MapperService mapperService;


    private TestData testData;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
       this.testData = new TestData();
    }

    @Test
    public void testConvertCardRequestToDBHouseAccountRequestAddressLine2NullAndCardLimitNull()
    {
        CardRequest request = testData.createCardRequest();
        request.getShippingAddress().setAddressLine2(null);
        request.getCard().setLimit(null);

        HouseAccountCreditCardRequest houseAccountCreditCardRequest = mapperService.convertCardRequestToDBHouseAccountRequest(request,null);
        assertNotNull(houseAccountCreditCardRequest);
        assertTrue(houseAccountCreditCardRequest.getStatusFlag()==CardRequestStatusType.RECEIVED.getCode());
        assertTrue(houseAccountCreditCardRequest.getRequestorUserId().equals(request.getUserId()));
        assertTrue(houseAccountCreditCardRequest.getHouseAccountCreditCardAccounts().size() == 1);
        assertTrue(houseAccountCreditCardRequest.getHouseAccountCreditCardAccounts().get(0).getHouseAccountCustomerAddress().size() == 1);

        HouseAccountCreditCardAccount houseAccountCreditCardAccount = houseAccountCreditCardRequest.getHouseAccountCreditCardAccounts().get(0);
        assertTrue(houseAccountCreditCardAccount.getBrandName().equals(request.getCard().getBrandName().getCode()));
        assertTrue(houseAccountCreditCardAccount.getBuyerId().equals(request.getCard().getBuyerId()));
        assertTrue(houseAccountCreditCardAccount.getCardHolderFirstName().equals(request.getCard().getCardHolderFirstName()));
        assertTrue(houseAccountCreditCardAccount.getCardHolderLastName().equals(request.getCard().getCardHolderLastName()));
        assertTrue(houseAccountCreditCardAccount.geteReceiptEmail().equals(request.getCard().geteReceiptEmail()));
        assertTrue(houseAccountCreditCardAccount.getLastUpdatedSysUserId().equals(request.getUserId()));
        assertTrue(houseAccountCreditCardAccount.getNickName().equals(request.getCard().getNickName()));
        assertTrue(houseAccountCreditCardAccount.getServiceAccountName().equals(request.getCard().getServiceAccountName()));
        assertTrue(String.valueOf(houseAccountCreditCardAccount.getServiceAccountNumber()).equals(request.getCard().getServiceAccountNumber()));
        assertTrue(houseAccountCreditCardAccount.getCardHolderXrefNumber().equals(request.getCard().getCrossReferenceNumber()));
        assertTrue(houseAccountCreditCardAccount.getSourceSystem().equals(request.getCard().getSourceSystem().toString()));
        assertTrue(houseAccountCreditCardAccount.getStatusCode()==request.getCard().getCardStatus().getCode());

        HouseAccountCustomerAddress houseAccountCustomerAddress = houseAccountCreditCardAccount.getHouseAccountCustomerAddress().get(0);
        assertTrue(houseAccountCustomerAddress.getCompanyName().equals(request.getShippingAddress().getCompanyName()));
        assertTrue(houseAccountCustomerAddress.getAddressLine1().equals(request.getShippingAddress().getAddressLine1()));
        assertTrue(houseAccountCustomerAddress.getCity().equals(request.getShippingAddress().getCity()));
        assertTrue(houseAccountCustomerAddress.getState().equals(request.getShippingAddress().getStateCode()));
        assertTrue(houseAccountCustomerAddress.getPostalCode().equals(request.getShippingAddress().getZip()));

        assertNull(houseAccountCreditCardAccount.getHouseAccountCreditCardSpendingFrequencies());

    }


    @Test
    public void testConvertCardRequestToDBHouseAccountRequestAddressLine2NotNullAndCardLimitNotNull()
    {
        CardRequest request = testData.createCardRequest();

        HouseAccountCreditCardRequest houseAccountCreditCardRequest = mapperService.convertCardRequestToDBHouseAccountRequest(request,null);
        assertNotNull(houseAccountCreditCardRequest);
        assertTrue(houseAccountCreditCardRequest.getStatusFlag()==CardRequestStatusType.RECEIVED.getCode());
        assertTrue(houseAccountCreditCardRequest.getRequestorUserId().equals(request.getUserId()));
        assertTrue(houseAccountCreditCardRequest.getHouseAccountCreditCardAccounts().size() == 1);
        assertTrue(houseAccountCreditCardRequest.getHouseAccountCreditCardAccounts().get(0).getHouseAccountCustomerAddress().size() == 1);

        HouseAccountCreditCardAccount houseAccountCreditCardAccount = houseAccountCreditCardRequest.getHouseAccountCreditCardAccounts().get(0);
        assertTrue(houseAccountCreditCardAccount.getBrandName().equals(request.getCard().getBrandName().getCode()));
        assertTrue(houseAccountCreditCardAccount.getBuyerId().equals(request.getCard().getBuyerId()));
        assertTrue(houseAccountCreditCardAccount.getCardHolderFirstName().equals(request.getCard().getCardHolderFirstName()));
        assertTrue(houseAccountCreditCardAccount.getCardHolderLastName().equals(request.getCard().getCardHolderLastName()));
        assertTrue(houseAccountCreditCardAccount.geteReceiptEmail().equals(request.getCard().geteReceiptEmail()));
        assertTrue(houseAccountCreditCardAccount.getLastUpdatedSysUserId().equals(request.getUserId()));
        assertTrue(houseAccountCreditCardAccount.getNickName().equals(request.getCard().getNickName()));
        assertTrue(houseAccountCreditCardAccount.getServiceAccountName().equals(request.getCard().getServiceAccountName()));
        assertTrue(String.valueOf(houseAccountCreditCardAccount.getServiceAccountNumber()).equals(request.getCard().getServiceAccountNumber()));
        assertTrue(houseAccountCreditCardAccount.getCardHolderXrefNumber().equals(request.getCard().getCrossReferenceNumber()));
        assertTrue(houseAccountCreditCardAccount.getSourceSystem().equals(request.getCard().getSourceSystem().toString()));
        assertTrue(houseAccountCreditCardAccount.getStatusCode()==request.getCard().getCardStatus().getCode());

        HouseAccountCustomerAddress houseAccountCustomerAddress = houseAccountCreditCardAccount.getHouseAccountCustomerAddress().get(0);
        assertTrue(houseAccountCustomerAddress.getCompanyName().equals(request.getShippingAddress().getCompanyName()));
        assertTrue(houseAccountCustomerAddress.getAddressLine1().equals(request.getShippingAddress().getAddressLine1()));
        assertTrue(houseAccountCustomerAddress.getAddressLine2().equals(request.getShippingAddress().getAddressLine2()));
        assertTrue(houseAccountCustomerAddress.getCity().equals(request.getShippingAddress().getCity()));
        assertTrue(houseAccountCustomerAddress.getState().equals(request.getShippingAddress().getStateCode()));
        assertTrue(houseAccountCustomerAddress.getPostalCode().equals(request.getShippingAddress().getZip()));

        HouseAccountCreditCardSpendingFrequency houseAccountCreditCardSpendingFrequency = houseAccountCreditCardAccount.getHouseAccountCreditCardSpendingFrequencies().get(0);
        assertTrue(houseAccountCreditCardSpendingFrequency.getSpendingLimitAmount()==request.getCard().getLimit().getAmount());
        assertTrue(houseAccountCreditCardSpendingFrequency.getFrequencyTypeCode()==request.getCard().getLimit().getFrequency().getCode());
    }


    @Test
    public void testConvertExistingCardRequestToDBHouseAccountRequest(){
        CardRequest request = testData.createCardRequest();
        HouseAccountCreditCardAccount account = testData.createHouseAccountCreditAccount();

        HouseAccountCreditCardRequest houseAccountCreditCardRequest = mapperService.convertCardRequestToDBHouseAccountRequest(request,account);
        assertNotNull(houseAccountCreditCardRequest);
        assertTrue(houseAccountCreditCardRequest.getRequestId()==account.getHouseAccountCreditCardRequest().getRequestId());
        assertTrue(houseAccountCreditCardRequest.getCreatedTimeStamp()==account.getHouseAccountCreditCardRequest().getCreatedTimeStamp());
        assertTrue(houseAccountCreditCardRequest.getStatusFlag()==CardRequestStatusType.RECEIVED.getCode());
        assertTrue(houseAccountCreditCardRequest.getRequestorUserId().equals(account.getHouseAccountCreditCardRequest().getRequestorUserId()));
        assertTrue(houseAccountCreditCardRequest.getHouseAccountCreditCardAccounts().size() == 1);
        assertTrue(houseAccountCreditCardRequest.getHouseAccountCreditCardAccounts().get(0).getHouseAccountCustomerAddress().size() == 1);

        HouseAccountCreditCardAccount houseAccountCreditCardAccount = houseAccountCreditCardRequest.getHouseAccountCreditCardAccounts().get(0);
        assertTrue(houseAccountCreditCardAccount.getBrandName().equals(request.getCard().getBrandName().getCode()));
        assertTrue(houseAccountCreditCardAccount.getBuyerId().equals(request.getCard().getBuyerId()));
        assertTrue(houseAccountCreditCardAccount.getCardHolderFirstName().equals(request.getCard().getCardHolderFirstName()));
        assertTrue(houseAccountCreditCardAccount.getCardHolderLastName().equals(request.getCard().getCardHolderLastName()));
        assertTrue(houseAccountCreditCardAccount.geteReceiptEmail().equals(request.getCard().geteReceiptEmail()));
        assertTrue(houseAccountCreditCardAccount.getLastUpdatedSysUserId().equals(request.getUserId()));
        assertTrue(houseAccountCreditCardAccount.getNickName().equals(request.getCard().getNickName()));
        assertTrue(houseAccountCreditCardAccount.getServiceAccountName().equals(request.getCard().getServiceAccountName()));
        assertTrue(String.valueOf(houseAccountCreditCardAccount.getServiceAccountNumber()).equals(request.getCard().getServiceAccountNumber()));
        assertTrue(houseAccountCreditCardAccount.getCardHolderXrefNumber().equals(request.getCard().getCrossReferenceNumber()));
        assertTrue(houseAccountCreditCardAccount.getSourceSystem().equals(request.getCard().getSourceSystem().toString()));
        assertTrue(houseAccountCreditCardAccount.getStatusCode()==request.getCard().getCardStatus().getCode());
        assertTrue(houseAccountCreditCardAccount.getCreatedTimeStamp()==account.getCreatedTimeStamp());

        HouseAccountCustomerAddress houseAccountCustomerAddress = houseAccountCreditCardAccount.getHouseAccountCustomerAddress().get(0);
        assertTrue(houseAccountCustomerAddress.getCompanyName().equals(request.getShippingAddress().getCompanyName()));
        assertTrue(houseAccountCustomerAddress.getAddressLine1().equals(request.getShippingAddress().getAddressLine1()));
        assertTrue(houseAccountCustomerAddress.getAddressLine2().equals(request.getShippingAddress().getAddressLine2()));
        assertTrue(houseAccountCustomerAddress.getCity().equals(request.getShippingAddress().getCity()));
        assertTrue(houseAccountCustomerAddress.getState().equals(request.getShippingAddress().getStateCode()));
        assertTrue(houseAccountCustomerAddress.getPostalCode().equals(request.getShippingAddress().getZip()));

        HouseAccountCreditCardSpendingFrequency houseAccountCreditCardSpendingFrequency = houseAccountCreditCardAccount.getHouseAccountCreditCardSpendingFrequencies().get(0);
        assertTrue(houseAccountCreditCardSpendingFrequency.getSpendingLimitAmount()==request.getCard().getLimit().getAmount());
        assertTrue(houseAccountCreditCardSpendingFrequency.getFrequencyTypeCode()==request.getCard().getLimit().getFrequency().getCode());

    }

    @Test
    public void testConvertCardUpdateRequestToTandemCardDisableRequest(){
        Card card = testData.createCard();
        HouseAccountCreditCardAccount houseAccountCreditCardAccount = new HouseAccountCreditCardAccount();
        houseAccountCreditCardAccount.setBuyerId("BY12345");
        houseAccountCreditCardAccount.setServiceAccountNumber(45367465L);
        houseAccountCreditCardAccount.setSourceSystem(SystemType.ERP.toString());
        TandemCardDisableRequest tandemCardDisableRequest = mapperService.convertCardUpdateRequestToTandemCardDisableRequest(card,houseAccountCreditCardAccount);
        assertTrue(tandemCardDisableRequest.getAuthBuyerId().equals(houseAccountCreditCardAccount.getBuyerId()));
        assertNull(tandemCardDisableRequest.getAddressLine1());
        assertNull(tandemCardDisableRequest.getAddressLine2());
        assertNull(tandemCardDisableRequest.getCity());
        assertNull(tandemCardDisableRequest.getState());
        assertNull(tandemCardDisableRequest.getZip());
        assertTrue(tandemCardDisableRequest.getBrandName().equals(card.getBrandName().toString()));
        assertTrue(tandemCardDisableRequest.getBusinessName().equals(card.getServiceAccountName()));
        assertTrue(tandemCardDisableRequest.getEmailAddress().equals(card.geteReceiptEmail()));
        assertTrue(tandemCardDisableRequest.getNickname().equals(card.getNickName()));
        assertNull(tandemCardDisableRequest.getExpeditedShippingCode());
        assertTrue(tandemCardDisableRequest.getSystemId().equals(card.getSourceSystem().toString()));
        assertTrue(tandemCardDisableRequest.getSysUserId().equals(card.getUserId()));
        assertTrue(tandemCardDisableRequest.getFirstName().equals(card.getCardHolderFirstName()));
        assertTrue(tandemCardDisableRequest.getLastName().equals(card.getCardHolderLastName()));
    }

    @Test
    public void testConvertCardUpdateRequestToTandemCardDisableRequestWithNoBrandName(){
        Card card = testData.createCard();
        card.setBrandName(null);
        HouseAccountCreditCardAccount houseAccountCreditCardAccount = new HouseAccountCreditCardAccount();
        houseAccountCreditCardAccount.setBuyerId("BY12345");
        houseAccountCreditCardAccount.setServiceAccountNumber(45367465L);
        houseAccountCreditCardAccount.setSourceSystem(SystemType.ERP.toString());
        TandemCardDisableRequest tandemCardDisableRequest = mapperService.convertCardUpdateRequestToTandemCardDisableRequest(card,houseAccountCreditCardAccount);
        assertTrue(tandemCardDisableRequest.getAuthBuyerId().equals(houseAccountCreditCardAccount.getBuyerId()));
        assertNull(tandemCardDisableRequest.getAddressLine1());
        assertNull(tandemCardDisableRequest.getAddressLine2());
        assertNull(tandemCardDisableRequest.getCity());
        assertNull(tandemCardDisableRequest.getState());
        assertNull(tandemCardDisableRequest.getZip());
        assertNull(tandemCardDisableRequest.getBrandName());
        assertTrue(tandemCardDisableRequest.getBusinessName().equals(card.getServiceAccountName()));
        assertTrue(tandemCardDisableRequest.getEmailAddress().equals(card.geteReceiptEmail()));
        assertTrue(tandemCardDisableRequest.getNickname().equals(card.getNickName()));
        assertNull(tandemCardDisableRequest.getExpeditedShippingCode());
        assertTrue(tandemCardDisableRequest.getSystemId().equals(card.getSourceSystem().toString()));
        assertTrue(tandemCardDisableRequest.getSysUserId().equals(card.getUserId()));
        assertTrue(tandemCardDisableRequest.getFirstName().equals(card.getCardHolderFirstName()));
        assertTrue(tandemCardDisableRequest.getLastName().equals(card.getCardHolderLastName()));
    }


    @Test
    public void testConvertCardUpdateRequestToTandemCardUpdateRequestWithNoBrandName(){
        Card card = testData.createCard();
        card.setBrandName(null);
        HouseAccountCreditCardAccount houseAccountCreditCardAccount = new HouseAccountCreditCardAccount();
        houseAccountCreditCardAccount.setBuyerId("BY12345");
        houseAccountCreditCardAccount.setServiceAccountNumber(45367465L);
        houseAccountCreditCardAccount.setSourceSystem(SystemType.ERP.toString());
        TandemCardUpdateRequest tandemCardDisableRequest = mapperService.convertCardUpdateRequestToTandemCardUpdateRequest(card,houseAccountCreditCardAccount);
        assertTrue(tandemCardDisableRequest.getAuthBuyerId().equals(houseAccountCreditCardAccount.getBuyerId()));
        assertNull(tandemCardDisableRequest.getAddressLine1());
        assertNull(tandemCardDisableRequest.getAddressLine2());
        assertNull(tandemCardDisableRequest.getCity());
        assertNull(tandemCardDisableRequest.getState());
        assertNull(tandemCardDisableRequest.getZip());
        assertNull(tandemCardDisableRequest.getBrandName());
        assertTrue(tandemCardDisableRequest.getBusinessName().equals(card.getServiceAccountName()));
        assertTrue(tandemCardDisableRequest.getEmailAddress().equals(card.geteReceiptEmail()));
        assertTrue(tandemCardDisableRequest.getNickname().equals(card.getNickName()));
        assertNull(tandemCardDisableRequest.getExpeditedShippingCode());
        assertTrue(tandemCardDisableRequest.getSystemId().equals(card.getSourceSystem().toString()));
        assertTrue(tandemCardDisableRequest.getSysUserId().equals(card.getUserId()));
        assertTrue(tandemCardDisableRequest.getFirstName().equals(card.getCardHolderFirstName()));
        assertTrue(tandemCardDisableRequest.getLastName().equals(card.getCardHolderLastName()));
    }


    @Test
    public void testConvertCardUpdateRequestToTandemCardUpdateRequest(){
        Card card = testData.createCard();
        HouseAccountCreditCardAccount houseAccountCreditCardAccount = new HouseAccountCreditCardAccount();
        houseAccountCreditCardAccount.setBuyerId("BY12345");
        houseAccountCreditCardAccount.setServiceAccountNumber(45367465L);
        houseAccountCreditCardAccount.setSourceSystem(SystemType.ERP.toString());
        TandemCardUpdateRequest tandemCardDisableRequest = mapperService.convertCardUpdateRequestToTandemCardUpdateRequest(card,houseAccountCreditCardAccount);
        assertTrue(tandemCardDisableRequest.getAuthBuyerId().equals(houseAccountCreditCardAccount.getBuyerId()));
        assertNull(tandemCardDisableRequest.getAddressLine1());
        assertNull(tandemCardDisableRequest.getAddressLine2());
        assertNull(tandemCardDisableRequest.getCity());
        assertNull(tandemCardDisableRequest.getState());
        assertNull(tandemCardDisableRequest.getZip());
        assertTrue(tandemCardDisableRequest.getBrandName().equals(card.getBrandName().toString()));
        assertTrue(tandemCardDisableRequest.getBusinessName().equals(card.getServiceAccountName()));
        assertTrue(tandemCardDisableRequest.getEmailAddress().equals(card.geteReceiptEmail()));
        assertTrue(tandemCardDisableRequest.getNickname().equals(card.getNickName()));
        assertNull(tandemCardDisableRequest.getExpeditedShippingCode());
        assertTrue(tandemCardDisableRequest.getSystemId().equals(card.getSourceSystem().toString()));
        assertTrue(tandemCardDisableRequest.getSysUserId().equals(card.getUserId()));
        assertTrue(tandemCardDisableRequest.getFirstName().equals(card.getCardHolderFirstName()));
        assertTrue(tandemCardDisableRequest.getLastName().equals(card.getCardHolderLastName()));
    }

    @Test
    public void testConvertCardCreateRequestToTandemCardUpdateRequest(){
        CardRequest cardRequest = testData.createCardRequest();
        TandemCardRequest tandemCardRequest = mapperService.convertCardRequestToTandemCardRequest(cardRequest);
        assertTrue(tandemCardRequest.getAuthBuyerId().equals(cardRequest.getCard().getBuyerId()));
        assertTrue(tandemCardRequest.getServiceAccountNumber().equals(cardRequest.getCard().getServiceAccountNumber()));
        assertTrue(tandemCardRequest.getAddressLine1().equals(cardRequest.getShippingAddress().getAddressLine1()));
        assertTrue(tandemCardRequest.getAddressLine2().equals(cardRequest.getShippingAddress().getAddressLine2()));
        assertTrue(tandemCardRequest.getCity().equals(cardRequest.getShippingAddress().getCity()));
        assertTrue(tandemCardRequest.getState().equals(cardRequest.getShippingAddress().getStateCode()));
        assertTrue(tandemCardRequest.getZip().equals(cardRequest.getShippingAddress().getZip()));
        assertTrue(tandemCardRequest.getBrandName().equals(cardRequest.getCard().getBrandName().toString()));
        assertTrue(tandemCardRequest.getBusinessName().equals(cardRequest.getShippingAddress().getCompanyName()));
        assertTrue(tandemCardRequest.getEmailAddress().equals(cardRequest.getCard().geteReceiptEmail()));
        assertTrue(tandemCardRequest.getNickname().equals(cardRequest.getCard().getNickName()));
        assertTrue(tandemCardRequest.getExpeditedShippingCode().equals(cardRequest.getShippingMode().toString()));
        assertTrue(tandemCardRequest.getSystemId().equals(cardRequest.getCard().getSourceSystem().toString()));
        assertTrue(tandemCardRequest.getSysUserId().equals(cardRequest.getUserId()));
        assertTrue(tandemCardRequest.getFirstName().equals(cardRequest.getCard().getCardHolderFirstName()));
        assertTrue(tandemCardRequest.getLastName().equals(cardRequest.getCard().getCardHolderLastName()));
    }

    @Test
    public void testConvertDBHouseAccountToCard(){
        HouseAccountCreditCardAccount account = testData.createHouseAccountCreditAccount();
        Card card = mapperService.convertDBHouseAccountToCard(account);
        assertTrue(card.getBrandName().getCode().equals(account.getBrandName()));
        assertTrue(card.getBuyerId().equals(account.getBuyerId()));
        assertTrue(card.getCardHolderFirstName().equals(account.getCardHolderFirstName()));
        assertTrue(card.getCardHolderLastName().equals(account.getCardHolderLastName()));
        assertTrue(card.getCrossReferenceNumber().equals(account.getCardHolderXrefNumber()));
        assertTrue(card.geteReceiptEmail().equals(account.geteReceiptEmail()));
        assertTrue(card.getNickName().equals(account.getNickName()));
        assertTrue(card.getServiceAccountName().equals(account.getServiceAccountName()));
        assertTrue(card.getServiceAccountNumber().equals(String.valueOf(account.getServiceAccountNumber())));
        assertTrue(card.getSourceSystem().toString().equals(account.getSourceSystem()));
    }


    @Test
    public void testConvertDBHouseAccountToCardNickNameEmpty(){
        HouseAccountCreditCardAccount account = testData.createHouseAccountCreditAccount();
        account.setNickName("");
        Card card = mapperService.convertDBHouseAccountToCard(account);
        assertTrue(card.getBrandName().getCode().equals(account.getBrandName()));
        assertTrue(card.getBuyerId().equals(account.getBuyerId()));
        assertTrue(card.getCardHolderFirstName().equals(account.getCardHolderFirstName()));
        assertTrue(card.getCardHolderLastName().equals(account.getCardHolderLastName()));
        assertTrue(card.getCrossReferenceNumber().equals(account.getCardHolderXrefNumber()));
        assertTrue(card.geteReceiptEmail().equals(account.geteReceiptEmail()));
        assertNull(card.getNickName());
        assertTrue(card.getServiceAccountName().equals(account.getServiceAccountName()));
        assertTrue(card.getServiceAccountNumber().equals(String.valueOf(account.getServiceAccountNumber())));
        assertTrue(card.getSourceSystem().toString().equals(account.getSourceSystem()));
        assertTrue(card.getLimit().getFrequency().getCode()==account.getHouseAccountCreditCardSpendingFrequencies().get(0).getFrequencyTypeCode());
        assertTrue(card.getLimit().getAmount()==account.getHouseAccountCreditCardSpendingFrequencies().get(0).getSpendingLimitAmount());
    }

    @Test
    public void testConvertDBHouseAccountToCardNickNameNull(){
        HouseAccountCreditCardAccount account = testData.createHouseAccountCreditAccount();
        account.setNickName(null);
        Card card = mapperService.convertDBHouseAccountToCard(account);
        assertTrue(card.getBrandName().getCode().equals(account.getBrandName()));
        assertTrue(card.getBuyerId().equals(account.getBuyerId()));
        assertTrue(card.getCardHolderFirstName().equals(account.getCardHolderFirstName()));
        assertTrue(card.getCardHolderLastName().equals(account.getCardHolderLastName()));
        assertTrue(card.getCrossReferenceNumber().equals(account.getCardHolderXrefNumber()));
        assertTrue(card.geteReceiptEmail().equals(account.geteReceiptEmail()));
        assertNull(card.getNickName());
        assertTrue(card.getServiceAccountName().equals(account.getServiceAccountName()));
        assertTrue(card.getServiceAccountNumber().equals(String.valueOf(account.getServiceAccountNumber())));
        assertTrue(card.getSourceSystem().toString().equals(account.getSourceSystem()));
        assertTrue(card.getLimit().getFrequency().getCode()==account.getHouseAccountCreditCardSpendingFrequencies().get(0).getFrequencyTypeCode());
        assertTrue(card.getLimit().getAmount()==account.getHouseAccountCreditCardSpendingFrequencies().get(0).getSpendingLimitAmount());
    }

    @Test
    public void testConvertDBHouseAccountToCardSpendingFrequencyIsNull(){
        HouseAccountCreditCardAccount account = testData.createHouseAccountCreditAccount();
        account.setHouseAccountCreditCardSpendingFrequencies(null);
        Card card = mapperService.convertDBHouseAccountToCard(account);
        assertTrue(card.getBrandName().getCode().equals(account.getBrandName()));
        assertTrue(card.getBuyerId().equals(account.getBuyerId()));
        assertTrue(card.getCardHolderFirstName().equals(account.getCardHolderFirstName()));
        assertTrue(card.getCardHolderLastName().equals(account.getCardHolderLastName()));
        assertTrue(card.getCrossReferenceNumber().equals(account.getCardHolderXrefNumber()));
        assertTrue(card.geteReceiptEmail().equals(account.geteReceiptEmail()));
        assertTrue(card.getNickName().equals(account.getNickName()));
        assertTrue(card.getServiceAccountName().equals(account.getServiceAccountName()));
        assertTrue(card.getServiceAccountNumber().equals(String.valueOf(account.getServiceAccountNumber())));
        assertTrue(card.getSourceSystem().toString().equals(account.getSourceSystem()));
        assertNull(card.getLimit());
    }

    @Test
    public void testConvertDBHouseAccountToCardSpendingFrequencyIsEmpty(){
        HouseAccountCreditCardAccount account = testData.createHouseAccountCreditAccount();
        List<HouseAccountCreditCardSpendingFrequency> frequencyList = new ArrayList<>();
        account.setHouseAccountCreditCardSpendingFrequencies(frequencyList);
        Card card = mapperService.convertDBHouseAccountToCard(account);
        assertTrue(card.getBrandName().getCode().equals(account.getBrandName()));
        assertTrue(card.getBuyerId().equals(account.getBuyerId()));
        assertTrue(card.getCardHolderFirstName().equals(account.getCardHolderFirstName()));
        assertTrue(card.getCardHolderLastName().equals(account.getCardHolderLastName()));
        assertTrue(card.getCrossReferenceNumber().equals(account.getCardHolderXrefNumber()));
        assertTrue(card.geteReceiptEmail().equals(account.geteReceiptEmail()));
        assertTrue(card.getNickName().equals(account.getNickName()));
        assertTrue(card.getServiceAccountName().equals(account.getServiceAccountName()));
        assertTrue(card.getServiceAccountNumber().equals(String.valueOf(account.getServiceAccountNumber())));
        assertTrue(card.getSourceSystem().toString().equals(account.getSourceSystem()));
        assertNull(card.getLimit());
    }

    @Test
    public void testConvertCardToHouseAccountCreditCardAccount(){
        Card card = testData.createCard();
        HouseAccountCreditCardAccount houseAccountCreditCardAccount = testData.createHouseAccountCreditAccount();
        houseAccountCreditCardAccount = mapperService.convertCardToHouseAccountCreditCardAccount(houseAccountCreditCardAccount,card);
        assertTrue(houseAccountCreditCardAccount.getCardHolderFirstName().equals(card.getCardHolderFirstName()));
        assertTrue(houseAccountCreditCardAccount.getBuyerId().equals(houseAccountCreditCardAccount.getBuyerId()));
        assertTrue(houseAccountCreditCardAccount.getServiceAccountNumber().equals(houseAccountCreditCardAccount.getServiceAccountNumber()));
        assertTrue(houseAccountCreditCardAccount.getBrandName().equals(card.getBrandName().getCode()));
        assertTrue(houseAccountCreditCardAccount.getServiceAccountName().equals(card.getServiceAccountName()));
        assertTrue(houseAccountCreditCardAccount.getNickName().equals(card.getNickName()));
        assertTrue(houseAccountCreditCardAccount.geteReceiptEmail().equals(card.geteReceiptEmail()));
        assertTrue(houseAccountCreditCardAccount.getCardHolderLastName().equals(card.getCardHolderLastName()));
        assertTrue(houseAccountCreditCardAccount.getCardHolderXrefNumber().equals(houseAccountCreditCardAccount.getCardHolderXrefNumber()));
        assertTrue(houseAccountCreditCardAccount.getLastUpdatedSysUserId().equals(card.getUserId()));
        assertTrue(houseAccountCreditCardAccount.getSourceSystem().equals(houseAccountCreditCardAccount.getSourceSystem()));
        assertNotNull(houseAccountCreditCardAccount.getCreatedTimeStamp());
        assertTrue(houseAccountCreditCardAccount.getStatusCode()==card.getCardStatus().getCode());
        assertTrue(houseAccountCreditCardAccount.getHouseAccountCreditCardSpendingFrequencies().get(0).getSpendingLimitAmount()==card.getLimit().getAmount());
        assertTrue(houseAccountCreditCardAccount.getHouseAccountCreditCardSpendingFrequencies().get(0).getFrequencyTypeCode()==card.getLimit().getFrequency().getCode());
    }

    @Test
    public void testConvertCardToHouseAccountCreditCardAccountWithNullValues(){
        Card card = new Card();
        card.setUserId("test");
        HouseAccountCreditCardAccount houseAccountCreditCardAccount = testData.createHouseAccountCreditAccount();
        houseAccountCreditCardAccount = mapperService.convertCardToHouseAccountCreditCardAccount(houseAccountCreditCardAccount,card);
        assertTrue(houseAccountCreditCardAccount.getServiceAccountNumber().equals(houseAccountCreditCardAccount.getServiceAccountNumber()));
        assertNotNull(houseAccountCreditCardAccount.getCardHolderFirstName());
        assertTrue(houseAccountCreditCardAccount.getBuyerId().equals(houseAccountCreditCardAccount.getBuyerId()));
        assertNotNull(houseAccountCreditCardAccount.getBrandName());
        assertNotNull(houseAccountCreditCardAccount.getServiceAccountName());
        assertNotNull(houseAccountCreditCardAccount.getNickName());
        assertNotNull(houseAccountCreditCardAccount.geteReceiptEmail());
        assertNotNull(houseAccountCreditCardAccount.getCardHolderLastName());
        assertTrue(houseAccountCreditCardAccount.getCardHolderXrefNumber().equals(houseAccountCreditCardAccount.getCardHolderXrefNumber()));
        assertTrue(houseAccountCreditCardAccount.getLastUpdatedSysUserId().equals(card.getUserId()));
        assertTrue(houseAccountCreditCardAccount.getSourceSystem().equals(houseAccountCreditCardAccount.getSourceSystem()));
        assertNotNull(houseAccountCreditCardAccount.getCreatedTimeStamp());
        assertNotNull(houseAccountCreditCardAccount.getStatusCode());
        assertNotNull(houseAccountCreditCardAccount.getHouseAccountCreditCardSpendingFrequencies());

    }









}
