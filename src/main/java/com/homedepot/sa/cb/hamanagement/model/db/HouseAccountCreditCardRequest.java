package com.homedepot.sa.cb.hamanagement.model.db;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by PXK2457 on 1/5/2017.
 */
@Entity
@Table(name = "HACCT_CR_CARD_RQST")
public class HouseAccountCreditCardRequest extends AuditFields {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "HOUS_ACCT_RQST_ID")
    private int requestId;

    @Column(name = "CRT_TS")
    private Date createdTimeStamp;

    @Column(name = "RQSTR_ASSOC_USER_ID")
    private String requestorUserId;

    @Column(name = "HACCT_CCARD_RQST_STAT_CD")
    private int statusFlag;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "houseAccountCreditCardRequest")
    private List<HouseAccountCreditCardAccount> houseAccountCreditCardAccounts;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Date getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Date createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public String getRequestorUserId() {
        return requestorUserId;
    }

    public void setRequestorUserId(String requestorUserId) {
        this.requestorUserId = requestorUserId;
    }

    public int getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(int statusFlag) {
        this.statusFlag = statusFlag;
    }

    public List<HouseAccountCreditCardAccount> getHouseAccountCreditCardAccounts() {
        return houseAccountCreditCardAccounts;
    }

    public void setHouseAccountCreditCardAccounts(List<HouseAccountCreditCardAccount> houseAccountCreditCardAccounts) {
        this.houseAccountCreditCardAccounts = houseAccountCreditCardAccounts;
    }

    public String getLastUpdatedSysUserId() {
        return lastUpdatedSysUserId;
    }

    public void setLastUpdatedSysUserId(String lastUpdatedSysUserId) {
        this.lastUpdatedSysUserId = lastUpdatedSysUserId;
    }


}
