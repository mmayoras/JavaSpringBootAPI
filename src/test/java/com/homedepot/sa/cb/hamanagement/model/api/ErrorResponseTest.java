package com.homedepot.sa.cb.hamanagement.model.api;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by n84qvs on 3/14/17.
 */
public class ErrorResponseTest {

    @Test
    public void testAddErrorListIsNull(){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.addError(new Error("error.test","Testing is failed"));
        assertNotNull(errorResponse.getError());
        assertTrue(errorResponse.getError().get(0).getErrorCode().equals("error.test"));
        assertTrue(errorResponse.getError().get(0).getErrorMessage().equals("Testing is failed"));
    }

    @Test
    public void testSetErrorList(){
        ErrorResponse errorResponse = new ErrorResponse();
        List<Error> errorList = new ArrayList<>();
        Error error = new Error("error.test","Testing is failed");
        errorList.add(error);
        errorResponse.setError(errorList);
        assertNotNull(errorResponse.getError());
        assertTrue(errorResponse.getError().get(0).getErrorCode().equals("error.test"));
        assertTrue(errorResponse.getError().get(0).getErrorMessage().equals("Testing is failed"));
    }

}
