package com.homedepot.sa.cb.hamanagement.model.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by n84qvs on 11/17/16.
 */
@ApiModel(description = "Address model")
@Validated
public class Address implements Serializable{


    @ApiModelProperty(notes = "Card receiver name.")
    @NotNull(message="error.address.companyName.required")
    @Size(max = 30 ,message = "error.address.companyName.invalid")
    @Pattern(regexp = ".*\\S.*",message="error.address.companyName.invalid")
    private String companyName;

    @ApiModelProperty(required = true,notes = "Maximum allowed length is 30.")
    @Size(max = 30 ,message = "error.address.addressLine1.invalid")
    @NotNull(message="error.address.addressLine1.required")
    @Pattern(regexp = ".*\\S.*",message="error.address.addressLine1.invalid")
    private String addressLine1;

    @ApiModelProperty(notes = "Maximum allowed length is 30.")
    @Size(max = 30 ,message = "error.address.addressLine2.invalid")
    private String addressLine2;

    @ApiModelProperty(required = true,notes = "Maximum allowed length is 30.")
    @Size(max = 30 ,message = "error.address.city.invalid")
    @NotNull(message="error.address.city.required")
    @Pattern(regexp = ".*\\S.*",message="error.address.city.invalid")
    private String city;

    @ApiModelProperty(required = true,notes = "Two characters state code")
    @NotNull(message="error.address.stateCode.required")
    @Pattern(regexp = "^([a-z]|[A-Z]){2}$",message="error.address.stateCode.invalid")
    private String stateCode;

    @ApiModelProperty(required = true,position =12,notes = "Maximum allowed digits are 9. Hypen is optional.")
    @NotNull(message="error.address.zip.required")
    @Pattern(regexp = "^\\d{5}(?:([-]\\d{4})|(\\d{4}))?$",message="error.address.zip.invalid")
    private String zip;

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

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Address{" +
                "companyName='" + companyName + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
