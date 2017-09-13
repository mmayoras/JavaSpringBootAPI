package com.homedepot.sa.cb.hamanagement.exception;

/**
 * Created by n84qvs on 12/5/16.
 */
public class InternalServerException extends ApplicationException {


    public InternalServerException(String errorCode,String message, Throwable ex) {
        super(errorCode,message, ex);
    }

    public InternalServerException(String errorCode,String message) {
        super(errorCode,message);
    }

}
