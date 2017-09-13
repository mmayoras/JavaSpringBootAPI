package com.homedepot.sa.cb.hamanagement.model.db;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * CardRequest object used for being stored in the database
 * Created by MXM8528 on 11/23/2016.
 */

@Entity
@Table(name = "HACCT_CR_CARD_ACCT")
public class HouseAccountCreditCardAccount extends AuditFields{

    @Column(name = "CARDHLDR_XREF_NBR")
    @Id
    private String cardHolderXrefNumber;

    @Column(name = "CRT_TS")
    private Date createdTimeStamp;

    @Column(name = "HOUS_ACCT_BYR_ID")
    private String buyerId;

    @Column(name = "CARDHLDR_FRST_NM")
    private String cardHolderFirstName;

    @Column(name = "CARDHLDR_MID_INIT_NM")
    private String cardHolderMiddleInitial;

    @Column(name = "CARDHLDR_LAST_NM")
    private String cardHolderLastName;

    @Column(name = "HOUS_ACCT_CC_NCKNM")
    private String nickName;

    @Column(name = "HACCT_BRAND_NM_CD")
    private String brandName;

    @Column(name = "ERCPT_EADDR_TXT")
    private String eReceiptEmail;

    @Column(name = "HACCT_CCARD_STAT_CD")
    private int statusCode;

    @Column(name = "CUST_SVC_ACCT_NBR")
    private Long serviceAccountNumber;

    @Column(name = "CUST_SVC_ACCT_BUS_NM")
    private String serviceAccountName;

    @Column(name = "SRC_SYS_IND")
    private String sourceSystem;

    @ManyToOne
    @JoinColumn(name = "HOUS_ACCT_RQST_ID")
    private HouseAccountCreditCardRequest houseAccountCreditCardRequest;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "houseAccountCreditCardAccount")
    private List<HouseAccountCustomerAddress> houseAccountCustomerAddress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "houseAccountCreditCardAccount")
    private List<HouseAccountCreditCardSpendingFrequency> houseAccountCreditCardSpendingFrequencies;

    public String getCardHolderXrefNumber() {
        return cardHolderXrefNumber;
    }

    public void setCardHolderXrefNumber(String cardHolderXrefNumber) {
        this.cardHolderXrefNumber = cardHolderXrefNumber;
    }

    public Date getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Date createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getCardHolderFirstName() {
        return cardHolderFirstName;
    }

    public void setCardHolderFirstName(String cardHolderFirstName) {
        this.cardHolderFirstName = cardHolderFirstName;
    }

    public String getCardHolderMiddleInitial() {
        return cardHolderMiddleInitial;
    }

    public void setCardHolderMiddleInitial(String cardHolderMiddleInitial) {
        this.cardHolderMiddleInitial = cardHolderMiddleInitial;
    }

    public String getCardHolderLastName() {
        return cardHolderLastName;
    }

    public void setCardHolderLastName(String cardHolderLastName) {
        this.cardHolderLastName = cardHolderLastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String geteReceiptEmail() {
        return eReceiptEmail;
    }

    public void seteReceiptEmail(String eReceiptEmail) {
        this.eReceiptEmail = eReceiptEmail;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Long getServiceAccountNumber() {
        return serviceAccountNumber;
    }

    public void setServiceAccountNumber(Long serviceAccountNumber) {
        this.serviceAccountNumber = serviceAccountNumber;
    }

    public String getServiceAccountName() {
        return serviceAccountName;
    }

    public void setServiceAccountName(String serviceAccountName) {
        this.serviceAccountName = serviceAccountName;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getLastUpdatedSysUserId() {
        return lastUpdatedSysUserId;
    }

    public void setLastUpdatedSysUserId(String lastUpdatedSysUserId) {
        this.lastUpdatedSysUserId = lastUpdatedSysUserId;
    }

    public HouseAccountCreditCardRequest getHouseAccountCreditCardRequest() {
        return houseAccountCreditCardRequest;
    }

    public void setHouseAccountCreditCardRequest(HouseAccountCreditCardRequest houseAccountCreditCardRequest) {
        this.houseAccountCreditCardRequest = houseAccountCreditCardRequest;
    }

    public List<HouseAccountCustomerAddress> getHouseAccountCustomerAddress() {
        return houseAccountCustomerAddress;
    }

    public void setHouseAccountCustomerAddress(List<HouseAccountCustomerAddress> houseAccountCustomerAddress) {
        this.houseAccountCustomerAddress = houseAccountCustomerAddress;
    }

    public List<HouseAccountCreditCardSpendingFrequency> getHouseAccountCreditCardSpendingFrequencies() {
        return houseAccountCreditCardSpendingFrequencies;
    }

    public void setHouseAccountCreditCardSpendingFrequencies(List<HouseAccountCreditCardSpendingFrequency> houseAccountCreditCardSpendingFrequencies) {
        this.houseAccountCreditCardSpendingFrequencies = houseAccountCreditCardSpendingFrequencies;
    }
}
