package com.homedepot.sa.cb.hamanagement.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by n84qvs on 11/17/16.
 */
public enum BrandType {

    SUPPLYWORKS("100","SUPPLYWORKS"),
    WILMAR("200","WILMAR"),
    MAINTENANCEUSA("300","MAINTENANCE U.S.A."),
    BARNETT("400","BARNETT"),
    COPPERFIELDCHIMNEYSUPPLY("500","COPPERFIELD CHIMNEY SUPPLY"),
    HARDWAREEXPRESS("600","HARDWARE EXPRESS"),
    LERANGASPRODUCTS("700","LERAN GAS PRODUCTS"),
    USLOCK("800","US LOCK"),
    RENOVATIONSPLUS("900","RENOVATIONS PLUS");

    private String code;
    private String description;

    BrandType(String code, String description){
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

   @JsonValue
   public String getDescription() {
        return description;
    }

    public static BrandType getByCode(String code){
        for(BrandType brandType : BrandType.values()){
            if(brandType.code.equalsIgnoreCase(code)){
                return brandType;
            }
        }
        return null;
    }

    @JsonCreator
    public static BrandType getByDescription(String description){
        for(BrandType brandType : BrandType.values()){
            if(brandType.description.equalsIgnoreCase(description)){
                return brandType;
            }
        }
        throw new IllegalArgumentException();
    }

}
