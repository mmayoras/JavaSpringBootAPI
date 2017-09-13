package com.homedepot.sa.cb.hamanagement.exception;

/**
 * Created by n84qvs on 12/5/16.
 */
public class ApplicationException extends Exception {

    private final String errorCode;

    public ApplicationException(String errorCode, String message,Throwable ex) {
        super(message,ex);
        this.errorCode = errorCode;

    }

    public ApplicationException(String errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }


}
