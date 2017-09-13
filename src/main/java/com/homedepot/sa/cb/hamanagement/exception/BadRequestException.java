package com.homedepot.sa.cb.hamanagement.exception;

/**
 * Created by n84qvs on 12/5/16.
 */
public class BadRequestException extends ApplicationException {

    public BadRequestException(String errorCode,String message) {
        super(errorCode,message);
    }


}
