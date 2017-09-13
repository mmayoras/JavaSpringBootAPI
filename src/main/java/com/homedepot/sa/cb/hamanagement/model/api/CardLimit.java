package com.homedepot.sa.cb.hamanagement.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * Created by gxk8688 on 11/21/16.
 */
@ApiModel(description = "Card Limit Model")
@Validated
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class CardLimit implements Serializable {

    private LimitFrequencyType frequency = LimitFrequencyType.WEEKLY;

    @ApiModelProperty(notes = "Amount can be  >=0 and <=9999999999999.9999")
    @DecimalMax(value="9999999999999.9999",message="error.creditLimit.amount.invalid")
    private double amount;

    public LimitFrequencyType getFrequency() {
        return frequency;
    }

    public void setFrequency(LimitFrequencyType frequency) {
        this.frequency = frequency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CardLimit{" +
                "frequency=" + frequency +
                ", amount=" + amount +
                '}';
    }
}

