package com.homedepot.sa.cb.hamanagement.repositories;

import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * JPA Interface used for all database transactions
 * Created by MXM8528 on 11/23/2016.
 */
public interface CardRequestRepository extends JpaRepository<HouseAccountCreditCardRequest,Integer> {

    @Modifying
    @Transactional
    @Query("update HouseAccountCreditCardRequest cardRequest set cardRequest.statusFlag = ?1, cardRequest.lastUpdatedSysUserId = ?2, cardRequest.lastUpdatedTimeStamp = ?3 where cardRequest.requestId = ?4")
    void updateHouseAccountCardRequestStatus(int newRequestStatus, String lastUpdatedUserId, Date lastUpdatedTimeStamp, int searchRequestId);

}
