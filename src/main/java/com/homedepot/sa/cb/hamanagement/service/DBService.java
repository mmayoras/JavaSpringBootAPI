package com.homedepot.sa.cb.hamanagement.service;

import com.homedepot.sa.cb.hamanagement.exception.ApplicationException;
import com.homedepot.sa.cb.hamanagement.exception.InternalServerException;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardAccount;
import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardRequest;
import com.homedepot.sa.cb.hamanagement.repositories.CardRepository;

import com.homedepot.sa.cb.hamanagement.repositories.CardRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by n84qvs on 1/9/17.
 */
@Service
public class DBService {

    @Autowired
    private CardRequestRepository cardRequestRepository;

    @Autowired
    private CardRepository cardRepository;

    public void saveCardRequest(HouseAccountCreditCardRequest dbRequest) throws ApplicationException {
        try {
            cardRequestRepository.save(dbRequest);
        }catch (Exception e) {
            throw new InternalServerException("error.database", "Problem saving new card request to DB. Please contact support team", e);
        }
    }

    public HouseAccountCreditCardAccount findByBuyerIdAndServiceAccountNumberAndSourceSystem(String buyerId, Long serviceAccountNumber, String sourceSystem){
        return cardRepository.findByBuyerIdAndServiceAccountNumberAndSourceSystem(buyerId,serviceAccountNumber,sourceSystem);
    }

    public HouseAccountCreditCardAccount findByCrossReferenceNumber(String xrefNumber) {
        return cardRepository.findOne(xrefNumber);
    }

    public void saveCard(HouseAccountCreditCardAccount houseAccountCreditCardAccount) throws ApplicationException {
        try {
            cardRepository.save(houseAccountCreditCardAccount);
        }catch (Exception e) {
            throw new InternalServerException("error.database", "Problem saving new card to DB. Please contact support team", e);
        }
    }

    @Transactional
    public void updateCardRequest(int requestId, String lastUpdUserid, Date lastUpdTimeStamp, int statusCode){
        cardRequestRepository.updateHouseAccountCardRequestStatus(statusCode,lastUpdUserid,lastUpdTimeStamp,requestId);
    }

}
