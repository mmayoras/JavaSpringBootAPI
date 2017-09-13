package com.homedepot.sa.cb.hamanagement.model.db;

import java.io.Serializable;

/**
 * Created by mxm8528 on 1/11/17.
 */
public class HouseAccountSpendingFrequencyId implements Serializable{
    private HouseAccountCreditCardAccount houseAccountCreditCardAccount;
    private int frequencyTypeCode;

    public HouseAccountSpendingFrequencyId(){
        // Class must have a no-arg constructor
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof HouseAccountSpendingFrequencyId){
            HouseAccountSpendingFrequencyId houseAccountSpendingFrequencyId = (HouseAccountSpendingFrequencyId) obj;

            if(houseAccountSpendingFrequencyId.getHouseAccountCreditCardAccount().equals(houseAccountCreditCardAccount)
                    && houseAccountSpendingFrequencyId.getFrequencyTypeCode() == frequencyTypeCode){
               return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return houseAccountCreditCardAccount.hashCode() + Integer.hashCode(frequencyTypeCode);
    }

    public HouseAccountCreditCardAccount getHouseAccountCreditCardAccount() {
        return houseAccountCreditCardAccount;
    }

    public void setHouseAccountCreditCardAccount(HouseAccountCreditCardAccount houseAccountCreditCardAccount) {
        this.houseAccountCreditCardAccount = houseAccountCreditCardAccount;
    }

    public int getFrequencyTypeCode() {
        return frequencyTypeCode;
    }

    public void setFrequencyTypeCode(int frequencyTypeCode) {
        this.frequencyTypeCode = frequencyTypeCode;
    }
}
