package com.homedepot.sa.cb.hamanagement.service;

import com.homedepot.sa.cb.hamanagement.model.api.*;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardAccount;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardSpendingFrequency;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCustomerAddress;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardRequest;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardCreateRequest;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardDisableRequest;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardUpdateRequest;
import com.ibm.db2.jcc.DBTimestamp;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by n84qvs on 1/10/17.
 */
@Service
public class MapperService {

    public TandemCardCreateRequest convertCardRequestToTandemCardRequest(CardRequest cardRequest){
        TandemCardCreateRequest request = new TandemCardCreateRequest();
        request.setSystemId(cardRequest.getCard().getSourceSystem().toString());
        request.setServiceAccountNumber(cardRequest.getCard().getServiceAccountNumber());
        request.setAuthBuyerId(cardRequest.getCard().getBuyerId());
        request.setSysUserId(cardRequest.getUserId());
        request.setBusinessName(cardRequest.getShippingAddress().getCompanyName());
        request.setNickname(cardRequest.getCard().getNickName());
        request.setBrandName(cardRequest.getCard().getBrandName().getDescription());
        request.setAddressLine1(cardRequest.getShippingAddress().getAddressLine1());
        request.setAddressLine2(cardRequest.getShippingAddress().getAddressLine2());
        request.setCity(cardRequest.getShippingAddress().getCity());
        request.setState(cardRequest.getShippingAddress().getStateCode());
        request.setZip(cardRequest.getShippingAddress().getZip());
        request.setExpeditedShippingCode(cardRequest.getShippingMode().toString());
        request.setEmailAddress(cardRequest.getCard().geteReceiptEmail());
        request.setFirstName(cardRequest.getCard().getCardHolderFirstName());
        request.setLastName(cardRequest.getCard().getCardHolderLastName());
        return request;
    }

    public TandemCardUpdateRequest convertCardUpdateRequestToTandemCardUpdateRequest(Card card, HouseAccountCreditCardAccount houseAccountCreditCardAccount){
        TandemCardUpdateRequest tandemCardUpdateRequest = new TandemCardUpdateRequest();
        tandemCardUpdateRequest.setSystemId(houseAccountCreditCardAccount.getSourceSystem());
        tandemCardUpdateRequest.setAuthBuyerId(houseAccountCreditCardAccount.getBuyerId());
        tandemCardUpdateRequest.setServiceAccountNumber(String.valueOf(houseAccountCreditCardAccount.getServiceAccountNumber()));
        tandemCardUpdateRequest.setSysUserId(card.getUserId());
        tandemCardUpdateRequest.setBusinessName(card.getServiceAccountName());
        tandemCardUpdateRequest.setNickname(card.getNickName());
        tandemCardUpdateRequest.setFirstName(card.getCardHolderFirstName());
        tandemCardUpdateRequest.setLastName(card.getCardHolderLastName());
        if(null!= card.getBrandName()){
            tandemCardUpdateRequest.setBrandName(card.getBrandName().getDescription());
        }
        tandemCardUpdateRequest.setEmailAddress(card.geteReceiptEmail());
        return tandemCardUpdateRequest;

    }


    public TandemCardDisableRequest convertCardUpdateRequestToTandemCardDisableRequest(Card card, HouseAccountCreditCardAccount houseAccountCreditCardAccount){
        TandemCardDisableRequest tandemCardDisableRequest = new TandemCardDisableRequest();
        tandemCardDisableRequest.setSystemId(houseAccountCreditCardAccount.getSourceSystem());
        tandemCardDisableRequest.setAuthBuyerId(houseAccountCreditCardAccount.getBuyerId());
        tandemCardDisableRequest.setServiceAccountNumber(String.valueOf(houseAccountCreditCardAccount.getServiceAccountNumber()));
        tandemCardDisableRequest.setSysUserId(card.getUserId());
        tandemCardDisableRequest.setBusinessName(card.getServiceAccountName());
        tandemCardDisableRequest.setNickname(card.getNickName());
        tandemCardDisableRequest.setFirstName(card.getCardHolderFirstName());
        tandemCardDisableRequest.setLastName(card.getCardHolderLastName());
        if(null!= card.getBrandName()){
            tandemCardDisableRequest.setBrandName(card.getBrandName().getDescription());
        }
        tandemCardDisableRequest.setEmailAddress(card.geteReceiptEmail());
        return tandemCardDisableRequest;

    }

