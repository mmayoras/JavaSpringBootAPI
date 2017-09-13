package com.homedepot.sa.cb.hamanagement.exception;

/**
 * Created by n84qvs on 12/5/16.
 */
public class TandemServiceException extends ApplicationException {

    private final String tandemServiceErrorCode;


    public TandemServiceException(String errorCode, String message, Throwable ex) {
        super(errorCode,message, ex);
        tandemServiceErrorCode = null;
    }

    public TandemServiceException(String errorCode, String message) {
        super(errorCode,message);
        tandemServiceErrorCode = null;
    }

    public TandemServiceException(String errorCode, String tandemServiceErrorCode,String message) {
        super(errorCode,message);
        this.tandemServiceErrorCode = tandemServiceErrorCode;
    }

    public String getTandemServiceErrorCode() {
        return tandemServiceErrorCode;
    }
}
