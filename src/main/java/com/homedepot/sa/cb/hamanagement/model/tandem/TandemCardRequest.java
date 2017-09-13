package com.homedepot.sa.cb.hamanagement.model.tandem;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Created by n84qvs on 3/2/17.
 */

public class TandemCardRequest{

    @JsonProperty("sys_id")
    private String systemId;

    @JsonProperty("svc_acct_nbr")
    private String serviceAccountNumber;

    @JsonProperty("auth_byr_id")
    private String authBuyerId;

    @JsonProperty("sys_usr_id")
    private String sysUserId;

    @JsonProperty("business_nm")
    private String businessName;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("brand_nm")
    private String brandName;

    @JsonProperty("address_line_1")
    private String addressLine1;

    @JsonProperty("address_line_2")
    private String addressLine2;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("expedited_ship_cd")
    private String expeditedShippingCode;

    @JsonProperty("email_addr")
    private String emailAddress;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getServiceAccountNumber() {
        return serviceAccountNumber;
    }

    public void setServiceAccountNumber(String serviceAccountNumber) {
        this.serviceAccountNumber = serviceAccountNumber;
    }

    public String getAuthBuyerId() {
        return authBuyerId;
    }

    public void setAuthBuyerId(String authBuyerId) {
        this.authBuyerId = authBuyerId;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getExpeditedShippingCode() {
        return expeditedShippingCode;
    }

    public void setExpeditedShippingCode(String expeditedShippingCode) {
        this.expeditedShippingCode = expeditedShippingCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
