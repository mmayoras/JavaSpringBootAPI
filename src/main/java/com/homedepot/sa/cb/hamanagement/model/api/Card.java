package com.homedepot.sa.cb.hamanagement.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.homedepot.sa.cb.hamanagement.validators.CreateCardRequest;
import com.homedepot.sa.cb.hamanagement.validators.UpdateCard;
import com.homedepot.sa.cb.hamanagement.validators.UpdateCardRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by n84qvs on 11/7/16.
 */
@ApiModel(description = "House Account Card Model", value="card")
@Validated
@XmlRootElement(name="card")
@JacksonXmlRootElement(localName="card")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Card implements Serializable {

    @ApiModelProperty(required = true)
    @NotNull(message= "error.card.sourceSystem.required",groups = CreateCardRequest.class)
    @XmlElement
    private SystemType sourceSystem;

    @ApiModelProperty(required = true, notes = "Maximum allowed length is 10.")
    @NotNull(message="error.card.serviceAccountNumber.required", groups = CreateCardRequest.class)
    @Pattern(regexp = "^[0-9]{1,10}$",message ="error.card.serviceAccountNumber.invalid")
    @XmlElement
    private String serviceAccountNumber;

    @ApiModelProperty(required = true, notes = "Maximum allowed length is 10.")
    @NotNull(message="error.card.buyerId.required", groups = CreateCardRequest.class)
    @Pattern(regexp = "^([a-z]|[A-Z]|[0-9]){1,10}$",message="error.card.buyerId.invalid")
    @XmlElement
    private String buyerId;

    @ApiModelProperty(required = true, notes = "Maximum allowed length is 30.")
    @NotNull(message="error.card.serviceAccountName.required", groups = CreateCardRequest.class)
    @Size(max = 30 ,message = "error.card.serviceAccountName.invalid")
    @Pattern(regexp = ".*\\S.*",message="error.card.serviceAccountName.invalid")
    @XmlElement
    private String serviceAccountName;

    @ApiModelProperty(required = true, notes = "Maximum allowed length is 40.")
    @NotNull(message="error.card.cardHolderFirstName.required", groups = CreateCardRequest.class)
    @Size(max = 40 ,message = "error.card.cardHolderFirstName.invalid")
    @Pattern(regexp = ".*\\S.*",message="error.card.cardHolderFirstName.invalid")
    private String cardHolderFirstName;

    @ApiModelProperty(required = true, notes = "Maximum allowed length is 60.")
    @NotNull(message="error.card.cardHolderLastName.required", groups = CreateCardRequest.class)
    @Size(max = 60 ,message = "error.card.cardHolderLastName.invalid")
    @Pattern(regexp = ".*\\S.*",message="error.card.cardHolderLastName.invalid")
    @XmlElement
    private String cardHolderLastName;

    @ApiModelProperty(notes = "Maximum allowed length is 30.")
    @Size(max = 30 ,message = "error.card.nickName.invalid")
    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nickName;

    @ApiModelProperty(required = true)
    @NotNull(message="error.card.brandName.required", groups = CreateCardRequest.class)
    @XmlElement
    private BrandType brandName;

    @ApiModelProperty(required = true, notes = "Maximum allowed length is 254")
    @NotNull(message="error.card.eReceiptEmail.required", groups = CreateCardRequest.class)
    @Size(max = 254 ,message = "error.card.eReceiptEmail.invalid")
    @XmlElement
    private String eReceiptEmail;

    @XmlElement
    private CardStatusType cardStatus;

    @ApiModelProperty(notes = "Xref number of the card. Required for patch request.", readOnly = true)
    @XmlElement
    @NotNull(message ="error.card.crossReferenceNumber.required", groups = UpdateCardRequest.class)
    @Pattern(regexp = "^[0-9]{16}$",message ="error.card.crossReferenceNumber.invalid",groups = UpdateCardRequest.class)
    private String crossReferenceNumber;

    @ApiModelProperty(notes = "Expiration date of the card. Read only. Format is mmyy.", readOnly = true)
    @XmlElement
    private String expirationDate;

    @ApiModelProperty(notes= "Card Limits")
    @Valid
    @XmlElement
    private CardLimit limit;

    @ApiModelProperty(notes = "User id of the person updating the card. Max length is 24. Required for updating card.")
    @NotNull(message="error.card.userId.required", groups = UpdateCard.class)
    @Size(max = 24 ,message = "error.card.userId.invalid", groups = UpdateCard.class)
    @Pattern(regexp = ".*\\S.*",message="error.card.userId.invalid", groups = UpdateCard.class)
    private String userId;

    public SystemType getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(SystemType sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getServiceAccountNumber() {
        return serviceAccountNumber;
    }

    public void setServiceAccountNumber(String serviceAccountNumber) {
        this.serviceAccountNumber = serviceAccountNumber;
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

    public String getServiceAccountName() {
        return serviceAccountName;
    }

    public void setServiceAccountName(String serviceAccountName) {
        this.serviceAccountName = serviceAccountName;
    }

    public void setCardHolderFirstName(String cardHolderFirstName) {
        this.cardHolderFirstName = cardHolderFirstName;
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

    public void setNickName(String cardNickName) {
        this.nickName = cardNickName;
    }

    public BrandType getBrandName() {
        return brandName;
    }

    public void setBrandName(BrandType brandName) {
        this.brandName = brandName;
    }

    public String geteReceiptEmail() {
        return eReceiptEmail;
    }

    public void seteReceiptEmail(String eReceiptEmail) {
        this.eReceiptEmail = eReceiptEmail;
    }

    public CardStatusType getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatusType cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCrossReferenceNumber() {
        return crossReferenceNumber;
    }

    public void setCrossReferenceNumber(String crossReferenceNumber) {
        this.crossReferenceNumber = crossReferenceNumber;
    }

    public CardLimit getLimit() {
        return limit;
    }

    public void setLimit(CardLimit limit) {
        this.limit = limit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Card{" +
                "sourceSystem=" + sourceSystem +
                ", serviceAccountNumber='" + serviceAccountNumber + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", serviceAccountName='" + serviceAccountName + '\'' +
                ", cardHolderFirstName='" + cardHolderFirstName + '\'' +
                ", cardHolderLastName='" + cardHolderLastName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", brandName=" + brandName +
                ", eReceiptEmail='" + eReceiptEmail + '\'' +
                ", cardStatus=" + cardStatus +
                ", crossReferenceNumber='" + crossReferenceNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", limit=" + limit +
                ", userId='" + userId + '\'' +
                '}';
    }
}