    public HouseAccountCreditCardRequest convertCardRequestToDBHouseAccountRequest(CardRequest cardRequest, HouseAccountCreditCardAccount account){

        DBTimestamp timeStamp = new DBTimestamp(new Date().getTime());


        // Populate House Account Card Request
        HouseAccountCreditCardRequest dbCardRequest = new HouseAccountCreditCardRequest();
        dbCardRequest.setLastUpdatedSysUserId(cardRequest.getUserId());
        dbCardRequest.setLastUpdatedTimeStamp(timeStamp);
        dbCardRequest.setStatusFlag(CardRequestStatusType.RECEIVED.getCode());

        // Populate House Account Credit Card
        HouseAccountCreditCardAccount crdb = new HouseAccountCreditCardAccount();
        crdb.setLastUpdatedSysUserId(cardRequest.getUserId());
        crdb.setLastUpdatedTimeStamp(timeStamp);
        crdb.setBrandName(cardRequest.getCard().getBrandName().getCode());
        crdb.setBuyerId(cardRequest.getCard().getBuyerId());
        crdb.setCardHolderFirstName(cardRequest.getCard().getCardHolderFirstName());
        crdb.setCardHolderLastName(cardRequest.getCard().getCardHolderLastName());
        crdb.setCardHolderXrefNumber(cardRequest.getCard().getCrossReferenceNumber());
        crdb.seteReceiptEmail(cardRequest.getCard().geteReceiptEmail());
        crdb.setStatusCode(CardStatusType.ACTIVE.getCode());
        crdb.setServiceAccountNumber(Long.valueOf(cardRequest.getCard().getServiceAccountNumber()));
        crdb.setServiceAccountName(cardRequest.getCard().getServiceAccountName());
        crdb.setSourceSystem(cardRequest.getCard().getSourceSystem().toString());
        crdb.setNickName(cardRequest.getCard().getNickName());

        // if the card already exist then do not change the created time stamp and requestor id so that we would know when it was first created

        if(account!=null){
            dbCardRequest.setRequestId(account.getHouseAccountCreditCardRequest().getRequestId());
            dbCardRequest.setRequestorUserId(account.getHouseAccountCreditCardRequest().getRequestorUserId());
            dbCardRequest.setCreatedTimeStamp(account.getHouseAccountCreditCardRequest().getCreatedTimeStamp());
            crdb.setCreatedTimeStamp(account.getCreatedTimeStamp());
        }else{
            dbCardRequest.setCreatedTimeStamp(timeStamp);
            dbCardRequest.setRequestorUserId(cardRequest.getUserId().substring(0,Math.min(cardRequest.getUserId().length(),8)));
            crdb.setCreatedTimeStamp(timeStamp);
        }

        // Populate House Account Address
        HouseAccountCustomerAddress address = new HouseAccountCustomerAddress();
        address.setAddressLine1(cardRequest.getShippingAddress().getAddressLine1());
        if (cardRequest.getShippingAddress().getAddressLine2() !=  null){
            address.setAddressLine2(cardRequest.getShippingAddress().getAddressLine2());
        }
        address.setCity(cardRequest.getShippingAddress().getCity());
        address.setCompanyName(cardRequest.getShippingAddress().getCompanyName());
        address.setCountryCode("US");
        address.setPostalCode(cardRequest.getShippingAddress().getZip());
        address.setState(cardRequest.getShippingAddress().getStateCode());
        address.setShippingCode(0);
        address.setLastUpdatedSysUserId(cardRequest.getUserId());
        address.setLastUpdatedTimeStamp(timeStamp);
        // Add set of customer addresses to credit card table
        address.setHouseAccountCreditCardAccount(crdb);
        List<HouseAccountCustomerAddress> custAddress = new ArrayList<HouseAccountCustomerAddress>();
        custAddress.add(address);
        crdb.setHouseAccountCustomerAddress(custAddress);

        // Populate House Account Frequencies
        if (cardRequest.getCard().getLimit() != null){
            HouseAccountCreditCardSpendingFrequency frequency = new HouseAccountCreditCardSpendingFrequency();
            frequency.setFrequencyTypeCode(cardRequest.getCard().getLimit().getFrequency().getCode());
            frequency.setSpendingLimitAmount(cardRequest.getCard().getLimit().getAmount());
            frequency.setLastUpdatedSysUserId(cardRequest.getUserId());
            frequency.setLastUpdatedTimeStamp(timeStamp);
            // Add set of card frequencies to credit card table
            frequency.setHouseAccountCreditCardAccount(crdb);
            List<HouseAccountCreditCardSpendingFrequency> frequencies = new ArrayList<HouseAccountCreditCardSpendingFrequency>();
            frequencies.add(frequency);
            crdb.setHouseAccountCreditCardSpendingFrequencies(frequencies);
        }

        // Add set of credit cards to card request table
        crdb.setHouseAccountCreditCardRequest(dbCardRequest);
        List<HouseAccountCreditCardAccount> ccAccount = new ArrayList<HouseAccountCreditCardAccount>();
        ccAccount.add(crdb);
        dbCardRequest.setHouseAccountCreditCardAccounts(ccAccount);

        return dbCardRequest;
    }

