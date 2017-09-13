package com.homedepot.sa.cb.hamanagement.service;

import com.homedepot.sa.cb.hamanagement.exception.*;
import com.homedepot.sa.cb.hamanagement.model.api.Card;
import com.homedepot.sa.cb.hamanagement.model.api.CardRequest;
import com.homedepot.sa.cb.hamanagement.model.api.CardStatusType;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardAccount;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardRequest;
import com.homedepot.sa.cb.hamanagement.model.tandem.TandemCardResponse;
import com.ibm.db2.jcc.DBTimestamp;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * This class is the service layer for all cardRequest DB interactions.
 * Created by MXM8528 on 11/23/2016.
 */
@Service
public class CardService {

    @Autowired
    private DBService dbService;

    @Autowired
    private TandemService tandemService;

    @Autowired
    private MapperService mapperService;


    private static final Logger LOGGER = Logger.getLogger(CardService.class);

    /**
     * Used for saving a HouseAccount object into the database based on new cardRequest
     * @param
     * @return Card
     * @throws ApplicationException
     */
    public Card create(CardRequest cardRequest) throws ApplicationException {
        TandemCardResponse tandemResponse = tandemService.submitTandemRequest(mapperService.convertCardRequestToTandemCardRequest(cardRequest));
        cardRequest.getCard().setCrossReferenceNumber(tandemResponse.getCrossReferenceNumber().trim());
        cardRequest.getCard().setExpirationDate(tandemResponse.getExpirationDate());

        // Find first HouseAccount record in DB that is a duplicate
        HouseAccountCreditCardAccount account = null;
        try{
            account = dbService.findByBuyerIdAndServiceAccountNumberAndSourceSystem(cardRequest.getCard().getBuyerId(),
                Long.valueOf(cardRequest.getCard().getServiceAccountNumber()),
                cardRequest.getCard().getSourceSystem().toString());
        }catch (JpaObjectRetrievalFailureException e){
            LOGGER.info("Card does not exist.",e);
        }

            dbService.saveCardRequest(mapperService.convertCardRequestToDBHouseAccountRequest(cardRequest,account));


        return cardRequest.getCard();
    }



    /**
     * Lookup HA card account record in DB
     * @param xrefNumber
     * @return
     * @throws NotFoundException
     */
    public HouseAccountCreditCardAccount retrieveHouseAccount(String xrefNumber) throws NotFoundException {
        HouseAccountCreditCardAccount houseAccount = null;
        // If cross reference number has a value and is 16 digits, then lookup card in DB
        if(null!=xrefNumber && xrefNumber.trim().length()==16){
            houseAccount =  dbService.findByCrossReferenceNumber(xrefNumber);
        }

        // If value was found then return
        if(houseAccount!=null){
           return houseAccount;
        }else{
            throw new NotFoundException("error.card.notFound","No card entity for id.");
        }
    }

    /**
     * Return HA card account as an API card object
     * @param houseAccount
     * @return
     */
    public Card getCard(HouseAccountCreditCardAccount houseAccount){
       return mapperService.convertDBHouseAccountToCard(houseAccount);
    }

    /**
     * Take Card object and update fields that aren't null
     * @param card
     * @throws NotFoundException
     */
    public void updateCard(Card card,String ifNotModifiedSince) throws ApplicationException{
        HouseAccountCreditCardAccount houseAccount  = retrieveHouseAccount(card.getCrossReferenceNumber());

        if(houseAccount.getLastUpdatedTimeStamp().toString().equals(ifNotModifiedSince)){
            TandemCardRequest request = null;
            // Get Tandem Card disable request when IBI requested to cancel the card
            if(null!=card.getCardStatus() && card.getCardStatus().toString().equals(CardStatusType.INACTIVE.toString())){
                request = mapperService.convertCardUpdateRequestToTandemCardDisableRequest(card,houseAccount);
            }else{
                request = mapperService.convertCardUpdateRequestToTandemCardUpdateRequest(card,houseAccount);
            }
            tandemService.submitTandemRequest(request);
            dbService.saveCard(mapperService.convertCardToHouseAccountCreditCardAccount(houseAccount, card));
        }else{
            throw new PreconditionFailedException("error.precondition.failed","Card entity has been modified since viewed.");
        }
    }

    public void updateCardRequest(CardRequest cardRequest) throws ApplicationException{
        HouseAccountCreditCardAccount houseAccount  = retrieveHouseAccount(cardRequest.getCard().getCrossReferenceNumber());
        try {
            dbService.updateCardRequest(houseAccount.getHouseAccountCreditCardRequest().getRequestId(), cardRequest.getUserId().trim(), new DBTimestamp(new Date().getTime()), cardRequest.getCardRequestStatus().getCode());
        }catch (Exception e) {
            throw new InternalServerException("error.database", "Problem updating new card to DB. Please contact support team", e);
        }

    }

}