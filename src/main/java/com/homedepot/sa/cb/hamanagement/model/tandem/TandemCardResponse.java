package com.homedepot.sa.cb.hamanagement.model.tandem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by PXK2457 on 12/8/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("CardResponse")
public class TandemCardResponse {

    @JsonProperty("sys_id")
    private String sysId;

    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("svc_acct_nbr")
    private String serviceAccountNumber;

    @JsonProperty("auth_byr_id")
    private String authBuyerId;

    @JsonProperty("xref_nbr")
    private String crossReferenceNumber;

    @JsonProperty("status_message")
    private String statusMessage;

    @JsonProperty("exp_dt")
    private String expirationDate;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setServiceAccountNumber(String serviceAccountNumber) {
        this.serviceAccountNumber = serviceAccountNumber;
    }

    public void setAuthBuyerId(String authBuyerId) {
        this.authBuyerId = authBuyerId;
    }

    public String getCrossReferenceNumber() {
        return crossReferenceNumber;
    }

    public void setCrossReferenceNumber(String crossReferenceNumber) {
        this.crossReferenceNumber = crossReferenceNumber;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    @Override
    public String toString() {
        return "TandemCardResponse{" +
                "sysId='" + sysId + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", serviceAccountNumber='" + serviceAccountNumber + '\'' +
                ", authBuyerId='" + authBuyerId + '\'' +
                ", crossReferenceNumber='" + crossReferenceNumber + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }
}
