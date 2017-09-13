package com.homedepot.sa.cb.hamanagement.model.api;

/**
 * Created by gxk8688 on 11/21/16.
 */
public enum LimitFrequencyType {
    DAILY(100,"DAILY"),
    WEEKLY(200,"WEEKLY"),
    MONTHLY(300,"MONTHLY");

    private int code;
    private String description;

    LimitFrequencyType(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static LimitFrequencyType getByCode(int code){
        for(LimitFrequencyType limitFrequencyType : LimitFrequencyType.values()){
            if(limitFrequencyType.code == code){
                return limitFrequencyType;
            }
        }
        return null;
    }
}
