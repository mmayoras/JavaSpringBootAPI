package com.homedepot.sa.cb.hamanagement.exception;

/**
 * Created by n84qvs on 12/5/16.
 */
public class NotFoundException extends ApplicationException {

   public NotFoundException(String errorCode,String message) {
        super(errorCode,message);
    }

}
