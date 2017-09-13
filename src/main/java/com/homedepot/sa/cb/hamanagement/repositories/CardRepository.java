package com.homedepot.sa.cb.hamanagement.repositories;

import com.homedepot.sa.cb.hamanagement.model.db.HouseAccountCreditCardAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by n84qvs on 1/9/17.
 */
public interface CardRepository extends JpaRepository<HouseAccountCreditCardAccount,String> {
        HouseAccountCreditCardAccount findByBuyerIdAndServiceAccountNumberAndSourceSystem(String buyerId, Long serviceAccountNumber, String sourceSystem);
}


