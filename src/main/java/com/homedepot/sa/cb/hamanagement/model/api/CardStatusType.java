package com.homedepot.sa.cb.hamanagement.model.api;

/**
 * Created by n84qvs on 11/17/16.
 */
public enum CardStatusType {
    ACTIVE(100, "ACTIVE"),
    INACTIVE(200,"INACTIVE");

    private int code;
    private String description;

    CardStatusType(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static CardStatusType getByCode(int code){
        for(CardStatusType cardStatusType : CardStatusType.values()){
            if(cardStatusType.code == code){
                return cardStatusType;
            }
        }
        return null;
    }

}
