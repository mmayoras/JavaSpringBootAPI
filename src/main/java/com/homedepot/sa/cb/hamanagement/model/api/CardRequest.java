package com.homedepot.sa.cb.hamanagement.model.api;

import com.homedepot.sa.cb.hamanagement.validators.CreateCardRequest;
import com.homedepot.sa.cb.hamanagement.validators.UpdateCardRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by n84qvs on 11/17/16.
 */
@ApiModel(description = "Card request model",value="cardRequest")
@XmlRootElement(name="cardRequest")
public class CardRequest implements Serializable{

    @ApiModelProperty(notes = "Card details are required.")
    @NotNull(message="error.cardRequest.card.required")
    @Valid
    private  Card card;

    @ApiModelProperty(required = true,notes = "User id of the person requesting the card. Max length is 24.")
    @NotNull(message="error.cardRequest.userId.required")
    @Pattern(regexp = "^([a-z]|[A-Z]|[0-9]){1,24}$",message= "error.cardRequest.userId.invalid")
    private String userId;

    @ApiModelProperty(required = true)
    @NotNull(message="error.cardRequest.shippingAddress.required", groups = CreateCardRequest.class)
    @Valid
    private Address shippingAddress;

    @ApiModelProperty(notes = "Defaults to Standard shipping")
    private ShippingType shippingMode = ShippingType.STANDARD;

    @ApiModelProperty(notes = "Required for Card Request status update")
    @NotNull(message = "error.cardRequest.cardRequestStatus.required", groups = UpdateCardRequest.class)
    private CardRequestStatusType cardRequestStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Card getCard() {
        return card;
    }


    public void setCard(Card card) {
        this.card = card;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    @XmlElement
    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public ShippingType getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(ShippingType shippingMode) {
        this.shippingMode = shippingMode;
    }

    public CardRequestStatusType getCardRequestStatus() {
        return cardRequestStatus;
    }

    public void setCardRequestStatus(CardRequestStatusType cardRequestStatus) {
        this.cardRequestStatus = cardRequestStatus;
    }

    @Override
    public String toString() {
        return "CardRequest{" +
                "card=" + card +
                ", userId='" + userId + '\'' +
                ", shippingAddress=" + shippingAddress +
                ", shippingMode=" + shippingMode +
                ", cardRequestStatus=" + cardRequestStatus +
                '}';
    }
}
