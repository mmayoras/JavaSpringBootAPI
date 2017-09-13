package com.homedepot.sa.cb.hamanagement;

import com.homedepot.sa.cb.hamanagement.model.api.*;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardAccount;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardRequest;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardSpendingFrequency;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardRequest;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardResponse;
import com.ibm.db2.jcc.DBTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by n84qvs on 3/13/17.
 */
public class TestData {


    public HouseAccountCreditCardAccount createHouseAccountCreditAccount(){
        HouseAccountCreditCardAccount account = new HouseAccountCreditCardAccount();
        account.setBuyerId("BY123456");
        account.setSourceSystem("ERP");
        account.setServiceAccountNumber(56748567L);
        account.setBrandName("100");
        account.setCardHolderFirstName("TestFirstName");
        account.setCardHolderLastName("TestLastName");
        account.setCardHolderXrefNumber("1234567890123456");
        account.setCreatedTimeStamp(new Date());
        account.setLastUpdatedTimeStamp(new Date());
        account.seteReceiptEmail("test@test.com");
        account.setNickName("test");
        account.setServiceAccountName("TestServiceAccountName");
        account.setLastUpdatedSysUserId("unitest");
        account.setCreatedTimeStamp(new DBTimestamp(new Date().getTime()));
        HouseAccountCreditCardSpendingFrequency frequency = new HouseAccountCreditCardSpendingFrequency();
        frequency.setFrequencyTypeCode(100);
        frequency.setSpendingLimitAmount(10000);
        List<HouseAccountCreditCardSpendingFrequency> frequencyList = new ArrayList<>();
        frequencyList.add(frequency);
        account.setHouseAccountCreditCardSpendingFrequencies(frequencyList);
        account.setHouseAccountCreditCardRequest(createHouseAccountCreditCardRequest());

        return account;
    }

    public Card createCard(){
        Card card = new Card();
        card.setBrandName(BrandType.BARNETT);
        card.setServiceAccountName("BUSINESS");
        card.setBuyerId("HI123456");
        card.setCardHolderFirstName("CardRequestTestFirstName");
        card.setCardHolderLastName("CardRequestTestLastName");
        card.setSourceSystem(SystemType.ERP);
        card.setServiceAccountNumber("134567");
        card.seteReceiptEmail("test.test@gmail.com");
        card.setCrossReferenceNumber("1234567890123456");
        card.setCardStatus(CardStatusType.ACTIVE);
        CardLimit validCardLimit = new CardLimit();
        validCardLimit.setAmount(85000.00);
        validCardLimit.setFrequency(LimitFrequencyType.DAILY);
        card.setLimit(validCardLimit);
        card.setNickName("TEST HELLO 325");
        card.setUserId("unittest");
        card.setExpirationDate("0319");
        return card;
    }

    public CardRequest createCardRequest(){
        CardRequest request = new CardRequest();
        request.setShippingAddress(createAddress());
        request.setCard(createCard());
        request.setUserId("test");
        request.setShippingMode(ShippingType.STANDARD);
        request.setCardRequestStatus(CardRequestStatusType.CREATED);
        return request;
    }

    public Address createAddress(){
        Address address = new Address();
        address.setCompanyName("The Home Depot");
        address.setAddressLine1("3375 SpringHill Pkwy");
        address.setAddressLine2("Apt 325");
        address.setCity("Smyrna");
        address.setStateCode("GA");
        address.setZip("30080");
        return address;
    }

    public TandemCardRequest createTandemCardRequest(){
        TandemCardRequest cardRequest = new TandemCardRequest();
        cardRequest.setServiceAccountNumber("1234567890");
        cardRequest.setAddressLine1("Address Line 1");
        cardRequest.setAuthBuyerId("AB12345678");
        cardRequest.setBrandName("ISS");
        cardRequest.setCity("ATLANTA");
        cardRequest.setBusinessName("UNIT TESTING");
        cardRequest.setEmailAddress("test@test.com");
        cardRequest.setExpeditedShippingCode("0");
        cardRequest.setNickname("test");
        cardRequest.setState("GA");
        cardRequest.setSystemId("test");
        cardRequest.setSysUserId("hatest");
        cardRequest.setZip("30080");
        return  cardRequest;

    }

    public HouseAccountCreditCardRequest createHouseAccountCreditCardRequest(){
        HouseAccountCreditCardRequest houseAccountCreditCardRequest = new HouseAccountCreditCardRequest();
        houseAccountCreditCardRequest.setRequestId(100);
        houseAccountCreditCardRequest.setRequestorUserId("TEST");
        houseAccountCreditCardRequest.setCreatedTimeStamp(new DBTimestamp(new Date().getTime()));
        return houseAccountCreditCardRequest;
    }

    public TandemCardResponse createTandemResponse(){
        TandemCardResponse tandemCardResponse = new TandemCardResponse();
        tandemCardResponse.setSysId("ISS");
        tandemCardResponse.setServiceAccountNumber("1234567890");
        tandemCardResponse.setExpirationDate("mmyy");
        tandemCardResponse.setStatusCode("0");
        tandemCardResponse.setAuthBuyerId("AB12345");
        tandemCardResponse.setCrossReferenceNumber("1234567890123456");
        tandemCardResponse.setStatusMessage("Request Successful");
        return tandemCardResponse;
    }


    public String generateServiceAccountNumber(){
        return new Random().ints(1,90,99).iterator().next()+LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy"));
    }

    public String generateBuyerId(){
        return new Random().ints(1,0,9).iterator().next()+LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS"));

    }

    public static void main(String args[]){
        System.out.println(new Random().ints(1,90,99).iterator().next()+LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy")));
        System.out.println(new Random().ints(1,0,9).iterator().next()+LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS")));
    }

}
