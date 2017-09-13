package com.homedepot.sa.cb.hamanagement.model.db;

import javax.persistence.*;

/**
 * Created by n84qvs on 1/5/17.
 */

@Entity
@Table(name = "HACCT_CR_CARD_SPND_FREQ")
@IdClass(value = HouseAccountSpendingFrequencyId.class)
public class HouseAccountCreditCardSpendingFrequency extends AuditFields{

    @Id
    @ManyToOne
    @JoinColumn(name = "CARDHLDR_XREF_NBR")
    private HouseAccountCreditCardAccount houseAccountCreditCardAccount;

    @Id
    @Column(name = "HACCT_CCARD_SFREQ_TYP_CD")
    private int frequencyTypeCode;

    @Column(name = "CR_CARD_SPND_LMT_AMT")
    private double spendingLimitAmount;

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

    public double getSpendingLimitAmount() {
        return spendingLimitAmount;
    }

    public void setSpendingLimitAmount(double spendingLimitAmount) {
        this.spendingLimitAmount = spendingLimitAmount;
    }
}
