package com.homedepot.sa.cb.hamanagement.model.db;

import javax.persistence.*;

/**
 * Created by n84qvs on 1/5/17.
 */

@Entity
@Table(name = "HACCT_CCARD_CUST_ADDR")
public class HouseAccountCustomerAddress extends AuditFields {

    @ManyToOne
    @JoinColumn(name = "CARDHLDR_XREF_NBR")
    @Id
    private HouseAccountCreditCardAccount houseAccountCreditCardAccount;

    @Column(name = "CO_NM")
    private String companyName;

    @Column(name = "ADDR_LINE1_TXT")
    private String addressLine1;

    @Column(name = "ADDR_LINE2_TXT")
    private String addressLine2;

    @Column(name = "CITY_NM")
    private String city;

    @Column(name = "ST_CD")
    private String state;

    @Column(name = "PSTL_CD")
    private String postalCode;

    @Column(name = "CNTRY_CD")
    private String countryCode;

    @Column(name = "PRCL_SVC_LVL_CD")
    private int shippingCode;

    public HouseAccountCreditCardAccount getHouseAccountCreditCardAccount() {
        return houseAccountCreditCardAccount;
    }

    public void setHouseAccountCreditCardAccount(HouseAccountCreditCardAccount houseAccountCreditCardAccount) {
        this.houseAccountCreditCardAccount = houseAccountCreditCardAccount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(int shippingCode) {
        this.shippingCode = shippingCode;
    }
}