    public Card convertDBHouseAccountToCard(HouseAccountCreditCardAccount houseAccountCreditCardAccount){
        Card card = new Card();
        card.setBrandName(BrandType.getByCode(houseAccountCreditCardAccount.getBrandName()));
        card.setServiceAccountName(houseAccountCreditCardAccount.getServiceAccountName().trim());
        card.setBuyerId(houseAccountCreditCardAccount.getBuyerId().trim());
        card.setCardHolderFirstName(houseAccountCreditCardAccount.getCardHolderFirstName().trim());
        card.setCardHolderLastName(houseAccountCreditCardAccount.getCardHolderLastName().trim());
        card.setCardStatus(CardStatusType.getByCode(houseAccountCreditCardAccount.getStatusCode()));
        card.setCrossReferenceNumber(houseAccountCreditCardAccount.getCardHolderXrefNumber());
        card.seteReceiptEmail(houseAccountCreditCardAccount.geteReceiptEmail().trim());
        card.setSourceSystem(SystemType.valueOf(houseAccountCreditCardAccount.getSourceSystem()));

        if(null!=houseAccountCreditCardAccount.getNickName() && ""!=houseAccountCreditCardAccount.getNickName()){
            card.setNickName(houseAccountCreditCardAccount.getNickName().trim());
        }

        card.setServiceAccountNumber(String.valueOf(houseAccountCreditCardAccount.getServiceAccountNumber()));

        if (null!=houseAccountCreditCardAccount.getHouseAccountCreditCardSpendingFrequencies()&&
                !houseAccountCreditCardAccount.getHouseAccountCreditCardSpendingFrequencies().isEmpty() ) {
                CardLimit limit = new CardLimit();
                limit.setAmount(houseAccountCreditCardAccount.getHouseAccountCreditCardSpendingFrequencies().get(0).getSpendingLimitAmount());
                limit.setFrequency(LimitFrequencyType.getByCode(houseAccountCreditCardAccount.getHouseAccountCreditCardSpendingFrequencies().get(0).getFrequencyTypeCode()));
                card.setLimit(limit);

        }

        card.setUserId(houseAccountCreditCardAccount.getLastUpdatedSysUserId().trim());

        return card;
    }


    public HouseAccountCreditCardAccount convertCardToHouseAccountCreditCardAccount(HouseAccountCreditCardAccount houseAccountCreditCardAccount, Card card){

        DBTimestamp timeStamp = new DBTimestamp(new Date().getTime());

        if(null!=card.getBrandName()){
            houseAccountCreditCardAccount.setBrandName(card.getBrandName().getCode());
        }

        // Populate House Account Frequencies
        if (card.getLimit() != null){
            HouseAccountCreditCardSpendingFrequency frequency = new HouseAccountCreditCardSpendingFrequency();
            frequency.setFrequencyTypeCode(card.getLimit().getFrequency().getCode());
            frequency.setSpendingLimitAmount(card.getLimit().getAmount());
            frequency.setLastUpdatedSysUserId(card.getUserId());
            frequency.setLastUpdatedTimeStamp(timeStamp);
            // Add set of card frequencies to credit card table
            frequency.setHouseAccountCreditCardAccount(houseAccountCreditCardAccount);
            List<HouseAccountCreditCardSpendingFrequency> frequencies = new ArrayList<HouseAccountCreditCardSpendingFrequency>();
            frequencies.add(frequency);
            houseAccountCreditCardAccount.setHouseAccountCreditCardSpendingFrequencies(frequencies);
        }

        if(null!=card.getCardHolderFirstName()){
            houseAccountCreditCardAccount.setCardHolderFirstName(card.getCardHolderFirstName());
        }

        if(null!=card.getCardHolderLastName()){
            houseAccountCreditCardAccount.setCardHolderLastName(card.getCardHolderLastName());
        }

        if(null!=card.getBrandName()){
            houseAccountCreditCardAccount.setBrandName(card.getBrandName().getCode());
        }

        if(null!=card.getCardStatus()){
            houseAccountCreditCardAccount.setStatusCode(card.getCardStatus().getCode());
        }

        if(null!=card.geteReceiptEmail()){
            houseAccountCreditCardAccount.seteReceiptEmail(card.geteReceiptEmail());
        }

        if(null!=card.getServiceAccountName()){
            houseAccountCreditCardAccount.setServiceAccountName(card.getServiceAccountName());
        }

        if(null!=card.getNickName()){
            houseAccountCreditCardAccount.setNickName(card.getNickName());
        }

        houseAccountCreditCardAccount.setLastUpdatedSysUserId(card.getUserId());
        houseAccountCreditCardAccount.setLastUpdatedTimeStamp(timeStamp);

        return houseAccountCreditCardAccount;
    }

}
