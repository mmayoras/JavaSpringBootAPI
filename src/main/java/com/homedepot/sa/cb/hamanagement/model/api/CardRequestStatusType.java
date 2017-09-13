package com.homedepot.sa.cb.hamanagement.model.api;

/**
 * Created by PXK2457 on 1/10/2017.
 */
public enum CardRequestStatusType {
    RECEIVED(100,"RECEIVED"),
    CREATED(200,"CREATED"),
    ERRORED(300,"ERRORED"),
    SENT(400,"SENT"),
    FAILED(500,"FAILED"),
    SHIPPED(600,"SHIPPED");

    private int code;
    private String description;

    CardRequestStatusType(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
